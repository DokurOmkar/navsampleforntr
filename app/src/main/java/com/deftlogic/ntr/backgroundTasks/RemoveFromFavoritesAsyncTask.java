package com.deftlogic.ntr.backgroundTasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.deftlogic.ntr.data.FieldAssistContract;
import com.deftlogic.ntr.data.FieldAssistDbHelper;

import java.util.ArrayList;

/**
 * Created by omkardokur on 2/5/16.
 */
public class RemoveFromFavoritesAsyncTask extends AsyncTask<Void,Void,Void> {
    Context mContext;
    int itemId;
    FieldAssistDbHelper fieldAssistDbHelper;
    SQLiteDatabase db;


    public RemoveFromFavoritesAsyncTask(Context mContext, int itemId) {
        this.mContext = mContext;
        this.itemId = itemId;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        fieldAssistDbHelper = new FieldAssistDbHelper(mContext);
        db = fieldAssistDbHelper.getWritableDatabase();
    }

    @Override
    protected Void doInBackground(Void... params) {
        String mSelection = FieldAssistContract.FavoritesEntry.COLUMN_ITEM_ID + " = ?";
        String[] mSelectionArgs = {""};
        mSelectionArgs[0] = String.valueOf(itemId );
        Log.e("itemid in ATFAT", String.valueOf(itemId));
        mContext.getContentResolver().delete(FieldAssistContract.FavoritesEntry.CONTENT_URI, mSelection, mSelectionArgs);
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Toast.makeText(mContext, "Successfully Unfavoured", Toast.LENGTH_SHORT).show();
        db.close();
    }
}
