package com.codingc.team20.restofinder;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class Tab3Fragment extends Fragment {
	
	 private ArrayList<Bitmap> bmp;
	 String placeId;
       public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.drawable.grid_layout, container, false);
	      
		GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
		placeId=Restaurant.placeId;
		bmp = new ArrayList<Bitmap>();
		setBitmap();
		// Instance of ImageAdapter Class
		gridView.setAdapter(new ImageAdapter(inflater.getContext(),R.layout.full_image,bmp));

		/**
		 * On Click event for Single Gridview Item
		 * */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				// Sending image id to FullScreenActivity
				Intent i = new Intent(getActivity(), FullImageActivity.class);
				// passing array index
				i.putExtra("id", position);
				i.putExtra("bit",bmp.get(position));
				startActivity(i);
			}
		});
		return rootView;
       }
       
       private void setBitmap() {
			// TODO Auto-generated method stub
    	 File extStore = Environment.getExternalStorageDirectory();
		    	File myFile = new File(extStore.getAbsolutePath() + File.separator +placeId);
		    	if(myFile!=null)
		    	{
    	File[] files = myFile.listFiles();
    	if(files!=null)
    	{
        int numberOfImages=files.length;
    	
        for(int i=1;i<=numberOfImages;i++)
        {
		Bitmap bitmap=loadFromFile(Environment.getExternalStorageDirectory()
                + File.separator+ placeId+File.separator+i+".jpg");
        if(bitmap!=null)
        {
       	 bmp.add(bitmap);
        }
        }
		    	}
      
        }
		    
			
		}
	 public static Bitmap loadFromFile(String filename) {
        try {
            File f = new File(filename);
            if (!f.exists()) { return null; }
            Bitmap tmp = BitmapFactory.decodeFile(filename);
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }
       
}
