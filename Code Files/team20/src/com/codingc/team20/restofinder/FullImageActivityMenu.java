package com.codingc.team20.restofinder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class FullImageActivityMenu extends Activity {
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        
        // get intent data
        Intent i = getIntent();
        
        // Selected image id
        int position = i.getExtras().getInt("id");
       String bmp=i.getExtras().getString("bit");
        Integer im=new Integer(Integer.parseInt(bmp));
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageResource(im);
    }

}
