package com.example.thilinab.tsprep;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.thilinab.tsprep.sqldb.SqlUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TSPrep extends FragmentActivity {

    // This holds general details of a time record
    Details details;

    // List of subjects
    List<String> subjectList = new ArrayList<>();

    static final String LECTURED_YEAR = "lecturedYear";
    static final String LECTURED_MONTH = "lecturedMonth";
    static final String LECTURED_DATE = "lecturedDate";
    static final String LECTURED_FROM_HOUR = "lecturedFromHour";
    static final String LECTURED_FROM_MINUTE = "lecturedFromMinute";
    static final String LECTURED_TO_HOUR = "lecturedToHour";
    static final String LECTURED_TO_MINUTE = "lecturedToMinute";
    static final String LECTURED_CLASS = "lecturedClass";
    static final String LECTURED_SUBJECT = "lecturedSubject";
    static final String TIMESHEET_MONTH = "timesheetMonth";


    ViewPager viewPager1, viewPager2;
    SqlUtility sqlUtility;

    ImageView timeBtn;
    ImageView exportBtn;

    TextView configureBtn;
    private ArrayList<String> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);

        details = Details.getInstance();

        sqlUtility = new SqlUtility(this);


        String classes = sqlUtility.getConfigClasses();
        if (classes != null && !classes.isEmpty()) {
            classList = new ArrayList<String>(Arrays.asList(classes.split(",")));
        } else {
            sqlUtility.insertClasses("PGPN,B.Sc.");
            classList = new ArrayList<>();
            classList.add(0, "PGPN");
            classList.add(1, "B.Sc.");
        }

        details.setSqlUtility(sqlUtility);

        timeBtn = (ImageView) findViewById(R.id.time_btn);
        timeBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // image released
                    timeBtn.setImageResource(R.drawable.time_down);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // image released
                    timeBtn.setImageResource(R.drawable.time_up);
                    Intent intent = new Intent(getApplicationContext(), NewTimeEntering.class);
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });
        exportBtn = (ImageView) findViewById(R.id.export_btn);
        exportBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // image released
                    exportBtn.setImageResource(R.drawable.mail_down);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // image released
                    exportBtn.setImageResource(R.drawable.mail_up);
                    Intent intent = new Intent(getApplicationContext(), FilterActivity.class);
                    startActivity(intent);
                    //Toast.makeText(getApplication(), "export button up", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        configureBtn = (TextView) findViewById(R.id.configure_btn);
        configureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureClicked();
            }
        });

        // Restoring values from the previously destroyed instance
        if (savedInstanceState != null) {
            details.setYear(savedInstanceState.getInt(LECTURED_YEAR));
            details.setMonth(savedInstanceState.getInt(LECTURED_MONTH));
            details.setDay(savedInstanceState.getInt(LECTURED_DATE));
            details.setFromHour(savedInstanceState.getInt(LECTURED_FROM_HOUR));
            details.setFromMinute(savedInstanceState.getInt(LECTURED_FROM_MINUTE));
            details.setToHour(savedInstanceState.getInt(LECTURED_TO_HOUR));
            details.setToMinute(savedInstanceState.getInt(LECTURED_TO_MINUTE));
            details.setClassName(savedInstanceState.getString(LECTURED_CLASS));
            details.setSubject(savedInstanceState.getString(LECTURED_SUBJECT));
            details.setFilterMonth(savedInstanceState.getString(TIMESHEET_MONTH));
        } else {
            // This is a fresh start. Set the current date as the initial value
            final Calendar c = Calendar.getInstance();
            details.setYear(c.get(Calendar.YEAR));
            details.setMonth(c.get(Calendar.MONTH));
            details.setDay(c.get(Calendar.DAY_OF_MONTH));
            details.setFromHour(c.get(Calendar.HOUR_OF_DAY));
            details.setFromMinute(c.get(Calendar.MINUTE));
            details.setToHour(c.get(Calendar.HOUR_OF_DAY));
            details.setToMinute(c.get(Calendar.MINUTE));

            details.setClassName(classList.get(0));
            details.setSubject("ISS");
            details.setFilterMonth("Jan");
        }

        // Set class details
        //fillClasses();
        // Set subject details
        //fillSubjects();

