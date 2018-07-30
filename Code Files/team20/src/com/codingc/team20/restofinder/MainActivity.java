package com.codingc.team20.restofinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.facebook.model.GraphUser;

public class MainActivity extends FragmentActivity {
	public static Activity a;
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	public static Context c;
	public static ArrayList<String> key;
	public static FragmentManager fm;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	  private ImageView profilePictureView;
	  public static int count=0;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;
	 private GraphUser user;
	   public static String latitude=null;
	   public static String longitude=null;
	List<DrawerItem> dataList;
	private SharedPreferences settings1;
	private Editor editor1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragactivity_main);
		
	this.getSharedPreferences("GoogleMap",0).edit().putBoolean("DBCREATED",false);
	        this.getSharedPreferences("GoogleMap",0).edit().commit();
	      
		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		     
		a=this;
		c=getApplicationContext();
	
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		//Retrieve the values
		settings=this.getSharedPreferences("wish",0);
		editor=settings.edit();
		Set<String> set=null;
		try{
		if(settings.getStringSet("key", null)!=null){
			set = settings.getStringSet("key", null);
		}
		}
		catch(Exception e)
		{
			Log.v("mohit","exception here="+e);
		}
		
		if(set!=null)
			key=new ArrayList<String>(set);    //Appending
		else
			key=new ArrayList<String>();      //creating new list

		
		
		
		editor.putString("ChIJG9HE8ubH5zsRJEmeEX_2T2Y","bigburger bigburger2");
		editor.commit();
		editor.putString("ChIJjZmEWe_H5zsRgSa49-atXaw","meluha1 meluha2 meluha3 meluha4 meluha5");
		editor.commit();
		editor.putString("ChIJ88dcaObH5zsRWLT4KyCLkI8","mainland1 mainland2 mainland3 mainland4 mainland5");
		editor.commit();
		editor.putString("ChIJxyKs-uTH5zsRQLpEPH2euy4","mangii1 mangii2 mangii3 mangii4 mangii5");
		editor.commit();
		editor.putString("ChIJs8l89OfH5zsR8cT_H2q8cz0","breeze1 breeze2 breeze3 breeze4 breeze5");
		editor.commit();
		editor.putString("ChIJSWijB-bH5zsRVLE5ipsxvHU","aroma1 aroma2 aroma3");
		editor.commit();
		editor.putString("ChIJJyTx-uTH5zsRve4QBqNRbP4","cucina1 cucina2 cucina3");
		editor.commit();
		editor.putString("ChIJY4DdQu_H5zsRLrp9BaHy8o8","blue1 blue2 blue3 blue4 blue5 blue6");
		editor.commit();
		editor.putString("ChIJH5X1Wu_H5zsRzbcoq7G3RZ4","parabola1");
		editor.commit();
		editor.putString("ChIJ-QAAQObH5zsRrDxQZOGu0ds","pizzae1 pizzae2 pizzae3 pizzae4 pizzae5 pizzae6");
		editor.commit();
		editor.putString("ChIJpVVYCebH5zsRUfmV_YQlHCg","sigre1 sigre2");
		editor.commit();
		editor.putString("ChIJv7ZTaObH5zsR1YjmIIFFopQ","yellow1 yellow2 yellow3 yellow4 yellow5 yellow6 yellow7");
		editor.commit();
		
		
		// Add Drawer Item to dataList
		//dataList.add(new DrawerItem(true)); // adding a spinner to the list

		dataList.add(new DrawerItem(true)); // adding a header to the list

		dataList.add(new DrawerItem("Search", R.drawable.search));
			
		dataList.add(new DrawerItem("Nearby me")); // adding a header to the list
		dataList.add(new DrawerItem("List View", R.drawable.showinlist));
		dataList.add(new DrawerItem("Map View", R.drawable.showinmap));
		dataList.add(new DrawerItem("Get Offers", R.drawable.getoffers));

		dataList.add(new DrawerItem("My Favourites"));
	 dataList.add(new DrawerItem("My wishlist", R.drawable.mywishlist)); // adding a header to the list
	 dataList.add(new DrawerItem("My bookings", R.drawable.mybooking));// adding a header to the list
	 dataList.add(new DrawerItem("Search by QRScan", R.drawable.search));// adding a header to the list
		dataList.add(new DrawerItem("Restaurant Admin"));
		 dataList.add(new DrawerItem("Login", R.drawable.update));// adding a header to the list
		 dataList.add(new DrawerItem("User"));
			
	 dataList.add(new DrawerItem("About", R.drawable.ic_action_about));
		dataList.add(new DrawerItem("Settings", R.drawable.ic_action_settings));
		dataList.add(new DrawerItem("Help", R.drawable.ic_action_help));


		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);
	//    	   profilePictureView.setProfileId(settings.getString("fbid", "121212"));
    	//   profilePictureView.setProfileId(loginActivity.user.getId());
	         
		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			SelectItem(1);
			if (dataList.get(0).isSpinner()
					& dataList.get(1).getTitle() != null) {
			//	SelectItem(2);
			} else if (dataList.get(0).getTitle() != null) {
//				SelectItem(1);
			} else {
	//			SelectItem(0);
			}
		}

	}
		@Override
	public void onDestroy(){
	super.onDestroy();
		Set<String> set = new HashSet<String>();
		set.addAll(MainActivity.key);
		
		settings=this.getSharedPreferences("wish",0);
		editor=settings.edit();
		editor.putStringSet("key", set);
		editor.commit();
		key=null;
		DatabaseHelper db=new DatabaseHelper(this);
		db.deleteTables();
		/*	boolean result = MainActivity.a.deleteDatabase(DatabaseHelper.DATABASE_NAME);
        if (result==true) {
                 Toast.makeText(MainActivity.a, "DB Deleted!", Toast.LENGTH_LONG).show();
               
        }*/
        settings=this.getSharedPreferences("GoogleMap",0);
		editor=settings.edit();
		 if(editor!=null)
		 {
			 editor.putBoolean("NETWORK",false);
			 editor.commit();
			 editor.putBoolean("NETWORKFRAG",false);
			 editor.commit();
			 editor.putBoolean("DBCREATED",false);
			 editor.commit();
			 editor.putBoolean("DBCREATEDFILTER",false);
			 editor.commit();
			 editor.putBoolean("SEARCH",false);
			 editor.commit();
		        editor.remove("GoogleMap");
		        editor.commit();
		 }
		 settings1=this.getSharedPreferences("Filter",0);
			editor1=settings1.edit();
			 if(editor1!=null)
			 {
				 editor1.putString("Cuisine",null);
				 editor1.commit();
				 editor1.putString("Category",null);
				 editor1.commit();
				 editor1.putString("Distance",null);
				 editor1.commit();
				 editor1.putString("Cost",null);
				 editor1.commit();
				 editor1.putString("Rating",null);
				 editor1.commit();
			        editor1.remove("Filter");
			        editor1.commit();
			 }
		finish();
		
	     android.os.Process.killProcess(android.os.Process.myPid());
	     		
	}

	
	 	public void SelectItem(int possition) {

		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (possition) {

		case 0:
			fragment = new SearchActivity();
			break;
		case 1:
			fragment = new SearchActivity();
				break;
	
		case 2:
			fragment = new SearchActivity();
			
			break;
		
		case 3:
			fragment = new Main_Activity();
						break;
		case 4:
			fragment = new SearchFragment();
			
			break;
		case 5:
			fragment = new Offers();
			
			break;
		case 6:
			fragment = new WishListHistory();
				
			break;
		case 7:
			fragment = new WishListHistory();
			
			break;
		case 8:
			fragment = new ViewOrders();
				
			break;
		case 9:fragment = new QRScan();
		
			break;
		case 10:
			fragment = new loginAdmin();
			break;
		case 11:
			fragment = new loginAdmin();
				
			break;
		case 12:
			fragment = new loginAdmin();
			break;
		case 13:
			fragment = new ViewOrders();
			break;
		case 14:
			fragment = new ViewOrders();
			break;
	default:
		fragment = new SearchActivity();
		
		}

		fragment.setArguments(args);
		fm= getSupportFragmentManager();
		 fm.beginTransaction().replace(R.id.content_frame, fragment,"MY_FRAGMENT"+possition)
         .commit();
		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
    public void onBackPressed(){
                    
            Fragment f;
            f= fm.findFragmentByTag("MY_FRAGMENT1");
            if(f!=null)
            {if(f.isVisible())
            {
            onDestroy();
                    
            }
            else{
                    Fragment fragment2 = new SearchActivity();
                    fm= getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.content_frame, fragment2,"MY_FRAGMENT1")
                                    .commit();

                    mDrawerList.setItemChecked(1, true);
                    setTitle(dataList.get(1).getItemName());
                    mDrawerLayout.closeDrawer(mDrawerList);
                    }
            
            
            
            }
            else{
            Fragment fragment2 = new SearchActivity();
            fm= getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, fragment2,"MY_FRAGMENT1")
                            .commit();

            mDrawerList.setItemChecked(1, true);
            setTitle(dataList.get(1).getItemName());
            mDrawerLayout.closeDrawer(mDrawerList);
            }
            
            
    }


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
		//SharedPreferences settings=this.getSharedPreferences("preference",0);
	//	String path=settings.getString("path", null);
		//if(path!=null)
	//	loadImageFromStorage(path);

	}

/*	private void loadImageFromStorage(String path) {
		// TODO Auto-generated method stub
		 try {
	            File f=new File(path, "profile.jpg");
	            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
	           ImageView img = (ImageView) findViewById(R.id.iv1);
			        if(img!=null)
	                img.setImageBitmap(b);
	        } 
	        catch (FileNotFoundException e) 
	        {
	            e.printStackTrace();
	        }

	}
*/
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (dataList.get(position).getTitle() == null) {
				SelectItem(position);
			}

		}
	}

}
