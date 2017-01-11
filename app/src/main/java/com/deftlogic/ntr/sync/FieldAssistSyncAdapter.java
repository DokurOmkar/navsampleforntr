package com.deftlogic.ntr.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.data.FieldAssistContract;
import com.deftlogic.ntr.data.FieldAssistDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Vector;

/**
 * Created by omkardokur on 1/31/16.
 */
public class FieldAssistSyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String categoriesUrl = "http://myrelirental.com/apex/fieldasyst/services/categories/";
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    private static final int WEATHER_NOTIFICATION_ID = 3004;
    private static final String TAG = "SyncAdapter" ;
    FieldAssistDbHelper fieldAssistDbHelper;

    public FieldAssistSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;
        Log.e("onPerformExtras", extras.toString());


        try {
            URL url = new URL(categoriesUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            int statusCode = urlConnection.getResponseCode();

            // 200 represents HTTP OK
            if (statusCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                parseResult(response.toString());

            }
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }



    }

    private void parseResult(String s) {

        final String DL_ID = "id";
        final String DL_NAME = "name";
        final String DL_DESCRIPTION = "description";
        final String DL_MAKE = "make";
        final String DL_MODEL = "model";
        final String DL_CATEGORY_ID = "categoryid";
        final String DL_CATEGORY_NAME = "categoryname";
        final String DL_IMAGE_LINK = "imagelink";
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsList = jsonObject.getJSONArray("items");
            Log.d(TAG, itemsList.toString());

            Vector<ContentValues> categoryVector = new Vector<ContentValues>(itemsList.length());
            Vector<ContentValues> itemVector = new Vector<ContentValues>(itemsList.length());

            Calendar c = Calendar.getInstance();
            int seconds = c.get(Calendar.SECOND);
            for (int i = 0; i < itemsList.length(); i++) {
                int id;
                String name;
                String description;
                String make;
                String model;
                int categoryId;
                String categoryName;
                String imageLink;
                String lastModified;
                lastModified = String.valueOf(seconds);

                JSONObject resultsListJSONObject =itemsList.getJSONObject(i);
                id = resultsListJSONObject.getInt(DL_ID);
                name = resultsListJSONObject.getString(DL_NAME);
                description = resultsListJSONObject.getString(DL_DESCRIPTION);
                make = resultsListJSONObject.getString(DL_MAKE);
                model = resultsListJSONObject.getString(DL_MODEL);
                categoryId = resultsListJSONObject.getInt(DL_CATEGORY_ID);
                categoryName = resultsListJSONObject.getString(DL_CATEGORY_NAME);
                imageLink = resultsListJSONObject.getString(DL_IMAGE_LINK);

                ContentValues categoryValues = new ContentValues();
                categoryValues.put(FieldAssistContract.CategoriesEntry.COLUMN_CATEGORY_NAME, categoryName);
                categoryValues.put(FieldAssistContract.CategoriesEntry.COLUMN_PARENT,"Accesoories");
                categoryValues.put(FieldAssistContract.CategoriesEntry.COLUMN_LAST_MODIFIED, lastModified);


                ContentValues itemValues = new ContentValues();
                itemValues.put(FieldAssistContract.ItemDetailsEntry._ID,id);
                itemValues.put(FieldAssistContract.ItemDetailsEntry.COLUMN_ITEM_NAME,name);
                itemValues.put(FieldAssistContract.ItemDetailsEntry.COLUMN_MAKE,make);
                itemValues.put(FieldAssistContract.ItemDetailsEntry.COLUMN_MODEL,model);
                itemValues.put(FieldAssistContract.ItemDetailsEntry.COLUMN_CATEGORY_ID,categoryId);
                itemValues.put(FieldAssistContract.ItemDetailsEntry.COLUMN_IMAGE_LINK,imageLink);
                itemValues.put(FieldAssistContract.ItemDetailsEntry.COLUMN_LAST_MODIFIED,lastModified);
                categoryVector.add(categoryValues);
                itemVector.add(itemValues);

            }
            int inserted = 0;
            // add to database
            if ( categoryVector.size() > 0 && itemVector.size() >0 ) {
                ContentValues[] cvArray = new ContentValues[categoryVector.size()];
                ContentValues[] ivArray = new ContentValues[itemVector.size()];
                categoryVector.toArray(cvArray);
                itemVector.toArray(ivArray);
                getContext().getContentResolver().bulkInsert(FieldAssistContract.CategoriesEntry.CONTENT_URI, cvArray);
                getContext().getContentResolver().bulkInsert(FieldAssistContract.ItemDetailsEntry.CONTENT_URI, ivArray);
                // delete old data so we don't build up an endless history
//                getContext().getContentResolver().delete(WeatherContract.WeatherEntry.CONTENT_URI,
//                        WeatherContract.WeatherEntry.COLUMN_DATE + " <= ?",
//                        new String[] {Long.toString(dayTime.setJulianDay(julianStartDay-1))});

                //  notifyWeather();

            }

            Log.d("Log", "Sync Complete. " + categoryVector.size() + itemVector.size()+ " Inserted");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context, Bundle _bundle ) {
        Bundle bundle = new Bundle();
        Log.e("extras", bundle.toString());
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putString("Message", _bundle.get("message").toString());
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        Bundle bundle = new Bundle();
        /*
         * Since we've created an account
         */
        FieldAssistSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context,bundle );
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}

