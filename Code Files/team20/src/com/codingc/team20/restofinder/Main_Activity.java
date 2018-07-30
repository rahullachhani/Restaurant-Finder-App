package com.codingc.team20.restofinder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Activity extends Fragment {
   public static  String latitude;
    public static String longitude;
    private boolean network_flag;
   // public static Activity a;
    String[][] filter;
    private String provider;
    private final String APIKEY = "AIzaSyCobsTWPTHBul5zbvsnHLCGzVvnTpKGKVw"; //REPLACE WITH YOUR OWN GOOGLE PLACES API KEY
      private final int radius =400;
    private String type = "food";
    private StringBuilder query = new StringBuilder();
   public static boolean flag=false;
    private ArrayList<Place> places = new ArrayList<Place>();
    private ArrayList<Place_Details> places_details = new ArrayList<Place_Details>();
    private ListView listView;
    MyLocation myLocation = new MyLocation();
    MyLocation.LocationResult locationResult;
    static ProgressDialog progressDialog = null;
    public Configuration config;
    public DetailedConfiguration detailedconfig;
    DatabaseHelper db;
    public static int count;
    public static final String PREF_NAME="GoogleMap" ;
    String[][] info;
    SharedPreferences settings;  
	SharedPreferences.Editor editor;
	ProgressDialog pd;
	long startTime;
	long elapsedTime;
	public boolean dbcreated=false;
	PlaceAdapter placeAdapter=null;
 View rootView;
private String[][] latlng;
private ListView list;
private ImageView img;
private String city;
static FragmentManager fmk;
	public Main_Activity(){
		
	} 
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
         rootView= inflater.inflate(R.layout.httptestlist, container, false);
         fmk=getFragmentManager();
         db=new DatabaseHelper(MainActivity.a);
         count=0;
         settings=MainActivity.a.getSharedPreferences(PREF_NAME,0);
         editor=settings.edit();
         img = (ImageView) rootView.findViewById(R.id.no_results);
         list=(ListView)rootView.findViewById(R.id.list);
         latlng=db.getLatLongTotal(true);
         if(latlng==null)
         {
         ComponentName receiver = new ComponentName(MainActivity.a,NetworkChangeReceiver.class);
         PackageManager pm = MainActivity.a.getPackageManager();

         pm.setComponentEnabledSetting(receiver,
                 PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                 PackageManager.DONT_KILL_APP);
       //  Toast.makeText(MainActivity.a, "Enabled broadcast receiver", Toast.LENGTH_SHORT).show();
         }
     //   a=this;
         boolean s=settings.getBoolean("NETWORK",false);
         if((settings.getBoolean("NETWORK",false)||NetworkState.isNetworkAvailable(MainActivity.a))||latlng!=null)
         {
        	 if(progressDialog!=null && progressDialog.isShowing())
        		 progressDialog.dismiss();
      editor.putBoolean("DBCREATEDFILTER",false);
      
         editor.commit();
      
        
         editor.putBoolean("SORTSELECTED",false);
                  editor.commit();
                  editor.putBoolean("SEARCH",false);
                  editor.commit();
                 
                  editor.putString("SELECTION",null);
                  editor.commit();

                  
                  
                  img.setBackgroundResource(R.drawable.no_results);
        img.setVisibility(View.INVISIBLE);
             
       if(latlng!=null)
       {
    	   info =db.getNearby();
   		editor.putBoolean("DBCREATED",true);
   		editor.commit();
   		
   		
   	 PlaceAdapter placeAdapter2 = new PlaceAdapter(MainActivity.a, R.layout.httptestrow, places,info,null);
	  listView = (ListView)rootView.findViewById(R.id.list);
	  listView.setAdapter(placeAdapter2);
	  listView.setOnItemClickListener(new OnItemClickListener() {
		  public void onItemClick(AdapterView<?> parent, View view,
				  int position, long id) {
			  Intent intent = new Intent(MainActivity.a, Restaurant.class);
              int i=position;
              intent.putExtra("placeid", db.getPlaceId(i));
     
              startActivity(intent);
       
		  		}
	  		});
       }
       else
       {
        LocationManager locationManager = (LocationManager) MainActivity.a.getSystemService(
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
        
       
        startTime=System.currentTimeMillis();
        progressDialog = ProgressDialog.show(MainActivity.a, "Loading your data",
                "Please wait...It may take  1-2 minutes", true,true);
              progressDialog.setCancelable(true);
       }
         }
         else
         {
        	 img.setBackgroundResource(R.drawable.network);
        	 Toast.makeText(MainActivity.a, "Just connect to the internet and data will load automatically", Toast.LENGTH_LONG).show();
         }
        return rootView;
    }
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setHasOptionsMenu(true);
    }
	
    
   


    public void showD()
    {
    //settings1=this.getSharedPreferences("Sort",0);
    //editor1=settings1.edit();
    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.a);
      /*  LayoutInflater adbInflater = LayoutInflater.from(MainActivity.a);
        final View eulaLayout = adbInflater.inflate(R.layout.house_popup_layout, null);
        //dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        adb.setView(eulaLayout);*/
        adb.setCancelable(false);
      
        adb.setTitle("No Network");
        adb.setMessage(Html.fromHtml("You are not connected to the network.Please connect to a network and try again."));

        //adb.setMessage(Html.fromHtml("You haven't yet registered yourself. Please register to be able to participate in events."));
        adb.setPositiveButton("Done",new DialogInterface.OnClickListener() {
            private String selection;

			public void onClick(DialogInterface dialog, int which) {
             	
           

       	       
       	   
               
               
                
             	  
             	  
                   
              
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               
            }
        });
        
       
    final AlertDialog dialog = adb.create();
   dialog.setCanceledOnTouchOutside(false);
   dialog.setCancelable(false);
   dialog.show();
  
    }
    
    
    public void showD2()
    {
    //settings1=this.getSharedPreferences("Sort",0);
    //editor1=settings1.edit();
    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.a);
      /*  LayoutInflater adbInflater = LayoutInflater.from(MainActivity.a);
        final View eulaLayout = adbInflater.inflate(R.layout.house_popup_layout, null);
        //dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        adb.setView(eulaLayout);*/
        adb.setCancelable(false);
      
        adb.setTitle("Caution");
        adb.setMessage(Html.fromHtml("This search gives better results beyond 500 m"));

        //adb.setMessage(Html.fromHtml("You haven't yet registered yourself. Please register to be able to participate in events."));
        adb.setPositiveButton("Done",new DialogInterface.OnClickListener() {
            private String selection;

			public void onClick(DialogInterface dialog, int which) {
             	
           

       	       
       	   
               
               
                
             	  
             	  
                   
              
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               
            }
        });
        
       
    final AlertDialog dialog = adb.create();
   dialog.setCanceledOnTouchOutside(false);
   dialog.setCancelable(false);
   dialog.show();
  
    }
    
    
    

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
    	Log.v("mohit","Achieveddddddd");
     super.onActivityResult(requestCode, resultCode, data);
     SharedPreferences settings=MainActivity.a.getSharedPreferences("Filter",0);

      flag=data.getBooleanExtra("Received", false);
      if(flag){
    	  String cuisine,type;
    	  cuisine=settings.getString("Cuisine", null);
    	  type=settings.getString("Category", null);
    	  if(cuisine!=null || type!=null)
    	  {
    		  if(NetworkState.isNetworkAvailable(MainActivity.a))
    		  {
    			 // showD2();
    	    	textsearch(cuisine, type);
    		  
    		  }
    		  else{
    			  showD();
    		  }
    		  }else{
    	  filter =db.getNearbyFilter();
    	  PlaceAdapter placeAdapter2 = new PlaceAdapter(MainActivity.a, R.layout.httptestrow, places,null,filter);
    	  listView = (ListView)rootView.findViewById(R.id.list);
    	  listView.setAdapter(placeAdapter2);
    	  listView.setOnItemClickListener(new OnItemClickListener() {
    		  public void onItemClick(AdapterView<?> parent, View view,
    				  int position, long id) {
    			  Intent intent = new Intent(MainActivity.a, Restaurant.class);
                   int i=position;
                   intent.putExtra("placeid", filter[i][4]);
          
                   startActivity(intent);
            
    		  		}
		  		});
    	//  placeAdapter2.notifyDataSetChanged();
     	 
    	  }
    
}
      else{
    	  
    	  
    	  refresh();
      }
    
    
    }
    
    
    
    
    
    
    
    
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu,inflater);
      inflater = MainActivity.a.getMenuInflater();
      inflater.inflate(R.menu.main_menu, menu);
    } 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
        
        case R.id.action_refresh:
        refresh();
        
        return super.onOptionsItemSelected(item);
        
        /*case R.id.action_search:
        Intent i=new Intent(MainActivity.a,SearchActivity.class);
        startActivity(i);
       
        return super.onOptionsItemSelected(item);
        */
            case R.id.filter:
            if(settings.getBoolean("DBCREATED",false))
            {
            editor.putBoolean("SORTSELECTED",false);
         	editor.commit();
         	editor.putString("SELECTION",null);
         	editor.commit();
            Intent intent=new Intent(MainActivity.a,filterActivity.class);
            startActivityForResult(intent, 0);
                return true;
            }
            else
            Toast.makeText(MainActivity.a, "Loading your data...Please wait!", Toast.LENGTH_LONG).show();
            //textsearch(null,"Pizza");
           
            return super.onOptionsItemSelected(item);
           
            case R.id.sort:
            if(settings.getBoolean("DBCREATED",false))
            {
           
            /* DialogFragment newFragment = new FirstDialog();
   	
   	
   	   newFragment.show(fm, "sort"); */
            editor.putBoolean("SORTSELECTED",false);
         	editor.commit();
         	editor.putString("SELECTION",null);
         	editor.commit();
         
            showDialog();
             
           
           
           
           

            }
            else
            Toast.makeText(MainActivity.a, "Loading your data...Please wait!", Toast.LENGTH_LONG).show();
           
            return super.onOptionsItemSelected(item);
           
            
           
          
            
            default:
                return super.onOptionsItemSelected(item);
        }
    }    
    @Override
    public void onDestroy(){
        super.onDestroy();
        exportDB();
       
        if(places!=null)
        for(int i=0;i<places.size();i++)
            places.remove(i);
         if(places_details!=null)   
        for(int j=0;j<places_details.size();j++)
            places_details.remove(j);

	//	MainActivity.a.getSharedPreferences("Filter",0).edit().clear().commit();
		  ComponentName receiver = new ComponentName(MainActivity.a, NetworkChangeReceiver.class);
          PackageManager pm = MainActivity.a.getPackageManager();
              pm.setComponentEnabledSetting(receiver,
                  PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                  PackageManager.DONT_KILL_APP);
       //   Toast.makeText(MainActivity.a,"Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
          
          
		 
	         

      //  android.os.Process.killProcess(android.os.Process.myPid());
    }
    
    private void exportDB() {
        // TODO Auto-generated method stub
 Log.v("mohit","exported");
            File sd = Environment.getExternalStorageDirectory();
    File data = Environment.getDataDirectory();
    FileChannel source=null;
    FileChannel destination=null;
    String currentDBPath = "/data/"+ "com.example.navigation" +"/databases/"+DatabaseHelper.DATABASE_NAME;
    String backupDBPath = DatabaseHelper.DATABASE_NAME+""+DatabaseHelper.DATABASE_VERSION;
    File currentDB = new File(data, currentDBPath);
    File backupDB = new File(sd, backupDBPath);
    try {
        source = new FileInputStream(currentDB).getChannel();
        destination = new FileOutputStream(backupDB).getChannel();
        destination.transferFrom(source, 0, source.size());
        source.close();
        destination.close();
   //     Toast.makeText(MainActivity.a, "DB Exported", Toast.LENGTH_LONG).show();
    } catch(IOException e) {
            e.printStackTrace();
    }
    
}
 
    public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
 
        InputSource is = new InputSource(new StringReader(xml));
 
        return builder.parse(is);
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
            query.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            query.append("location=" +  latitude + "," + longitude + "&");
            query.append("radius=" + radius + "&");
            query.append("types=" + type + "&");
            query.append("sensor=true&"); //Must be true if queried from a device with GPS*/
            query.append("key=" +APIKEY);
            //textsearch("Indian","Pizza");
            if(NetworkState.isNetworkAvailable(MainActivity.a))
           new QueryGooglePlaces().execute(query.toString(),null,null);
        }
    }
 
    /**
     * Based on: http://stackoverflow.com/questions/3505930
     */
    private class QueryGooglePlaces extends AsyncTask<String, String, String> {
 
    	String cuisine;
    	String type;
        @Override
        protected String doInBackground(String... args) {
        	cuisine=args[1];
        	type=args[2];
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
            	progressDialog.setMessage("It feels some error has occured.Please try again");
                Log.e("ERROR", e.getMessage());
            } catch (IOException e) {
                Log.e("ERROR", e.getMessage());
                progressDialog.setMessage("It feels some error has occured.Please try again");
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
            	
            	Log.v("Rahullach",result);
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
            		/*elapsedTime=startTime-System.currentTimeMillis();
            		if(elapsedTime>1000)
            			progressDialog.setMessage("It is taking longer time than usual.");
            		*/
            		Place place=new Place(i,lat,lng,cuisine,type);
            		place.setAttributes(MainActivity.a);
            		//System.out.println(place.getPlace_id());
            		 //new QueryGooglePlacesDetails().execute(place.getPlace_id());
            	/*	String res= new Receive().execute(place.getPlace_id()).get();
                    String arr[]=res.split(" ");
                    
                    place.setLD(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[3]));
                    Log.v("likes","likes="+Integer.parseInt(arr[0])+" dislikes="+Integer.parseInt(arr[1]));
            		*/
            		places.add(place);
            		String placeid=places.get(i).getPlace_id();	
            	}
            	
            	/*PlaceAdapter placeAdapter = new PlaceAdapter(Main_Activity.this, R.layout.httptestlist, places);
                listView = (ListView)findViewById(R.id.list);
                listView.setAdapter(placeAdapter);*/
            	if(cuisine==null && type==null)
            	{
            		progressDialog.dismiss();
            		
            	
            	
        		//if(settings.getBoolean("DBCREATED",false))
               	 placeAdapter = new PlaceAdapter(MainActivity.a, R.layout.httptestrow, places,null,null);
               //	else
               		//placeAdapter = new PlaceAdapter(Main_Activity.this, R.layout.httptestrow, places,null);
                    listView = (ListView)rootView.findViewById(R.id.list);
                    listView.setAdapter(placeAdapter);
                    listView.setOnItemClickListener(new OnItemClickListener() {

                    	@Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                            // TODO Auto-generated method stub
                        if(cuisine==null && type==null)
                        {
                       	if(settings.getBoolean("DBCREATED",false))
                       	{
                       	Intent intent = new Intent(MainActivity.a, Restaurant.class);
             	  //intent.putExtra("category", value)
                       	int i=position;
                       	if(cuisine==null && type==null)
                       	intent.putExtra("placeid", db.getPlaceId(i));
else
intent.putExtra("placeid", filter[i][4]);
             	 
             	          startActivity(intent);
                       	}
                       	else
                       	{
                       	Toast.makeText(MainActivity.a, "Loading your data...Please wait!", Toast.LENGTH_LONG).show();
                       	}
                        }
                        else
                        {
                       	if(settings.getBoolean("DBCREATEDFILTER",false))
                      {
                      Intent intent = new Intent(MainActivity.a, Restaurant.class);
              //intent.putExtra("category", value)
                      int i=position;
                      if(cuisine==null && type==null)
                      {}
else
intent.putExtra("placeid", filter[i][4]);
             
                      startActivity(intent);
                      }
                      else
                      {
                      Toast.makeText(MainActivity.a, "Loading your data...Please wait!", Toast.LENGTH_LONG).show();
                      }
                        }
                       	 
                        }
                    });
            	}
            	
            	if(cuisine==null && type==null)
            		new QueryGooglePlacesDetails().execute(places.get(0).getPlace_id(),null,null);
            	else if(cuisine!=null && type==null)
            		new QueryGooglePlacesDetails().execute(places.get(0).getPlace_id(),cuisine,null);
            	else if(cuisine==null && type!=null)
            		new QueryGooglePlacesDetails().execute(places.get(0).getPlace_id(),null,type);
            	else if(cuisine!=null && type!=null)
            		new QueryGooglePlacesDetails().execute(places.get(0).getPlace_id(),cuisine,type);
            	
            }
            else
            {
            progressDialog.dismiss();
            
            img.setVisibility(View.VISIBLE);
            list.setVisibility(View.INVISIBLE);
           
           
           
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
                             // Toast.makeText(Restaurant.getContext(), "Invalid IP Address",
                             // Toast.LENGTH_LONG).show();
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
    	String cuisine;
    	String type;
        @Override
        protected String doInBackground(String... args) {
        	
        	id=args[0];
        	cuisine=args[1];
        	type=args[2];
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
            	//System.out.println(result);
            	if(new JSONObject(result).getString("status").equals("OK"))
            	{
            	
            	Log.v("Rahullach",result);
            	
            	try {
        			detailedconfig = new DetailedConfiguration(result);
        			detailedconfig.save(MainActivity.a,id);
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            	
            	Place_Details place_det=new Place_Details();
            	place_det.setAttributes(MainActivity.a, id);
            	
            	//String res= getLikes(id);
            	
            	String res= new Receive().execute(id).get();
                String arr[]=res.split(" ");
                
                place_det.setLD(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[3]));
                Log.v("likes_mohit","likes="+Integer.parseInt(arr[0])+" dislikes="+Integer.parseInt(arr[1])+"l_user="+Integer.parseInt(arr[2])+"d_user="+Integer.parseInt(arr[3]));
            	
            	
            	
            	places_details.add(place_det);
            	
            	Log.v("place1",count+ places.get(count).getPlace_id());
            	Log.v("place2",count+ places_details.get(count).getPlace_id());
            	
            	count++;
            	if(count<places.size())
            		 
            			 new QueryGooglePlacesDetails().execute(places.get(count).getPlace_id(),cuisine,type);
            	else
            	{
            		
            		db.insertRecords(places, places_details);
                    if(progressDialog.isShowing())
                    {
                    progressDialog.dismiss();
                    
                    }
                    
                    ComponentName receiver = new ComponentName(MainActivity.a, NetworkChangeReceiver.class);
                    PackageManager pm = MainActivity.a.getPackageManager();
                        pm.setComponentEnabledSetting(receiver,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
                 //   Toast.makeText(MainActivity.a,"Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
                    
                    
        if(cuisine==null && type==null)
        {
                    info =db.getNearby();
                    editor.putBoolean("DBCREATED",true);
                    editor.commit();
                    PlaceAdapter placeAdapter2 = new PlaceAdapter(MainActivity.a, R.layout.httptestrow, places,info,null);
                         listView = (ListView)rootView.findViewById(R.id.list);
                         listView.setAdapter(placeAdapter2);
                         listView.setOnItemClickListener(new OnItemClickListener() {
                         public void onItemClick(AdapterView<?> parent, View view,
                         int position, long id) {
                         Intent intent = new Intent(MainActivity.a, Restaurant.class);
                                       int i=position;
                                       String pl=db.getPlaceId(i);
                                       intent.putExtra("placeid", db.getPlaceId(i));
                              
                                       MainActivity.a.startActivity(intent);
                                
                         	}
                     	});
        }
        else
        {
        filter=db.getNearbyFilter();
                    editor.putBoolean("DBCREATEDFILTER",true);
                    editor.commit();
                    PlaceAdapter placeAdapter2 = new PlaceAdapter(MainActivity.a, R.layout.httptestrow, places,null,filter);
                       listView = (ListView)rootView.findViewById(R.id.list);
                       listView.setAdapter(placeAdapter2);
                       listView.setOnItemClickListener(new OnItemClickListener() {
                       public void onItemClick(AdapterView<?> parent, View view,
                       int position, long id) {
                       Intent intent = new Intent(MainActivity.a, Restaurant.class);
                                     int i=position;
                                     intent.putExtra("placeid", filter[i][4]);
                            
                                     MainActivity.a.startActivity(intent);
                              
                       	}
                   	});
        }
                   
                    }
                    }
                    else
                    {
                    progressDialog.dismiss();
                    
                    img.setVisibility(View.VISIBLE);
                    list.setVisibility(View.INVISIBLE);
                   
                   
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
    
    
    private class PlaceAdapter extends ArrayAdapter<Place> {
        public Context context;
        public int layoutResourceId;
        public ArrayList<Place> places;
        String[][] info;
        String[][] filter;

        public PlaceAdapter(Context context, int layoutResourceId, ArrayList<Place> places,String[][] info,String[][] filter) {
            super(context, layoutResourceId, places);
            this.layoutResourceId = layoutResourceId;
            this.places = places; 
            this.info=info;
            this.filter=filter;
        }
        @Override
        public int getCount() {
        if(flag)
        {
        if(filter!=null )
        {
        int i=filter.length;
        if(i>0)
        {
        img.setVisibility(View.INVISIBLE);
           list.setVisibility(View.VISIBLE);
        }
        else
        {
        img.setVisibility(View.VISIBLE);
           list.setVisibility(View.INVISIBLE);
        }
        return filter.length;
        }
        return 0;
        }
           
        else if(info!=null )
        {
        if(info.length>0)
        {
        img.setVisibility(View.INVISIBLE);
           list.setVisibility(View.VISIBLE);
        }
        else
        {
        img.setVisibility(View.VISIBLE);
           list.setVisibility(View.INVISIBLE);
        } 
        return info.length;
        }
       
        else
        {
        if(places.size()>0)
        {
        img.setVisibility(View.INVISIBLE);
           list.setVisibility(View.VISIBLE);
        }
        else 
        {
        img.setVisibility(View.VISIBLE);
           list.setVisibility(View.INVISIBLE);
        }
        return places.size();
        }
        }

        @Override
        public View getView(int rowIndex, View convertView, ViewGroup parent) {
            View row = convertView;
            LayoutInflater layout = null;
            if(null == layout) {
                layout = (LayoutInflater)MainActivity.a.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
            }
            if(null==row)
            	row = layout.inflate(R.layout.httptestrow, null);
           
          
            TextView dist=(TextView)row.findViewById(R.id.distance);
            
            TextView name = (TextView) row.findViewById(R.id.name);
            Typeface font = Typeface.createFromAsset(MainActivity.a.getAssets(),"fonts/Mark Simonson - Proxima Nova Soft Bold.otf"); 
             name.setTypeface(font);
            TextView vicinity = (TextView) row.findViewById(
                    R.id.address);
            Typeface font1 = Typeface.createFromAsset(MainActivity.a.getAssets(), "fonts/Mark Simonson - Proxima Nova Soft Regular.otf");
            vicinity.setTypeface(font1);
            TextView rating = (TextView) row.findViewById(R.id.rating);
            Typeface font2 = Typeface.createFromAsset(MainActivity.a.getAssets(), "fonts/ratingz.ttf");
            rating.setTypeface(font2);
            
            TextView likes = (TextView) row.findViewById(R.id.textView1);
            Typeface font3 = Typeface.createFromAsset(MainActivity.a.getAssets(), "fonts/empty.ttf");
            likes.setTypeface(font3);
            
            TextView dislikes = (TextView) row.findViewById(R.id.textView2);
            
            dislikes.setTypeface(font3);
            dist.setTypeface(font3);
        	
            
           if(flag)
           {
        	//   		if(settings.getBoolean("DBCREATEDFILTER",false))
        	  // 			filter=db.getNearbyFilter();
                   	if(null != filter) {
                   				name.setText(filter[rowIndex][0]);
                            dist.setText(filter[rowIndex][2]);
                    //    if(null != vicinity) {
                        	String v=filter[rowIndex][1];
                        	int i=v.lastIndexOf(",");
                        	String sub=getCity(v);
                            vicinity.setText(sub);
                            likes.setText(""+filter[rowIndex][5]+" likes");
                            dislikes.setText(""+filter[rowIndex][6]+ "dislikes");

                      //  }
                       
                        rating.setText(filter[rowIndex][3]);
                     }
                   
                   else{
                	   Place place = places.get(rowIndex);
                        name.setText(place.getName());
                        dist.setText(""+place.getDistance());
                   // if(null != vicinity) {
                    	String v=place.getVicinity();
                    	int i=v.lastIndexOf(",");
                    	String sub=getCity(v);
                        vicinity.setText(sub);
                        likes.setText("Loading...");
                        //     if(place.retdisLikes()!=0)
                                     dislikes.setText("Loading...");
        
                    
                    //}
                    rating.setText(place.getRating());
                   }
           }
           else
           {
            
           if(settings.getBoolean("DBCREATED",false))
           info=db.getNearby();
           if(null != info) {
            	
                    name.setText(info[rowIndex][0]);
                    dist.setText(info[rowIndex][2]);
                if(null != vicinity) {
                	String v=info[rowIndex][1];
                	int i=v.lastIndexOf(",");
                	String sub=getCity(v);
                    vicinity.setText(sub);
                    likes.setText(""+info[rowIndex][4]+" likes");
                    //     if(place.retdisLikes()!=0)
                                 dislikes.setText(""+info[rowIndex][5]+ "dislikes");
                }
               
                rating.setText(info[rowIndex][3]);
            }
           
           else{
        	   Place place = places.get(rowIndex);
                name.setText(place.getName());
                dist.setText(""+place.getDistance());
            if(null != vicinity) {
            	String v=place.getVicinity();
            	int i=v.lastIndexOf(",");
            	String sub=getCity(v);
                vicinity.setText(sub);
                likes.setText("Loading...");
                //     if(place.retdisLikes()!=0)
                             dislikes.setText("Loading...");
  
            
            }
            rating.setText(place.getRating());
           }
           }
            return row;
        }
    }
    public String getCity(String vicinity) {
    	// TODO Auto-generated method stub
    	String[] a;
    	String v=vicinity;
    	a=v.split(",");
    	String s=a[a.length-1].trim();
    	if(s.equals("India")||s.equals("Maharashtra")||s.equals("Mumbai")||s.equals("Greater Mumbai")||s.equals("Navi Mumbai")||s.equals("Thane"))
    	{
    	city=a[a.length-1];
    	int i=v.lastIndexOf(",");
    	if(i==(-1))
    	return city;
    	String sub=v.substring(0,i);
    	s=getCity(sub);
    	}
    	else
    	{ 
    	return s;
    	}
    	    //int i=v.lastIndexOf(",");
    	    //String sub=v.substring(i+1);
    	   
    	return s;
    	}
    	    
    	    public void textsearch(String cuisine,String type)
    	    
    	    {
    	   
    	    if(db.getCuisineAndType(cuisine, type))
    	    {
    	    filter=db.getNearbyFilter();
    	    editor.putBoolean("DBCREATEDFILTER",true);
    	    editor.commit();
    	    PlaceAdapter placeAdapter2 = new PlaceAdapter(MainActivity.a, R.layout.httptestrow, places,null,filter);
    	       listView = (ListView)rootView.findViewById(R.id.list);
    	       listView.setAdapter(placeAdapter2);
    	       listView.setOnItemClickListener(new OnItemClickListener() {
    	       public void onItemClick(AdapterView<?> parent, View view,
    	       int position, long id) {
    	       Intent intent = new Intent(MainActivity.a, Restaurant.class);
    	                     int i=position;
    	                     intent.putExtra("placeid", filter[i][4]);
    	            
    	                     MainActivity.a.startActivity(intent);
    	              
    	       	}
    	   	});
    	    }
    	   
    	    else
    	    {
    	    	progressDialog = ProgressDialog.show(MainActivity.a, "Loading your data",
                        "Please wait...It may take  1-2 minutes", true,true);
                     count=0;
    	    for(int i=0;i<places.size();i++)
    	             places.remove(i);
    	             
    	         for(int j=0;j<places_details.size();j++)
    	             places_details.remove(j);
    	         
    	         places=null;
    	         places_details=null;
    	         places=new ArrayList<Place>();
    	         places_details=new ArrayList<Place_Details>();
    	         
    	         
    	     StringBuilder textquery = new StringBuilder();
    	   if(type.equals("Cakes and Pastery"))
    		   type="Bakeries";
    	     if(cuisine!=null && type!=null)
    	      textquery.append("https://maps.googleapis.com/maps/api/place/search/json?keyword="+type);
    	     if(cuisine!=null && type==null)
    	      textquery.append("https://maps.googleapis.com/maps/api/place/textsearch/json?query="+cuisine+"+Restaurant");
    	     if(cuisine==null && type!=null)
    	      textquery.append("https://maps.googleapis.com/maps/api/place/search/json?keyword="+type);
    	   
    	          //  textquery.append("https://maps.googleapis.com/maps/api/place/search/json?");
    	          // textquery.append("keyword=Italian+pizza");
    	          // query.append("name=Amar+fast+Food&");
    	            
    	     textquery.append("&location=" +  latitude + "," + longitude + "&");
    	     textquery.append("radius=2000&");
    	     textquery.append("sensor=true&"); 
    	     textquery.append("key=" +APIKEY);
    	     new QueryGooglePlaces().execute(textquery.toString(),cuisine,type);
    	    }
    	    }
    	    public void showDialog()
    	    {
    	    //settings1=this.getSharedPreferences("Sort",0);
    	    //editor1=settings1.edit();
    	    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.a);
    	        LayoutInflater adbInflater = LayoutInflater.from(MainActivity.a);
    	        final View eulaLayout = adbInflater.inflate(R.layout.house_popup_layout, null);
    	        //dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
    	        adb.setView(eulaLayout);
    	        adb.setCancelable(false);
    	      
    	        adb.setTitle("Select to sort your results");
    	        //adb.setMessage(Html.fromHtml("You haven't yet registered yourself. Please register to be able to participate in events."));
    	        adb.setPositiveButton("Done",new DialogInterface.OnClickListener() {
    	            private String selection;

					public void onClick(DialogInterface dialog, int which) {
    	             	
    	            PlaceAdapter placeAdapter4=null;
    	                    RadioGroup rg = (RadioGroup) eulaLayout.findViewById(R.id.radioHouse);
    	                if(rg.getCheckedRadioButtonId()!=-1){
    	                   int id= rg.getCheckedRadioButtonId();
    	                   View radioButton = rg.findViewById(id);
    	                   int radioId = rg.indexOfChild(radioButton);
    	                   RadioButton btn = (RadioButton) rg.getChildAt(radioId);
    	                   selection = (String) btn.getText();
    	                   
    	                   
    	                   editor.putBoolean("SORTSELECTED",true);
    	               	editor.commit();
    	               	editor.putString("SELECTION", selection);
    	               	editor.commit();
    	               	Boolean f=settings.getBoolean("SORTSELECTED",false);
    	               	String s=settings.getString("SELECTION",null);
    	               	if(flag)
    	               	{
    	               	filter=db.getNearbyFilter();
    	               	placeAdapter4 = new PlaceAdapter(MainActivity.a, R.layout.httptestrow, places,null,filter);
    	               	
    	               	}
    	               	else
    	               	{
    	               	info=db.getNearby();
    	               	placeAdapter4 = new PlaceAdapter(MainActivity.a, R.layout.httptestrow, places,info,null);
    	               	}
    	                   	 listView = (ListView)rootView.findViewById(R.id.list);
    	                   	 listView.setAdapter(placeAdapter4);
    	                   	 listView.setOnItemClickListener(new OnItemClickListener() {
    	                   	 public void onItemClick(AdapterView<?> parent, View view,
    	                   	 int position, long id) {
    	                   	 Intent intent = new Intent(MainActivity.a, Restaurant.class);
    	                                  int i=position;
    	                                  String placeid=null;
    	                                  info=db.getNearby();
    	                                  String[][] s=info;
    	                                  if(flag)
    	                                  {
    	                               	 placeid=filter[i][4];
    	                                  }
    	                                  else if(info!=null)
    	                                  {
    	                               	   placeid=info[i][6];
    	                                  }
    	                                  
    	                                  intent.putExtra("placeid",placeid);
    	                         
    	                                  MainActivity.a.startActivity(intent);
    	                           
    	                   	 	}
    	               	 	});
    	               	
    	                   	 
    	                }
    	                 
    	               
    	       	           	           
    	       	           	
    	       	           	
    	       	
    	                   	/*LayoutInflater inflater=getActivity().getLayoutInflater();
    	                           View row = inflater.inflate(R.layout.overview, null);
    	                           setBack(row,settings.getString("HOUSE","normal"));
    	                   	*/
    	               	//RelativeLayout content = (RelativeLayout)row.findViewById(R.id.rr); 
    	       	              
    	       	              
    	       	                  
    	       	             
    	       	          

    	       	       
    	       	   
    	               
    	               
    	                
    	             	  
    	             	  
    	                   
    	              
    	            }
    	        });

    	        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	            public void onClick(DialogInterface dialog, int which) {
    	               
    	            }
    	        });
    	        
    	       
    	    final AlertDialog dialog = adb.create();
    	   dialog.setCanceledOnTouchOutside(false);
    	   dialog.setCancelable(false);
    	   dialog.show();
    	   editor.putBoolean("SORTSELECTED",false);
     	  editor.commit();
     	  editor.putString("SELECTION",null);
     	  editor.commit();

    	    }
    	    public void refresh()
    	    {
    	    editor.putBoolean("SORTSELECTED",false);
    	  editor.commit();
    	  editor.putString("SELECTION",null);
    	  editor.commit();
    	  editor.putBoolean("DBCREATEDFILTER",false);
    	  editor.commit();
    	 
    	    if(settings.getBoolean("DBCREATED",false))
    	    {
    	    /*Intent i2=new Intent(Main_Activity.this,sendHTTPData.class);
    	    startActivity(i2);
    	        return true;*/
    	    flag=false;
    	    MainActivity.a.getSharedPreferences("Filter",0).edit().clear().commit();
    	    info=db.getNearby();
    	    PlaceAdapter placeAdapter3 = new PlaceAdapter(MainActivity.a, R.layout.httptestrow, places,info,null);
    	       	 listView = (ListView)rootView.findViewById(R.id.list);
    	       	 listView.setAdapter(placeAdapter3);
    	       	 listView.setOnItemClickListener(new OnItemClickListener() {
    	       	 public void onItemClick(AdapterView<?> parent, View view,
    	       	 int position, long id) {
    	       	 Intent intent = new Intent(MainActivity.a, Restaurant.class);
    	                      int i=position;
    	                      intent.putExtra("placeid",  db.getPlaceId(i));
    	             
    	                      MainActivity.a.startActivity(intent);
    	               
    	       	 	}
    	   	 	});
    	    }
    	    else
    	    Toast.makeText(MainActivity.a, "Loading your data...Please wait!", Toast.LENGTH_LONG).show();
    	   
    	    }
    	    
    	    public String getLikes(String placeid)
    	    {
    	    	  InputStream is=null;
    	         String line=null;
    	         String l_user=null;
    	          String d_user=null;
    	         String result=null;
    	          int l=0;
    	          int d=0;
    	          SharedPreferences settings1;

    	    	String placeId=placeid;
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
                                  //Toast.makeText(Restaurant.getContext(), "Invalid IP Address",
                                  //Toast.LENGTH_LONG).show();
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
    	    
    	    
    	    
    	    }
 


   