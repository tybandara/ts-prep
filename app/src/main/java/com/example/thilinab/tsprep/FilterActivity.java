package com.example.thilinab.tsprep;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_filter_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.swipe_viewpager_filter);
        FilterSwipeAdapter swipeAadpter = new FilterSwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAadpter);
    }
}
