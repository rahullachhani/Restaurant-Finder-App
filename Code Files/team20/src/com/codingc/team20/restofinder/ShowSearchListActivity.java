package com.codingc.team20.restofinder;

import java.util.ArrayList;




import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ShowSearchListActivity extends Activity {

	View rootView;
	ImageView img;
	ListView list;
	String city;
	String[][] info=null;
	public DatabaseHelper db;
	PlaceAdapter placeAdapter;
	boolean flag;
	boolean flag1;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.httptestlist);
        db=new DatabaseHelper(this);
        settings=this.getSharedPreferences("GoogleMap",0);
        editor=settings.edit();
        flag=getIntent().getBooleanExtra("flag",true);
		 
        LayoutInflater  inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView= inflater.inflate(R.layout.httptestlist ,null);
        img=(ImageView) findViewById(R.id.no_results);
        list=(ListView)findViewById(R.id.list);
        if(!flag)
        {
        img.setVisibility(View.INVISIBLE);
        list.setVisibility(View.VISIBLE);
        }
        else
        {
        	img.setVisibility(View.VISIBLE);
            list.setVisibility(View.INVISIBLE);
        }
        	
        
        if(!flag)
        {
        info=db.getNearbySearch();
        editor.putBoolean("SEARCH",true);
        editor.commit();
		 placeAdapter = new PlaceAdapter(ShowSearchListActivity.this, R.layout.httptestrow,info);
        
       //  list = (ListView)findViewById(R.id.list);
         list.setAdapter(placeAdapter);
         list.setOnItemClickListener(new OnItemClickListener() {

             @Override
             public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                 
            	 Intent intent = new Intent(ShowSearchListActivity.this, Restaurant.class);
  			  
            	 int i=position;
            	 String pi=info[i][4];
            	 	
            		 intent.putExtra("placeid",info[i][4]);
					
            		 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  	           ShowSearchListActivity.this.startActivity(intent);
            	
             	
             
            	 
             }

         });
        }
}
	@Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
    	Log.v("mohit","Achieveddddddd");
     super.onActivityResult(requestCode, resultCode, data);
     SharedPreferences settings=this.getSharedPreferences("Filter",0);

      flag1=data.getBooleanExtra("Received", false);
      if(flag1){
    	 
    	  info =db.getNearbySearchFilter();
    	  PlaceAdapter placeAdapter2 = new PlaceAdapter(this, R.layout.httptestrow,info);
    	//  list = (ListView)rootView.findViewById(R.id.list);
    	  list.setAdapter(placeAdapter2);
    	  list.setOnItemClickListener(new OnItemClickListener() {
    		  public void onItemClick(AdapterView<?> parent, View view,
    				  int position, long id) {
    			  Intent intent = new Intent(ShowSearchListActivity.this, Restaurant.class);
                   int i=position;
                   intent.putExtra("placeid", info[i][4]);
          
                   ShowSearchListActivity.this.startActivity(intent);
            
    		  		}
		  		});
    	//  placeAdapter2.notifyDataSetChanged();
     	 
    	
    
}
      else{
    	  
    	  
    	  refresh();
      }
    
    
    }
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	super.onCreateOptionsMenu(menu);
	    	 MenuInflater inflater = getMenuInflater();
	    	    inflater.inflate(R.menu.main_menu, menu);
	    	    return true;
	    } 
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId())
	        {
	        
	        case R.id.action_refresh:
	        refresh();
	        
	        return super.onOptionsItemSelected(item);
	        
	     /*   case R.id.action_search:
	        Intent i=new Intent(ShowSearchListActivity.this,SearchActivity.class);
	        startActivity(i);
	       
	        return super.onOptionsItemSelected(item);
	       */ 
	            case R.id.filter:
	           
	            editor.putBoolean("SORTSELECTED",false);
	         	editor.commit();
	         	editor.putString("SELECTION",null);
	         	editor.commit();
	            Intent intent=new Intent(this,FilterActivitySearch.class);
	            startActivityForResult(intent, 0);
	              
	            return super.onOptionsItemSelected(item);
	           
	            case R.id.sort:
	           
	           
	            /* DialogFragment newFragment = new FirstDialog();
	   	
	   	
	   	   newFragment.show(fm, "sort");*/
	            editor.putBoolean("SORTSELECTED",false);
	         	editor.commit();
	         	editor.putString("SELECTION",null);
	         	editor.commit();
	         
	            showDialog();
	             
	           
	           
	           
	           

	           
	            return super.onOptionsItemSelected(item);
	           
	            
	           
	           
	            
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	    }   
	    public void refresh()
	    {
	    editor.putBoolean("SORTSELECTED",false);
	  editor.commit();
	  editor.putString("SELECTION",null);
	  editor.commit();
	  editor.putBoolean("DBCREATEDFILTER",false);
	  editor.commit();
	 
	   
	    /*Intent i2=new Intent(Main_Activity.this,sendHTTPData.class);
	    startActivity(i2);
	        return true;*/
	    flag1=false;
	    MainActivity.a.getSharedPreferences("Filter",0).edit().clear().commit();
	    info=db.getNearbySearch();
	    PlaceAdapter placeAdapter3 = new PlaceAdapter(this, R.layout.httptestrow,info);
	      //	 list = (ListView)rootView.findViewById(R.id.list);
	       	 list.setAdapter(placeAdapter3);
	       	 list.setOnItemClickListener(new OnItemClickListener() {
	       	 public void onItemClick(AdapterView<?> parent, View view,
	       	 int position, long id) {
	       	 Intent intent = new Intent(ShowSearchListActivity.this, Restaurant.class);
	                      int i=position;
	                      intent.putExtra("placeid",  info[i][4]);
	             
	                     ShowSearchListActivity.this.startActivity(intent);
	               
	       	 	}
	   	 	});
	   
	    }
	    public void showDialog()
	    {
	    //settings1=this.getSharedPreferences("Sort",0);
	    //editor1=settings1.edit();
	    AlertDialog.Builder adb = new AlertDialog.Builder(this);
	        LayoutInflater adbInflater = LayoutInflater.from(this);
	        final View eulaLayout = adbInflater.inflate(R.layout.house_popup_layout, null);
	        //dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
	        adb.setView(eulaLayout);
	        adb.setCancelable(false);
	      
	        adb.setTitle("Select to sort your results");
	        //adb.setMessage(Html.fromHtml("You haven't yet registered yourself. Please register to be able to participate in events."));
	        adb.setPositiveButton("Done",new DialogInterface.OnClickListener() {
	            private String selection;

				public void onClick(DialogInterface dialog, int which) {
	             	
	            PlaceAdapter placeAdapter4=null;
	                    RadioGroup rg = (RadioGroup) eulaLayout.findViewById(R.id.radioHouse);
	                if(rg.getCheckedRadioButtonId()!=-1){
	                   int id= rg.getCheckedRadioButtonId();
	                   View radioButton = rg.findViewById(id);
	                   int radioId = rg.indexOfChild(radioButton);
	                   RadioButton btn = (RadioButton) rg.getChildAt(radioId);
	                   selection = (String) btn.getText();
	                   
	                   
	                   editor.putBoolean("SORTSELECTED",true);
	               	editor.commit();
	               	editor.putString("SELECTION", selection);
	               	editor.commit();
	               	Boolean f=settings.getBoolean("SORTSELECTED",false);
	               	String s=settings.getString("SELECTION",null);
	               	if(flag1)
	               	{
	              info=db.getNearbySearchFilter();
	               	placeAdapter4 = new PlaceAdapter(ShowSearchListActivity.this, R.layout.httptestrow,info);
	               	
	               	}
	               	else
	               	{
	               	info=db.getNearbySearch();
	               	placeAdapter4 = new PlaceAdapter(ShowSearchListActivity.this, R.layout.httptestrow,info);
	               	}
	                //   	 list = (ListView)rootView.findViewById(R.id.list);
	                   	 list.setAdapter(placeAdapter4);
	                   	 list.setOnItemClickListener(new OnItemClickListener() {
	                   	 public void onItemClick(AdapterView<?> parent, View view,
	                   	 int position, long id) {
	                   	 Intent intent = new Intent(ShowSearchListActivity.this, Restaurant.class);
	                                  int i=position;
	                                  String placeid=null;
	                                  info=db.getNearbySearch();
	                                  String[][] s=info;
	                                 
	                               	   placeid=info[i][4];
	                                  
	                                  
	                                  intent.putExtra("placeid",placeid);
	                         
	                                 ShowSearchListActivity.this.startActivity(intent);
	                           
	                   	 	}
	               	 	});
	               	
	                   	 
	                }
	                 
	               
	       	           	           
	       	           	
	       	           	
	       	
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
	   dialog.show();
	   editor.putBoolean("SORTSELECTED",false);
 	  editor.commit();
 	  editor.putString("SELECTION",null);
 	  editor.commit();

	    }
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		SharedPreferences   settings=this.getSharedPreferences("GoogleMap",0);
		SharedPreferences.Editor editor=settings.edit();
		editor.putBoolean("SEARCH",false);
		 editor.commit();
		 
	}
	
	
	 private class PlaceAdapter extends ArrayAdapter<String[][]> {
	        public Context context;
	        public int layoutResourceId;
	       // public ArrayList<Place> places;
	        String[][] info;
	       // String[][] filter;

	        public PlaceAdapter(Context context, int layoutResourceId,String[][] info) {
	            super(context, layoutResourceId);
	            this.layoutResourceId = layoutResourceId;
	            //this.places = places; 
	            this.info=info;
	          //  this.filter=filter;
	        }
	        @Override
	        public int getCount() {
	        	
	        	if(null!=info)
	        	{
	        		int l=info.length;
	        		if(info.length>0)
	        		{
	        			img.setVisibility(View.INVISIBLE);
	          		  list.setVisibility(View.VISIBLE);
	        		}
	        		else
	        		{
	        			img.setVisibility(View.VISIBLE);
	          		  list.setVisibility(View.INVISIBLE);
	        		} 
	        		return info.length;
	        	
	        	}
	        	return 4;
	        }

	        @Override
	        public View getView(int rowIndex, View convertView, ViewGroup parent) {
	             View row = convertView;
	            if(null == row) {
	                LayoutInflater layout = (LayoutInflater)getSystemService(
	                        Context.LAYOUT_INFLATER_SERVICE
	                );
	                row = layout.inflate(R.layout.httptestrow, null);
	           }
	          
	            TextView dist=(TextView)row.findViewById(R.id.distance);
	            
	            TextView name = (TextView) row.findViewById(R.id.name);
	            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Mark Simonson - Proxima Nova Soft Bold.otf"); 
	             name.setTypeface(font);
	            TextView vicinity = (TextView) row.findViewById(
	                    R.id.address);
	            Typeface font1 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Mark Simonson - Proxima Nova Soft Regular.otf");
	            vicinity.setTypeface(font1);
	            TextView rating = (TextView) row.findViewById(R.id.rating);
	            Typeface font2 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/ratingz.ttf");
	            rating.setTypeface(font2);
	            
	            TextView likes = (TextView) row.findViewById(R.id.textView1);
	            Typeface font3 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/empty.ttf");
	            likes.setTypeface(font3);
	            
	            TextView dislikes = (TextView) row.findViewById(R.id.textView2);
	            
	            dislikes.setTypeface(font3);
	            dist.setTypeface(font3);
	        	
	            
	         
	         
	            
	        //   if(settings.getBoolean("DBCREATED",false))
	          /*  if(flag1)
	            	info=db.getNearbySearchFilter();
	            else
	            	info=db.getNearbySearch();*/
	           if(null != info) {
	            	
	                    name.setText(info[rowIndex][0]);
	                    dist.setText(info[rowIndex][2]+" m");
	                if(null != vicinity) {
	                	String v=info[rowIndex][1];
	                	int i=v.lastIndexOf(",");
	                	//String sub=v.substring(i+1);
	                	String sub=getCity(v);
	                    vicinity.setText(sub);
	                    likes.setText(""+info[rowIndex][5]+" likes");
	                    //     if(place.retdisLikes()!=0)
	                                 dislikes.setText(""+info[rowIndex][6]+ "dislikes");
	                }
	               
	                rating.setText(info[rowIndex][3]);
	            }
	           
	          
	            return row;
	        }
			
	    }
	    
	    
	    public String getCity(String vicinity) {
			// TODO Auto-generated method stub
			String[] a;
			String v=vicinity;
			a=v.split(",");
			String s=a[a.length-1].trim();
			if(s.equals("India")||s.equals("Maharashtra")||s.equals("Mumbai")||s.equals("Greater Mumbai")||s.equals("Navi Mumbai")||s.equals("Thane"))
			{
				city=a[a.length-1];
				int i=v.lastIndexOf(",");
				if(i==(-1))
					return city;
				String sub=v.substring(0,i);
				s=getCity(sub);
			}
			else
			{
				
				return s;
			}
	    	//int i=v.lastIndexOf(",");
	    	//String sub=v.substring(i+1);
	    	
			return s;
			}
}
