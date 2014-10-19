package com.example.adrianliu.newevent;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements CalendarFragment.OnCompleteListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //Hide the App icon
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));



        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        NewEventFragment mNewEventFragment=new NewEventFragment();
        ft.replace(R.id.fragment_container,mNewEventFragment,"New Event Fragment");
        ft.commit();


    }

    @Override
    public void onComplete(String dayOfWeekString,String dayOfMonthString,String monthString) {
        System.out.println("adrian@MainActivity "+dayOfWeekString+dayOfMonthString+monthString);

        NewEventFragment mNewEventFragment=(NewEventFragment)getFragmentManager().findFragmentByTag("New Event Fragment");
        if(mNewEventFragment!=null){
            mNewEventFragment.updateDateValueView(dayOfWeekString,dayOfMonthString,monthString);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
