package com.codingc.team20.restofinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Configuration extends JSONObject {
	
	public static final String PREF_NAME="GoogleMap" ;
	public static final String PREF_KEY_NEARBY="Nearby";
	
	public Configuration() {
		super();
	}

	public Configuration(String json) throws JSONException {
		super(json);
	}

	public void save(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		prefs.edit().putString(PREF_KEY_NEARBY, toString()).commit();
	}

	public static Configuration load(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return Configuration.load(prefs);
	}

	public static Configuration load(SharedPreferences prefs) {
		String json = prefs.getString(PREF_KEY_NEARBY, null);
		try {
			if (json != null) {
				return new Configuration(json);
			}
		} catch (JSONException e) {
			Log.e("Error loading Configuration from SharedPreferences.", e.toString());
		}
		return new Configuration();
	}
	public  String getAttribute(int index,String...args)
	{
		JSONArray cat=null;
		JSONObject obj=null;
		Object item=null;
		try {
			cat = getJSONArray("results");
			 obj= cat.getJSONObject(index);
			item=obj.get(args[0]);
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
						/*try {
							item=myArray.get(0);
							if(item instanceof JSONObject)
							{
								getObjects(myArray,args);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
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
	public String[] getTypes(int index)
	{
		String[] types;
		JSONArray cat;
		JSONArray typ;
		try {
			cat = getJSONArray("results");
			typ=cat.getJSONObject(index).getJSONArray("types");
			types=new String[typ.length()];
			for(int i=0;i<typ.length();i++)
			{
				types[i]=typ.getString(i);
			}
			return types;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getlength()
	{
		
		try {
			JSONArray cat = getJSONArray("results");
			return cat.length();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	public String[][] getObjects(JSONArray a,String...args)
	{
		String[][] str=new String[a.length()][args.length-1];
		for(int j=0;j<a.length();j++)
		{
			for(int k=1;k<args.length;k++)
			{
				try {
					Object o=a.getJSONObject(j).get(args[k]);
					if(o instanceof String)
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
	

}
