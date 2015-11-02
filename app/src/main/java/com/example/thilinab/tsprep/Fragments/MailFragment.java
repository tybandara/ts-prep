package com.example.thilinab.tsprep.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thilinab.tsprep.Details;
import com.example.thilinab.tsprep.R;
import com.example.thilinab.tsprep.TSPrep;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MailFragment extends android.support.v4.app.Fragment  {
    private static final int SENDING_EMAIL = 1000;
    TextView largeText;
    TextView sendMailBtn;
    String filteredText;
    Details details = Details.getInstance();

    public MailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mail_layout, container, false);


        // we are showing the filtered records on a text view.
        largeText = (TextView) view.findViewById(R.id.filtered_tv);
        // we want the text view to be scrollable
        largeText.setMovementMethod(new ScrollingMovementMethod());

        sendMailBtn = (TextView) view.findViewById(R.id.mail_btn);
        sendMailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSendMail();
            }
        });
        return view;
    }

    private void handleSendMail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{});
        i.putExtra(Intent.EXTRA_SUBJECT, "Timesheet for the month - " + details.getFilterMonth());
        i.putExtra(Intent.EXTRA_TEXT, filteredText);
        try {
            //startActivity(Intent.createChooser(i, "Send mail..."));
            startActivityForResult(Intent.createChooser(i, "Send mail..."), SENDING_EMAIL);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SENDING_EMAIL) {
            Intent intent = new Intent(getActivity(), TSPrep.class);
            startActivity(intent);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // fragment visible
            // convert the user selected month to an integer month value
        DateFormat df = new SimpleDateFormat("MMM");
        Calendar cal  = Calendar.getInstance();
        try {
            String filterMonth = details.getFilterMonth();
            cal.setTime(df.parse(filterMonth));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // query the sqlite db by the selected month
            int selectedMonth = cal.get(Calendar.MONTH) + 1;
            filteredText = details.getSqlUtility().filterByMonth(selectedMonth);
            largeText.setText(filteredText);
        }
        else{
            // invisible
            //Toast.makeText(getActivity(), "InVisible", Toast.LENGTH_SHORT).show();
        }
    }

}
