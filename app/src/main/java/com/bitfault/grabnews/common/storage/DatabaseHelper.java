package com.bitfault.grabnews.common.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.bitfault.grabnews.common.core.GrabNewsApplication;

/**
 * Singleton Helper class to be used for database operations
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "grab_news_db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper sInstance;

    public static DatabaseHelper getInstance() {
        if (GrabNewsApplication.mContext == null) {
            throw new IllegalStateException("Application not started");
        }
        if (sInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (sInstance == null) {
                    sInstance = new DatabaseHelper(GrabNewsApplication.mContext);
                }
            }
        }
        return sInstance;
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HttpResponseCacheTable.CREATE_SQL);
        db.execSQL(ImageCacheTable.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(tableName, null, contentValues);
    }

    /*
     * Free up cursor after use using cursor.close()
     *
     * */
    public Cursor readData(String tableName, String[] projection, String selection, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(tableName, projection, selection, selectionArgs, null, null, null);
    }

    public void deleteData(String tableName, String selection, String[] selectionArgs) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(tableName, selection, selectionArgs);
    }
}
