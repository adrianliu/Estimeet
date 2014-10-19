package com.example.adrianliu.newevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.jar.Attributes;

/**
 * Created by adrianliu on 18/10/14.
 */
public class LinearLayoutForListView extends LinearLayout{

    private BaseAdapter adapter;
    private OnClickListener onClickListener=null;

    public void bindLinearLayout(){
        int count=adapter.getCount();
        this.removeAllViews();

        for(int i=0;i<count;i++){
            View v=adapter.getView(i,null,null);
            v.setOnClickListener(this.onClickListener);
            addView(v,i);
        }
    }



    public LinearLayoutForListView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);

    }

    public void setAdapter(ArrayAdapter<Friend> adapter){
        this.adapter=adapter;
    }





}
