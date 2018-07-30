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

public class NetworkChangeReceiver2 extends BroadcastReceiver  {
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
       	editor.putBoolean("NETWORKFRAG",false);
           editor.commit();
          // showDialog();
       }
       else
       {
       	settings=MainActivity.a.getSharedPreferences("GoogleMap",0);
       	editor=settings.edit();
       	editor.putBoolean("NETWORKFRAG",true);
           editor.commit();
           boolean s=MainActivity.a.getSharedPreferences("GoogleMap",0).getBoolean("NETWORKFRAG",false);
           Fragment fragment2 = new SearchFragment();
         
		    FragmentManager fragmentManager = Main_Activity.fmk;
		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		 //  fragmentTransaction.addToBackStack(null); 
		    fragmentTransaction.replace(R.id.fragment1, fragment2);
		 
		    fragmentTransaction.commit();
/*
    		    FragmentManager fragmentManager =MainActivity.fm;
    		    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    		    fragmentTransaction.replace(R.id.fragment1, fragment2);
    		   // fragmentTransaction.remove((Fragment)Main_Activity.fmk.findFragmentById(R.id.frag1));
    		   fragmentTransaction.addToBackStack(null);
    		   fragmentTransaction.commitAllowingStateLoss();
    */		    //fragmentTransaction.commit();
       //    Toast.makeText(context, status, Toast.LENGTH_LONG).show();
           
           
       }
     
   }
	public void showDialog()
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
	
	
}
