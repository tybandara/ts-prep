package com.example.thilinab.tsprep;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.thilinab.tsprep.sqldb.SqlUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TSPrep extends AppCompatActivity {
    // This holds general details of a time record
    DetailsPojo detailsPojo = new DetailsPojo();
    // List of classes
    List<String> classList = new ArrayList<>();
    // List of subjects
    List<String> subjectList = new ArrayList<>();

    static final String LECTURED_YEAR = "lecturedYear";
    static final String LECTURED_MONTH = "lecturedMonth";
    static final String LECTURED_DATE = "lecturedDate";
    static final String LECTURED_FROM_HOUR = "lecturedFromHour";
    static final String LECTURED_FROM_MINUTE = "lecturedFromMinute";
    static final String LECTURED_TO_HOUR = "lecturedToHour";
    static final String LECTURED_TO_MINUTE = "lecturedToMinute";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsprep);

        // Restoring values from the previously destroyed instance
        if (savedInstanceState != null) {
            detailsPojo.setYear(savedInstanceState.getInt(LECTURED_YEAR));
            detailsPojo.setMonth(savedInstanceState.getInt(LECTURED_MONTH));
            detailsPojo.setDay(savedInstanceState.getInt(LECTURED_DATE));
            detailsPojo.setFromHour(savedInstanceState.getInt(LECTURED_FROM_HOUR));
            detailsPojo.setFromMinute(savedInstanceState.getInt(LECTURED_FROM_MINUTE));
            detailsPojo.setToHour(savedInstanceState.getInt(LECTURED_TO_HOUR));
            detailsPojo.setToMinute(savedInstanceState.getInt(LECTURED_TO_MINUTE));
        }
        else{
            // This is a fresh start. Set the current date as the initial value
            final Calendar c = Calendar.getInstance();
            detailsPojo.setYear(c.get(Calendar.YEAR));
            detailsPojo.setMonth(c.get(Calendar.MONTH));
            detailsPojo.setDay(c.get(Calendar.DAY_OF_MONTH));
            detailsPojo.setFromHour(c.get(Calendar.HOUR_OF_DAY));
            detailsPojo.setFromMinute(c.get(Calendar.MINUTE));
            detailsPojo.setToHour(c.get(Calendar.HOUR_OF_DAY));
            detailsPojo.setToMinute(c.get(Calendar.MINUTE));
        }

        // Set class details
        fillClasses();
        // Set subject details
        fillSubjects();
        // Configure the current view
        SetView();
    }

    // Fills the subject list
    private void fillSubjects() {
        subjectList.add("Embedded Systems");
        subjectList.add("Introduction to Smart Systems");
    }

    // Fills the classes list
    private void fillClasses() {
        classList.add("PGPN");
        classList.add("DEGREE (B.Sc.)");
    }

    private void SetView() {
        // Save current record button
        Button saveButton = (Button) findViewById(R.id.Save_Btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveBtn();
            }
        });

        // Export the records button
        Button exportButton = (Button) findViewById(R.id.Export_Btn);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExportButton();
            }
        });

        // Button for selecting the date of the class
        Button dateButton = (Button) findViewById(R.id.Date_Btn);
        dateButton.setText("Date : " + getCurrentDate("yyyy-MMM-dd"));
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClassDateBtn();
            }
        });

        // Button for selecting the class start time
        Button fromTimeButton = (Button) findViewById(R.id.From_Btn);
        fromTimeButton.setText("From : " + getFromTime("h:mm a"));
        fromTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromTimeSelectionDialog();
            }
        });

        // Button for selecting the class end time
        Button toTimeButton = (Button) findViewById(R.id.Totime_Btn);
        toTimeButton.setText("To : " + getToTime("h:mm a"));
        toTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToTimeSelectionDialog();            }
        });

        // Populate class spinner
        Spinner classSpinner = (Spinner) findViewById(R.id.Class_Spinner);
        // Application of the Array to the Spinner
        ArrayAdapter<String> classSpinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, classList);
        // The drop down view
        classSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classSpinnerArrayAdapter);

        // Populate subject spinner
        Spinner subjectSpinner = (Spinner) findViewById(R.id.Subject_Spinner);
        // Adding the array of string
        ArrayAdapter<String> subjectSpinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectList);
        // Add the dropdown view
        subjectSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectSpinnerArrayAdapter);
    }

    /**
     * Currently entered data would be entered in the sqlite database
     */
    private void handleSaveBtn() {
        Spinner classSpinner=(Spinner) findViewById(R.id.Class_Spinner);
        Spinner subjectSpinner=(Spinner) findViewById(R.id.Subject_Spinner);

        SqlUtility sqlUtility = new SqlUtility(this);

        /* Add the current information to the sqlite db. Return value should be greater than 1
        if the call is succeeded */
        long ret = sqlUtility.insertValues(detailsPojo.getYear(), detailsPojo.getMonth(), detailsPojo.getDay(),
                classSpinner.getSelectedItem().toString(),
                subjectSpinner.getSelectedItem().toString(),
                getFromTime("h:mm a"), getToTime("h:mm a")
                );

        Context context = getApplicationContext();
        CharSequence text;

        if(ret > 0 )
            text = "Added information successfully";
        else
            text = "Something wrong!!!";


        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Open the new view to handle the exporting of filtered records
     */
    private void handleExportButton() {
        Intent intent = new Intent(this, ExportActivity.class);
        startActivity(intent);
    }

    /**
     * Handle class end time selection using a dialog
     */
    private void showToTimeSelectionDialog() {
        DialogFragment newFragment = new ToTimePicker(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                detailsPojo.setToHour(hourOfDay);
                detailsPojo.setToMinute(minute);
                Button toButton = (Button) findViewById(R.id.Totime_Btn);
                toButton.setText("To : " + getToTime("h:mm a"));
            }
        };
        newFragment.show(getFragmentManager(), "To Time Picker");
    }

    /**
     * Handle class start time selection using a dialog
     */
    private void showFromTimeSelectionDialog() {
        DialogFragment newFragment = new FromTimePicker(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                detailsPojo.setFromHour(hourOfDay);
                detailsPojo.setFromMinute(minute);
                Button fromButton = (Button) findViewById(R.id.From_Btn);
                fromButton.setText("From : " + getFromTime("h:mm a"));
            }
        };
        newFragment.show(getFragmentManager(), "From Time Picker");
    }

    /**
     * Handle class date selection using a dialog
     */
    private void handleClassDateBtn() {
        DialogFragment newFragment = new DatePicker(){
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                detailsPojo.setYear(year);
                detailsPojo.setMonth(monthOfYear);
                detailsPojo.setDay(dayOfMonth);
                Button dateButton = (Button) findViewById(R.id.Date_Btn);
                dateButton.setText("Date : " + getCurrentDate("yyyy-MMM-dd"));
            }
        };
        newFragment.show(getFragmentManager(), "Date Picker");
    }

    /**
     * Simple function to get the class start time as a string
     * @param format expected string format
     * @return string class start time
     */
    private String getFromTime(String format) {
        final Calendar c = Calendar.getInstance();
        c.set(detailsPojo.getYear(), detailsPojo.getMonth(), detailsPojo.getDay(),
                detailsPojo.getFromHour(), detailsPojo.getFromMinute());
        SimpleDateFormat fromTime = new SimpleDateFormat(format);
        String retString = fromTime.format(c.getTime());
        return retString;
    }

    /**
     * function to get the class stop time as a string
     * @param format expected string format
     * @return string class end time
     */
    private String getToTime(String format) {
        final Calendar c = Calendar.getInstance();
        c.set(detailsPojo.getYear(), detailsPojo.getMonth(), detailsPojo.getDay(),
                detailsPojo.getToHour(), detailsPojo.getToMinute());
        SimpleDateFormat fromTime = new SimpleDateFormat(format);
        String retString = fromTime.format(c.getTime());
        return retString;
    }

    /**
     * function to get the class date as a string
     * @param format expected string format
     * @return string class date
     */
    private String getCurrentDate(String format) {
        final Calendar c = Calendar.getInstance();
        c.set(detailsPojo.getYear(), detailsPojo.getMonth(), detailsPojo.getDay());
        SimpleDateFormat date = new SimpleDateFormat(format);
        String retString = date.format(c.getTime());
        return retString;
    }

    /**
     * handling the state when the app is being paused
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the users current information
        outState.putInt(LECTURED_YEAR, detailsPojo.getYear());
        outState.putInt(LECTURED_MONTH, detailsPojo.getMonth());
        outState.putInt(LECTURED_DATE, detailsPojo.getDay());
        outState.putInt(LECTURED_FROM_HOUR, detailsPojo.getFromHour());
        outState.putInt(LECTURED_FROM_MINUTE, detailsPojo.getFromMinute());
        outState.putInt(LECTURED_TO_HOUR, detailsPojo.getToHour());
        outState.putInt(LECTURED_TO_MINUTE, detailsPojo.getToMinute());

        super.onSaveInstanceState(outState);
    }
}
