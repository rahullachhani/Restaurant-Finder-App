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


public class ListAdapter2 extends ArrayAdapter<String> {
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
ImageView ind;
static int position=0;
// Constructor 
public ListAdapter2(Activity context,ArrayList<String[]> wishes,Context cc) {
super(context,R.layout.row_booking,wishes.get(0));
this.context2 = context;
this.wishes = wishes;
this.cx=cc;
}

//this.imageId = imageId;



@Override
public View getView(final int position, View view, ViewGroup parent) {
LayoutInflater inflater =  context2.getLayoutInflater();
View rowView= inflater.inflate(R.layout.row_booking, null, true);

TextView doubtt = (TextView) rowView.findViewById(R.id.t1);
ind=(ImageView) rowView.findViewById(R.id.indicator);
//ImageView imageinlist = (ImageView) rowView.findViewById(R.id.t3);
try{
	if(wishes.get(position)[0]!=null)
		doubtt.setText(wishes.get(position)[0]);
	if(wishes.get(position)[1]!=null)
	{
		if(Integer.parseInt(wishes.get(position)[1])==1)
			ind.setBackgroundResource(R.drawable.confirmed);
		else
			ind.setBackgroundResource(R.drawable.pending);
				
	}
}
catch(Exception e)
{
Log.d("mohit", "count="+count);	
}
    	
    	


	        	        	
	        	
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

}
	        