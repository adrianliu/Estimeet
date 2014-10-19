package com.example.adrianliu.newevent;



import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends DialogFragment {

    Calendar mCalendar;
    String dayOfWeekString;
    String dayOfMonthString;
    String monthString;

    private OnCompleteListener listener;

    public static interface OnCompleteListener{
        public abstract void onComplete(String dayOfWeekString,String dayOfMonthString,String monthString);
    }


    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //Make sure the activity implemented the listener interface
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            this.listener=(OnCompleteListener)activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+" must implement OnCompleteListener interface");
        }
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog mDialog= super.onCreateDialog(savedInstanceState);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        return mDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_calendar, container, false);
        final TextView monthView=(TextView)v.findViewById(R.id.month);
        final TextView dayOfMonthView=(TextView)v.findViewById(R.id.day_of_month);
        final TextView yearView=(TextView)v.findViewById(R.id.year);
        final TextView dayOfWeekView=(TextView)v.findViewById(R.id.day_result);
        CalendarView mCalendarView=(CalendarView)v.findViewById(R.id.cal);
        mCalendarView.setFirstDayOfWeek(2);

        //focus CalendarViwe to current date
        mCalendar=Calendar.getInstance();
        int mYear=mCalendar.get(Calendar.YEAR);
        int mMonth=mCalendar.get(Calendar.MONTH);
        monthString=convertMonth(mMonth);
        int mDay=mCalendar.get(Calendar.DAY_OF_MONTH);
        dayOfMonthString=mDay+"";

        int mDayOfWeek=mCalendar.get(Calendar.DAY_OF_WEEK);

        monthView.setText(monthString);
        dayOfMonthView.setText(dayOfMonthString);
        yearView.setText(mYear+"");
        dayOfWeekString=convertDayOfWeek(mDayOfWeek);
        dayOfWeekView.setText(dayOfWeekString);
        long milliTime=mCalendar.getTimeInMillis();
        mCalendarView.setDate(milliTime,true,true);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                monthString=convertMonth(month);
                monthView.setText(monthString);
                dayOfMonthString=dayOfMonth+"";
                dayOfMonthView.setText(dayOfMonthString);
                yearView.setText(year+"");

                Calendar cal=Calendar.getInstance();
                Date date=new Date(year,month,dayOfMonth);
                cal.setTime(date);
                int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
                dayOfWeekString=convertDayOfWeek(dayOfWeek);
                dayOfWeekView.setText(dayOfWeekString);


            }
        });




        Button btnDone=(Button)v.findViewById(R.id.btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onComplete(dayOfWeekString,dayOfMonthString,monthString);
                dismiss();
            }
        });

        return v;
    }

    private static String convertMonth(int month){
        String result="";
        switch (month){
            case 0:
                result="JAN";
                break;
            case 1:
                result="FEB";
                break;
            case 2:
                result="MAR";
                break;
            case 3:
                result="APR";
                break;
            case 4:
                result="MAY";
                break;
            case 5:
                result="JUN";
                break;
            case 6:
                result="JUL";
                break;
            case 7:
                result="AUG";
                break;
            case 8:
                result="SEP";
                break;
            case 9:
                result="OCT";
                break;
            case 10:
                result="NOV";
                break;
            default:
                result="DEC";
                break;

        }

        return result;

    }

    private static String convertDayOfWeek(int dayOfWeek){
        String result="";
        switch (dayOfWeek){
            case 1:
                result="SATURDAY";
                break;
            case 2:
                result="SUNDAY";
                break;
            case 3:
                result="MONDAY";
                break;
            case 4:
                result="TUESDAY";
                break;
            case 5:
                result="WEDNESDAY";
                break;
            case 6:
                result="THURSDAY";
                break;
            default:
                result="FRIDAY";
                break;


        }

        return result;

    }

    @Override
    public void onResume(){
        super.onResume();
        //set the dialog size in runtime
        WindowManager.LayoutParams params=getDialog().getWindow().getAttributes();
        Display mDisplay=getActivity().getWindowManager().getDefaultDisplay();
        int width=mDisplay.getWidth();
        int height=mDisplay.getHeight();
        params.width=width-40;
        params.height=height-80;
        getDialog().getWindow().setAttributes(params);


    }


}













