package com.example.adrianliu.newevent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by adrianliu on 18/10/14.
 */
public class FriendImageAdapter extends ArrayAdapter<Friend>  {

    public static final String TAG="FriendImageAdapter";

    Context context;
    int layoutResourceId;
    ArrayList<Friend> friends=new ArrayList<Friend>();

    public FriendImageAdapter(Context context,int layoutResourceId,ArrayList<Friend> friends){
        super(context,layoutResourceId,friends);
        this.context=context;
        this.layoutResourceId=layoutResourceId;
        this.friends=friends;
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View row=convertView;


        ImageHolder holder=null;
        if(row==null){

            row=inflater.inflate(layoutResourceId,parent,false);
            holder=new ImageHolder();
            holder.imgIcon=(ImageView)row.findViewById(R.id.friend_image);
            holder.txtTitle=(TextView)row.findViewById(R.id.friend_name);
            row.setTag(holder);
        }else{
            holder=(ImageHolder)row.getTag();
        }
        Friend mFriend=friends.get(position);
        holder.txtTitle.setText(mFriend.getName());
        //TODO:convert byte to bitmap from Friend class
//        byte[] outImage=mFriend.getImage();
//        ByteArrayInputStream imageStream=new ByteArrayInputStream(outImage);
//        Bitmap theImage= BitmapFactory.decodeStream(imageStream);

        //holder.imgIcon.setImageBitmap(theImage);
        holder.imgIcon.setImageResource(R.drawable.green_circle);

        return row;
    }

    static class ImageHolder{
        ImageView imgIcon;
        TextView txtTitle;
    }

}












