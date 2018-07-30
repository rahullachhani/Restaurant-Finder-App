package com.codingc.team20.restofinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.internal.view.menu.ActionMenuView.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

	Context context;
	List<DrawerItem> drawerItemList;
	int layoutResID;

  //  private ProfilePictureView profilePictureView;
    

	public CustomDrawerAdapter(Context context, int layoutResourceID,
			List<DrawerItem> listItems) {
		super(context, layoutResourceID, listItems);
		this.context = context;
		this.drawerItemList = listItems;
		this.layoutResID = layoutResourceID;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

	SharedPreferences settings=MainActivity.a.getSharedPreferences("preference",0);
		  SharedPreferences.Editor editor=settings.edit();
		DrawerItemHolder drawerHolder;
		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			drawerHolder = new DrawerItemHolder();

			view = inflater.inflate(layoutResID, parent, false);
			drawerHolder.ItemName = (TextView) view
					.findViewById(R.id.drawer_itemName);
			drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
		    
////			drawerHolder.image = (ImageView) view
	////				.findViewById(R.id.iv10);
		    drawerHolder.fbname = (TextView) view
						.findViewById(R.id.fbname);
			
	    	
	    	drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);

		drawerHolder.headerLayout = (LinearLayout) view
				.findViewById(R.id.headerLayout);
	
		drawerHolder.itemLayout = (LinearLayout) view
				.findViewById(R.id.itemLayout);
		drawerHolder.pic = (LinearLayout) view
				.findViewById(R.id.pic);
		
		    //loadImageFromStorage(path);

	
        
        

			view.setTag(drawerHolder);

		} else {
			drawerHolder = (DrawerItemHolder) view.getTag();

		}
		
		DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

		if (dItem.isSpinner()) {
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.pic.setVisibility(LinearLayout.VISIBLE);
			
			drawerHolder.fbname.setVisibility(TextView.VISIBLE);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) drawerHolder.pic.getLayoutParams();
			params.height = 300;
			params.width=500;
			drawerHolder.pic.setLayoutParams(params);
			drawerHolder.profilePictureView=(ProfilePictureView)view.findViewById(R.id.iv11);
			drawerHolder.fbname.setText(settings.getString("id","Rahul"));
	     	
			    if(settings.getString("graph", null)!=null)
			    {
			    drawerHolder.profilePictureView.setProfileId(settings.getString("graph", null));
			    ImageView fbImage = ( ( ImageView)drawerHolder.profilePictureView.getChildAt( 0));
	     	    Bitmap    bitmap  = ( ( BitmapDrawable) fbImage.getDrawable()).getBitmap();
	     	    String path=saveToInternalSorage(bitmap);
	     	    editor.putString("path",path);
	     	    Log.v("fbNameeee",""+settings.getString("id","Rahul"));
	     		
	    		
	   ////  	 LinearLayout.LayoutParams par = (LinearLayout.LayoutParams) drawerHolder.image.getLayoutParams();
	     	////   		par.height = 0;
	     	   		
	     	   	////	par.width=0;

	     	   ////	drawerHolder.image.setLayoutParams(par);

	     	    
	     	
			    }
			    else
			    {
			    	
			    	drawerHolder.profilePictureView.setProfileId(null);
				RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) drawerHolder.pic.getLayoutParams();
				param.height = 600;
				param.width=700;
				drawerHolder.pic.setLayoutParams(param);
			
		        try {
		    		String imgpath=settings.getString("path", null);
		        	File f=new File(imgpath, "profile.jpg");
		            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
		           ///       drawerHolder.image.setImageBitmap(b);
				 
		                  
		          	/*	
		     	     	 LinearLayout.LayoutParams par = (LinearLayout.LayoutParams) drawerHolder.profilePictureView.getLayoutParams();
			     	   		par.height = 0;
			     	   		
			     	   		par.width=0;

			     	   	drawerHolder.profilePictureView.setLayoutParams(par);

			     	  */  		                  
		                  
		                  //     drawerHolder.image.setVisibility(LinearLayout.VISIBLE);
				  //	  ViewGroup pa = (ViewGroup) drawerHolder.profilePictureView.getParent();
				    //     parent.removeView(drawerHolder.profilePictureView);
				  
		        } 
		        catch (FileNotFoundException e) 
		        {
		            e.printStackTrace();
		        }

			    
			    }  


		} else if (dItem.getTitle() != null) {
			drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.pic.setVisibility(LinearLayout.INVISIBLE);
		
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) drawerHolder.pic.getLayoutParams();
			params.height = 0;
			params.width=0;
			drawerHolder.pic.setLayoutParams(params);
			drawerHolder.title.setText(dItem.getTitle());
			Log.d("Getview","Passed4");
		} else{
			
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.pic.setVisibility(LinearLayout.INVISIBLE);
			
			drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) drawerHolder.pic.getLayoutParams();
			params.height = 0;
			params.width=0;
			drawerHolder.pic.setLayoutParams(params);
			
			/*		if(drawerHolder.pic!=null)
			{ViewGroup p = (ViewGroup) drawerHolder.pic.getParent();
			p.removeView(drawerHolder.pic);
			}
	*/	//  ViewGroup pa = (ViewGroup) drawerHolder.pic.getParent();
          //  pa.removeView(drawerHolder.pic);
     	
			drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
					dItem.getImgResID()));
			drawerHolder.ItemName.setText(dItem.getItemName());
			Log.d("Getview","Passed5");
		}
	//	loadImageFromStorage(path);

	
		return view;
	}
	 private String saveToInternalSorage(Bitmap bitmapImage){
	        ContextWrapper cw = new ContextWrapper(MainActivity.a);
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
	        return directory.getAbsolutePath();
	    }





	private static class DrawerItemHolder {
		public ProfilePictureView profilePictureView;
	//	public ImageView image;
		TextView ItemName, title,fbname;
		ImageView icon;
		LinearLayout headerLayout, itemLayout, pic;
	}
}