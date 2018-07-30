package com.codingc.team20.restofinder;
import java.util.ArrayList;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

public class WishListHistory extends Fragment {
static String[][] wishes;
 static ListView list;
ScrollView parentScroll;
DatabaseHelper db;
static ArrayList<String> doubt2=new ArrayList<String>();
static ArrayList<String> textMessage2=new ArrayList<String>();
 public static ListAdapter adapter;
static Context context;
public static boolean token=false;
View rootView;
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
public WishListHistory(){
	
	
}
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
   
db=new DatabaseHelper(MainActivity.a);
/*if (AudioMainActivity.count==5)
	{
		setContentView(R.layout.nohistory);
	}
	
    
    else
    {
   */

	rootView = inflater.inflate(R.layout.wishlist, container,
			false);

  	if(MainActivity.key.size()!=0)
  		wishes=list2String(MainActivity.key);
   
    
	try{
    		adapter = new ListAdapter(MainActivity.a,wishes,context);
			list=(ListView)rootView.findViewById(R.id.lv);
			  			
	}
  catch(Exception e)
  {
  	Log.d("mohit","List Adapter problem");
  }
	/*
try{
			list.setOnTouchListener(new View.OnTouchListener() {

              public boolean onTouch(View v, MotionEvent event) {

            //	  v.getParent().requestDisallowInterceptTouchEvent(true);
                  return false;
              }
          });
			
}
  catch(Exception e)
  {
  	Log.d("mohit","onclick listeners");
  }
		*/	
			
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	            	String placeid=db.getPlaceId(wishes[position][1]);
	            	Log.v("mohit",placeid);
	            	ListAdapter.position=position;    	
	            	 Intent i=new Intent(MainActivity.a,Restaurant.class);
	            	 i.putExtra("placeid", placeid);
	            	 startActivity(i);
	            	
	            }
	     
		
		});
			
	
		list.setAdapter(adapter);
		list.setScrollingCacheEnabled(false);

			
	return rootView;		
			
     }		
  
  
public static void dofunction(){

			
            //	adapter=new ListAdapter(ViewHistory.this,doubt,textMessage);
	   			list.setAdapter(adapter);
	   			list.setScrollingCacheEnabled(false);

}
	


}
