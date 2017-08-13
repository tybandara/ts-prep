package com.example.thilinab.tsprep.sqldb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by thilinab on 10/25/2015.
 */
public class SqlDb extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    /* string to create the table. If columns are added or removed, it should reflect here */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_YEAR + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_MONTH + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_SUBJECT + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_CLASS + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_FROMTIME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_TOTIME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_CONFIGS_CLASSES + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_CONFIGS_SUBJECTS + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TSPrep.db";

    public SqlDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}


