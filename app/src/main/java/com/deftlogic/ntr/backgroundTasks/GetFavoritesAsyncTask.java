package com.deftlogic.ntr.backgroundTasks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.data.FieldAssistContract;
import com.deftlogic.ntr.data.FieldAssistDbHelper;
import com.deftlogic.ntr.interfaces.CartResultsCallback;
import com.deftlogic.ntr.interfaces.FavoriteResultsCallback;
import com.deftlogic.ntr.models.ItemDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omkardokur on 2/4/16.
 */
public class GetFavoritesAsyncTask extends AsyncTask<Void, Void, List<ItemDetail> > {
    private Context mContext;
    private FavoriteResultsCallback favoriteResultsCallback;
    private List<ItemDetail> itemDetailList ;
    Cursor mCursor;
    FieldAssistDbHelper fieldAssistDbHelper;
    SQLiteDatabase db;

    public GetFavoritesAsyncTask(FavoriteResultsCallback favoriteResultsCallback,Context context) {
        this.mContext = context;
        this.favoriteResultsCallback = favoriteResultsCallback;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        fieldAssistDbHelper = new FieldAssistDbHelper(mContext);
        db = fieldAssistDbHelper.getWritableDatabase();
    }

    @Override
    protected List<ItemDetail> doInBackground(Void... params) {
        parseResult();
        return itemDetailList;
    }

    @Override
    protected void onPostExecute(List<ItemDetail> itemDetailList) {
        super.onPostExecute(itemDetailList);
        //progressDialog.dismiss();
        if(itemDetailList!=null)
        {
            favoriteResultsCallback.onItemSuccess(itemDetailList);}
        else
        {
            favoriteResultsCallback.onFailure(mContext.getResources().getString(R.string.failure_to_load));
        }
        db.close();

    }

    private List<ItemDetail> parseResult() {
        itemDetailList = new ArrayList<>();
        String[] mProjection =
                {
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_ID,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_NAME,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_CATEGORY_ID,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_IMAGE_LINK,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_MAKE,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_MODEL,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_PRICE_4_HOURS,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_PRICE_1_DAY,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_PRICE_1_WEEK,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_PRICE_1_MONTH,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_ITEM_CART_COUNT,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_FAVORITES_ID

                };

        // Defines a string to contain the selection clause
        String mSelectionClause = FieldAssistContract.ItemViewDetailsEntry.COLUMN_FAVORITES_ID + " > ?";
        String[] mSelectionArgs = {"0"};
        // Initializes an array to contain selection arguments

        mCursor = mContext.getContentResolver().query(
                FieldAssistContract.ItemViewDetailsEntry.CONTENT_URI,  // The content URI of the words table
                mProjection,                       // The columns to return for each row
                mSelectionClause,                   // Either null, or the word the user entered
                mSelectionArgs,                    // Either empty, or the string the user entered
                null);
        if ( mCursor.moveToFirst()) {

            do {
                int itemId = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_ID));
                String itemName = mCursor.getString(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_NAME));
                int itemCategoryId = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_CATEGORY_ID));
                String itemImageLink = mCursor.getString(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_IMAGE_LINK));
                String make = mCursor.getString(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_MAKE));
                String model = mCursor.getString(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_MODEL));
                int price_4_hours = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_PRICE_4_HOURS));
                int price_1_day = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_PRICE_1_DAY));
                int price_1_week = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_PRICE_1_WEEK));
                int price_1_month = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_PRICE_1_MONTH));
                int cart_count = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_ITEM_CART_COUNT));
                int favorites_id = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_FAVORITES_ID));
                ItemDetail item = new ItemDetail();
                //Log.d("ite", String.valueOf(itemCategoryId));
                item.setItemId(itemId);
                item.setItemName(itemName);
                item.setItemCategoryId(itemCategoryId);
                item.setIteImageLink(itemImageLink);
                item.setItemMake(make);
                item.setItemModel(model);
                item.setItemPrice_4_Hours(price_4_hours);
                item.setItemPrice_1_Day(price_1_day);
                item.setItemPrice_1_Week(price_1_week);
                item.setItemPrice_1_Month(price_1_month);
                item.setItemCartCount(cart_count);
                item.setItemFavoritesId(favorites_id);
                //item.setItemCategoryId(mCategoryId);
                itemDetailList.add(item);
             //   Log.d("GFAT", "Hello");
                Log.d("FF", String.valueOf(itemImageLink));

            } while (mCursor.moveToNext());

        }
        return itemDetailList;
    }

}
