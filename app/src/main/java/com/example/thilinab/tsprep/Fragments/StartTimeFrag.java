package com.example.thilinab.tsprep.Fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.thilinab.tsprep.Details;
import com.example.thilinab.tsprep.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by thilinab on 10/31/2015.
 */
public class StartTimeFrag extends android.support.v4.app.Fragment {
    TextView start_time_tv;
    Details details = Details.getInstance();

    public StartTimeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.start_time_layout, container, false);
        start_time_tv = (TextView) view.findViewById(R.id.start_time_tv);
        start_time_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleStartTime();
            }
        });
        start_time_tv.setText(getFromTime("h:mm a"));

        return view;
    }

    private void handleStartTime() {
        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                details.setFromHour(hourOfDay);
                details.setFromMinute(minute);

                start_time_tv.setText(getFromTime("h:mm a"));
            }
        };

        int hour = details.getFromHour();
        int minute = details.getFromMinute();

        TimePickerDialog d = new TimePickerDialog(getActivity(), mTimeSetListener, hour, minute, false);
        d.show();
    }

    /**
     * Simple function to get the class start time as a string
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

}
