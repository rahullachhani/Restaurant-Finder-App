package com.codingc.team20.restofinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class DetailedConfiguration extends JSONObject {
	
	public static final String PREF_NAME="GoogleMap" ;
	
	
	public DetailedConfiguration() {
		super();
	}

	public DetailedConfiguration(String json) throws JSONException {
		super(json);
	}

	public void save(Context context,String id) {
		SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		prefs.edit().putString(id, toString()).commit();
	}

	public static DetailedConfiguration load(Context context,String id) {
		SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return DetailedConfiguration.load(prefs,id);
	}

	public static DetailedConfiguration load(SharedPreferences prefs,String id) {
		String json = prefs.getString(id, null);
		try {
			if (json != null) {
				return new DetailedConfiguration(json);
			}
		} catch (JSONException e) {
			Log.e("Error loading Configuration from SharedPreferences.", e.toString());
		}
		return new DetailedConfiguration();
	}
	public  String getAttribute(String...args)
	{
		JSONObject cat=null;
		
		
		Object item=null;
		try {
			cat = getJSONObject("result");
			 
			item=cat.get(args[0]);
			if(item==null)
			{
				return null;
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		JSONArray myArray=null;
		JSONObject myObject=null;
		
			if (item instanceof JSONArray)
		    {
		        // it's an array
		        myArray = (JSONArray) item;
		        // do all kinds of JSONArray'ish things with urlArray
		    }
		    else if(item instanceof JSONObject)
		    {
		        // if you know it's either an array or an object, then it's an object
		        myObject = (JSONObject) item;
		        // do objecty stuff with urlObject
		    }
		    else
		    {
		    	
		    	return item.toString();
		    }
			for(int i=1;i<args.length;i++)
			{
				if(myArray!=null)
				{
					if(myArray.length()>1)
					{
						
						myArray=null;
						myObject=null;
					}
					else
					{
						try {
							item=myArray.get(0);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						myArray=null;
						myObject=null;
					}
				}
				else if(myObject!=null)
				{
					try {
						item=myObject.get(args[i]);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myObject=null;
					myArray=null;
				}
				if (item instanceof JSONArray)
			    {
			        // it's an array
			        myArray = (JSONArray) item;
			        // do all kinds of JSONArray'ish things with urlArray
			    }
			    else if(item instanceof JSONObject)
			    {
			        // if you know it's either an array or an object, then it's an object
			        myObject = (JSONObject) item;
			        // do objecty stuff with urlObject
			    }
			    else
			    {
			    	String f=item.toString();
			    	Log.v("String",f);
			    	
			    	return f;
			    }
			}
			return null;
		

	}
	public String[] getTypes()
	{
		String[] types;
		JSONObject cat;
		JSONArray typ;
		try {
			cat = getJSONObject("result");
			typ=cat.getJSONArray("types");
			types=new String[typ.length()];
			for(int i=0;i<typ.length();i++)
			{
				types[i]=typ.getString(i);
			}
			return types;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public int getlength()
	{
		
		try {
			JSONArray cat = getJSONArray("result");
			return cat.length();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	public String[][] getObjects(String...args)
	{
		JSONObject cat;
		JSONArray a=null;
		try {
			cat = getJSONObject("result");
			a=cat.getJSONArray(args[0]);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		
		String[][] str=new String[a.length()][args.length-1];
		for(int j=0;j<a.length();j++)
		{
			for(int k=0;k<args.length-1;k++)
			{
				try {
					Object o=a.getJSONObject(j).get(args[k+1]);
					if(o instanceof String || o instanceof Integer)
					{
						str[j][k]=o.toString();
					}
					if(o instanceof JSONArray)
					{
						JSONArray arr=(JSONArray)o;
						String sdr="";
						for(int i=0;i<arr.length();i++)
						{
							sdr=sdr+arr.get(i);
						}
						str[j][k]=sdr; 
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					str[j][k]=null;
				}
			}
		}
		return str;
	}
	public String[] getPhotoReferences()
	{
		JSONObject cat;
		JSONArray a=null;
		try {
			cat = getJSONObject("result");
			a=cat.getJSONArray("photos");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		String[] ph=new String[a.length()];
		for(int i=0;i<a.length();i++)
		{
			JSONObject oiu;
			try {
				oiu = a.getJSONObject(i);
				ph[i]=oiu.getString("photo_reference");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return ph;
	}
	public String getOpenHours()
	{
		JSONObject cat;
		JSONObject a=null,day=null,open=null,close=null;
		JSONArray period=null;
		String time=null,time2=null,open_time=null,close_time=null,total_time=null;
		try {
			cat = getJSONObject("result");
			a=cat.getJSONObject("opening_hours");
			period=a.getJSONArray("periods");
			day=period.getJSONObject(0);
			open=day.getJSONObject("open");
			time=open.getString("time");
			open_time=time.substring(0,2)+":"+time.substring(2,4);
			total_time=open_time;
			
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return total_time;
		}
		try {
			close=day.getJSONObject("close");
			time2=close.getString("time");
			close_time=time2.substring(0,2)+":"+time2.substring(2,4);
			total_time=open_time+" - "+close_time;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return total_time;
		}
		
		
		return total_time;
	}
	

}
