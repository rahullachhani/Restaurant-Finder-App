package com.codingc.team20.restofinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FirstDialog extends DialogFragment{

	
	
	FragmentManager fm;
	
	public static final String PREFS_NAME="Sort";
	AlertDialog d;
	String selection;
	

	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		final SharedPreferences settings=getActivity().getSharedPreferences(PREFS_NAME,0);
		final SharedPreferences.Editor editor=settings.edit();
		
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        LayoutInflater adbInflater = LayoutInflater.from(getActivity());
        final View eulaLayout = adbInflater.inflate(R.layout.house_popup_layout, null);
        //dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        adb.setView(eulaLayout);
        adb.setCancelable(false);
      
        adb.setTitle("Select to sort your results");
        //adb.setMessage(Html.fromHtml("You haven't yet registered yourself. Please register to be able to participate in events."));
        adb.setPositiveButton("Done",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            	             	
            	
                		
                
                		RadioGroup rg = (RadioGroup) eulaLayout.findViewById(R.id.radioHouse);
                		if(rg.getCheckedRadioButtonId()!=-1){
                		    int id= rg.getCheckedRadioButtonId();
                		    View radioButton = rg.findViewById(id);
                		    int radioId = rg.indexOfChild(radioButton);
                		    RadioButton btn = (RadioButton) rg.getChildAt(radioId);
                		    selection = (String) btn.getText();
                		}
                		
    	    	            	             	
    	    	            	
    	    	            	editor.putBoolean("SORTSELECTED",true);
    	                		editor.commit();
    	                		editor.putString("SELECTION", selection);
    	                		editor.commit();
    	                		
    	                		
    	    					
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
   

    return dialog;
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}

