package com.codingc.team20.restofinder;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends ArrayAdapter<Bitmap> {
	private Context mContext;
	 public ArrayList<Bitmap> bmp = new ArrayList<Bitmap>();
	
	// Keep all Images in array
	public Integer[] mThumbIds = {
			R.drawable.img2, R.drawable.img3,
			R.drawable.img4, R.drawable.img5
	};
	
	// Constructor
	public ImageAdapter(Context c,int layoutResourceId,ArrayList<Bitmap> bmp){
		 super(c,layoutResourceId,bmp);
		mContext = c;
		this.bmp=bmp;
	}

	@Override
	public int getCount() {
		if(bmp!=null)
		{
			if(bmp.size()>0)
				return bmp.size();
			else
				return 1;
		}
		else
			return 1;
		
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		 View row = convertView;
         LayoutInflater layout = null;
         if(null == layout) {
             layout = (LayoutInflater)MainActivity.a.getSystemService(
                     Context.LAYOUT_INFLATER_SERVICE);
         }
         if(null==row)
         	row = layout.inflate(R.layout.full_image, null);
        
		ImageView imageView = (ImageView)row.findViewById(R.id.full_image_view);
		if(bmp!=null && bmp.size()>0)
        imageView.setImageBitmap(bmp.get(position));
		else
			imageView.setImageResource(R.drawable.no_image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(270, 270));
        return imageView;
	}

}