//        viewPager1 = (ViewPager) findViewById(R.id.swipe_viewpager);
//        viewPager2 = (ViewPager) findViewById(R.id.swipe_viewpager);
//        SwipeAdapter swipeAadpter = new SwipeAdapter(getSupportFragmentManager());
//        viewPager1.setAdapter(swipeAadpter);
//        viewPager2.setAdapter(swipeAadpter);

//

//        // Configure the current view
//        SetView();
    }

    // Fills the subject list
    private void fillSubjects() {
        details.setClassName("B.Sc.");
    }

    // Fills the classes list
    private void fillClasses() {
        details.setSubject("ISS");
    }

    private void configureClicked() {
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Test", duration);
        toast.show();

        Intent intent = new Intent(getApplicationContext(), ConfigureActivity.class);
        startActivity(intent);
    }

    /**
     * Currently entered data would be entered in the sqlite database
     */
    private void handleSaveBtn() {
        Spinner classSpinner = (Spinner) findViewById(R.id.Class_Spinner);
        Spinner subjectSpinner = (Spinner) findViewById(R.id.Subject_Spinner);

        SqlUtility sqlUtility = new SqlUtility(this);

        /* Add the current information to the sqlite db. Return value should be greater than 1
        if the call is succeeded */
        long ret = sqlUtility.insertValues(details.getYear(), details.getMonth(), details.getDay(),
                classSpinner.getSelectedItem().toString(),
                subjectSpinner.getSelectedItem().toString(),
                getFromTime("h:mm a"), getToTime("h:mm a")
        );

        Context context = getApplicationContext();
        CharSequence text;

        if (ret > 0)
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
        DialogFragment newFragment = new ToTimePicker() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                details.setToHour(hourOfDay);
                details.setToMinute(minute);
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
        DialogFragment newFragment = new FromTimePicker() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                details.setFromHour(hourOfDay);
                details.setFromMinute(minute);
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
        DialogFragment newFragment = new DatePicker() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                details.setYear(year);
                details.setMonth(monthOfYear);
                details.setDay(dayOfMonth);
                Button dateButton = (Button) findViewById(R.id.Date_Btn);
                dateButton.setText("Date : " + getCurrentDate("yyyy-MMM-dd"));
            }
        };
        newFragment.show(getFragmentManager(), "Date Picker");
    }

    /**
     * Simple function to get the class start time as a string
     *
     * @param format expected string format
     * @return string class start time
     */
    private String getFromTime(String format) {
        final Calendar c = Calendar.getInstance();
        c.set(details.getYear(), details.getMonth(), details.getDay(),
                details.getFromHour(), details.getFromMinute());
        SimpleDateFormat fromTime = new SimpleDateFormat(format);
        String retString = fromTime.format(c.getTime());
        return retString;
    }

    /**
     * function to get the class stop time as a string
     *
     * @param format expected string format
     * @return string class end time
     */
    private String getToTime(String format) {
        final Calendar c = Calendar.getInstance();
        c.set(details.getYear(), details.getMonth(), details.getDay(),
                details.getToHour(), details.getToMinute());
        SimpleDateFormat fromTime = new SimpleDateFormat(format);
        String retString = fromTime.format(c.getTime());
        return retString;
    }

    /**
     * function to get the class date as a string
     *
     * @param format expected string format
     * @return string class date
     */
    private String getCurrentDate(String format) {
        final Calendar c = Calendar.getInstance();
        c.set(details.getYear(), details.getMonth(), details.getDay());
        SimpleDateFormat date = new SimpleDateFormat(format);
        String retString = date.format(c.getTime());
        return retString;
    }

    /**
     * handling the state when the app is being paused
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the users current information
        outState.putInt(LECTURED_YEAR, details.getYear());
        outState.putInt(LECTURED_MONTH, details.getMonth());
        outState.putInt(LECTURED_DATE, details.getDay());
        outState.putInt(LECTURED_FROM_HOUR, details.getFromHour());
        outState.putInt(LECTURED_FROM_MINUTE, details.getFromMinute());
        outState.putInt(LECTURED_TO_HOUR, details.getToHour());
        outState.putInt(LECTURED_TO_MINUTE, details.getToMinute());

        super.onSaveInstanceState(outState);
    }
}
