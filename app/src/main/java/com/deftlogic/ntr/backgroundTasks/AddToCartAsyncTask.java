package com.deftlogic.ntr.backgroundTasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.deftlogic.ntr.data.FieldAssistContract;
import com.deftlogic.ntr.data.FieldAssistDbHelper;

import java.util.ArrayList;

/**
 * Created by omkardokur on 2/4/16.
 */
public class AddToCartAsyncTask extends AsyncTask<ArrayList<Integer>,Void,Void> {
    Context mContext;
    int itemId;
    int itemQuantity;
    FieldAssistDbHelper fieldAssistDbHelper;
    SQLiteDatabase db;
    ArrayList<Integer> result;

    public AddToCartAsyncTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        fieldAssistDbHelper = new FieldAssistDbHelper(mContext);
        db = fieldAssistDbHelper.getWritableDatabase();
    }


    @Override
    protected Void doInBackground(ArrayList<Integer>... params) {
        result= new ArrayList<Integer>();
        result = params[0];
        itemId = result.get(0);
        itemQuantity = result.get(1);

        ContentValues contentValues = new ContentValues();
        contentValues.put(FieldAssistContract.CartEntry.COLUMN_ITEM_ID, itemId);
        contentValues.put(FieldAssistContract.CartEntry.COLUMN_QUANTITY,itemQuantity);
        mContext.getContentResolver().insert(FieldAssistContract.CartEntry.CONTENT_URI, contentValues);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(mContext, "Successfully Added", Toast.LENGTH_SHORT).show();
        db.close();
    }
}
