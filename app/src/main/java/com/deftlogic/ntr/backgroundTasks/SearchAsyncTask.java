package com.deftlogic.ntr.backgroundTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.connections.HttpPost;
import com.deftlogic.ntr.data.FieldAssistContract;
import com.deftlogic.ntr.data.FieldAssistDbHelper;
import com.deftlogic.ntr.interfaces.ResultsCallback;
import com.deftlogic.ntr.models.CategoryItem;
import com.deftlogic.ntr.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by omkardokur on 1/15/16.
 */
public class SearchAsyncTask extends AsyncTask<Void, Void, List<CategoryItem> > {
    private ProgressDialog progressDialog;
    private Context mContext;
    private ResultsCallback resultCallback;
    private List<CategoryItem> categoryItemsList ;
    Cursor mCursor;
    FieldAssistDbHelper fieldAssistDbHelper;
    SQLiteDatabase db;
    public SearchAsyncTask(ResultsCallback resultCallback,Context context) {
        this.mContext = context;
        this.resultCallback = resultCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        fieldAssistDbHelper = new FieldAssistDbHelper(mContext);
        db = fieldAssistDbHelper.getWritableDatabase();
    }
    @Override
    protected List<CategoryItem> doInBackground(Void... params) {
        String Url = "http://myrelirental.com/apex/fieldasyst/services/isInventoryModifiedFromLastLoad/";
        String[] paramName = {"clientDate"};
        String[] paramValue = {"2016-02-02 05:59:18"};
        HttpPost httpPostNew = new HttpPost();
        String[] headerName = {CommonUtils.RR_API_KEY_NAME};
        String[] headerValue = {CommonUtils.RR_API_KEY_VALUE};
        try {
           // String value = httpPostNew.httpPost(Url,paramName,paramValue,headerName,headerValue);
           // Log.e("value", value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        parseResult();
        return categoryItemsList;
    }

    @Override
    protected void onPostExecute(List<CategoryItem> categoryItemsList) {
        super.onPostExecute(categoryItemsList);
        //progressDialog.dismiss();
        if(categoryItemsList!=null)
        { resultCallback.onSuccess(categoryItemsList);}
        else
        {
            resultCallback.onFailure(mContext.getResources().getString(R.string.failure_to_load));
        }
        db.close();
    }

    private List<CategoryItem> parseResult() {
        categoryItemsList = new ArrayList<>();
        String[] mProjection =
                {
                        FieldAssistContract.ViewDetailsEntry.COLUMN_ID,
                        FieldAssistContract.ViewDetailsEntry.COLUMN_NAME,
                        FieldAssistContract.ViewDetailsEntry.COLUMN_COUNT
                };

        // Defines a string to contain the selection clause
        String mSelectionClause = null;

        // Initializes an array to contain selection arguments
        String[] mSelectionArgs = null;
        mCursor = mContext.getContentResolver().query(
                FieldAssistContract.ViewDetailsEntry.CONTENT_URI,  // The content URI of the words table
                mProjection,                       // The columns to return for each row
                mSelectionClause,                   // Either null, or the word the user entered
                mSelectionArgs,                    // Either empty, or the string the user entered
                null);
        if ( mCursor.moveToFirst()) {

            do {
                String title = mCursor.getString(mCursor.getColumnIndex(FieldAssistContract.ViewDetailsEntry.COLUMN_NAME));
                int count = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ViewDetailsEntry.COLUMN_COUNT));
                int id = mCursor.getInt(mCursor.getColumnIndex(FieldAssistContract.ViewDetailsEntry.COLUMN_ID));
                CategoryItem item = new CategoryItem();
                item.setCategoryId(id);
                item.setCategoryName(title);
                item.setCategoryCount(String.valueOf(count));
                categoryItemsList.add(item);


            } while (mCursor.moveToNext());

        }
        return categoryItemsList;
    }
}



