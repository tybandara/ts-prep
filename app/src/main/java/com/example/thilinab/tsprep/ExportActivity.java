package com.example.thilinab.tsprep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.thilinab.tsprep.sqldb.SqlUtility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExportActivity extends AppCompatActivity  {

    SqlUtility sqlUtility = new SqlUtility(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        setView();
    }

    /**
     * set views of the second export activity
     */
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

        // Populate filter spinner.
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

    /**
     * test button to drop the sqlite db
     */
    private void handleDelDbButton() {
        sqlUtility.dropTable();
    }

    /**
     * filter the set of records for the records of the given month and show on the text view.
     */
    private void handleExpButton() {
        // we are showing the filtered records on a text view.
        TextView largeText = (TextView) findViewById(R.id.Testing_Text);
        // we want the text view to be scrollable
        largeText.setMovementMethod(new ScrollingMovementMethod());

        // check the month user has selected
        Spinner spinner=(Spinner) findViewById(R.id.Month_FIlter);

        // convert the user selected month to an integer month value
        DateFormat df = new SimpleDateFormat("MMM");
        Calendar cal  = Calendar.getInstance();
        try {
            cal.setTime(df.parse(spinner.getSelectedItem().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // query the sqlite db by the selected month
        largeText.setText(sqlUtility.filterByMonth(cal.get(Calendar.MONTH)));

    }



}
