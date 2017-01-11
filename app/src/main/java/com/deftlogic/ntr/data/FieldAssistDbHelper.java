package com.deftlogic.ntr.data;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by omkardokur on 1/31/16.
 */
public class FieldAssistDbHelper extends SQLiteAssetHelper {
    public static final String DATABASE_NAME = "Sample6.db";
    private static final int DATABASE_VERSION = 1;

    public FieldAssistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


}
