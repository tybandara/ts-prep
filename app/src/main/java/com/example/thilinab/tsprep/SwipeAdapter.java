package com.example.thilinab.tsprep;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.thilinab.tsprep.Fragments.ClassDateFrag;
import com.example.thilinab.tsprep.Fragments.ClassNameFrag;
import com.example.thilinab.tsprep.Fragments.EndTimeFrag;
import com.example.thilinab.tsprep.Fragments.SaveDbFrag;
import com.example.thilinab.tsprep.Fragments.StartTimeFrag;
import com.example.thilinab.tsprep.Fragments.SubjectFrag;

/**
 * Created by thilinab on 10/31/2015.
 */
public class SwipeAdapter extends FragmentStatePagerAdapter {
    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

        switch (position) {
            case 0:
                frag = new ClassDateFrag();
                break;
            case 1:
                frag = new StartTimeFrag();
                break;
            case 2:
                frag = new EndTimeFrag();
                break;
            case 3:
                frag = new ClassNameFrag();
                break;
            case 4:
                frag = new SubjectFrag();
                break;
            case 5:
                frag = new SaveDbFrag();
                break;
        }

        return frag;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
