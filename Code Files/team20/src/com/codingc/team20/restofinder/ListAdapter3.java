package com.codingc.team20.restofinder;



import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ListAdapter3 extends ArrayAdapter<String> {
int count=0;	
private final Activity context2;
Context cx;
Socket socket;
String macid;
String dbt,txt;
DataInputStream dis;
DataOutputStream dos;
//private final Integer[] imageId;
ArrayList<String[]> wishes;
TextView ind;
private ImageButton confirm;
static int position=0;
// Constructor 
public ListAdapter3(Activity context,ArrayList<String[]> wishes,Context cc) {
super(context,R.layout.row_confirmation,wishes.get(0));
this.context2 = context;
this.wishes = wishes;
this.cx=cc;
}

//this.imageId = imageId;



@Override
public View getView(final int position, View view, ViewGroup parent) {
LayoutInflater inflater =  context2.getLayoutInflater();
View rowView= inflater.inflate(R.layout.row_confirmation, null, true);


confirm=(ImageButton)rowView.findViewById(R.id.cnf);
TextView doubtt = (TextView) rowView.findViewById(R.id.t1);
ind=(TextView) rowView.findViewById(R.id.t2);


TextView mobile = (TextView) rowView.findViewById(R.id.t3);



//ImageView imageinlist = (ImageView) rowView.findViewById(R.id.t3);
try{
	if(wishes.get(position)[0]!=null)
		doubtt.setText(wishes.get(position)[0]);
	if(wishes.get(position)[1]!=null)
	{
		ind.setText("Date: "+wishes.get(position)[1]);
				
	}
	if(wishes.get(position)[2]!=null)
	{
		mobile.setText("Contact: "+wishes.get(position)[2]);
				
	}



}
catch(Exception e)
{
Log.d("mohit", "count="+count);	
}
confirm.setOnClickListener(new OnClickListener(){
	@Override
	public void onClick(View v) {

		final Context context=getContext();
			AlertDialog.Builder adb=new AlertDialog.Builder(context);
	        adb.setTitle("Confirm?");
	        adb.setMessage("Are you sure you want to Confirm this booking?");

	        final int positionToRemove = position;
	        adb.setNegativeButton("Cancel", null);
	       
	        try{
	        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int which) {
	   Log.d("mohit","Reached successfully");
	        		
	   
	  
	   try{
	          	
	            	new Order().execute("mohit",wishes.get(position)[0],wishes.get(position)[1],wishes.get(position)[2]);
	            	Tab3AdminFragment.info.remove(positionToRemove);
	            	Tab3AdminFragment.adapter.notifyDataSetChanged();
	                
	                       	
	   }
	            catch(Exception e)
	            {
	            	Log.d("mohit","Access problem");
	            }
	            
	            Log.d("mohit", "Reacheddddddddddddddd");
	         /*   try{
	            	
	           Intent intr=new Intent("com.example.navigation.WishListHistory");
	      	
	            intr.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            cx.startActivity(intr);
	            
	            
	            Log.d("mohit","msg token sent");
	            

	            
	            context2.finish();
	            
	            }
	            catch(Exception e)
	            {
	            	Log.d("mohit", "abc:"+e);
	            }
	            
	           */ 
	            
	        	  }});
	       
	        
	        }catch(Exception e){
	        	Log.d("mohit","calling problem");
	        }
	        adb.show();

    	
    	
    	
	        }
	
});




	        	        	
	        	
return rowView;
}

@Override
public int getCount()
{
if(wishes!=null)	
	return wishes.size();
else
	return 0;
}

private class Order extends AsyncTask<String, String, String> {
   String result;
   int code=0;
private InputStream is;
	protected String doInBackground(String... str) {
      //  int count = urls.length;
		
		
		String owner,name,mobile,date;
		owner=str[0];
		name=str[1];
		date=str[2];
		mobile=str[3];
		if(mobile==null)
			mobile="";
		Log.v("dbcheck",owner+name+date+mobile);
    //	SharedPreferences s=MainActivity.a.getSharedPreferences("preferences", 0);
   	   	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
         	nameValuePairs.add(new BasicNameValuePair("owner",owner));
         	nameValuePairs.add(new BasicNameValuePair("name",name));
         	nameValuePairs.add(new BasicNameValuePair("mobile",mobile));
         	nameValuePairs.add(new BasicNameValuePair("date",date));
         	  
         	try
          	   {
      		    HttpClient httpclient = new DefaultHttpClient();
      	        HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//sendConfirmation.php");
      	       
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
      	//    	Toast.makeText(Restaurant.a.getApplicationContext(), "Invalid IP Address",
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



}
	        