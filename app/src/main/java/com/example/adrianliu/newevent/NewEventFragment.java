package com.example.adrianliu.newevent;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class NewEventFragment extends Fragment {

    public static final String TAG="NewEventFragment";

    ArrayList<Friend> friends=new ArrayList<Friend>();
    FriendImageAdapter adapter;




    Calendar mCalendar;


    FragmentTransaction ft;
    FragmentManager fmg;
    TextView dateValueView;

    public static final String STATIC_MAP_API_ENDPOINT
      ="http://maps.google.com/maps/api/staticmap?center=37.4223662,-122.0839445&zoom=15&size=200x200&sensor=false";


    String[] spinnerValues={"green","red","yellow"};
    int title_images[]={R.drawable.green_circle,R.drawable.red_circle,R.drawable.yellow_circle};

    ImageView staticMap;

    public NewEventFragment() {
        // Required empty public constructor
    }

    private byte[] convertDrawableToByteArray(Drawable drawable){
        byte[] bitmapData;
        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        bitmapData=stream.toByteArray();

        return bitmapData;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();


        friends.add(new Friend(1,convertDrawableToByteArray(getActivity().getResources().getDrawable(R.drawable.ic_launcher)),"Adrian Liu"));
        friends.add(new Friend(2,convertDrawableToByteArray(getActivity().getResources().getDrawable(R.drawable.ic_launcher)),"Kevin Wang"));
        friends.add(new Friend(3,convertDrawableToByteArray(getActivity().getResources().getDrawable(R.drawable.ic_launcher)),"Kim Lim"));
        friends.add(new Friend(4,convertDrawableToByteArray(getActivity().getResources().getDrawable(R.drawable.ic_launcher)),"Jin Sun"));

        adapter=new FriendImageAdapter(getActivity(),R.layout.friend_list_item,friends);
    }

    private void initActionBar(){
        setHasOptionsMenu(true);
        ActionBar mActionBar=getActivity().getActionBar();
        LayoutInflater mInflater=LayoutInflater.from(getActivity());

        View mCustomView=(View)mInflater.inflate(R.layout.action_new_event,null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void updateDateValueView(String dayOfWeekString,String dayOfMonthString,String monthString){
        if(dayOfWeekString.length()!=0){
            dateValueView.setText(dayOfWeekString.substring(0,1)+dayOfWeekString.substring(1,3).toLowerCase()+" "+dayOfMonthString+" "+monthString);
        }

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_new_event, container, false);

        Spinner titleSpinner=(Spinner)v.findViewById(R.id.titleSpinner);
        titleSpinner.setAdapter(new MyAdapter(getActivity(),R.layout.title_spinner_item,spinnerValues));
        // ListView friendList=(ListView)v.findViewById(R.id.friend_list);
        ListView friendList=(ListView)v.findViewById(R.id.friend_list);
        friendList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        friendList.setAdapter(adapter);
        setListViewHeightBasedOnChildren(friendList);

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG,"clicked: "+i);
            }
        });


        //show static map
       staticMap =(ImageView)v.findViewById(R.id.static_map);
        AsyncTask<Void,Void,Bitmap> mapImageFromUrl=new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                Bitmap bmp=null;
                HttpClient httpClient=new DefaultHttpClient();
                HttpGet request=new HttpGet(STATIC_MAP_API_ENDPOINT);
                InputStream in=null;
                try{
                    in=httpClient.execute(request).getEntity().getContent();
                    bmp= BitmapFactory.decodeStream(in);
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap bmp){
                if(bmp!=null){
                    staticMap.setImageBitmap(bmp);
                }
            }

        };

        mapImageFromUrl.execute();

        dateValueView=(TextView)v.findViewById(R.id.date_value);
        mCalendar=Calendar.getInstance();
        //Fri Oct 17 20:30:11 NZDT 2014
        //System.out.println("adrian: "+mCalendar.getTime());
        SimpleDateFormat mSimpleDateFormat=new SimpleDateFormat("EEE d MMM");
        String formattedDate=mSimpleDateFormat.format(mCalendar.getTime());
        //System.out.println("adrian:"+formattedDate);
        dateValueView.setText(formattedDate);





        //date view
        View dateView=v.findViewById(R.id.date_layout);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args=new Bundle();

                fmg=getActivity().getFragmentManager();
                CalendarFragment mCalendarFragment=new CalendarFragment();
                mCalendarFragment.show(fmg,"Calendar Fragment");

            }
        });




        return v;


    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;//if without this statement,the listview will be a little short
        listView.setLayoutParams(params);
    }



    public class MyAdapter extends ArrayAdapter<String>{

        public MyAdapter(Context ctx,int textViewResourceId,String[] objects){
            super(ctx,textViewResourceId,objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView,ViewGroup parent){
            LayoutInflater inflater=getActivity().getLayoutInflater();
            View titleSpinner=inflater.inflate(R.layout.title_spinner_item,parent,false);
            ImageView titleImageView=(ImageView)titleSpinner.findViewById(R.id.title_image_view);
            titleImageView.setImageResource(title_images[position]);

            return titleSpinner;
        }




    }


}
