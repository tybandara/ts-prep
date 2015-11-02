package com.example.thilinab.tsprep.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.thilinab.tsprep.Details;
import com.example.thilinab.tsprep.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassDateFrag extends android.support.v4.app.Fragment {
    TextView date_tv, month_tv, year_tv;
    Details details = Details.getInstance();

    public ClassDateFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.class_date_layout, container, false);
        date_tv = (TextView) view.findViewById(R.id.date_tv);
        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClassDate();
            }
        });
        date_tv.setText(getCurrentDate("dd"));

        month_tv = (TextView) view.findViewById(R.id.month_tv);
        month_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClassDate();
            }
        });
        month_tv.setText(getCurrentDate("MMM"));

        year_tv = (TextView) view.findViewById(R.id.year_tv);
        year_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClassDate();
            }
        });
        year_tv.setText(getCurrentDate("yyyy"));

        return view;
    }

    private String getCurrentDate(String format) {
        final Calendar c = Calendar.getInstance();
        c.set(details.getYear(), details.getMonth(), details.getDay());
        SimpleDateFormat date = new SimpleDateFormat(format);
        String retString = date.format(c.getTime());
        return retString;
    }

    /**
     * Handle class date selection using a dialog
     */
    private void handleClassDate() {
        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                details.setYear(year);
                details.setMonth(monthOfYear);
                details.setDay(dayOfMonth);
                date_tv.setText(getCurrentDate("dd"));
                month_tv.setText(getCurrentDate("MMM"));
                year_tv.setText(getCurrentDate("yyyy"));
            }
        };

        int year = details.getYear();
        int month = details.getMonth();
        int day = details.getDay();

        DatePickerDialog d = new DatePickerDialog(getActivity(),
                mDateSetListener, year, month, day);
        d.show();
    }

}
