package com.deftlogic.ntr.backgroundTasks;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.data.FieldAssistContract;
import com.deftlogic.ntr.data.FieldAssistDbHelper;
import com.deftlogic.ntr.interfaces.ResultsCallback;
import com.deftlogic.ntr.models.ItemDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omkardokur on 2/2/16.
 */
public class SearchItemAsyncTask extends AsyncTask<Void, Void, List<ItemDetail> > {
    private Context mContext;
    private ResultsCallback resultCallback;
    private List<ItemDetail> itemDetailList ;
    Cursor mCursor;
    FieldAssistDbHelper fieldAssistDbHelper;
    SQLiteDatabase db;
    int mCategoryId;

    public SearchItemAsyncTask(ResultsCallback resultCallback,Context context, int CategoryId) {
        this.mContext = context;
        this.resultCallback = resultCallback;
        this.mCategoryId = CategoryId;
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
        { resultCallback.onItemSuccess(itemDetailList);}
        else
        {
            resultCallback.onFailure(mContext.getResources().getString(R.string.failure_to_load));
        }
        db.close();
    }


    private List<ItemDetail> parseResult() {
        itemDetailList = new ArrayList<>();
        String[] mProjection =
                {
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_ID,
                        FieldAssistContract.ItemViewDetailsEntry.COLUMN_NAME,
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
        String mSelectionClause = FieldAssistContract.ItemViewDetailsEntry.COLUMN_CATEGORY_ID + " = ?";
        String[] mSelectionArgs = {""};
        // Initializes an array to contain selection arguments
        mSelectionArgs[0] = String.valueOf(mCategoryId );
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
               // int itemCategoryId = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ItemViewDetailsEntry.COLUMN_CATEGORY_ID));
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
                //item.setItemCategoryId(itemCategoryId);
                item.setIteImageLink(itemImageLink);
                item.setItemMake(make);
                item.setItemModel(model);
                item.setItemPrice_4_Hours(price_4_hours);
                item.setItemPrice_1_Day(price_1_day);
                item.setItemPrice_1_Week(price_1_week);
                item.setItemPrice_1_Month(price_1_month);
                item.setItemCartCount(cart_count);
                item.setItemFavoritesId(favorites_id);
               // item.setItemCategoryId(mCategoryId);
                itemDetailList.add(item);


            } while (mCursor.moveToNext());

        }
        return itemDetailList;
    }

}
