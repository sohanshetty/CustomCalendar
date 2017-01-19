package com.example.sohan.customcalender;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.days);
        setBackgroundColor(checkBox, day, month, year, today);
        // set text
        checkBox.setText(String.valueOf(date.getDate()));
        checkBox.setTag(position);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Date date1 = getItem(position);
                if (isChecked) {
                    if (!selectedDate.contains(date1)) {
                        selectedDate.add(date1);
                    }
                    compoundButton.setTextColor(Color.WHITE);
                    setShapeColor(compoundButton, Color.RED, Color.TRANSPARENT);
                } else {
                    selectedDate.remove(date1);
                    setBackgroundColorAndShapeForDate(date1, today, ((CheckBox)compoundButton));
                }
                Toast.makeText(mContext, date1.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void setBackgroundColor(CheckBox checkBox, int day, int month, int year, Date today) {
        // clear styling
        checkBox.setTypeface(null, Typeface.NORMAL);
        checkBox.setTextColor(Color.BLACK);
        setShapeColor(checkBox, Color.TRANSPARENT, Color.TRANSPARENT);
        if (month != today.getMonth() || year != today.getYear()) {
            // if this day is outside current month, grey it out
            checkBox.setTextColor(Color.GRAY);
        }
        if (day == today.getDate() && month == today.getMonth() && year == today.getYear()) {
            // if it is today, set it to blue/bold
            checkBox.setTypeface(null, Typeface.BOLD);
            checkBox.setTextColor(Color.BLUE);
            setShapeColor(checkBox, Color.TRANSPARENT, Color.BLUE);
        }
        if (selectedDate.size() > 0) {
            for (Date date : selectedDate) {
                if (date.getDate() == day && date.getMonth() == month &&  date.getYear() == year) {
                    checkBox.setTextColor(Color.WHITE);
                    checkBox.setChecked(true);
                    setShapeColor(checkBox, Color.RED, Color.TRANSPARENT);
                    break;
                } else {
                    //checkBox.setChecked(false);
                }
            }
        }

    }

    private void setBackgroundColorAndShapeForDate(Date date1,  Date today, CheckBox checkBox){
        int day = date1.getDate();
        int month = date1.getMonth();
        int year = date1.getYear();

        if (day == today.getDate() && month == today.getMonth() && year == today.getYear()){
            checkBox.setTypeface(null, Typeface.BOLD);
            checkBox.setTextColor(Color.BLUE);
            setShapeColor(checkBox, Color.TRANSPARENT, Color.BLUE);
        } else {
            checkBox.setTextColor(month !=  today.getMonth() ? Color.GRAY : Color.BLACK);
            setShapeColor(checkBox, Color.TRANSPARENT, Color.TRANSPARENT);
        }

    }

    public static void setShapeColor(View v, int backgroundColor, int borderColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { 8, 8, 8, 8, 8, 8, 8, 8 });
        shape.setColor(backgroundColor);
        shape.setStroke(3, borderColor);
        v.setBackgroundDrawable(shape);
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
