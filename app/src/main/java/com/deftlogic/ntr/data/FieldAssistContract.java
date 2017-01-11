package com.deftlogic.ntr.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by omkardokur on 1/31/16.
 */
public class FieldAssistContract {


    public static final String CONTENT_AUTHORITY = "com.deftlogic.ntr";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CATEGORY = "categories";
    public static final String PATH_ITEM = "itemDetails";
    public static final String PATH_PRICE = "prices";
    public static final String PATH_VIEW = "catalog";
    public static final String PATH_ITEM_VIEW = "itemDetailsCatalog";
    public static final String PATH_CART = "cart";
    public static final String PATH_FAVORITES = "favorites";

    public static final class CategoriesEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORY;

        // Table name
        public static final String TABLE_NAME = "categories";

        public static final String COLUMN_CATEGORY_ID = "ID";

        public static final String COLUMN_CATEGORY_NAME = "NAME";

        public static final String COLUMN_PARENT = "PARENT_ID";

        public static final String COLUMN_LAST_MODIFIED = "LAST_MODIFIED";

        public static Uri buildCategoryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ItemDetailsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEM).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;

        // Table name
        public static final String TABLE_NAME = "itemDetails";

        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_ITEM_NAME = "NAME";

        public static final String COLUMN_MAKE = "MAKE";

        public static final String COLUMN_MODEL = "MODEL";

        public static final String COLUMN_CATEGORY_ID = "CATEGORY_ID";

        public static final String COLUMN_IMAGE_LINK = "IMAGELINK";

        public static final String COLUMN_LAST_MODIFIED = "LAST_MODIFIED";

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class PricesEntry implements BaseColumns{


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PRICE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRICE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRICE;

        // Table name
        public static final String TABLE_NAME = "itemDetails";

        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_ITEM_ID = "ITEM_ID";

        public static final String COLUMN_PRICE = "PRICE";

        public static final String COLUMN_PRICE_TYPE = "PRICE_TYPE";

        public static final String COLUMN_LAST_MODIFIED = "LAST_MODIFIED";

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


    public static final class FavoritesEntry implements BaseColumns{


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;

        // Table name
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_ITEM_ID = "ITEM_ID";

        public static final String COLUMN_ADDED_ON = "ADDED_ON";

        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class CartEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CART).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CART;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CART;

        // Table name
        public static final String TABLE_NAME = "cart";

        public static final String COLUMN__ID = "ID";

        public static final String COLUMN_ITEM_ID = "ITEM_ID";

        public static final String COLUMN_QUANTITY = "QUANTITY";

        public static final String COLUMN_ADDED_ON = "ADDED_ON";

        public static final String COLUMN_LAST_MODIFIED = "LAST_MODIFIED";

        public static Uri buildCategoryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ViewDetailsEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIEW).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VIEW;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VIEW;

        public static final String TABLE_NAME = "catalog";

        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_NAME = "NAME";

        public static final String COLUMN_COUNT = "child_count";

    }

    public static final class ItemViewDetailsEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEM_VIEW).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM_VIEW;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEM_VIEW;

        public static final String TABLE_NAME = "itemDetailsCatalog";

        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_NAME = "NAME";

        public static final String COLUMN_CATEGORY_ID = "CATEGORYID";

        public static final String COLUMN_IMAGE_LINK = "IMAGELINK";

        public static final String COLUMN_MAKE = "MAKE";

        public static final String COLUMN_MODEL = "MODEL";

        public static final String COLUMN_PRICE_4_HOURS = "price_4_hours";

        public static final String COLUMN_PRICE_1_DAY = "price_1_day";

        public static final String COLUMN_PRICE_1_WEEK = "price_1_week";

        public static final String COLUMN_PRICE_1_MONTH = "price_1_month";

        public static final String COLUMN_ITEM_CART_COUNT = "itemCartCount";

        public static final String COLUMN_FAVORITES_ID = "favoritesId";

    }
}

