package com.example.sohan.customcalender;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by sohan on 18/1/17.
 */
public class CalendarAdapter extends ArrayAdapter<Date> {
    private final LayoutInflater mInflater;
    private final Context mContext;
    private ArrayList<Date> mData;
    private HashSet<Date> eventDays;
    private List<Date> selectedDate = new ArrayList<>();

    public CalendarAdapter(Context context, ArrayList<Date> data) {
        super(context, R.layout.calendar_item_text, data);
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Date getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        // day in question
        Date date = getItem(position);
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();
        // today
        final Date today = new Date();
        // inflate item if it does not exist yet
        if (view == null) {
            view = mInflater.inflate(R.layout.calendar_item_text, viewGroup, false);
        }

        setBackgroundColor(view, day, month, year, today);
        // set text
        ((TextView)view).setText(String.valueOf(date.getDate()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date1 = getItem(position);
                selectedDate.add(date1);
                view.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_red_dark));
                Toast.makeText(mContext, date1.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void setBackgroundColor(View view, int day, int month, int year, Date today) {
        // if this day has an event, specify event image
        view.setBackgroundResource(0);
        if (eventDays != null)
        {
            for (Date eventDate : eventDays)
            {
                if (eventDate.getDate() == day && eventDate.getMonth() == month &&
                        eventDate.getYear() == year) {
                    // mark this day for event
                   // view.setBackgroundResource(R.drawable.reminder);
                    break;
                }
            }
        }

        // clear styling
        ((TextView)view).setTypeface(null, Typeface.NORMAL);
        ((TextView)view).setTextColor(Color.BLACK);

        if (month != today.getMonth() || year != today.getYear())
        {
            // if this day is outside current month, grey it out
            ((TextView)view).setTextColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
        }
        else if (day == today.getDate())
        {
            // if it is today, set it to blue/bold
            ((TextView)view).setTypeface(null, Typeface.BOLD);
            ((TextView)view).setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_blue_bright));
        } else {
            if (selectedDate.size() > 0) {
                for (Date date : selectedDate) {
                    if (date.getDate() == day) {
                        ((TextView)view).setTypeface(null, Typeface.BOLD);
                        view.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_red_dark));
                        break;
                    }
                }
            } else {

            }
        }
    }


    public void updateData(ArrayList<Date> cells, HashSet<Date> events) {
        mData = cells;
        eventDays = events;
        notifyDataSetChanged();
    }

    public interface OnDateClickListener{
        void onDateClick(Date date);
    }
}
