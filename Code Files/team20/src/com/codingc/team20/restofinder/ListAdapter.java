package com.codingc.team20.restofinder;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdapter extends ArrayAdapter<String> {
int count=0;	
private final Activity context2;
Context cx;
Socket socket;
String macid;
String dbt,txt;
DataInputStream dis;
DataOutputStream dos;
//private final Integer[] imageId;
String[][] wishes;
ImageView delete;
static int position=0;
// Constructor 
public ListAdapter(Activity context,String[][] wishes,Context cc) {
super(context,R.layout.row,MainActivity.key);
this.context2 = context;
this.wishes = wishes;
this.cx=cc;
}

//this.imageId = imageId;



public String[][] list2String(ArrayList<String> a)
{
	String info[][]=new String[a.size()][3];
	
	
	for(int i=0;i<a.size();i++)
		{
		String[] tr=a.get(i).split("#");
		for(int j=0;j<3;j++)
		{
			info[i][j]=tr[j];
		}
		}
	
	return info;
}



@Override
public View getView(final int position, View view, ViewGroup parent) {
LayoutInflater inflater =  context2.getLayoutInflater();
View rowView= inflater.inflate(R.layout.row, null, true);

TextView doubtt = (TextView) rowView.findViewById(R.id.t1);
TextView txtMessage = (TextView) rowView.findViewById(R.id.t2);
delete=(ImageView) rowView.findViewById(R.id.delete);
//ImageView imageinlist = (ImageView) rowView.findViewById(R.id.t3);
try{
	if(wishes[position][1]!=null)
		doubtt.setText(wishes[position][1].trim());
	if(wishes[position][2]!=null)
		txtMessage.setText(wishes[position][2].trim());
//count++;
}
catch(Exception e)
{
Log.d("mohit", "count="+count);	
}//imageinlist.setImageResource(imageId[position]);
delete.setOnClickListener(new OnClickListener(){
	@Override
	public void onClick(View v) {

		final Context context=getContext();
			AlertDialog.Builder adb=new AlertDialog.Builder(context);
	        adb.setTitle("Delete?");
	        adb.setMessage("Are you sure you want to delete this wish?");

	        final int positionToRemove = position;
	        adb.setNegativeButton("Cancel", null);
	       
	        try{
	        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int which) {
	   Log.d("mohit","Reached successfully");
	        		
	   
	  
	   try{
	          	
	            	
	            	MainActivity.key.remove(positionToRemove);
	            	wishes=list2String(MainActivity.key);
	            	WishListHistory.adapter.notifyDataSetChanged();
	            	WishListHistory.token=true;
	                
	            	/*   	AudioMainActivity.doubt.remove(positionToRemove);
	            	AudioMainActivity.textMessage.remove(positionToRemove);
	            	AudioMainActivity.count=5-AudioMainActivity.doubt.size();
	            	Log.d("mohit","doubt size="+AudioMainActivity.doubt.size());
	            	Log.d("mohit","doubt count="+AudioMainActivity.count);
	            	
	            	//AudioMainActivity.count--;
	            	AudioMainActivity.counter.setText("Doubt remaining:"+(AudioMainActivity.count));
	           */
	            	
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



}
	        