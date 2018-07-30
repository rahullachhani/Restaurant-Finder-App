package com.codingc.team20.restofinder;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapOverView extends Fragment //implements OnMarkerClickListener 
{

	String placeId;
	ArrayList<String> latlong;
	DatabaseHelper db;
    static String ARG_VENUEID="232";
	private static View view;
	private Marker[] marker;
	private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
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
	Marker mk;
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
	LatLng l;
	  MyLocation myLocation = new MyLocation();
	     MyLocation.LocationResult locationResult;
	    
	
	public mapOverView()
	{}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		
		if (container == null) {
	        return null;
	    }
	    if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	    }
	    try {
	        view =  inflater.inflate(R.layout.map_overlay, container, false);
	  	      } catch (InflateException e) {
	        /* map is already there, just return view as it is */
	    }
	   
	  // Passing harcoded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map
	   db=new DatabaseHelper(MainActivity.a);
   mMap = ((SupportMapFragment) MainActivity.fm.findFragmentById(R.id.map)).getMap();
     mMap.setMyLocationEnabled(true);
     final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)view.findViewById(R.id.map_relative_layout);

     mapWrapperLayout.init(mMap, getPixelsFromDp(MainActivity.a, 39 + 20)); 
     this.infoWindow = (ViewGroup) inflater.inflate(R.layout.infowindow, null);
     this.infoTitle = (TextView)infoWindow.findViewById(R.id.title);
     this.infoSnippet = (TextView)infoWindow.findViewById(R.id.snippet);
     this.infoButton = (ImageButton)infoWindow.findViewById(R.id.button);

     
    this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
          getResources().getDrawable(R.drawable.abc_ic_go)
            ,
             getResources().getDrawable(R.drawable.abc_ic_go)
       ) 
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

        	 String placeid=db.getPlaceId(""+lat,""+lng);
        	 if(placeid!=null){
        	 Intent i=new Intent(MainActivity.a,Restaurant.class);
        	 i.putExtra("placeid", placeid);
        	 startActivity(i);
        	 }
         }
     }; 
     this.infoButton.setOnTouchListener(infoButtonListener);

      mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
         @Override
         public void onInfoWindowClick(Marker marker) {
        	
        	 l=marker.getPosition();
        	 db=new DatabaseHelper(MainActivity.a);
    	     

        	 double lat = l.latitude;
        	 double lng = l.longitude;
        	 double factor = 1e6; // = 1 * 10^5 = 100000.
        	 lat = Math.round(lat * factor) / factor;
        	 lng = Math.round(lng * factor) / factor;

        	 String placeid=db.getPlaceId(""+lat,""+lng);
        	 if(placeid!=null){
        	 Intent i=new Intent(MainActivity.a,Restaurant.class);
        	 i.putExtra("placeid", placeid);
        	 startActivity(i);

         }
     }});
     mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

		@Override
		public View getInfoContents(Marker marker) {
			// Setting up the infoWindow with current's marker info
            infoTitle.setText(marker.getTitle());
            infoSnippet.setText(marker.getSnippet());
            infoButtonListener.setMarker(marker);
l=marker.getPosition();
            // We must call this to set the current marker and infoWindow references
            // to the MapWrapperLayout
            mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
            return infoWindow;
            }

		@Override
		public View getInfoWindow(Marker arg0) {
			// TODO Auto-generated method stub
			return null;
		}
});
  
     // For dropping a marker at a point on the Map
     												
     			
     
     Nearby=true;     // false for SearchLocations
		int i=0;
     latlng=db.getLatLongTotal(Nearby);
    
     
     if(latlng==null)
     {
    	// progressDialog = ProgressDialog.show(a, "Finding your location",
         //        "Please wait...", true);
    	//	Intent ib=new Intent(MainActivity.a,SearchActivity.class);
			//MainActivity.a.startActivity(ib);
		/*
    	 count=0;
	 for(int j=0;i<places1.size();j++)
         places1.remove(i);
         
     for(int j=0;j<places1_details.size();j++)
         places1_details.remove(j);
     
     places1=null;
     places1_details=null;
     places1=new ArrayList<Place>();
     places1_details=new ArrayList<Place_Details>();
	
	
	
	
	LocationManager locationManager = (LocationManager) a.getSystemService(
            Context.LOCATION_SERVICE);
    LocationListener myLocationListener = new MyLocationListener();

    locationResult = new MyLocation.LocationResult() {
        @Override
        public void gotLocation(Location location) {
            latitude1 = String.valueOf(location.getLatitude());
            longitude1 = String.valueOf(location.getLongitude());
            //progressDialog.dismiss();
           try {
			boolean result=new GetCurrentLocation().execute(latitude1, longitude1).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
    };

   MyRunnable myRun = new MyRunnable();
  // myRun.run();
  Thread t=new Thread(myRun);
  t.start();
  try {
	t.join();
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   
   
   /*
   while(true)
    	{
    	
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	latlng=db.getLatLongTotal(Nearby);
    	if(latlng!=null)
    		break;
    	}
    
     
     
     */
     
     }
     
     
     
      
     
     
     
     
     
     			 marker=new Marker[latlng.length];
	            for(;i<latlng.length;i++)
	            {
	    	   marker[i]= mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latlng[i][0]),Double.parseDouble(latlng[i][1]))).title(latlng[i][2]).snippet("Restaurant"));
	    	  // marker[i].setDraggable(true);
	    	    }

	    	    // For zooming automatically to the Dropped PIN Location
	    	   // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
	    	     //       longitude), 18.0f));
	    	  //  Move the camera instantly to hamburg with a zoom of 15.
	            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latlng[i/2][0]),Double.parseDouble(latlng[i/2][1])), 17));

	            // Zoom in, animating the camera.
	            mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
	//            mMap.setOnMarkerClickListener(this);
     
	            return view;
	}

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
	
    @Override
    public void onDestroy(){
        super.onDestroy();
      //  exportDB();
     
        if(places1!=null)
        for(int i=0;i<places1.size();i++)
            places1.remove(i);
         if(places1_details!=null)   
        for(int j=0;j<places1_details.size();j++)
            places1_details.remove(j);

		//MainActivity.a.getSharedPreferences("Filter",0).edit().clear().commit();
		  ComponentName receiver = new ComponentName(MainActivity.a, NetworkChangeReceiver2.class);
          PackageManager pm = MainActivity.a.getPackageManager();
              pm.setComponentEnabledSetting(receiver,
                  PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                  PackageManager.DONT_KILL_APP);
          Toast.makeText(MainActivity.a,"Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
          
          
		 
	         

      //  android.os.Process.killProcess(android.os.Process.myPid());
    }
	
	    
	   
	    
	
	
	    public class MyRunnable implements Runnable {
	        public MyRunnable() {
	        }

	        public void run() {
	            myLocation.getLocation(a, locationResult);
	           
	        }
	    }

	
    
    
    


}
