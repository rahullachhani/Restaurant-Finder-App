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


import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
 
public class signup extends Activity {

	String name;
	String password;
	String id;
	
	InputStream is=null;
	String result=null;
	String line=null;
	int code;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
       final EditText e_id=(EditText) findViewById(R.id.editText4);
        final EditText e_name=(EditText) findViewById(R.id.editText1);
        final EditText e_password=(EditText) findViewById(R.id.editText5);
        
       
        Button register=(Button) findViewById(R.id.button2);
        
        register.setOnClickListener(new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				
			id = e_id.getText().toString();
			name = e_name.getText().toString();
			password = e_password.getText().toString();
			Log.d("mohit",""+id+""+name+""+""+password);
			if(id.equals("") || name.equals("") ||   password.equals("") )
				Toast.makeText(getBaseContext(), "All Fields are Mandatory",
						Toast.LENGTH_SHORT).show();
			else          
			insert();
		}
	});
    }
 
    public void insert()
    {
    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 
   	nameValuePairs.add(new BasicNameValuePair("id",id));
   	nameValuePairs.add(new BasicNameValuePair("name",name));

   	nameValuePairs.add(new BasicNameValuePair("password",password));
  
   	
   	
   	
   	
   	try
    	   {
		    HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//insert.php");
	       
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
	    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
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
		Toast.makeText(getBaseContext(), "Login Successfully",
			Toast.LENGTH_SHORT).show();
	  	SharedPreferences settings=this.getSharedPreferences("preference",0);
        SharedPreferences.Editor editor=settings.edit(); 
        editor.putString("id",id);
        editor.putString("name",id);
        editor.putString("password", password);
        editor.commit();


            
            Intent i=new Intent(signup.this,MainActivity.class);
            startActivity(i);
            
            }
            else
            {
		 Toast.makeText(getBaseContext(), "Sorry, Try Again",
			Toast.LENGTH_LONG).show();
            }
	}
	catch(Exception e)
	{
            Log.e("Fail 3", e.toString());
	}
    }
	
    	
    
    

}