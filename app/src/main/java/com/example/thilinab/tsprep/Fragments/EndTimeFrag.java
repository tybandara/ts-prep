package com.example.thilinab.tsprep.Fragments;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 */
public class EndTimeFrag extends android.support.v4.app.Fragment {

    TextView end_time_tv;
    Details details = Details.getInstance();
    public EndTimeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.end_time_layout, container, false);
        end_time_tv = (TextView) view.findViewById(R.id.end_time_tv);
        end_time_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEndTime();
            }
        });
        end_time_tv.setText(getToTime("h:mm a"));

        return view;
    }

    /**
     * function to get the class stop time as a string
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


    private void handleEndTime() {
        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                details.setToHour(hourOfDay);
                details.setToMinute(minute);

                end_time_tv.setText(getToTime("h:mm a"));
            }
        };

        int hour = details.getToHour();
        int minute = details.getToMinute();

        TimePickerDialog d = new TimePickerDialog(getActivity(), mTimeSetListener, hour, minute, false);
        d.show();
    }

}
