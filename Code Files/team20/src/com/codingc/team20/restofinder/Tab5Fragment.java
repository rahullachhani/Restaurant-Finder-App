package com.codingc.team20.restofinder;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class Tab5Fragment extends Fragment {
	SharedPreferences settings;
	 private ArrayList<Integer> bmp;
	 String placeId;
       public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.drawable.grid_layout, container, false);
	      settings=MainActivity.a.getSharedPreferences("wish",0);
		GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
		placeId=Restaurant.placeId;
		bmp = new ArrayList<Integer>();
		setBitmap();
		// Instance of ImageAdapter Class
		gridView.setAdapter(new ImageAdapterMenu(inflater.getContext(),R.layout.full_image,bmp));

		/**
		 * On Click event for Single Gridview Item
		 * */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				// Sending image id to FullScreenActivity
				Intent i = new Intent(getActivity(), FullImageActivityMenu.class);
				// passing array index
				i.putExtra("id", position);
				i.putExtra("bit",bmp.get(position).toString());
				startActivity(i);
			}
		});
		return rootView;
       }
       
       private void setBitmap() {
			// TODO Auto-generated method stub
    	 String s=settings.getString(placeId,null);
    	 if(s!=null)
    	 {
    	 String arr[]=s.split(" ");
    	 if(arr!=null && arr.length>0)
		  for(int i=0;i<arr.length;i++)
		  {
			  int drawableResourceId = MainActivity.a.getResources().getIdentifier(arr[i], "drawable", MainActivity.a.getPackageName());
			  bmp.add(drawableResourceId);
		  }
    
    	 }
    	 else
    	 {
    		 bmp.add(R.drawable.default1);
    		 bmp.add(R.drawable.default2);
    		 bmp.add(R.drawable.default3);
    		 bmp.add(R.drawable.default4);
    		 bmp.add(R.drawable.default5);
    	 }
		}
	
       
}
