package com.codingc.team20.restofinder;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
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

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
 
public class loginActivity extends Activity {
	 private static final String PERMISSION = "publish_actions";

	
private static int counter=0;
	String password;
	String id;
	InputStream is=null;
	String result=null;
	String line=null;
	int code;
	boolean trigger=false;
	  SharedPreferences.Editor editor;
	  SharedPreferences settings;
	    private LoginButton loginButton;
	    public static GraphUser user;
	    private boolean canPresentShareDialog;
	    private boolean canPresentShareDialogWithPhotos;

	
	//    private ProfilePictureView profilePictureView;
	    
	
	
	
	
	
	
	  
	
	
	 private UiLifecycleHelper uiHelper;

	    private Session.StatusCallback callback = new Session.StatusCallback() {
	        @Override
	        public void call(Session session, SessionState state, Exception exception) {
	            onSessionStateChange(session, state, exception);
	            Log.v("login","1");
	        }
	    };

	    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
	            Log.v("login","2");
		        
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.d("HelloFacebook", "Success!");
	            Log.v("login","3");
		        
	        }
	    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

       settings=this.getSharedPreferences("preference",0);
       editor=settings.edit(); 
 
        setContentView(R.layout.login);
        Log.v("login","4");
        

      //  final Thread thread2 = new Thread(){
        //    public void run(){
            

                loginButton = (LoginButton) findViewById(R.id.login_button);
                loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
                    @Override
                    public void onUserInfoFetched(GraphUser user) {
                        loginActivity.this.user = user;
                       
                        Log.v("login","5");
            	        
                          updateUI();
                        // It's possible that we were waiting for this.user to be populated in order to post a
                        // status update.
                     //   handlePendingAction();
                   new thr().execute();
                    }
                });            
            
          //  }
          //};
