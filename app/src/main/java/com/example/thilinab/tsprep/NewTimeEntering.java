package com.example.thilinab.tsprep;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewTimeEntering extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_layout);

        viewPager = (ViewPager) findViewById(R.id.swipe_viewpager);
        SwipeAdapter swipeAadpter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAadpter);
    }
}
