package com.codingc.team20.restofinder;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class search_map extends FragmentActivity //implements OnMarkerClickListener 
{

	String placeId;
	ArrayList<String> latlong;
	DatabaseHelper db;
    static String ARG_VENUEID="232";
	private Marker[] marker;
	private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    SharedPreferences settings;
   SharedPreferences.Editor editor;
    private ImageButton infoButton;
    private OnInfoWindowElemTouchListener infoButtonListener;

	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */

	private static GoogleMap mMap;
	private static Double latitude, longitude;
boolean Nearby;
	String [][]latlng;
	
	

	 Configuration config;
	public  static String latitude1;
	public  static String longitude1;
	 String text;
	public  Activity a=MainActivity.a;
	
	private  ArrayList<Place> places1 = new ArrayList<Place>();
	private  ArrayList<Place_Details> places1_details = new ArrayList<Place_Details>();
	 ProgressDialog progressDialog;
	 private  final String APIKEY ="AIzaSyCZzifpN0KDuxSdB-0yxe8JsOiHCJ7x7Bs"; //Add your api key
	public  DetailedConfiguration detailedconfig;
	//public  DatabaseHelper db;
	  private  String type = "food";
	 StringBuilder textquery;
	public  int radius=400;
	 String[] split;
	 int count;
	boolean flag;
	boolean trip;
	  MyLocation myLocation = new MyLocation();
	     MyLocation.LocationResult locationResult;
	    
	
	
	
	public search_map()
	{}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
			 
		 setContentView(R.layout.map_overlay);
		    if (mMap == null) {
	          //   mMap = ((MapFragment) getFragmentManager().findFragmentById(
	            //         R.id.map)).getMap();
		  
		    	mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		    	   
		    }
		    settings=this.getSharedPreferences("GoogleMap", 0);
		    editor=settings.edit();
		 db=new DatabaseHelper(MainActivity.a);
		 LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	       trip=getIntent().getBooleanExtra("flag", true);
		 if(!trip)
		 {
			 editor.putBoolean("SEARCH", true);
			 editor.commit();
			 
		 }
    mMap.setMyLocationEnabled(true);
    final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);

   mapWrapperLayout.init(mMap, getPixelsFromDp(MainActivity.a, 39 + 20)); 
   this.infoWindow = (ViewGroup) inflater.inflate(R.layout.infowindow, null);
   this.infoTitle = (TextView)infoWindow.findViewById(R.id.title);
   this.infoSnippet = (TextView)infoWindow.findViewById(R.id.snippet);
   this.infoButton = (ImageButton)infoWindow.findViewById(R.id.button);

     
   this.infoButtonListener = new OnInfoWindowElemTouchListener(infoWindow,getResources().getDrawable(R.drawable.abc_ic_go),getResources().getDrawable(R.drawable.abc_ic_go)) 
     {
         @Override
         protected void onClickConfirmed(View v, Marker marker) {
             // Here we can perform some action triggered after clicking the button
        	 LatLng l=marker.getPosition();
        	 db=new DatabaseHelper(MainActivity.a);
        	     

        	 double lat = l.latitude;
        	 double lng = l.longitude;
        	 double factor = 1e6; // = 1 * 10^5 = 100000.
        	 lat = Math.round(lat * factor) / factor;
        	 lng = Math.round(lng * factor) / factor;
        	 MainActivity.latitude=""+lat;
        	 MainActivity.longitude=""+lng;
        	 String placeid=db.getPlaceId(""+lat,""+lng);
        	 if(placeid!=null){
        	 Intent i=new Intent(MainActivity.a,Restaurant.class);
        	 i.putExtra("placeid", placeid);
        	 startActivity(i);
        	 }
         }
     }; 
     this.infoButton.setOnTouchListener(infoButtonListener);


     mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

		@Override
		public View getInfoContents(Marker marker) {
			// Setting up the infoWindow with current's marker info
            infoTitle.setText(marker.getTitle());
            infoSnippet.setText(marker.getSnippet());
            infoButtonListener.setMarker(marker);
           mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
            return infoWindow;
            }

		@Override
		public View getInfoWindow(Marker arg0) {
			// TODO Auto-generated method stub
			return null;
		}
});
    
     Nearby=false;     // true for mapOverView
		int i=0;
     latlng=db.getLatLongTotal(Nearby);
     			 marker=new Marker[latlng.length];
	            for(;i<latlng.length;i++)
	            {
	    	   marker[i]= mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latlng[i][0]),Double.parseDouble(latlng[i][1]))).title(latlng[i][2]).snippet("Restaurant"));
	    	    }
	            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latlng[i/2][0]),Double.parseDouble(latlng[i/2][1])), 17));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
	}
	
	private void initilizeMap() {
        if (mMap == null) {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (mMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		SharedPreferences   settings=this.getSharedPreferences("GoogleMap",0);
		SharedPreferences.Editor editor=settings.edit();
		editor.putBoolean("SEARCH",false);
		 editor.commit();
		 
	}
	
    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
	

	
	    
	   
	    
	
	
	
	    @Override
	    protected void onResume() {
	        super.onResume();
	        initilizeMap();
	    }    
    
    


}
