package com.codingc.team20.restofinder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SearchFragment extends Fragment {

	Configuration config;
	public String latitude;
	public String longitude;
	String text;
	public static Activity a;
	static FragmentManager fmk2;
	private ArrayList<Place> places1 = new ArrayList<Place>();
	private ArrayList<Place_Details> places1_details = new ArrayList<Place_Details>();
	ProgressDialog progressDialog;
	 private final String APIKEY ="AIzaSyCobsTWPTHBul5zbvsnHLCGzVvnTpKGKVw";
	public DetailedConfiguration detailedconfig;
	public DatabaseHelper db;
	EditText e;
	Button b;
	StringBuilder textquery;
	public String radius="400";
	String[] split;
	boolean Nearby;
	String [][]latlng;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	View rootView;
	boolean flag;
	private String type = "food";
	MyLocation myLocation = new MyLocation();
	MyLocation.LocationResult locationResult;
	
	public static int count=0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
         rootView= inflater.inflate(R.layout.activity_blank, container, false);
    	 db=new DatabaseHelper(MainActivity.a);
    	 textquery=new StringBuilder();
    	 settings=MainActivity.a.getSharedPreferences("GoogleMap",0);
    	 editor=settings.edit();
		 flag=false;
		    fmk2=getFragmentManager();
		 a=MainActivity.a;
		 latlng=db.getLatLongTotal(true);
		    
	     
	     if(latlng!=null)
	     {
	    	 Fragment fragment2 = new mapOverView();
     		
 		    FragmentManager fragmentManager = getFragmentManager();
 		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
 		   fragmentTransaction.addToBackStack(null); 
 		    fragmentTransaction.replace(R.id.fragment1, fragment2);
 		 
 		    fragmentTransaction.commit();

	     }
	     else{
	    	
	         ComponentName receiver = new ComponentName(MainActivity.a,NetworkChangeReceiver2.class);
	         PackageManager pm = MainActivity.a.getPackageManager();

	         pm.setComponentEnabledSetting(receiver,
	                 PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
	                 PackageManager.DONT_KILL_APP);
	         Toast.makeText(MainActivity.a, "Enabled broadcast receiver", Toast.LENGTH_SHORT).show();
	        
	     //   a=this;
	         boolean s=settings.getBoolean("NETWORKFRAG",false);
	         if((settings.getBoolean("NETWORKFRAG",false)||NetworkState.isNetworkAvailable(MainActivity.a))||latlng!=null)
	         { if(progressDialog!=null && progressDialog.isShowing())
                 progressDialog.dismiss();
						
					 progressDialog = ProgressDialog.show(MainActivity.a, "Loading your data",
		                     "Please wait....It may take 1-2 minutes", true,true);
				count=0;
				 for(int i=0;i<places1.size();i++)
		             places1.remove(i);
		             
		         for(int j=0;j<places1_details.size();j++)
		             places1_details.remove(j);
		         
		         places1=null;
		         places1_details=null;
		         places1=new ArrayList<Place>();
		         places1_details=new ArrayList<Place_Details>();
				
		        
				
				
				LocationManager locationManager = (LocationManager)MainActivity.a.getSystemService(
		                Context.LOCATION_SERVICE);
		        LocationListener myLocationListener = new MyLocationListener();
		 
		        locationResult = new MyLocation.LocationResult() {
		            @Override
		            public void gotLocation(Location location) {
		                latitude = String.valueOf(location.getLatitude());
		                longitude = String.valueOf(location.getLongitude());
		                //progressDialog.dismiss();
		                MainActivity.latitude=latitude;
		                MainActivity.longitude=longitude;
		                new GetCurrentLocation().execute(latitude, longitude);
		            }
		        };
		 
		        MyRunnable myRun = new MyRunnable();
		        myRun.run();
			 
				
				//user = e_user.getText().toString();
				//pic = e_pic.getText().toString();
				//date = e_date.getText().toString();
	         }
	         else
	         {
	        	 Fragment fragment2 = new Blank();
	      		
	  		    FragmentManager fragmentManager = getFragmentManager();
	  		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	  		  // fragmentTransaction.addToBackStack(null); 
	  		    fragmentTransaction.replace(R.id.fragment1, fragment2);
	  		 
	  		    fragmentTransaction.commit();
 }
	     }
				
				
		return rootView;		
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        //setHasOptionsMenu(true);
    }
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		 ComponentName receiver = new ComponentName(MainActivity.a, NetworkChangeReceiver2.class);
         PackageManager pm = MainActivity.a.getPackageManager();
             pm.setComponentEnabledSetting(receiver,
                 PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                 PackageManager.DONT_KILL_APP);
         Toast.makeText(MainActivity.a,"Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
         
	}
	 
	private class GetCurrentLocation extends AsyncTask<Object, String, Boolean> {
		 
        @Override
        protected Boolean doInBackground(Object... myLocationObjs) {
        	
            if(null != latitude && null != longitude) {
                return true;
            } else {
                return false;
            }
           
        }
 
        @Override
        protected void onPostExecute(Boolean result) {
        	 super.onPostExecute(result);
             assert result;
            // query.append("https://maps.googleapis.com/maps/api/place/textsearch/json?query=pizza+in+Mumbai&");
             textquery.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
             textquery.append("location=" +  latitude + "," + longitude + "&");
             textquery.append("radius=" + radius + "&");
             textquery.append("types=" + type + "&");
             textquery.append("sensor=true&"); //Must be true if queried from a device with GPS*/
             textquery.append("key=" +APIKEY);
             //textsearch("Indian","Pizza");
            new QueryGooglePlaces().execute(textquery.toString(),null,null);
            
        }
    }
	
	
	 private class QueryGooglePlaces extends AsyncTask<String, String, String> {
		 
	    	
		
		 
	        @Override
	        protected String doInBackground(String... args) {
	        	
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpResponse response;
	            String responseString = null;
	            try {
	                response = httpclient.execute(new HttpGet(args[0]));
	                StatusLine statusLine = response.getStatusLine();
	                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	                    ByteArrayOutputStream out = new ByteArrayOutputStream();
	                    response.getEntity().writeTo(out);
	                    out.close();
	                    responseString = out.toString();
	                } else {
	                    //Closes the connection.
	                    response.getEntity().getContent().close();
	                    throw new IOException(statusLine.getReasonPhrase());
	                }
	            } catch (ClientProtocolException e) {
	                Log.e("ERROR", e.getMessage());
	            } catch (IOException e) {
	                Log.e("ERROR", e.getMessage());
	            }
	            return responseString;
	        }
	 
	        @Override
	        protected void onPostExecute(String result) {
	            super.onPostExecute(result);
	            try {
	            	System.out.println(result);
	            	if(new JSONObject(result).getString("status").equals("OK"))
	            	{
	            	//Log.v("Rahullach",result);
	            	//	rootView.findViewById(R.id.noresults).setVisibility(View.INVISIBLE);
	            	try {
	        			config = new Configuration(result);
	        			config.save(MainActivity.a);
	        		} catch (JSONException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            	Double lat=Double.parseDouble(latitude);
	            	Double lng=Double.parseDouble(longitude);
	            	for(int i=0;i<Configuration.load(MainActivity.a).getlength();i++)
	            	{
	            		Place place=new Place(i,lat,lng,null,null);
	            		place.setAttributes(MainActivity.a);
	            		//System.out.println(place.getPlace_id());
	            		 //new QueryGooglePlacesDetails().execute(place.getPlace_id());
	            			
	            		places1.add(place);
	            		String placeid=places1.get(i).getPlace_id();
	            	}
	            	

	            	
            		new QueryGooglePlacesDetails().execute(places1.get(0).getPlace_id(),null,null);
            	
	            	
	            	
	              	}
	            	else
	            	{
	            		if(progressDialog.isShowing())
	            			progressDialog.dismiss();
	            		flag=true;
	            		 Fragment fragment2 = new Blank();
	              		
	          		    FragmentManager fragmentManager = getFragmentManager();
	          		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	          		   fragmentTransaction.addToBackStack(null); 
	          		    fragmentTransaction.replace(R.id.fragment1, fragment2);
	          		 
	          		    fragmentTransaction.commit();

	            	}
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	

	               
	            	
	            } catch (Exception e) {
	                //Log.e("ERROR", e.getMessage());
	            }
	            
	        }
	 }
	    
	   
	 private class Receive extends AsyncTask<String, String, String> {
	        private InputStream is;
	       private String line;
	       private String l_user;
	       private String d_user;
	       private String result;
	       private int l;
	       private int d;
	       private SharedPreferences settings1;

	       protected String doInBackground(String... params) {
	          //  int count = urls.length;
	       String placeId=params[0];
	            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	                 settings1=MainActivity.a.getSharedPreferences("preference",0);

	                 nameValuePairs.add(new BasicNameValuePair("placeid",placeId));
	                 nameValuePairs.add(new BasicNameValuePair("user",""+settings1.getString("name", "mohit")));

	                         try
	                             {
	                              HttpClient httpclient = new DefaultHttpClient();
	                              HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//likesreceiver.php");
	                           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	                                 
	                         HttpResponse  response  = httpclient.execute(httppost); 
	                           HttpEntity entity = response.getEntity();
	                           is = entity.getContent();
	                           Log.d("mohit",entity.toString());
	                           Log.d("mohit",is.toString());
	                           Log.e("pass 1", "connection success ");
	                      }
	                          catch(Exception e)
	                      {
	                              Log.e("Fail 1", e.toString());
	                              Toast.makeText(Restaurant.getContext(), "Invalid IP Address",
	                              Toast.LENGTH_LONG).show();
	                      }     
	                        
	                         try
	                          {
	                              BufferedReader reader = new BufferedReader
	                              (new InputStreamReader(is,"iso-8859-1"),8);
	                              StringBuilder sb = new StringBuilder();
	                              while ((line = reader.readLine()) != null)
	                          {
	                                  sb.append(line + "\n");
	                              }
	                              is.close();
	                              result = sb.toString();
	                          Log.e("pass 2", "connection success ="+result);
	                      }
	                          catch(Exception e)
	                      {
	                              Log.e("Fail 2", e.toString());
	                      }     
	                      try
	                      {
	                              JSONObject json_data = new JSONObject(result);
	                              l=0;d=0;
	                        l=     Integer.parseInt(json_data.getString("likes"));
	                     d=  Integer.parseInt(json_data.getString("dislikes"));
	                     l_user=json_data.getString("likes_user");
	                        d_user=json_data.getString("dislikes_user");
	                    
	                      }
	                      catch(Exception e)
	                      {
	                              Log.e("Fail 3", e.toString());
	                      }

	                
	                return l+" "+d+" "+l_user+" "+d_user;


	        
	        
	        }

	        protected void onProgressUpdate(Integer... progress) {
	           // setProgressPercent(progress[0]);
	        }

	        protected void onPostExecute(String result) {
	           // showDialog("Downloaded " + result + " bytes");

	                      }

	        }
	    
	    

	   
	 
	 
	 
	 
	 
	    
	    private class QueryGooglePlacesDetails extends AsyncTask<String, String, String> {
	    	 
	    	String id;
	    	
	        @Override
	        protected String doInBackground(String... args) {
	        	
	        	id=args[0];
	        	
	        	 StringBuilder query_details = new StringBuilder();
	        	query_details.append("https://maps.googleapis.com/maps/api/place/details/json?");
	            query_details.append("placeid="+args[0]);
	            
	            query_details.append("&key=" +APIKEY);
	            HttpClient httpclient = new DefaultHttpClient();
	            HttpResponse response;
	            String responseString = null;
	            try {
	            	//try {
	            		//System.out.println(query_details.toString());
						/*Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
	                response = httpclient.execute(new HttpGet(query_details.toString()));
	                StatusLine statusLine = response.getStatusLine();
	                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	                    ByteArrayOutputStream out = new ByteArrayOutputStream();
	                    response.getEntity().writeTo(out);
	                    out.close();
	                    responseString = out.toString();
	                } else {
	                    //Closes the connection.
	                    response.getEntity().getContent().close();
	                    throw new IOException(statusLine.getReasonPhrase());
	                }
	            } catch (ClientProtocolException e) {
	                Log.e("ERROR", e.getMessage());
	            } catch (IOException e) {
	                Log.e("ERROR", e.getMessage());
	            }
	            return responseString;
	        }
	 
	        @Override
	        protected void onPostExecute(String result) {
	            super.onPostExecute(result);
	            try {
	            	System.out.println(result);
	            	//Log.v("Rahullach",result);
	            	if(new JSONObject(result).getString("status").equals("OK"))
	            	{
	            	try {
	        			detailedconfig = new DetailedConfiguration(result);
	        			detailedconfig.save(MainActivity.a,id);
	        		} catch (JSONException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	            	
	            	Place_Details place_det=new Place_Details();
	            	place_det.setAttributes(MainActivity.a, id);
	            	
	            	String res= new Receive().execute(id).get();
                    String arr[]=res.split(" ");
                    
                    place_det.setLD(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[3]));
                    Log.v("likes","likes="+Integer.parseInt(arr[0])+" dislikes="+Integer.parseInt(arr[1]));
            		
            
	            	
	            	
	            	places1_details.add(place_det);
	            	
	            	Log.v("place1",count+ places1.get(count).getPlace_id());
	            	Log.v("place2",count+ places1_details.get(count).getPlace_id());
	            	
	            	count++;
	            	if(count<places1.size())
	            	new QueryGooglePlacesDetails().execute(places1.get(count).getPlace_id(),null,null);
	            	else
	            	{
	            		
	        			db.insertRecords(places1, places1_details);
	            		if(progressDialog.isShowing())
	            			progressDialog.dismiss();
	            		  ComponentName receiver = new ComponentName(MainActivity.a, NetworkChangeReceiver2.class);
	                      PackageManager pm = MainActivity.a.getPackageManager();
	                          pm.setComponentEnabledSetting(receiver,
	                              PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
	                              PackageManager.DONT_KILL_APP);
	                      Toast.makeText(MainActivity.a,"Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
	                      
	            	
	            		 Fragment fragment2 = new mapOverView();
		            		
	            		    FragmentManager fragmentManager = getFragmentManager();
	            		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	            		    fragmentTransaction.replace(R.id.fragment1, fragment2);
	            		    fragmentTransaction.commit();

	            	}
	            	}
	            	else
	            	{
	            		if(progressDialog.isShowing())
	            			progressDialog.dismiss();
						flag=true;
						 Fragment fragment2 = new Blank();
				     		
				 		    FragmentManager fragmentManager = getFragmentManager();
				 		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				 		   fragmentTransaction.addToBackStack(null); 
				 		    fragmentTransaction.replace(R.id.fragment1, fragment2);
				 		 
				 		    fragmentTransaction.commit();

	            	}
	            		
	            		/*editor.putBoolean("DBCREATED",true);
	            		editor.commit();
	            		
	            		
	            		/*LazyImageLoadAdapter adapt=new LazyImageLoadAdapter(a,info);
	            		 LayoutInflater layout = (LayoutInflater)getSystemService(
	                             Context.LAYOUT_INFLATER_SERVICE
	                     );
	                     View row = layout.inflate(R.layout.httptestlist, null);
	                    listView = (ListView)row.findViewById(R.id.list);
	                    listView.setAdapter(adapt);*/
	                    
	            	
	            	//db.getAllRestaurants();
	            	//db.closeDB();
	            	//System.out.println(place_det.getOpen_hours()+" "+place_det.getRating()+" "+place_det.getWebsite());
	            		
	            		//places.add(place);
	            	
	            	//db.insertRecords(places, places_details);
	            	
	            	

	                
	 
	            } catch (Exception e) {
	                //Log.e("ERROR", e.getMessage());
	            }
	        }
	    }
	    
	
	
	    private class MyLocationListener implements LocationListener {

	        @Override
	        public void onLocationChanged(Location location) {
	            latitude = String.valueOf(location.getLatitude());
	            longitude = String.valueOf(location.getLongitude());
	        }

	        @Override
	        public void onStatusChanged(String s, int i, Bundle bundle) {
	        }

	        @Override
	        public void onProviderEnabled(String s) {
	        }

	        @Override
	        public void onProviderDisabled(String s) {
	        }
	    }

	    public class MyRunnable implements Runnable {
	        public MyRunnable() {
	        }

	        public void run() {
	            myLocation.getLocation(MainActivity.a, locationResult);
	        }
	    }
	    
	
	   
	
	

}

