package com.example.thilinab.tsprep;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.thilinab.tsprep.Fragments.ClassDateFrag;
import com.example.thilinab.tsprep.Fragments.ClassNameFrag;
import com.example.thilinab.tsprep.Fragments.EndTimeFrag;
import com.example.thilinab.tsprep.Fragments.ExportMonthSelect;
import com.example.thilinab.tsprep.Fragments.MailFragment;
import com.example.thilinab.tsprep.Fragments.SaveDbFrag;
import com.example.thilinab.tsprep.Fragments.StartTimeFrag;
import com.example.thilinab.tsprep.Fragments.SubjectFrag;

/**
 * Created by thilinab on 11/1/2015.
 */
public class FilterSwipeAdapter extends FragmentStatePagerAdapter {
    public FilterSwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

        switch (position) {
            case 0:
                frag = new ExportMonthSelect();
                break;
            case 1:
                frag = new MailFragment();
                break;
        }

        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
