package com.codingc.team20.restofinder;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class loginAdmin extends Fragment {

	
	String password;
	String id;
	InputStream is=null;
	String result=null;
	String line=null;
	int code;
	boolean trigger=false;
	  SharedPreferences.Editor editor;
	  SharedPreferences settings;
	private View rootView;
	
	    
	
	
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	         rootView= inflater.inflate(R.layout.loginadmin, container, false);
	  //     settings=MainActivity.a.getSharedPreferences("preference",0);
      // editor=settings.edit(); 
	         if (android.os.Build.VERSION.SDK_INT > 9) {
	             StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	             StrictMode.setThreadPolicy(policy);
	         }
	  
        

      //  final Thread thread2 = new Thread(){
        //    public void run(){
            

      final EditText e_id=(EditText) rootView.findViewById(R.id.editText1);
        final EditText e_password=(EditText)rootView.findViewById(R.id.editText3);
        Button login=(Button) rootView.findViewById(R.id.button1);
        
        login.setOnClickListener(new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				
			id = e_id.getText().toString();
			password = e_password.getText().toString();
		
			validate();
		
		}
	});
    //	SharedPreferences settings=this.getSharedPreferences("preference",0);
      //  final SharedPreferences.Editor editor3=settings.edit(); 
      
       return rootView;
    
       }
    public void validate()
    {
    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 
   	nameValuePairs.add(new BasicNameValuePair("id",id));
   	nameValuePairs.add(new BasicNameValuePair("password",password));
         	try
    	   {
		    HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//login_admin.php");
	        
	       
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
	    	Toast.makeText(MainActivity.a, "Invalid IP Address",
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
            code=(json_data.getInt("code"));
			Log.d("mohit","code="+code);
            if(code==1)
            {
    //        editor.putString("id",id);
      //      editor.putString("name",id);
        //    editor.putString("password", password);
          // editor.commit();
            	Intent tile=new Intent(MainActivity.a,adminMainActivity.class);
        //    	 tile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(tile);
		    }
            else
            {
		 Toast.makeText(MainActivity.a, "Sorry, Try Again",
			Toast.LENGTH_LONG).show();
            }
	}
	catch(Exception e)
	{
            Log.e("Fail 3", e.toString());
	}
    }
    

 
 

}