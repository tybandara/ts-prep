package com.example.thilinab.tsprep.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thilinab.tsprep.Details;
import com.example.thilinab.tsprep.R;
import com.example.thilinab.tsprep.TSPrep;
import com.example.thilinab.tsprep.sqldb.SqlUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by thilinab on 10/31/2015.
 */
public class SaveDbFrag extends android.support.v4.app.Fragment {

    TextView summary_tv;
    TextView enter_db_tv;
    Details details = Details.getInstance();

    public SaveDbFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.enter_db_layout, container, false);
        summary_tv = (TextView) view.findViewById(R.id.summary_tv);


        enter_db_tv = (TextView) view.findViewById(R.id.save_db_btn);
        enter_db_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveDb();
            }
        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // fragment visible
            summary_tv.setText(getSummary());
        }
        else{
            // invisible
            //Toast.makeText(getActivity(), "InVisible", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSummary() {

        String str = "";
        int month = details.getMonth() + 1;
        str += "Date :\t" + details.getDay()  + "/" + month + "/" + details.getYear();
        str += "\n\nStart :\t" + details.getFromHour() + ":" + details.getFromMinute();
        str += "\n\nEnd :\t" + details.getToHour() + ":" + details.getToMinute();
        str += "\n\nClass :\t" + details.getClassName();
        str += "\n\nSubject :\t" + details.getSubject();

        return str;
    }

    /**
     * Currently entered data would be entered in the sqlite database
     */
    private void handleSaveDb() {
        SqlUtility sqlUtility = details.getSqlUtility();

        /* Add the current information to the sqlite db. Return value should be greater than 1
        if the call is succeeded */
        long ret = sqlUtility.insertValues(details.getYear(), details.getMonth() + 1, details.getDay(),
                details.getClassName(),
                details.getSubject(),
                getFromTime("h:mm a"), getToTime("h:mm a")
        );

        Context context = getActivity();
        CharSequence text;

        if(ret > 0 )
            text = "Added information successfully";
        else
            text = "Something wrong!!!";


        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent intent = new Intent(getActivity(), TSPrep.class);
        startActivity(intent);
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



