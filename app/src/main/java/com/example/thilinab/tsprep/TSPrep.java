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

    DetailsPojo detailsPojo = new DetailsPojo();
    List<String> classList = new ArrayList<>();
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
        
        SetView();

    }

    private void fillSubjects() {
        subjectList.add("Embedded Systems");
        subjectList.add("Introduction to Smart Systems");
    }

    private void fillClasses() {
        classList.add("PGPN");
        classList.add("DEGREE (B.Sc.)");
    }

    private void SetView() {
        Button saveButton = (Button) findViewById(R.id.Save_Btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TSPrep.this.handleESaveButton((Button) v);
            }
        });


        Button exportButton = (Button) findViewById(R.id.Export_Btn);

        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TSPrep.this.handleExportButton((Button) v);
            }
        });

        Button dateButton = (Button) findViewById(R.id.Date_Btn);
        dateButton.setText("Date : " + getCurrentDate("yyyy-MMM-dd"));

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        Button fromTimeButton = (Button) findViewById(R.id.From_Btn);
        fromTimeButton.setText("From : " + getFromTime("h:mm a"));

        fromTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromTimeSelectionDialog();
            }
        });

        Button toTimeButton = (Button) findViewById(R.id.Totime_Btn);
        toTimeButton.setText("To : " + getToTime("h:mm a"));

        toTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showToTimeSelectionDialog();            }
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

    private void handleESaveButton(Button v) {
        Spinner classSpinner=(Spinner) findViewById(R.id.Class_Spinner);

        Spinner subjectSpinner=(Spinner) findViewById(R.id.Subject_Spinner);

        SqlUtility sqlUtility = new SqlUtility(this);


        long ret = sqlUtility.insertValues(detailsPojo.getYear(), detailsPojo.getMonth(), detailsPojo.getDay(),
                classSpinner.getSelectedItem().toString(),
                subjectSpinner.getSelectedItem().toString(),
                getFromTime("h:mm a"), getToTime("h:mm a")
                );

        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";

        if(ret > 0 )
            text = "Added information successfully";
        else
            text = "Something wrong!!!";


        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void handleExportButton(Button v) {
        Intent intent = new Intent(this, ExportActivity.class);
        startActivity(intent);
    }

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

    private void showDialog() {
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

    private String getFromTime(String format) {
        final Calendar c = Calendar.getInstance();
        c.set(detailsPojo.getYear(), detailsPojo.getMonth(), detailsPojo.getDay(),
                detailsPojo.getFromHour(), detailsPojo.getFromMinute());
        SimpleDateFormat fromTime = new SimpleDateFormat(format);
        String retString = fromTime.format(c.getTime());
        return retString;
    }

    private String getToTime(String format) {
        final Calendar c = Calendar.getInstance();
        c.set(detailsPojo.getYear(), detailsPojo.getMonth(), detailsPojo.getDay(),
                detailsPojo.getToHour(), detailsPojo.getToMinute());
        SimpleDateFormat fromTime = new SimpleDateFormat(format);
        String retString = fromTime.format(c.getTime());
        return retString;
    }

    private String getCurrentDate(String format) {
        final Calendar c = Calendar.getInstance();
        c.set(detailsPojo.getYear(), detailsPojo.getMonth(), detailsPojo.getDay());
        SimpleDateFormat date = new SimpleDateFormat(format);
        String retString = date.format(c.getTime());
        return retString;
    }

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
