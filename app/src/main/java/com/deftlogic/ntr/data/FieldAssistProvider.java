package com.deftlogic.ntr.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by omkardokur on 1/31/16.
 */
public class FieldAssistProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher FAUriMatcher = buildUriMatcher();

    static final int CATEGORY = 100;
    static final int ITEM = 101;
    static final int VIEW = 103;
    static final int ITEM_VIEW = 105;
    static final int CART = 111;
    static final int FAVORITES = 115;

    SQLiteDatabase db;
    FieldAssistDbHelper fieldAssistDbHelper;

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FieldAssistContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, FieldAssistContract.PATH_CATEGORY, CATEGORY);
        // matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*", WEATHER_WITH_LOCATION);
        //  matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/#", WEATHER_WITH_LOCATION_AND_DATE);

        matcher.addURI(authority, FieldAssistContract.PATH_ITEM, ITEM);
        matcher.addURI(authority,FieldAssistContract.PATH_VIEW,VIEW);
        matcher.addURI(authority,FieldAssistContract.PATH_ITEM_VIEW, ITEM_VIEW);
        matcher.addURI(authority,FieldAssistContract.PATH_CART,CART);
        matcher.addURI(authority, FieldAssistContract.PATH_FAVORITES,FAVORITES);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        fieldAssistDbHelper = new FieldAssistDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        db = fieldAssistDbHelper.getReadableDatabase();
        switch (FAUriMatcher.match(uri)) {
            case CATEGORY: {
                cursor = db.query(FieldAssistContract.CategoriesEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

                break;
            }
            case ITEM: {
                cursor = db.query(FieldAssistContract.ItemDetailsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            case VIEW:{
                cursor = db.query(FieldAssistContract.ViewDetailsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;}
            case ITEM_VIEW:{
                cursor = db.query(FieldAssistContract.ItemViewDetailsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = FAUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case CATEGORY:
                return FieldAssistContract.CategoriesEntry.CONTENT_TYPE;

            case ITEM:
                return FieldAssistContract.ItemDetailsEntry.CONTENT_TYPE;
            case VIEW:
                return FieldAssistContract.ViewDetailsEntry.CONTENT_TYPE;
            case ITEM_VIEW:
                return FieldAssistContract.ItemViewDetailsEntry.CONTENT_TYPE;
            case CART:
                return FieldAssistContract.CartEntry.CONTENT_TYPE;
            case FAVORITES:
                return FieldAssistContract.FavoritesEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = fieldAssistDbHelper.getWritableDatabase();
        final int match = FAUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case CATEGORY: {
                db.insert(FieldAssistContract.CategoriesEntry.TABLE_NAME,null,values);
                break;
            }
            case ITEM: {
                db.insert(FieldAssistContract.ItemDetailsEntry.TABLE_NAME,null,values);
                break;
            }
            case CART:{
                db.insert(FieldAssistContract.CartEntry.TABLE_NAME,null,values);
                break;
            }
            case FAVORITES:{
                db.insert(FieldAssistContract.FavoritesEntry.TABLE_NAME,null,values);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        fieldAssistDbHelper.close();
        getContext().getContentResolver().notifyChange(uri,null);
        return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = fieldAssistDbHelper.getWritableDatabase();
        final int match = FAUriMatcher.match(uri);
        switch (match) {
            case CATEGORY:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        //normalizeDate(value);
                        long _id = db.insert(FieldAssistContract.CategoriesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case ITEM:
                db.beginTransaction();
                int returnCountI = 0;
                try {
                    for (ContentValues value : values) {
                        //normalizeDate(value);
                        long _id = db.insert(FieldAssistContract.ItemDetailsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCountI++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCountI;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = fieldAssistDbHelper.getWritableDatabase();
        final int match = FAUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case CART:
                rowsDeleted = db.delete(
                        FieldAssistContract.CartEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVORITES:
                rowsDeleted = db.delete(
                        FieldAssistContract.FavoritesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = fieldAssistDbHelper.getWritableDatabase();
        final int match = FAUriMatcher.match(uri);
        int count = 0;

        switch (match){
            case CART:
                count = db.update(FieldAssistContract.CartEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }
}