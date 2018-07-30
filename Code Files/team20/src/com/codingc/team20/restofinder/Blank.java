package com.codingc.team20.restofinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Blank extends Fragment {

	private View view;
	public Blank(){
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    try {
	        view =  inflater.inflate(R.layout.activity_blank_n, container, false);
	      	 Toast.makeText(MainActivity.a, "Just connect to the internet and data will load automatically", Toast.LENGTH_LONG).show();
	             
	    } catch (InflateException e) {}
	        /* map is already there, just return view as it is */
	    
	  	      
	return view;	
	}
}
