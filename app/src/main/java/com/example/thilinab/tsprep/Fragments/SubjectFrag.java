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


/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectFrag extends android.support.v4.app.Fragment {
    TextView subject_tv;
    Details details = Details.getInstance();

    public SubjectFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.subject_layout, container, false);
        subject_tv = (TextView) view.findViewById(R.id.subject_tv);
        subject_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubject();
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            subject_tv.setText(details.getSubject());
        }
    }

    private void handleSubject() {
        AlertDialog.OnClickListener mClickListener = new AlertDialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        details.setSubject("ISS");
                        subject_tv.setText("ISS");
                        break;
                    case 1:
                        details.setSubject("ES");
                        subject_tv.setText("ES");
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Subject")
                .setItems(new String[] {"ISS","ES"}, mClickListener);
        builder.show();
    }


}