//thread2.start();

              	//try {
  				//	thread2.join();
  				
      	//} catch (InterruptedException e) {
  					// TODO Auto-generated catch block
  			//		e.printStackTrace();
  				//}
          
      //  profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        Log.v("login","6");
        
    //    setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
      final EditText e_id=(EditText) findViewById(R.id.editText1);
        final EditText e_password=(EditText) findViewById(R.id.editText3);
        Button login=(Button) findViewById(R.id.button1);
        TextView signup=(TextView) findViewById(R.id.button2);
        
        login.setOnClickListener(new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				
			id = e_id.getText().toString();
			password = e_password.getText().toString();
		
			validate();
		
		}
	});
    	SharedPreferences settings=this.getSharedPreferences("preference",0);
        final SharedPreferences.Editor editor3=settings.edit(); 
      
        signup.setOnClickListener(new View.OnClickListener() {
			
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    	/*	  
    			ImageView fbImage = ( ( ImageView)profilePictureView.getChildAt( 0));
    	     	    Bitmap    bitmap  = ( ( BitmapDrawable) fbImage.getDrawable()).getBitmap();
    	String path=saveToInternalSorage(bitmap);
    	editor3.putString("path",path);
    	loadImageFromStorage(path);
    	editor3.commit();
*/
    		Intent register=new Intent(loginActivity.this,signup.class);
    		startActivity(register);
    		}
    	});
   
    
    
    
        // Can we present the share dialog for regular links?
        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
        // Can we present the share dialog for photos?
        canPresentShareDialogWithPhotos = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.PHOTOS);
 
    
    
    
    }

    private class thr extends AsyncTask<String, String, String> {
 	     protected String doInBackground(String... str) {
    
 	       	        	     return null;
 	     
 	     }

 	     protected void onProgressUpdate(Integer... progress) {
 	        // setProgressPercent(progress[0]);
 	     }

 	     protected void onPostExecute(String result) {
      	if(trigger==true){
    //	      ImageView fbImage = ( ( ImageView)profilePictureView.getChildAt( 0));
	  //   	    Bitmap    bitmap  = ( ( BitmapDrawable) fbImage.getDrawable()).getBitmap();
//	String path=saveToInternalSorage(bitmap);
//	editor.putString("path",path);
	//loadImageFromStorage(path);
//	editor.commit();
trigger=false;
Intent i=new Intent(loginActivity.this,MainActivity.class);
i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
startActivity(i);
finish();
	

         }	
     	          

 	     } 

			 }
 	 

    public void validate()
    {
    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 
   	nameValuePairs.add(new BasicNameValuePair("id",id));
   	nameValuePairs.add(new BasicNameValuePair("password",password));
         	try
    	   {
		    HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//login.php");
	        
	       
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
            editor.putString("id",id);
            editor.putString("name",id);
            editor.putString("password", password);
           editor.commit();
            	Intent tile=new Intent(loginActivity.this,MainActivity.class);
            	 tile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(tile);
				finish();
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
    
    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
        Log.v("login","7");
        
        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
        Log.v("login","8");
        
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
        Log.v("login","9");
        
    }
    public static Intent getOpenFacebookIntent(Context context) {

		   try {
		    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);

			   Log.v("login","10");
		        
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/136220994671"));
		   } catch (Exception e) {

			   Log.v("login","10");
		        
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/136220994671"));
		   }
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();

		   Log.v("login","11");
	        
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();

		   Log.v("login","12");
	        
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        updateUI();

		   Log.v("login","13");
	          }

    private void updateUI() {
        Session session = Session.getActiveSession();
        boolean enableButtons = (session != null && session.isOpened());

		   Log.v("login","14");
	        
        if (enableButtons && user != null) {
        	SharedPreferences settings=this.getSharedPreferences("preference",0);
        	SharedPreferences.Editor editor=settings.edit(); 
        	editor.putString("id",user.getFirstName()+" "+user.getLastName());
            editor.putString("name",user.getFirstName()+" "+user.getLastName());    //////////////////////////////////
            editor.putString("graph",user.getId());
            editor.commit();
          String userString=user.getFirstName();
         counter++;
      /*   profilePictureView=(ProfilePictureView)findViewById(R.id.profilePicture);
	      profilePictureView.setProfileId(user.getId());

		   Log.v("login","15  -profile id="+profilePictureView.getProfileId());

		   Log.v("login","15  -profile="+profilePictureView);
	        


		   Log.v("login","15  -profile id="+profilePictureView.getContext());

		   Log.v("login","15  -profile id="+ profilePictureView.getWindowVisibility());
   */ trigger=true;
     	 
//if(counter==2){
 //}


          } else {
      //        profilePictureView.setProfileId(null);
        //  Toast.makeText(getApplicationContext(),"Login UnSuccessful : Try Again",     Toast.LENGTH_SHORT).show();
          
      }
    }
	/*private void loadImageFromStorage(String path) {
		// TODO Auto-generated method stub
		 try {
	            File f=new File(path, "profile.jpg");
	            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
	           ImageView img = (ImageView) findViewById(R.id.profpic);
			        if(img!=null)
	                img.setImageBitmap(b);
			        //invalidate();
			        img.postInvalidate();
	        } 
	        catch (FileNotFoundException e) 
	        {
	            e.printStackTrace();
	        }

	}*/
    private void handlePendingAction() {
        // These actions may re-set pendingAction if they are still pending, but we assume they
        // will succeed.
      
    }
    private String saveToInternalSorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
         // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        Log.v("login","file-path="+directory.getAbsolutePath());
        
        
        try {           

            fos = new FileOutputStream(mypath);

       // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        exportpic(directory.getAbsolutePath(),"profile.jpg");
        return directory.getAbsolutePath();
    }


    private void exportpic(String file2, String string) {
        // TODO Auto-generated method stub
 Log.v("mohit","exported");
            File sd = Environment.getExternalStorageDirectory();
    File data = Environment.getDataDirectory();
    FileChannel source=null;
    FileChannel destination=null;
    String currentDBPath = file2+"/"+string;
    Log.v("login",currentDBPath);
    String backupDBPath = "profile_tasty.jpg";
    File currentDB = new File(data, currentDBPath);
    File backupDB = new File(sd, backupDBPath);
    try {
        source = new FileInputStream(currentDB).getChannel();
        destination = new FileOutputStream(backupDB).getChannel();
        destination.transferFrom(source, 0, source.size());
        source.close();
        destination.close();
        Toast.makeText(MainActivity.a, "pic Exported", Toast.LENGTH_LONG).show();
    } catch(IOException e) {
            e.printStackTrace();
    }
    
}
    public interface GraphObjectWithId extends GraphObject {
        String getId();
    }

 
 

}