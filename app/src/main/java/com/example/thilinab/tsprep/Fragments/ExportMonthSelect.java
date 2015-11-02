package com.example.thilinab.tsprep.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thilinab.tsprep.Details;
import com.example.thilinab.tsprep.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExportMonthSelect extends android.support.v4.app.Fragment {
    TextView export_month_tv;
    Details details = Details.getInstance();

    public ExportMonthSelect() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.export_month_select_layout, container, false);
        export_month_tv = (TextView) view.findViewById(R.id.export_month_tv);
        export_month_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExportMonth();
            }
        });
        export_month_tv.setText(getCurrentDate("MMM"));
        details.setFilterMonth(getCurrentDate("MMM"));
        return view;
    }

    private void handleExportMonth() {
        AlertDialog.OnClickListener mClickListener = new AlertDialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        details.setFilterMonth("Jan");
                        export_month_tv.setText("Jan");
                        break;
                    case 1:
                        details.setFilterMonth("Feb");
                        export_month_tv.setText("Feb");
                        break;
                    case 2:
                        details.setFilterMonth("Mar");
                        export_month_tv.setText("Mar");
                        break;
                    case 3:
                        details.setFilterMonth("Apr");
                        export_month_tv.setText("Apr");
                        break;
                    case 4:
                        details.setFilterMonth("May");
                        export_month_tv.setText("May");
                        break;
                    case 5:
                        details.setFilterMonth("Jun");
                        export_month_tv.setText("Jun");
                        break;
                    case 6:
                        details.setFilterMonth("Jul");
                        export_month_tv.setText("Jul");
                        break;
                    case 7:
                        details.setFilterMonth("Aug");
                        export_month_tv.setText("Aug");
                        break;
                    case 8:
                        details.setFilterMonth("Sep");
                        export_month_tv.setText("Sep");
                        break;
                    case 9:
                        details.setFilterMonth("Oct");
                        export_month_tv.setText("Oct");
                        break;
                    case 10:
                        details.setFilterMonth("Nov");
                        export_month_tv.setText("Nov");
                        break;
                    case 11:
                        details.setFilterMonth("Dec");
                        export_month_tv.setText("Dec");
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Class")
                .setItems(new String[]{"Jan", "Feb", "Mar", "Apr", "May",
                        "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}, mClickListener);
        builder.show();
    }

    /**
     * function to get the class date as a string
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

}

