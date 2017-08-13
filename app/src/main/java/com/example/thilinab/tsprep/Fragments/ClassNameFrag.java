package com.example.thilinab.tsprep.Fragments;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.thilinab.tsprep.Details;
import com.example.thilinab.tsprep.R;
import com.example.thilinab.tsprep.sqldb.SqlUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassNameFrag extends android.support.v4.app.Fragment {
    TextView class_name_tv;
    Details details = Details.getInstance();
    String[] classNames;

    public ClassNameFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.class_name_layout, container, false);
        class_name_tv = (TextView) view.findViewById(R.id.class_name_tv);
        class_name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClassName();
            }
        });

        SqlUtility sqlUtility = details.getSqlUtility();
        String classes = sqlUtility.getConfigClasses();
        List<String> list = new ArrayList<String>(Arrays.asList(classes.split(",")));
        classNames = list.toArray(new String[list.size()]);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            class_name_tv.setText(details.getClassName());
        }
    }

    private void handleClassName() {

        AlertDialog.OnClickListener mClickListener = new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                details.setClassName(classNames[which]);
                class_name_tv.setText(classNames[which]);
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Class")
                .setItems(classNames, mClickListener);
        builder.show();
    }


}
