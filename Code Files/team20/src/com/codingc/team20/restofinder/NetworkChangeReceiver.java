package com.codingc.team20.restofinder;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver  {
	 SharedPreferences settings;
	 SharedPreferences.Editor editor;
	 boolean gps_avail;
	
    @Override
    public void onReceive(final Context context, final Intent intent) {
 
        String status = NetworkUtil.getConnectivityStatusString(context);
       // LocationManager manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
       // gps_avail=manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(status.equals("Not connected to Internet"))
        {
        	//Toast.makeText(context, "in android.location.PROVIDERS_CHANGED",
             //       Toast.LENGTH_SHORT).show();
        	settings=MainActivity.a.getSharedPreferences("GoogleMap",0);
        	editor=settings.edit();
        	editor.putBoolean("NETWORK",false);
            editor.commit();
           
        }
        else
        {
        
        	settings=MainActivity.a.getSharedPreferences("GoogleMap",0);
        	editor=settings.edit();
        	editor.putBoolean("NETWORK",true);
            editor.commit();
            boolean s=MainActivity.a.getSharedPreferences("GoogleMap",0).getBoolean("NETWORK",false);
            

            Fragment fragment2 = new Main_Activity();
     		
 		    FragmentManager fragmentManager = Main_Activity.fmk;
 		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
 		 //  fragmentTransaction.addToBackStack(null); 
 		    fragmentTransaction.replace(R.id.frag10, fragment2);
 		 
 		    fragmentTransaction.commit();
/*
            
            Fragment fragment2 = new Main_Activity();
     		
    		    FragmentManager fragmentManager =MainActivity.fm;
    		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    		    fragmentTransaction.addToBackStack(null);
    		    fragmentTransaction.replace(R.id.frag10, fragment2);
    		   // fragmentTransaction.remove((Fragment)Main_Activity.fmk.findFragmentById(R.id.frag1));
    		  
    		   fragmentTransaction.commitAllowingStateLoss();
    		    //fragmentTransaction.commit();
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
  */          
        }
       
        
    }
		
	
}