package com.example.sohan.customcalender;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomCalendarView customCalendarView = (CustomCalendarView) findViewById(R.id.calender);
        HashSet<Date> events = new HashSet<>();
        events.add(new Date());
        customCalendarView.updateCalendar(events);
    }
}
