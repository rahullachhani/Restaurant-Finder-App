package com.codingc.team20.restofinder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class FullImageActivity extends Activity {
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        
        // get intent data
        Intent i = getIntent();
        
        // Selected image id
        int position = i.getExtras().getInt("id");
       Bitmap bmp=(Bitmap)i.getExtras().getParcelable("bit");
        
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageBitmap(bmp);
    }

}
