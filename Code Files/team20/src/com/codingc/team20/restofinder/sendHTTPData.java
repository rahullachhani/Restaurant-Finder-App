package com.codingc.team20.restofinder;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;
 
public class sendHTTPData extends Activity {

	String placeid;
	double rating;
	String review;
	String user;
	String pic;
	String date;
	InputStream is=null;
	String result=null;
	String line=null;
	int code=0;
	 private RatingBar ratingBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendhttp);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        final EditText e_review=(EditText) findViewById(R.id.editText3);
    	SharedPreferences settings=this.getSharedPreferences("preference",0);
    	user=settings.getString("name", "mohit");
    	getActionBar().setTitle(Restaurant.restaurantName);
		
        ImageButton register=(ImageButton) findViewById(R.id.button2);
        
        register.setOnClickListener(new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				
		rating = ratingBar.getRating();
		review = e_review.getText().toString();
	 
		//user = e_user.getText().toString();
		//pic = e_pic.getText().toString();
		//date = e_date.getText().toString();
		 
		
		
		new	insert().execute();
		}
	});
    }
 private static String convertStreamToString(InputStream is) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
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
	    return sb.toString();
	}

    	
    private class insert extends AsyncTask<String, String, String> {
	     protected String doInBackground(String... str) {
	       //  int count = urls.length;
	    		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    		String placeId=Restaurant.placeId;
	    	   	nameValuePairs.add(new BasicNameValuePair("placeid",placeId));
	    	   	nameValuePairs.add(new BasicNameValuePair("rating",""+rating));

	    	   	nameValuePairs.add(new BasicNameValuePair("review",review));
	    	 	nameValuePairs.add(new BasicNameValuePair("user",user));
	    	 	nameValuePairs.add(new BasicNameValuePair("pic","pic"));
	    	 	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	 	       //get current date time with Date()
	 	       Date date = new Date();
	 	 
	    	 	nameValuePairs.add(new BasicNameValuePair("date",dateFormat.format(date)));
	    	 	  
	    	   	
	    	   	
	    	   	
	    	   	
	    	   	try
	    	    	   {
	    			    HttpClient httpclient = new DefaultHttpClient();
	    		        HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//insert2.php");
	    		       
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
	    	//	    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
	    		//		Toast.LENGTH_LONG).show();
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
	    	            code=(json_data.getInt("code"));
	    				Log.d("mohit","code="+code);
	    		}   	catch(Exception e)
	    		{
	    	            Log.e("Fail 3", e.toString());
	    		}  	        	     return result;
	     
	     }

	     protected void onProgressUpdate(Integer... progress) {
	        // setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(String result) {
	    	    if(code==1)
	            {
			Toast.makeText(getBaseContext(), "Submitted Successfully",
				Toast.LENGTH_LONG).show();
	            
	            
	           // Intent i=new Intent(signup.this,Main_Activity.class);
	           // startActivity(i);
	            
	            }
	            else
	            {
			 Toast.makeText(getBaseContext(), "Sorry, Try Again",
				Toast.LENGTH_LONG).show();
	            }
	    	    Intent intent=new Intent();
        	   
        	    setResult(RESULT_OK, intent);
        	    finish();
		}
	
	     
	     }

		 }