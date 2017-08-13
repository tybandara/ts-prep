package com.example.thilinab.tsprep.sqldb;

import android.provider.BaseColumns;

/**
 * Created by thilinab on 10/25/2015.
 */
/* Inner class that defines the table contents */
public abstract class FeedEntry implements BaseColumns {
    public static final String TABLE_NAME = "TSPREP";
    public static final String COLUMN_NAME_YEAR = "Year";
    public static final String COLUMN_NAME_MONTH = "Month";
    public static final String COLUMN_NAME_DATE = "Date";
    public static final String COLUMN_NAME_SUBJECT = "Subject";
    public static final String COLUMN_NAME_CLASS = "Class";
    public static final String COLUMN_NAME_FROMTIME = "FromTime";
    public static final String COLUMN_NAME_TOTIME = "ToTime";
    public static final String COLUMN_NAME_CONFIGS_CLASSES = "ConfigClasses";
    public static final String COLUMN_NAME_CONFIGS_SUBJECTS = "ConfigSubjects";
}