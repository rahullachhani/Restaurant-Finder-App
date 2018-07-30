package com.codingc.team20.restofinder;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class SplashScreen extends Activity {
Thread t;
String user;
boolean b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		b=true;
		  ComponentName receiver = new ComponentName(this, NetworkChangeReceiver.class);
          PackageManager pm = this.getPackageManager();
              pm.setComponentEnabledSetting(receiver,
                  PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                  PackageManager.DONT_KILL_APP);
         // Toast.makeText(this,"Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
          
          ComponentName receiver2 = new ComponentName(this, NetworkChangeReceiver2.class);
          PackageManager pm2 = this.getPackageManager();
              pm2.setComponentEnabledSetting(receiver2,
                  PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                  PackageManager.DONT_KILL_APP);
        //  Toast.makeText(this,"Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
          
          
		SharedPreferences settings=this.getSharedPreferences("preference",0);
		SharedPreferences.Editor editor=settings.edit(); 
		user=settings.getString("id","null");

		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                               // WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		 t = new Thread(){
			public void run() {
				try{
				//	Thread.sleep(3000);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(b){
					if(user.equals("null"))
					{	Intent intent = new Intent(SplashScreen.this,loginActivity.class);
				    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				    startActivity(intent);
					finish();
					}
					else
					{Intent i = new Intent(SplashScreen.this,MainActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
					finish();
						
					}
					}
				}
			};
		};
		t.start();
	}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	b=false;
finish();	
}
}
