package com.codingc.team20.restofinder;
import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Tab4Fragment extends Fragment {

	String placeId;
	ArrayList<String> latlong;
	DatabaseHelper db;

	private static View view;
	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */

	private static GoogleMap mMap;
	private static Double latitude, longitude;

	
	
	public Tab4Fragment()
	{}

	@Override
	public void onCreate(Bundle s){
		   super.onCreate(s);
	        setRetainInstance(true);
	  }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    if (container == null) {
	        return null;
	    }
	    if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	    }
	    try {
	        view =  inflater.inflate(R.layout.google_map, container, false);
	  	      } catch (InflateException e) {
	        /* map is already there, just return view as it is */
	    }
	   
	  // Passing harcoded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map
	    placeId=Restaurant.placeId;
        db=new DatabaseHelper(Restaurant.getContext());
        latlong=db.getLatitudeLongitude(placeId);
		latitude=Double.parseDouble(latlong.get(0));
		longitude=Double.parseDouble(latlong.get(1));
		String place=latlong.get(2);
		mMap = ((SupportMapFragment) Restaurant.fm
	                .findFragmentById(R.id.map)).getMap();
		ImageButton ib=(ImageButton)view.findViewById(R.id.ib);
		    ib.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.v("mohit","hi");
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
						    Uri.parse("http://maps.google.com/maps?saddr="+MainActivity.latitude+","+MainActivity.longitude+"&daddr="+latitude+","+longitude));
						startActivity(intent);
				}
			});
		    
	            mMap.setMyLocationEnabled(true);
	    	    // For dropping a marker at a point on the Map
	    	    mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(place).snippet("Restaurant"));
	    	    // For zooming automatically to the Dropped PIN Location
	    	   // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
	    	     //       longitude), 18.0f));
	    	    // Move the camera instantly to hamburg with a zoom of 15.
	            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));

	            // Zoom in, animating the camera.
	            mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);

	    return view;
	}
@Override
public void onDestroy(){
	super.onDestroy();
	
}

	
}
