package com.codingc.team20.restofinder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

public class Tab3AdminFragment extends Fragment {
public static  ArrayList<String[]> info;
 static ListView list;
 InputStream is;
ScrollView parentScroll;
 public static ListAdapter3 adapter;
static Context context;
public static boolean token=false;
View rootView;
//static int count=0;
//String copy[][];
public Tab3AdminFragment(){
	
	
	
}
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
rootView = inflater.inflate(R.layout.myconfirmation, container,
		
			false);
	
	
  	


    
    		list=(ListView)rootView.findViewById(R.id.lv);
	/*		  			
	
try{
			list.setOnTouchListener(new View.OnTouchListener() {

              public boolean onTouch(View v, MotionEvent event) {
                 // Log.v("CHILD", "CHILD TOUCH");
                  // Disallow the touch request for parent scroll on touch of
                  // child view
                  v.getParent().requestDisallowInterceptTouchEvent(true);
                  return false;
              }
          });
			
}
  catch(Exception e)
  {
  	Log.d("mohit","onclick listeners");
  }
			*/
			

			/*
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	      //  ListAdapter.position=position;    	
	
	            }
	     
		
		});
			*/
	new Receive().execute();






SwipeDismissListViewTouchListener touchListener =
        new SwipeDismissListViewTouchListener(
                list,
                new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                        	
                        	deleteUser(position);
                        	//adapter.remove(adapter.getItem(position));                                
                        }
                        //adapter.notifyDataSetChanged();
                    }
                });

list.setOnTouchListener(touchListener);
// Setting this scroll listener is required to ensure that during ListView scrolling,
// we don't look for swipes.
list.setOnScrollListener(touchListener.makeScrollListener());







			
	return rootView;		
			
     }		  

protected void deleteUser(int position) {
	// TODO Auto-generated method stub
	
	new delete().execute("mohit",info.get(position)[0],info.get(position)[2],info.get(position)[1]);
	info.remove(position);
	adapter.notifyDataSetChanged();
	
	
	
	
	
	
	
	
	
}

// Params,Progress,Result
private class Receive extends AsyncTask<Void, Void, String[][]> {
   String result;
	protected String[][] doInBackground(Void... str) {
      //  int count = urls.length;
    //	SharedPreferences s=MainActivity.a.getSharedPreferences("preferences", 0);
   	   	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
         	nameValuePairs.add(new BasicNameValuePair("admin","mohit"));
         	  
         	try
          	   {
      		    HttpClient httpclient = new DefaultHttpClient();
      	        HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//receiveOrderOwner.php");
      	       
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
      	    	//Toast.makeText(Restaurant.a.getApplicationContext(), "Invalid IP Address",
      			//Toast.LENGTH_LONG).show();
      	}     
            
             try
              {
            	 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
         	    StringBuilder sb = new StringBuilder();

         	    String line = null;
         	    try {
         	        while ((line = reader.readLine()) != null) {
         	            sb.append(line + "\n");
         	        }
         	    } catch (IOException e) {
         	        e.printStackTrace();
         	    } finally {
         	        try {
         	            is.close();
         	        } catch (IOException e) {
         	            e.printStackTrace();
         	        }
         	    }
                result = sb.toString();
      	    Log.e("pass 2", "connection success ="+result);
      	}
              catch(Exception e)
      	{
                  Log.e("Fail 2", e.toString());
      	}     
             
             String rs[][] = null;
         	try
    		{
         		
    	            JSONObject json_data = new JSONObject(result);
    	            int code=json_data.getInt("code");
    	            if(code==1){
    	            rs=new String[json_data.length()-1][3];
    	            
    	            for(int j=0;j<(json_data.length()-1);j++){
    	            JSONArray js=json_data.getJSONArray(""+j);	
    	            	rs[j][0]=js.getString(0);
    	            	rs[j][1]=js.getString(1);
    	            	rs[j][2]=js.getString(2);
    	            	
    	            }
         		}
         		else{
         			
         	rs=null;		
         		}
    	            
    	         
    	            
    	           // Intent i=new Intent(signup.this,Main_Activity.class);
    	           // startActivity(i);
    	            
    	    }
    		catch(Exception e)
    		{
    	            Log.e("Fail 3", e.toString());
    		}

      	        	     return rs;
    
    }

    protected void onProgressUpdate(Void param) {
       // setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String[][] result) {
       // showDialog("Downloaded " + result + " bytes");
    if(result!=null)
    	info=new ArrayList<String[]>(Arrays.asList(result));

    //	count=info.size();
    	try
	       	{
   			
    		 if(info!=null && result!=null)
    	        {
    			    
    			    
    				adapter = new ListAdapter3(adminMainActivity.a,info,context);
    				list.setAdapter(adapter);
    				list.setScrollingCacheEnabled(false);
     }
    			else
    	    		Toast.makeText(MainActivity.a,"No Updates", Toast.LENGTH_SHORT).show();
    		
    	     }
           	catch(Exception e)
	       	{
	                   Log.e("Fail 3", e.toString());
	       	}

    
    return;
    
    }




	 }


private class delete extends AsyncTask<String, String, String> {
   String result;
   int code=0;
private InputStream is;
	protected String doInBackground(String... str) {
		
		String owner,name,mobile,date;
		owner=str[0];
		name=str[1];
		mobile=str[2];
		date=str[3];
		
		
		
		 	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
         	nameValuePairs.add(new BasicNameValuePair("owner",owner));
         	nameValuePairs.add(new BasicNameValuePair("name",name));
         	nameValuePairs.add(new BasicNameValuePair("mobile",mobile));
         	nameValuePairs.add(new BasicNameValuePair("date",date));
         	  
         	try
          	   {
      		    HttpClient httpclient = new DefaultHttpClient();
      	        HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//delete.php");
      	       
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
      	  //  	Toast.makeText(Restaurant.a.getApplicationContext(), "Invalid IP Address",
      		//	Toast.LENGTH_LONG).show();
      	}     
            
             try
              {
            	 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
         	    StringBuilder sb = new StringBuilder();

         	    String line = null;
         	    try {
         	        while ((line = reader.readLine()) != null) {
         	            sb.append(line + "\n");
         	        }
         	    } catch (IOException e) {
         	        e.printStackTrace();
         	    } finally {
         	        try {
         	            is.close();
         	        } catch (IOException e) {
         	            e.printStackTrace();
         	        }
         	    }
                result = sb.toString();
      	    Log.e("pass 2", "connection success ="+result);
      	}
              catch(Exception e)
      	{
                  Log.e("Fail 2", e.toString());
      	}     
             
             String rs = null;
         	try
    		{
         		
    	            JSONObject json_data = new JSONObject(result);
    	            code=json_data.getInt("code");
    	    
    	    }
    		catch(Exception e)
    		{
    	            Log.e("Fail 3", e.toString());
    		}

      	        	     return ""+code;
    
    }

    protected void onProgressUpdate(Void param) {
    }

    protected void onPostExecute(String result) {

    
    return;
    
    }



	 }


public static void dofunction(){

			
            //	adapter=new ListAdapter(ViewHistory.this,doubt,textMessage);
	   			list.setAdapter(adapter);
	   			list.setScrollingCacheEnabled(false);

}
	


}
