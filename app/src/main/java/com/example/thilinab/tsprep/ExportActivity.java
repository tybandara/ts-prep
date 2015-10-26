package com.example.thilinab.tsprep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.thilinab.tsprep.sqldb.FeedEntry;
import com.example.thilinab.tsprep.sqldb.SqlDb;
import com.example.thilinab.tsprep.sqldb.SqlUtility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExportActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        setView();
    }

    private void setView() {
        Button expButton = (Button) findViewById(R.id.Update_Btn);

        expButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExpButton();
            }
        });

        Button delDbButton = (Button) findViewById(R.id.DBDelete_Btn);

        delDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDelDbButton();
            }
        });

        // Populate filter spinner
        String strList[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};
        Spinner filterSpinner = (Spinner) findViewById(R.id.Month_FIlter);

        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, strList);
        // The drop down view
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerArrayAdapter);
    }

    private void handleDelDbButton() {
        SqlUtility sqlUtility = new SqlUtility(this);
        sqlUtility.dropTable();
    }

    private void handleExpButton() {
        SqlUtility sqlUtility = new SqlUtility(this);

        TextView largeText = (TextView) findViewById(R.id.Testing_Text);
        largeText.setMovementMethod(new ScrollingMovementMethod());
        //largeText.setText(sqlUtility.getWholeDb());

        Spinner spinner=(Spinner) findViewById(R.id.Month_FIlter);

        DateFormat df = new SimpleDateFormat("MMM");
        Calendar cal  = Calendar.getInstance();
        try {
            cal.setTime(df.parse(spinner.getSelectedItem().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        largeText.setText(sqlUtility.filterByMonth(cal.get(Calendar.MONTH)));

    }



}
