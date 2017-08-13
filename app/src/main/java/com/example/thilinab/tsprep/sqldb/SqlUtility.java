package com.example.thilinab.tsprep.sqldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.example.thilinab.tsprep.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by thilinab on 10/25/2015.
 */
public class SqlUtility {


    Context context;
    SqlDb mDbHelper = null;
    SQLiteDatabase db = null;

    public SqlUtility(Context context) {
        this.context = context;
        mDbHelper = new SqlDb(context);
        // Gets the data repository in write mode
        db = mDbHelper.getWritableDatabase();
    }

    public long insertClasses(String classes) {
        long newRowId;
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_CONFIGS_CLASSES, classes);
        newRowId = db.insert(
                FeedEntry.TABLE_NAME,
                null,
                values);

        return newRowId;
    }

    public long insertSubjects(String subjects) {
        long newRowId;
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_CONFIGS_SUBJECTS, subjects);
        newRowId = db.insert(
                FeedEntry.TABLE_NAME,
                null,
                values);

        return newRowId;
    }

    public long updateClasses(String classes) {
        long newRowId;
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_CONFIGS_CLASSES, classes);
        newRowId = db.update(FeedEntry.TABLE_NAME, values, null, null);
        return newRowId;
    }

    public long updateSubjects(String subjects) {
        long newRowId;
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_CONFIGS_SUBJECTS, subjects);
        newRowId = db.update(FeedEntry.TABLE_NAME, values, null, null);
        return newRowId;
    }

    public long insertValues(int year, int month, int day,
                             String mSubject, String mClass, String mFrom, String mTo) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_YEAR, Integer.toString(year));
        values.put(FeedEntry.COLUMN_NAME_MONTH, Integer.toString(month));
        values.put(FeedEntry.COLUMN_NAME_DATE, Integer.toString(day));
        values.put(FeedEntry.COLUMN_NAME_CLASS, mClass);
        values.put(FeedEntry.COLUMN_NAME_SUBJECT, mSubject);
        values.put(FeedEntry.COLUMN_NAME_FROMTIME, mFrom);
        values.put(FeedEntry.COLUMN_NAME_TOTIME, mTo);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedEntry.TABLE_NAME,
                null,
                values);

        return newRowId;
    }

    public String getWholeDb() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_YEAR,
                FeedEntry.COLUMN_NAME_MONTH,
                FeedEntry.COLUMN_NAME_DATE,
                FeedEntry.COLUMN_NAME_CLASS,
                FeedEntry.COLUMN_NAME_SUBJECT,
                FeedEntry.COLUMN_NAME_FROMTIME,
                FeedEntry.COLUMN_NAME_TOTIME,
        };

        Cursor c = db.query(
                FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        c.moveToFirst();
        long itemId = c.getLong(
                c.getColumnIndexOrThrow(FeedEntry._ID)
        );

        String testString = null;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                testString += c.getString(0) + "-" + c.getString(1) + "-" +
                        c.getString(2) + "-" + c.getString(3) + "-" + c.getString(4) +
                        "-" + c.getString(5) + "-" + c.getString(6) + "-" + c.getString(7) +
                        "\n";
            } while (c.moveToNext());
        }
        return testString;
    }

    public void deleteTable() {
        db.delete(FeedEntry.TABLE_NAME, null, null);
    }

    public void dropTable() {
        db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);
    }

    public String filterByMonth(int month) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedEntry._ID,
                FeedEntry.COLUMN_NAME_YEAR,
                FeedEntry.COLUMN_NAME_MONTH,
                FeedEntry.COLUMN_NAME_DATE,
                FeedEntry.COLUMN_NAME_CLASS,
                FeedEntry.COLUMN_NAME_SUBJECT,
                FeedEntry.COLUMN_NAME_FROMTIME,
                FeedEntry.COLUMN_NAME_TOTIME,
        };

        Cursor c = db.query(
                FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                FeedEntry.COLUMN_NAME_MONTH + " = ?",                                // The columns for the WHERE clause
                new String[]{
                        Integer.toString(month),
                },                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        String testString = "";
        int totalTime = 0;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int duration = calculateClassDuration(c.getString(6), c.getString(7));
                testString +=
                        "Date : " + c.getString(1) + "-" +
                                c.getString(2) + "-" + c.getString(3) + "\n" +
                                "Class : " + c.getString(5) + "\n" +
                                "Subject : " + c.getString(4) + "\n" +
                                "Start Time : " + c.getString(6) + "\n" +
                                "End Time : " + c.getString(7) + "\n" +
                                "Duration : " + duration + " Hours" + "\n================\n\n";
                totalTime += duration;
            } while (c.moveToNext());
        }
        testString += "\nTotal hours : " + totalTime + " hours";
        return testString;
    }

    int calculateClassDuration(String fromTime, String toTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "h:mm a");
        Date from = null;
        Date to = null;
        try {
            from = dateFormat.parse(fromTime);
            to = dateFormat.parse(toTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        int duration = (int)
                ((to.getTime() / (1000 * 60 * 60)) - (from.getTime() / (1000 * 60 * 60)));
        return duration;
    }

    public String getConfigSubjects() {
        String[] projection = {
                FeedEntry.COLUMN_NAME_CONFIGS_SUBJECTS
        };

        Cursor c = db.query(
                FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        String retString = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            retString = c.getString(0);
        }
        return retString;
    }

    public String getConfigClasses() {
        String[] projection = {
                FeedEntry.COLUMN_NAME_CONFIGS_CLASSES
        };

        Cursor c = db.query(
                FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        String retString = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            retString = c.getString(0);
        }
        return retString;
    }

    public void deleteClasses() {
        ContentValues cv = new ContentValues();
        cv.put(FeedEntry.COLUMN_NAME_CONFIGS_CLASSES, "");
        db.update(FeedEntry.TABLE_NAME, cv, null, null);
    }
}
