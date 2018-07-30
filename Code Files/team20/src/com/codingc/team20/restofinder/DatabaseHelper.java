package com.codingc.team20.restofinder;


import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper3";

    // Database Version
    static final int DATABASE_VERSION = 1;

    // Database Name
     static final String DATABASE_NAME = "RestaurantInfo123";

    // Table Names
    private static final String TABLE_RESTAURANT = "Restaurant";
    private static final String TABLE_INFO = "Info";
    private static final String TABLE_REVIEW = "Review";
    private static final String TABLE_TYPES = "Types";
    private static final String TABLE_PHOTO_REFERENCES = "PhotoReferences";
    private static final String TABLE_CUISINE_TYPE_BOOL = "Cuisine_type";

    // Common column names
    private static final String KEY_PLACE_ID = "placeId";
    private static final String KEY_RATING = "rating";
    private static final String KEY_ID = "id";

    // Hotel Table - column nmaes
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_PRICE_LEVEL = "priceLevel";
    private static final String KEY_TYPES = "types";
    private static final String KEY_OPEN_NOW = "openNow";
    private static final String KEY_LIKES = "likes";
    private static final String KEY_DISLIKES = "dislikes";
    private static final String KEY_LIKES_USER = "likes_user";
    private static final String KEY_DISLIKES_USER = "dislikes_user";
    
    private static final String KEY_FILTER_CUISINE="cuisine";
    private static final String KEY_FILTER_TYPE="type";
    private static final String KEY_FILTER_BOOLEAN="filter";


    // INFO Table - column names
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHOTO_REFERENCE = "photoReference";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_VICINITY = "vicinity";
    private static final String KEY_REFERENCE = "reference";
    private static final String KEY_OPEN_TIMINGS = "openTimings";
    private static final String KEY_PHONE = "Phone";
    private static final String KEY_WEBURL = "WebUrl";
    
    private static final String KEY_CUISINE_NAME = "cuisine";
    private static final String KEY_TYPE_NAME = "type";
   
    // REVIEW Table - column names
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_TEXT = "text";
    
    public String cuisine;
    public  String type;

    // Table Create Statements
    // RESTAURANT table create statement
    private static final String CREATE_TABLE_RESTAURANT = "CREATE TABLE "
            + TABLE_RESTAURANT + "(" + KEY_PLACE_ID + " TEXT PRIMARY KEY," + KEY_ID
            + " TEXT NULL,"+ KEY_RATING
            + " DOUBLE NULL," + KEY_DISTANCE + " DOUBLE NULL," + KEY_PRICE_LEVEL
            + " DOUBLE NULL," 
            + KEY_OPEN_NOW + " TEXT NULL,"+ KEY_FILTER_CUISINE + " TEXT NULL," + KEY_FILTER_TYPE + " TEXT NULL," + KEY_FILTER_BOOLEAN + " TEXT NULL, " + KEY_LIKES
            + " INT NULL, "+ KEY_DISLIKES+ " INT NULL,"+ KEY_LIKES_USER+ " INT NULL,"+ KEY_DISLIKES_USER+ " INT NULL"+");";
    //CREATING types table
	private static final String CREATE_TABLE_TYPES = "CREATE TABLE "
			+ TABLE_TYPES + "(" + KEY_PLACE_ID + " TEXT," + KEY_TYPES
			+ " TEXT,"+ KEY_FILTER_BOOLEAN + " TEXT" + ");";	

	// Info table create statement
	private static final String CREATE_TABLE_info = "CREATE TABLE " + TABLE_INFO
			+ "(" + KEY_PLACE_ID + " TEXT PRIMARY KEY,"+KEY_ID+" TEXT NULL," + KEY_LATITUDE + " TEXT NULL,"
			+ KEY_LONGITUDE + " TEXT NULL," + KEY_NAME + " TEXT NULL," +KEY_ADDRESS + " TEXT NULL," +KEY_VICINITY + " TEXT NULL," +KEY_REFERENCE + " TEXT NULL," +KEY_OPEN_TIMINGS + " TEXT NULL," +KEY_PHONE + " TEXT NULL," + KEY_WEBURL + " TEXT NULL,"+ KEY_FILTER_BOOLEAN + " TEXT NULL" +");";
	//CREATING PHOTO REFERENCES table
	private static final String CREATE_TABLE_PHOTO_REFERENCES = "CREATE TABLE "
			+ TABLE_PHOTO_REFERENCES + "(" + KEY_PLACE_ID + " TEXT," + KEY_PHOTO_REFERENCE
			+ " TEXT,"+ KEY_FILTER_BOOLEAN + " TEXT NULL" +");";

	// Review table create statement
	private static final String CREATE_TABLE_REVIEW = "CREATE TABLE "
			+ TABLE_REVIEW + "(" + KEY_PLACE_ID + " TEXT,"
			+ KEY_AUTHOR + " TEXT NULL," + KEY_TEXT + " TEXT NULL,"
			+ KEY_RATING + " TEXT NULL,"+ KEY_FILTER_BOOLEAN + " TEXT NULL" + ");"; 
	
	private static final String CREATE_TABLE_CUISINE_TYPE = "CREATE TABLE "
			+ TABLE_CUISINE_TYPE_BOOL + "(" + KEY_CUISINE_NAME + " TEXT NULL," + KEY_TYPE_NAME
			+ " TEXT NULL"+");";
	
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    
        
    
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_info);
        db.execSQL(CREATE_TABLE_REVIEW);
        db.execSQL(CREATE_TABLE_TYPES);
        db.execSQL(CREATE_TABLE_PHOTO_REFERENCES);
        db.execSQL(CREATE_TABLE_CUISINE_TYPE);
    Log.v("mohit","Tables created");
    
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db = this.getWritableDatabase();
        
        // on upgrade drop older tables
        Log.v("mohit","Upgrading Database from old version "+oldVersion +"  to new version "+newVersion);
        
        db.execSQL("DELETE FROM  " + TABLE_RESTAURANT);
        db.execSQL("DELETE FROM " + TABLE_INFO);
        db.execSQL("DELETE FROM " + TABLE_REVIEW);
        db.execSQL("DELETE FROM " + TABLE_TYPES);
        db.execSQL("DELETE FROM " + TABLE_PHOTO_REFERENCES);
    // create new tables
        onCreate(db);
    }

    // ------------------------ "RESTAURANTs" table methods ----------------//

    /*
     * Creating a RESTAURANT
     */
    public String deleteValue() 
	 {
    	 SQLiteDatabase db = this.getWritableDatabase();
	     long val1= db.delete(TABLE_RESTAURANT,KEY_FILTER_BOOLEAN+ "='search'" , null) ;
	     long val2= db.delete(TABLE_INFO,KEY_FILTER_BOOLEAN+ "='search'" , null) ;
	     long val3= db.delete(TABLE_PHOTO_REFERENCES,KEY_FILTER_BOOLEAN+ "='search'" , null) ;
	     long val4= db.delete(TABLE_REVIEW,KEY_FILTER_BOOLEAN+ "='search'" , null) ;
	     long val5= db.delete(TABLE_TYPES,KEY_FILTER_BOOLEAN+ "='search'" , null) ;
	     if(val1>0 && val2>0) return "true";
	     return "false";
	 }
    public String deleteTables() 
	 {
   	 SQLiteDatabase db = this.getWritableDatabase();
	     long val1= db.delete(TABLE_RESTAURANT,KEY_FILTER_BOOLEAN+ "='search'" , null) ;
	     long val2= db.delete(TABLE_INFO,KEY_FILTER_BOOLEAN+ "='search'" , null) ;
	     long val3= db.delete(TABLE_PHOTO_REFERENCES,KEY_FILTER_BOOLEAN+ "='search'" , null) ;
	     long val4= db.delete(TABLE_REVIEW,KEY_FILTER_BOOLEAN+ "='search'" , null) ;
	     long val5= db.delete(TABLE_TYPES,KEY_FILTER_BOOLEAN+ "='search'" , null) ;
	    val1= db.delete(TABLE_RESTAURANT,KEY_FILTER_BOOLEAN+ "='true'" , null) ;
	    val2= db.delete(TABLE_INFO,KEY_FILTER_BOOLEAN+ "='true'" , null) ;
	    val3= db.delete(TABLE_PHOTO_REFERENCES,KEY_FILTER_BOOLEAN+ "='true'" , null) ;
	    val4= db.delete(TABLE_REVIEW,KEY_FILTER_BOOLEAN+ "='true'" , null) ;
	    val5= db.delete(TABLE_TYPES,KEY_FILTER_BOOLEAN+ "='true'" , null) ;
	    val1= db.delete(TABLE_RESTAURANT,KEY_FILTER_BOOLEAN+ "='false'" , null) ;
	    val2= db.delete(TABLE_INFO,KEY_FILTER_BOOLEAN+ "='false'" , null) ;
	    val3= db.delete(TABLE_PHOTO_REFERENCES,KEY_FILTER_BOOLEAN+ "='false'" , null) ;
	    val4= db.delete(TABLE_REVIEW,KEY_FILTER_BOOLEAN+ "='false'" , null) ;
	    val5= db.delete(TABLE_TYPES,KEY_FILTER_BOOLEAN+ "='false'" , null) ;
	    
	     
	     
	    long val6= db.delete(TABLE_CUISINE_TYPE_BOOL,null, null) ;
	     
	     if(val1>0 && val2>0) return "true";
	     return "false";
	 }
	
    
    public void insertSearchRecords( ArrayList<Place> places , ArrayList<Place_Details> places_details ) {
        SQLiteDatabase db = this.getWritableDatabase();
       
        if(places==null || places.size()==0)
        {}
        else
for(int i=0;i<places.size();i++)
{
	 
		
		
        ContentValues values = new ContentValues();
        values.put(KEY_ID, places.get(i).getId());
        values.put(KEY_PLACE_ID, places.get(i).getPlace_id());
        values.put(KEY_RATING,places_details.get(i).getRating());
        values.put(KEY_DISTANCE, places.get(i).getDistance());
        values.put(KEY_LIKES, places_details.get(i).retLikes());
        values.put(KEY_DISLIKES, places_details.get(i).retdisLikes());
        values.put(KEY_LIKES_USER, places_details.get(i).getLikes_user());
        values.put(KEY_DISLIKES_USER, places_details.get(i).getdisLikes_user());
        if(places_details.get(i).getPrice_level()==0)
			values.put(KEY_PRICE_LEVEL,2.0);
		else
			values.put(KEY_PRICE_LEVEL,(places_details.get(i).getPrice_level()+1));

        values.put(KEY_OPEN_NOW,places.get(i).getOpen_now());
        
       
        	values.put(KEY_FILTER_BOOLEAN,"search");
       
        
        	 Log.v("db-restaurant", "record1->"+KEY_ID+places.get(i).getId()+"$"+places.get(i).getPlace_id()+"$"+places_details.get(i).getRating()+"$"+ places.get(i).getDistance()+"$"+(places_details.get(i).getPrice_level()+1)+"$"+places.get(i).getOpen_now());
             
        long RESTAURANT_id = db.insert(TABLE_RESTAURANT, null, values);
        Log.v("mohit", "record restaurant created");
        String types[]=places.get(i).getTypes();
        if(types==null || types.length==0)
        {}
        else
        for(int j=0;j<types.length;j++)
        {
            ContentValues value = new ContentValues();
            value.put(KEY_PLACE_ID, places.get(i).getPlace_id());
            value.put(KEY_TYPES, types[j]);
            
           
            	value.put(KEY_FILTER_BOOLEAN,"search");
            
            long tr=db.insert(TABLE_TYPES, null, value);
            Log.v("mohit", "record types created");
            
        }
        
        ContentValues values2 = new ContentValues();
        values2.put(KEY_ID, places.get(i).getId());
        values2.put(KEY_PLACE_ID, places.get(i).getPlace_id());
        values2.put(KEY_LATITUDE,places.get(i).getLatitude());
        values2.put(KEY_LONGITUDE, places.get(i).getLongitude());
        values2.put(KEY_NAME,places.get(i).getName());
        values2.put(KEY_ADDRESS,places_details.get(i).getFormatted_address());
        if(places.get(i).getVicinity()==null)
			values2.put(KEY_VICINITY,places.get(i).getAddress());
		else
			values2.put(KEY_VICINITY,places.get(i).getVicinity());
	
        values2.put(KEY_REFERENCE,places.get(i).getReference());
        values2.put(KEY_OPEN_TIMINGS,places_details.get(i).getOpen_hours());
        values2.put(KEY_PHONE,places_details.get(i).getFormatted_phone());
        values2.put(KEY_WEBURL,places_details.get(i).getWebsite());
        
        
        	values2.put(KEY_FILTER_BOOLEAN,"search");
        	 Log.v("db-info", "record1->"+KEY_ID+places.get(i).getLatitude()+"$"+places.get(i).getLongitude()+"$"+places.get(i).getName()+"$"+ places.get(i).getDistance()+"$"+places_details.get(i).getFormatted_address()+"$"+places.get(i).getAddress()+"$"+places.get(i).getVicinity()+"$"+places.get(i).getReference()+"$"+places_details.get(i).getOpen_hours()+"$"+places_details.get(i).getFormatted_phone()+"$"+places_details.get(i).getWebsite());
             
        
        
        long INFOID = db.insert(TABLE_INFO, null, values2);
        
        Log.v("mohit", "record info created");
        
        if(places_details.get(i).getReviews()==null || places_details.get(i).getReviews().length==0)
        {}
        else
        for(int j=0;j<places_details.get(i).getReviews().length;j++)
        {
            ContentValues values3=new ContentValues();
        
            values3.put(KEY_PLACE_ID,places.get(i).getPlace_id());
            String author=(places_details.get(i).getReviews())[j][0];
            values3.put(KEY_AUTHOR,author);
            String rating=(places_details.get(i).getReviews())[j][1];
            values3.put(KEY_RATING,rating);
            String text=(places_details.get(i).getReviews())[j][2];
            values3.put(KEY_TEXT,text);
            
           
            	values3.put(KEY_FILTER_BOOLEAN,"search");
            
            

           	 Log.v("db-review", "record1->"+author+"$"+rating+"$"+text);
            
            
            long REVIEWID=db.insert(TABLE_REVIEW,null,values3);
            Log.v("mohit", "record review created");
            
        }
        if(places_details.get(i).getPhotoreferences()==null)
        {}
        else
        for(int j=0;j<places_details.get(i).getPhotoreferences().length;j++)
        {
            ContentValues values4=new ContentValues();
        
            values4.put(KEY_PLACE_ID,places.get(i).getPlace_id());
            String ref=(places_details.get(i).getPhotoreferences())[j];    
            values4.put(KEY_PHOTO_REFERENCE,ref);
            
            
           
            	values4.put(KEY_FILTER_BOOLEAN,"search");
            	 Log.v("db-photoref", "record1->"+ref);
                 
            
            long PHOTOID=db.insert(TABLE_PHOTO_REFERENCES,null,values4);
        }
    
}

       
        
    }

    
    
    
    
    
    
    
    public void insertRecords( ArrayList<Place> places , ArrayList<Place_Details> places_details ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String cuisine=null;
        String type=null;
        if(places==null || places.size()==0)
        {}
        else
for(int i=0;i<places.size();i++)
{
	 cuisine=places.get(i).getCuisine();
		 type=places.get(i).getType();
		
		
        ContentValues values = new ContentValues();
        values.put(KEY_ID, places.get(i).getId());
        values.put(KEY_PLACE_ID, places.get(i).getPlace_id());
        values.put(KEY_RATING,places_details.get(i).getRating());
        values.put(KEY_DISTANCE, places.get(i).getDistance());
        values.put(KEY_LIKES, places_details.get(i).retLikes());
        values.put(KEY_DISLIKES, places_details.get(i).retdisLikes());
        Log.v("insertMohit","l_user="+places_details.get(i).getLikes_user()+"2nd="+ places_details.get(i).getdisLikes_user());
        values.put(KEY_LIKES_USER, places_details.get(i).getLikes_user());
        values.put(KEY_DISLIKES_USER, places_details.get(i).getdisLikes_user());
        if(places_details.get(i).getPrice_level()==0)
			values.put(KEY_PRICE_LEVEL,2.0);
		else
			values.put(KEY_PRICE_LEVEL,(places_details.get(i).getPrice_level()+1));

        values.put(KEY_OPEN_NOW,places.get(i).getOpen_now());
        
        if(cuisine!=null)
        {
        	values.put(KEY_FILTER_CUISINE,cuisine);
        }
        
        if(type!=null)
        {
        	values.put(KEY_FILTER_TYPE,type);
        }
        
        if(cuisine==null && type==null)
        {
        	values.put(KEY_FILTER_BOOLEAN,"false");
        }
        else
        {
        	values.put(KEY_FILTER_BOOLEAN,"true");
        }
        
        
        
        long RESTAURANT_id = db.insert(TABLE_RESTAURANT, null, values);
        Log.v("mohit", "record restaurant created");
        String types[]=places.get(i).getTypes();
        if(types==null || types.length==0)
        {}
        else
        for(int j=0;j<types.length;j++)
        {
            ContentValues value = new ContentValues();
            value.put(KEY_PLACE_ID, places.get(i).getPlace_id());
            value.put(KEY_TYPES, types[j]);
            
            if(cuisine==null && type==null)
            {
            	value.put(KEY_FILTER_BOOLEAN,"false");
            }
            else
            {
            	value.put(KEY_FILTER_BOOLEAN,"true");
            }
            
            long tr=db.insert(TABLE_TYPES, null, value);
            Log.v("mohit", "record types created");
            
        }
        
        ContentValues values2 = new ContentValues();
        values2.put(KEY_ID, places.get(i).getId());
        values2.put(KEY_PLACE_ID, places.get(i).getPlace_id());
        values2.put(KEY_LATITUDE,places.get(i).getLatitude());
        values2.put(KEY_LONGITUDE, places.get(i).getLongitude());
        values2.put(KEY_NAME,places.get(i).getName());
        values2.put(KEY_ADDRESS,places_details.get(i).getFormatted_address());
        if(places.get(i).getVicinity()==null)
			values2.put(KEY_VICINITY,places.get(i).getAddress());
		else
			values2.put(KEY_VICINITY,places.get(i).getVicinity());
	
        values2.put(KEY_REFERENCE,places.get(i).getReference());
        values2.put(KEY_OPEN_TIMINGS,places_details.get(i).getOpen_hours());
        values2.put(KEY_PHONE,places_details.get(i).getFormatted_phone());
        values2.put(KEY_WEBURL,places_details.get(i).getWebsite());
        
        if(cuisine==null && type==null)
        {
        	values2.put(KEY_FILTER_BOOLEAN,"false");
        }
        else
        {
        	values2.put(KEY_FILTER_BOOLEAN,"true");
        }
        
        
        long INFOID = db.insert(TABLE_INFO, null, values2);
        
        Log.v("mohit", "record info created");
        
        if(places_details.get(i).getReviews()==null || places_details.get(i).getReviews().length==0)
        {}
        else
        for(int j=0;j<places_details.get(i).getReviews().length;j++)
        {
            ContentValues values3=new ContentValues();
        
            values3.put(KEY_PLACE_ID,places.get(i).getPlace_id());
            String author=(places_details.get(i).getReviews())[j][0];
            values3.put(KEY_AUTHOR,author);
            String rating=(places_details.get(i).getReviews())[j][1];
            values3.put(KEY_RATING,rating);
            String text=(places_details.get(i).getReviews())[j][2];
            values3.put(KEY_TEXT,text);
            
            if(cuisine==null && type==null)
            {
            	values3.put(KEY_FILTER_BOOLEAN,"false");
            }
            else
            {
            	values3.put(KEY_FILTER_BOOLEAN,"true");
            }
            
            
            
            
            long REVIEWID=db.insert(TABLE_REVIEW,null,values3);
            Log.v("mohit", "record review created");
            
        }
        if(places_details.get(i).getPhotoreferences()==null)
        {}
        else
        for(int j=0;j<places_details.get(i).getPhotoreferences().length;j++)
        {
            ContentValues values4=new ContentValues();
        
            values4.put(KEY_PLACE_ID,places.get(i).getPlace_id());
            String ref=(places_details.get(i).getPhotoreferences())[j];    
            values4.put(KEY_PHOTO_REFERENCE,ref);
            
            
            if(cuisine==null && type==null)
            {
            	values4.put(KEY_FILTER_BOOLEAN,"false");
            }
            else
            {
            	values4.put(KEY_FILTER_BOOLEAN,"true");
            }
            
            long PHOTOID=db.insert(TABLE_PHOTO_REFERENCES,null,values4);
        }
    
}

        if(cuisine!=null || type!=null)
        {
        	insertCuisineAndType();
        }
        Log.v("mohit", "record ENTERED");
        
    }

    
    /*public Hotel getRESTAURANT(String place_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " WHERE "
                + KEY_PLACE_ID + " = " + place_id;

        Log.d("mohit", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        Hotel td = new Hotel();
        Log.d("mohit",""+c.getString(c.getColumnIndex(KEY_ID))+c.getString(c.getColumnIndex(KEY_PLACE_ID))+c.getLong(c.getColumnIndex(KEY_DISTANCE))+c.getLong(c.getColumnIndex(KEY_PRICE_LEVEL))+c.getDouble(c.getColumnIndex(KEY_RATING)));
        td.setId(c.getString(c.getColumnIndex(KEY_ID)));
        td.setPlaceId(c.getString(c.getColumnIndex(KEY_PLACE_ID)));
        td.setDistance(c.getLong(c.getColumnIndex(KEY_DISTANCE)));
        td.setPriceLevel(c.getLong(c.getColumnIndex(KEY_PRICE_LEVEL)));
        td.setRatings(c.getDouble(c.getColumnIndex(KEY_RATING)));
        td.setopenNow(c.getString(c.getColumnIndex(KEY_OPEN_NOW)));
    //write code for types
        return td;
    }
    public Hotel getAllRESTAURANT() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT ;

        String selectQuery2 = "SELECT  * FROM  Restaurant WHERE placeId = 'PL16'";
        Log.d("mohit", selectQuery2);

        Cursor c = db.rawQuery(selectQuery2, null);

        if (c != null)
            c.moveToFirst();

        Hotel td = new Hotel();
        Log.d("mohit","1"+c.getString(c.getColumnIndex(KEY_ID))+"\n2"+c.getString(c.getColumnIndex(KEY_PLACE_ID))+"\n3"+c.getLong(c.getColumnIndex(KEY_DISTANCE))+c.getLong(c.getColumnIndex(KEY_PRICE_LEVEL))+c.getDouble(c.getColumnIndex(KEY_RATING)));
        td.setId(c.getString(c.getColumnIndex(KEY_ID)));
        td.setPlaceId(c.getString(c.getColumnIndex(KEY_PLACE_ID)));
        td.setDistance(c.getLong(c.getColumnIndex(KEY_DISTANCE)));
        td.setPriceLevel(c.getLong(c.getColumnIndex(KEY_PRICE_LEVEL)));
        td.setRatings(c.getDouble(c.getColumnIndex(KEY_RATING)));
        td.setopenNow(c.getString(c.getColumnIndex(KEY_OPEN_NOW)));
    //write code for types
        /*
        c.moveToNext();
        Hotel td2 = new Hotel();
        Log.d("mohit",""+c.getString(c.getColumnIndex(KEY_ID))+c.getString(c.getColumnIndex(KEY_PLACE_ID))+c.getLong(c.getColumnIndex(KEY_DISTANCE))+c.getLong(c.getColumnIndex(KEY_PRICE_LEVEL))+c.getDouble(c.getColumnIndex(KEY_RATING)));
        td2.setId(c.getString(c.getColumnIndex(KEY_ID)));
        td2.setPlaceId(c.getString(c.getColumnIndex(KEY_PLACE_ID)));
        td2.setDistance(c.getLong(c.getColumnIndex(KEY_DISTANCE)));
        td2.setPriceLevel(c.getLong(c.getColumnIndex(KEY_PRICE_LEVEL)));
        td2.setRatings(c.getDouble(c.getColumnIndex(KEY_RATING)));
        td2.setopenNow(c.getString(c.getColumnIndex(KEY_OPEN_NOW)));
        
        
        
        return td;
    }

    /**
     * getting all RESTAURANTs
     */ 
    public void getAllRestaurants() {
    
        
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT +" WHERE "+KEY_FILTER_BOOLEAN+"='false';";
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Log.v("mohit","1="+c.getString(c.getColumnIndex(KEY_ID)));
                Log.v("mohit","2="+c.getString(c.getColumnIndex(KEY_PLACE_ID)));
                Log.v("mohit","3="+c.getString(c.getColumnIndex(KEY_RATING)));
                Log.v("mohit","4="+c.getString(c.getColumnIndex(KEY_DISTANCE)));
                Log.v("mohit","5="+c.getString(c.getColumnIndex(KEY_PRICE_LEVEL)));
                Log.v("mohit","6="+c.getString(c.getColumnIndex(KEY_OPEN_NOW)));
              
                Log.v("mohit","\n NEw record");
                //write code for types
                        // adding to RESTAURANT list
            } while (c.moveToNext());
        }

    }
    
    public String[][] getNearbyFilter()
    {
    String[][] str=null;
   String place = null;
    SQLiteDatabase db = this.getReadableDatabase();
    SharedPreferences settings=MainActivity.a.getSharedPreferences("Filter",0);
    SharedPreferences settings1=MainActivity.a.getSharedPreferences("GoogleMap",0);
   


//	listDataHeader.add("Distance");
	//listDataHeader.add("Rating");
//	listDataHeader.add("Cost");
String distance,rating,cost,cuisine,type;
    distance=settings.getString("Distance",null);
    rating=settings.getString("Rating", null);
    cost=settings.getString("Cost", null);
    cuisine=settings.getString("Cuisine", null);
    type=settings.getString("Category", null);
    this.cuisine=cuisine;
    this.type=type;
    StringBuffer sq=new StringBuffer();
    sq.append("SELECT "+KEY_RATING+","+KEY_DISTANCE+","+KEY_PLACE_ID+","+KEY_LIKES+","+KEY_DISLIKES+" FROM " + TABLE_RESTAURANT + " WHERE ");
    		//" WHERE KEY_DISTANCE BETWEEN "+pos1+" AND "+pos2+";";

    String rs[];
    double dist = 0,r1=0,r2=0;
    if(distance!=null){
    rs=distance.split(" "); 
    Log.v("mohit",rs[2]);
    dist=Integer.parseInt(rs[2]);
    Log.v("mohit",""+dist);
}
    double price=0;
    if(cost!=null){
	if(cost.equals("Very Expensive"))
		price=4;
	else if(cost.equals("Expensive"))
		price=3;
	else if(cost.equals("Moderate"))
		price=2;
	else if(cost.equals("Inexpensive"))
		price=1;
	else if(cost.equals("Free"))
		price=0;
    }
if(rating!=null){
    String rs2[]=rating.split("-"); 
    r1=Double.parseDouble(rs2[0]);
    r2=Double.parseDouble(rs2[1]);
    Log.v("mohit",""+r1+"  "+r2);
}
int count=0;
    if(distance!=null)
    {
    	if(count==0)
    		sq.append(KEY_DISTANCE + "<="+dist);
    	else
    		sq.append(" AND "+KEY_DISTANCE + "<="+dist);
        
    	count++;
    }
    if(rating!=null)
    {   
    	if(count==0)
    		sq.append(KEY_RATING + " BETWEEN " + r1 + " AND "+r2);
    	else
    		sq.append(" AND "+KEY_RATING + " BETWEEN " + r1 + " AND "+r2);
        
    	count++;
    }
    if(cost!=null)
    {

    	if(count==0)
    		sq.append(KEY_PRICE_LEVEL + "="+ price);
    	else
    		sq.append(" AND "+KEY_PRICE_LEVEL + "="+ price);
    	
    	count++;
    }
    if(cuisine!=null)
    {

    	if(count==0)
    		sq.append(KEY_FILTER_CUISINE + "='"+ cuisine+"'");
    	else
    		sq.append(" AND "+KEY_FILTER_CUISINE + "='"+ cuisine +"'");
    	
    	count++;
    }
    
    if(type!=null)
    {

    	if(count==0)
    		sq.append(KEY_FILTER_TYPE + "='"+ type+"'");
    	else
    		sq.append(" AND "+KEY_FILTER_TYPE + "='"+ type+"'");
    	
    	count++;
    }
    
      
    
    
    
    
    
    if(cuisine!=null || type!=null)
    	sq.append( " AND "+KEY_FILTER_BOOLEAN+"='true'");
    
    else if(settings1.getBoolean("SEARCH",false))
    	sq.append( " AND "+KEY_FILTER_BOOLEAN+"='search'");
    
    else
    	sq.append( " AND "+KEY_FILTER_BOOLEAN+"='false'");
    
    if(settings1.getBoolean("SORTSELECTED",false))
    {
    	String selection=settings1.getString("SELECTION",null);
    	if(selection!=null)
    	{
    		if(selection.equals("By Distance"))
    			sq.append(" ORDER BY "+KEY_DISTANCE);
    			if(selection.equals("By Rating"))
    				sq.append(" ORDER BY "+KEY_RATING +" DESC");
    			
    			if(selection.equals("By Cost"))
    				sq.append(" ORDER BY "+KEY_PRICE_LEVEL);
    			
    			if(selection.equals("By Likes"))
                    sq.append(" ORDER BY "+KEY_LIKES+" DESC");

    			
    	}
    			
    }
    
    String s=sq.toString();
    Cursor c = db.rawQuery(s, null);
           int j=0;
           if(c!=null)
        	   
                {
        	    int counter=c.getCount();
        	    str=new String[counter][7];

                if(counter!=0)
                {
                c.moveToFirst();
                do
                {
                	str[j][2]=c.getString(1);
                	str[j][3]=c.getString(0);
                	str[j][4]=c.getString(2);
                	str[j][5]=""+c.getInt(3);
                    str[j][6]=""+c.getInt(4);
                	place=c.getString(2);
                	Log.v("mohit","result="+str[j][2]+""+str[j][3]);
                	
               	 String selectQuery;
                    if(cuisine!=null || type!=null)
                    	 selectQuery = "SELECT "+KEY_NAME+","+KEY_VICINITY+" FROM " + TABLE_INFO+" WHERE "+KEY_PLACE_ID+"='"+place+"' AND "+KEY_FILTER_BOOLEAN+"='true';";
                    
                    else if(settings1.getBoolean("SEARCH",false))
                    	selectQuery = "SELECT "+KEY_NAME+","+KEY_VICINITY+" FROM " + TABLE_INFO+" WHERE "+KEY_PLACE_ID+"='"+place+"' AND "+KEY_FILTER_BOOLEAN+"='search';";
                    
                    else 	    	
                    	selectQuery = "SELECT "+KEY_NAME+","+KEY_VICINITY+" FROM " + TABLE_INFO+" WHERE "+KEY_PLACE_ID+"='"+place+"' AND "+KEY_FILTER_BOOLEAN+"='false';";
                    
                	 Cursor c2 = db.rawQuery(selectQuery, null);
                     int i=0;
                     if(c2!=null)
                          {
                       c2.moveToFirst();
                         
                          	str[j][0]=c2.getString(c2.getColumnIndex(KEY_NAME));
                          	str[j][1]=c2.getString(c2.getColumnIndex(KEY_VICINITY));
                          	Log.v("mohit","result="+str[i][0]+""+str[i][1]);
                         	
                          }
                     
                     j++;
                 	
                	
                }while(c.moveToNext());
                }
                }
             return str;
   }
    
    public String[][] getNearbySearchFilter()
    {
    String[][] str=null;
   String place = null;
    SQLiteDatabase db = this.getReadableDatabase();
    SharedPreferences settings=MainActivity.a.getSharedPreferences("Filter",0);
    SharedPreferences settings1=MainActivity.a.getSharedPreferences("GoogleMap",0);
   


//	listDataHeader.add("Distance");
	//listDataHeader.add("Rating");
//	listDataHeader.add("Cost");
String distance,rating,cost,cuisine,type;
    distance=settings.getString("Distance",null);
    rating=settings.getString("Rating", null);
    cost=settings.getString("Cost", null);
    
    StringBuffer sq=new StringBuffer();
    sq.append("SELECT "+KEY_RATING+","+KEY_DISTANCE+","+KEY_PLACE_ID+","+KEY_LIKES+","+KEY_DISLIKES+" FROM " + TABLE_RESTAURANT + " WHERE ");
    		//" WHERE KEY_DISTANCE BETWEEN "+pos1+" AND "+pos2+";";

    String rs[];
    double dist = 0,r1=0,r2=0;
    if(distance!=null){
    rs=distance.split(" "); 
    Log.v("mohit",rs[2]);
    dist=Integer.parseInt(rs[2]);
    Log.v("mohit",""+dist);
}
    double price=0;
    if(cost!=null){
	if(cost.equals("Very Expensive"))
		price=4;
	else if(cost.equals("Expensive"))
		price=3;
	else if(cost.equals("Moderate"))
		price=2;
	else if(cost.equals("Inexpensive"))
		price=1;
	else if(cost.equals("Free"))
		price=0;
    }
if(rating!=null){
    String rs2[]=rating.split("-"); 
    r1=Double.parseDouble(rs2[0]);
    r2=Double.parseDouble(rs2[1]);
    Log.v("mohit",""+r1+"  "+r2);
}
int count=0;
    if(distance!=null)
    {
    	if(count==0)
    		sq.append(KEY_DISTANCE + "<="+dist);
    	else
    		sq.append(" AND "+KEY_DISTANCE + "<="+dist);
        
    	count++;
    }
    if(rating!=null)
    {   
    	if(count==0)
    		sq.append(KEY_RATING + " BETWEEN " + r1 + " AND "+r2);
    	else
    		sq.append(" AND "+KEY_RATING + " BETWEEN " + r1 + " AND "+r2);
        
    	count++;
    }
    if(cost!=null)
    {

    	if(count==0)
    		sq.append(KEY_PRICE_LEVEL + "="+ price);
    	else
    		sq.append(" AND "+KEY_PRICE_LEVEL + "="+ price);
    	
    	count++;
    }
    
    
    
    
    
    
   
   
    	sq.append( " AND "+KEY_FILTER_BOOLEAN+"='search'");
    
    
    
    if(settings1.getBoolean("SORTSELECTED",false))
    {
    	String selection=settings1.getString("SELECTION",null);
    	if(selection!=null)
    	{
    		if(selection.equals("By Distance"))
    			sq.append(" ORDER BY "+KEY_DISTANCE);
    			if(selection.equals("By Rating"))
    				sq.append(" ORDER BY "+KEY_RATING +" DESC");
    			
    			if(selection.equals("By Cost"))
    				sq.append(" ORDER BY "+KEY_PRICE_LEVEL);
    			
    			
    			if(selection.equals("By Likes"))
                    sq.append(" ORDER BY "+KEY_LIKES+" DESC");
    			
    	}
    			
    }
    
    String s=sq.toString();
    Cursor c = db.rawQuery(s, null);
           int j=0;
           if(c!=null)
        	   
                {
        	    int counter=c.getCount();
        	    str=new String[counter][7];

                if(counter!=0)
                {
                c.moveToFirst();
                do
                {
                	str[j][2]=c.getString(1);
                	str[j][3]=c.getString(0);
                	str[j][4]=c.getString(2);
                	str[j][5]=c.getString(3);
                	str[j][6]=c.getString(4);
                	place=c.getString(2);
                	Log.v("mohit","result="+str[j][2]+""+str[j][3]);
                	
               	 String selectQuery;
                   
                   
                    	selectQuery = "SELECT "+KEY_NAME+","+KEY_VICINITY+" FROM " + TABLE_INFO+" WHERE "+KEY_PLACE_ID+"='"+place+"' AND "+KEY_FILTER_BOOLEAN+"='search';";
                    
                   
                	 Cursor c2 = db.rawQuery(selectQuery, null);
                     int i=0;
                     if(c2!=null)
                          {
                       c2.moveToFirst();
                         
                          	str[j][0]=c2.getString(c2.getColumnIndex(KEY_NAME));
                          	str[j][1]=c2.getString(c2.getColumnIndex(KEY_VICINITY));
                          	Log.v("mohit","result="+str[i][0]+""+str[i][1]);
                         	
                          }
                     
                     j++;
                 	
                	
                }while(c.moveToNext());
                }
                }
             return str;
   }
	



public String[][] getNearby()    {   
    
    
  	 	SharedPreferences settings=MainActivity.a.getSharedPreferences("GoogleMap",0);
	SharedPreferences.Editor editor=settings.edit();
	String[][] str=null;
    	String selectQuery=null;
    	SharedPreferences settings1=MainActivity.a.getSharedPreferences("Filter",0);
		 cuisine=settings1.getString("Cuisine", null);
		    type=settings1.getString("Category", null);
		
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	if(settings.getBoolean("SORTSELECTED",false))
    	{
    		String selection=settings.getString("SELECTION",null);
    		if(selection!=null)
    		{
    			if(selection.equals("By Distance"))
    			{
    				if(settings.getBoolean("SEARCH",false))
    					selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='search'" + " ORDER BY R."+KEY_DISTANCE+";";
    				else
    					selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='false'" + " ORDER BY R."+KEY_DISTANCE+";";
    			}
    			if(selection.equals("By Rating"))
    			{
    				if(settings.getBoolean("SEARCH",false))
    					selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='search'" + " ORDER BY R."+KEY_RATING+" DESC"+";";
    				else
        			selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='false'" + " ORDER BY R."+KEY_RATING+" DESC"+";";
    			}
    			if(selection.equals("By Cost"))
    			{
    				
    				if(settings.getBoolean("SEARCH",false))
    					selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='search'" + " ORDER BY R."+KEY_PRICE_LEVEL+";";
    				else
        			selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='false'" + " ORDER BY R."+KEY_PRICE_LEVEL+";";
    			}
    			 if(selection.equals("By Likes"))
                     selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='false'" + " ORDER BY R."+KEY_LIKES+" DESC;";
    		
    			 Cursor c = db.rawQuery(selectQuery, null);
    	    	 int i=0;
    	    	 int countr=c.getCount();
    	    	 if(c!=null && countr>0)
    	         {
    	    	 int count=c.getCount();
    	    	 str=new String[count][7];
    	    	 
    	         
    	         c.moveToFirst();
    	         do
    	         {
    	         	str[i][0]=c.getString(0);
    	         	str[i][1]=c.getString(1);
    	         	str[i][2]=""+c.getDouble(3);
    	         	str[i][3]=""+c.getDouble(2);
    	         	str[i][6]=c.getString(4);
    	         	 str[i][4]=""+c.getInt(5);
                     str[i][5]=""+c.getInt(6);
    	         	
    	         	i++;
    	         	
    	         	
    	         }while(c.moveToNext());
    	         }
    		
    		}
    		
    	}
    	else
    	{
    		if(settings.getBoolean("SEARCH",false))
    			selectQuery = "SELECT "+KEY_NAME+","+KEY_VICINITY+" FROM " + TABLE_INFO+" WHERE "+KEY_FILTER_BOOLEAN+"='search';";
    		else
    			selectQuery = "SELECT "+KEY_NAME+","+KEY_VICINITY+" FROM " + TABLE_INFO+" WHERE "+KEY_FILTER_BOOLEAN+"='false';";
    	 Cursor c = db.rawQuery(selectQuery, null);
    	 int i=0;
    	 int count=c.getCount();
     	
    	 if(c!=null && count>0)
         {
    	 str=new String[count][6];
    	 
         
         c.moveToFirst();
         do
         {
         	str[i][0]=c.getString(c.getColumnIndex(KEY_NAME));
         	str[i][1]=c.getString(c.getColumnIndex(KEY_VICINITY));
         	
         	i++;
         	
         	
         }while(c.moveToNext());
         }
    	 String selectQuery1;
    	 if(settings.getBoolean("SEARCH",false))
    		 selectQuery1 = "SELECT "+KEY_RATING+","+KEY_DISTANCE+","+KEY_LIKES+","+KEY_DISLIKES+" FROM " + TABLE_RESTAURANT+" WHERE "+KEY_FILTER_BOOLEAN+"='search';";
    	 else
    		 selectQuery1 = "SELECT "+KEY_RATING+","+KEY_DISTANCE+","+KEY_LIKES+","+KEY_DISLIKES+" FROM " + TABLE_RESTAURANT+" WHERE "+KEY_FILTER_BOOLEAN+"='false';";
   c = db.rawQuery(selectQuery1, null);
    	 int j=0;
    	 int counterr=c.getCount();
    	 if(c!=null && counterr>0)
         {
    	 
         
         c.moveToFirst();
         do
         {
         	str[j][2]=""+c.getDouble(1);
         	str[j][3]=""+c.getDouble(0);
         	str[j][4]=""+c.getInt(2);
            str[j][5]=""+c.getInt(3);

         	j++;
         	
         	
         }while(c.moveToNext());
         }
    	}
    	
    	 return str;
    	

               
    }
	

    
    
    public ArrayList<String> getRestaurantInfo(String PlaceId) {
    	
		ArrayList<String> ar=new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery=null;
		 SharedPreferences settings=MainActivity.a.getSharedPreferences("Filter",0);
		 SharedPreferences settings1=MainActivity.a.getSharedPreferences("GoogleMap",0);
		 cuisine=settings.getString("Cuisine", null);
		    type=settings.getString("Category", null);
		
		if(cuisine!=null || type!=null)
			 selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='true';";
		  else if(settings1.getBoolean("SEARCH",false))
			  selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='search';";

		  else
			  selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='false';";

				
		Log.e(LOG, selectQuery);
		Cursor c = db.rawQuery(selectQuery, null);
		int count=c.getCount();

		// looping through all rows and adding to list
		
		if (c!=null && count>0) {c.moveToFirst();
				ar.add(""+c.getDouble(c.getColumnIndex(KEY_RATING)));
				String d=ar.get(0);
				ar.add(""+c.getDouble(c.getColumnIndex(KEY_DISTANCE)));
				String d2=ar.get(1);
				ar.add(""+c.getDouble(c.getColumnIndex(KEY_PRICE_LEVEL)));
				String d3=ar.get(2);
				ar.add(c.getString(c.getColumnIndex(KEY_OPEN_NOW)));
				String d4=ar.get(3);
				
		}
		String selectQuery2;
		if(cuisine!=null || type!=null)
			selectQuery2 = "SELECT  * FROM " + TABLE_INFO+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='true';";
		 else if(settings1.getBoolean("SEARCH",false))
			 selectQuery2 = "SELECT  * FROM " + TABLE_INFO+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='search';";
			
		else
			selectQuery2 = "SELECT  * FROM " + TABLE_INFO+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='false';";
		
		Log.e(LOG, selectQuery2);
		c = db.rawQuery(selectQuery2, null);
		 count=c.getCount();
		
		// looping through all rows and adding to list
		if (c!=null && count>0) {

			c.moveToFirst();
				ar.add(c.getString(c.getColumnIndex(KEY_NAME)));
				String d=ar.get(4);
				ar.add(c.getString(c.getColumnIndex(KEY_OPEN_TIMINGS)));
				String d2=ar.get(5);
				
				ar.add(c.getString(c.getColumnIndex(KEY_WEBURL)));
				String d3=ar.get(6);
				ar.add(c.getString(c.getColumnIndex(KEY_ADDRESS)));
				String d4=ar.get(7);
				
		}
		
		String selectQuery3;
		if(cuisine!=null || type!=null)
			selectQuery3 = "SELECT  * FROM " + TABLE_TYPES+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='true';";
		 else if(settings1.getBoolean("SEARCH",false))
			 selectQuery3 = "SELECT  * FROM " + TABLE_TYPES+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='search';";
				
		else
			selectQuery3 = "SELECT  * FROM " + TABLE_TYPES+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='false';";
		
		Log.e(LOG, selectQuery3);
		c = db.rawQuery(selectQuery3, null);
int i=8;
count=c.getCount();


		// looping through all rows and adding to list
		if (c!=null && count>0) {

			c.moveToFirst();
			do{
				ar.add(c.getString(c.getColumnIndex(KEY_TYPES)));
				String d=ar.get(i++);
			}while(c.moveToNext());
		
			}
		
			
return ar;
	}
    public String call(String placeid)
    {
    	String str=null;
    	SQLiteDatabase db = this.getReadableDatabase();
    	String selectQuery = "SELECT "+KEY_PHONE+" FROM " + TABLE_INFO+" WHERE "+KEY_PLACE_ID+"='"+placeid+"';";
    	Cursor c = db.rawQuery(selectQuery, null);
    	 int count=c.getCount();
    	   	
   	 if(c!=null && count>0)
        {
   	
   	 
        
        c.moveToFirst();
       
        	str=c.getString(0);
        	
        	
        	
        	
        	
       
        }
   	 return str;
    	
    }
    public String getPlaceId(int position)
    {
    	String str=null;
    	String selectQuery;
        SharedPreferences settings=MainActivity.a.getSharedPreferences("Filter",0);
        SharedPreferences settings1=MainActivity.a.getSharedPreferences("GoogleMap",0);
		
        cuisine=settings.getString("Cuisine", null);
        type=settings.getString("Category", null);
    
    	SQLiteDatabase db = this.getReadableDatabase();
    	if(cuisine!=null || type!=null)
     selectQuery = "SELECT "+KEY_PLACE_ID+" FROM " + TABLE_INFO+" WHERE "+KEY_FILTER_BOOLEAN+"='true';";
    	else if(settings1.getBoolean("SEARCH",false))
    		 selectQuery = "SELECT "+KEY_PLACE_ID+" FROM " + TABLE_INFO+" WHERE "+KEY_FILTER_BOOLEAN+"='search';";
    	
    	else
    		 selectQuery = "SELECT "+KEY_PLACE_ID+" FROM " + TABLE_INFO+" WHERE "+KEY_FILTER_BOOLEAN+"='false';";
    	 Cursor c = db.rawQuery(selectQuery, null);
    	 int i=position+1;
    	 if(c!=null)
         {
    	 int count=c.getCount();
    	
    	 
         
         c.moveToFirst();
         while(i>0)
         {
         	str=c.getString(c.getColumnIndex(KEY_PLACE_ID));
         	
         	c.moveToNext();
         	i--;
         	
         	
         }
         }
    	 return str;
    }

    public String getPlaceId(String placeName)
    {
    	String str=null;
    	String selectQuery;
    	SQLiteDatabase db = this.getReadableDatabase();
    	selectQuery = "SELECT "+KEY_PLACE_ID+" FROM " + TABLE_INFO+" WHERE "+KEY_NAME+"='"+placeName.trim()+"';";
    	 Cursor c = db.rawQuery(selectQuery, null);
    	 if(c!=null)
         {
    	 int count=c.getCount();
    	
    	 
         
         c.moveToFirst();
         	
         str=c.getString(0);
         	
         
         }
    	 return str;
    }

public String getPlaceId(String lat,String lng) {
		
	String info;
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery;

	selectQuery = "SELECT "+KEY_PLACE_ID+" FROM " + TABLE_INFO
			+" WHERE  "+KEY_LATITUDE+"='"+lat+"' AND "+KEY_LONGITUDE+"='"+lng+"'";
	
		
		Log.e(LOG, selectQuery);
		Cursor c = db.rawQuery(selectQuery, null);
int count=c.getCount();
info=new String();

		// looping through all rows and adding to list
if (c!=null && count>0) 
{c.moveToFirst();

info=c.getString(0);	
return info;
}		
else
	return null;


	}
		
	
	

public ArrayList<String> getLatitudeLongitude(String PlaceId) {
		
		ArrayList<String> ar=new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery;
		SharedPreferences settings=MainActivity.a.getSharedPreferences("GoogleMap",0);
		
		if(cuisine!=null || type!=null)
			selectQuery = "SELECT "+KEY_LATITUDE+","+KEY_LONGITUDE+","+KEY_NAME+" FROM " + TABLE_INFO
			+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='true'";
		else if(settings.getBoolean("SEARCH",false))
			selectQuery = "SELECT "+KEY_LATITUDE+","+KEY_LONGITUDE+","+KEY_NAME+" FROM " + TABLE_INFO
			+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"' AND "+KEY_FILTER_BOOLEAN+"='search'";
		else
			selectQuery = "SELECT "+KEY_LATITUDE+","+KEY_LONGITUDE+","+KEY_NAME+" FROM " + TABLE_INFO
			+" WHERE "+ KEY_PLACE_ID + "='"+PlaceId+"'AND "+KEY_FILTER_BOOLEAN+"='false'";
	
		
		Log.e(LOG, selectQuery);
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		int count=c.getCount();
		if (c!=null && count!=0) {c.moveToFirst();
				ar.add(c.getString(0));
				ar.add(c.getString(1));
				ar.add(c.getString(2));
		}
		return ar;
	}


public String[][] getLatLongTotal(boolean Nearby) {
		
	String info[][]=null;
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery;
		if(Nearby)
		{	
		
	selectQuery = "SELECT "+KEY_LATITUDE+", "+KEY_LONGITUDE+", "+KEY_NAME+" FROM " + TABLE_INFO
			+" WHERE  "+KEY_FILTER_BOOLEAN+"='false'";
		}
		else
		{
			
			selectQuery = "SELECT "+KEY_LATITUDE+", "+KEY_LONGITUDE+", "+KEY_NAME+" FROM " + TABLE_INFO
					+" WHERE  "+KEY_FILTER_BOOLEAN+"='search'";
				
		}
	//selectQuery = "SELECT i."+KEY_LATITUDE+", i."+KEY_LONGITUDE+", r."+KEY_NAME+" FROM " + TABLE_INFO
		//	+" i INNER JOIN "+TABLE_RESTAURANT+" r ON "+TABLE_INFO+"."+KEY_PLACE_ID+"="+TABLE_RESTAURANT+"."+KEY_PLACE_ID+" WHERE  "+KEY_FILTER_BOOLEAN+"='false'";
		
		Log.e(LOG, selectQuery);
		Cursor c = db.rawQuery(selectQuery, null);
int count=c.getCount();

		// looping through all rows and adding to list
if (c!=null && count>0) 
{c.moveToFirst();
info=new String[count][3];

for(int i=0;i<count;i++){
	
info[i][0]=c.getString(0);
info[i][1]=c.getString(1);
info[i][2]=c.getString(2);
	c.moveToNext();
}		

}
	return info;
	}



public String[][] getReviews(String placeid)
    {
    	String str[][]=null;
    	Cursor c=null;
    	String selectQuery1;
    	SharedPreferences settings=MainActivity.a.getSharedPreferences("Filter",0);
    	 SharedPreferences settings1=MainActivity.a.getSharedPreferences("GoogleMap",0);
 		
		 cuisine=settings.getString("Cuisine", null);
		    type=settings.getString("Category", null);
		
    	SQLiteDatabase db = this.getReadableDatabase();
    	if(cuisine!=null || type!=null)
    	selectQuery1 = "SELECT * FROM " + TABLE_REVIEW+" WHERE "+KEY_FILTER_BOOLEAN+"='true' AND "+ KEY_PLACE_ID +"='"+placeid+"';";
    	else if(settings1.getBoolean("SEARCH",false))
    		selectQuery1 = "SELECT * FROM " + TABLE_REVIEW+" WHERE "+KEY_FILTER_BOOLEAN+"='search' AND "+ KEY_PLACE_ID +"='"+placeid+"';";
    	
    	else
    	 selectQuery1 = "SELECT * FROM " + TABLE_REVIEW+" WHERE "+KEY_FILTER_BOOLEAN+"='false' AND "+ KEY_PLACE_ID +"='"+placeid+"';";
    	try{
    	 c = db.rawQuery(selectQuery1, null);
    	}catch(Exception e)
    	{
    		
    	}
    	
    	int count=c.getCount();
    	 
    	 if(c!=null && count>0)
         {
    		 
    		 str=new String[count][3];
    	
    		 int i=0; 
    		 
         
        
    		 c.moveToFirst();
 			do{
 				str[i][0]=(c.getString(c.getColumnIndex(KEY_AUTHOR)));
 				str[i][1]=(c.getString(c.getColumnIndex(KEY_RATING)));
 				str[i][2]=(c.getString(c.getColumnIndex(KEY_TEXT)));
 				i++;
 			}while(c.moveToNext());
 		
         }
    	 return str;
    }
   

    /*
     * getting RESTAURANT count
     
    public int getRESTAURANTCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /*
     * Updating a RESTAURANT
     
    public int updateRESTAURANT(Hotel RESTAURANT) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
    //    values.put(KEY_RESTAURANT, RESTAURANT.getNote());
    //    values.put(KEY_STATUS, RESTAURANT.getStatus());

        // updating row
        return db.update(TABLE_RESTAURANT, values, KEY_PLACE_ID + " = ?",
                new String[] { String.valueOf(RESTAURANT.getPlaceId()) });
    }

    /*
     * Deleting a RESTAURANT
     
    public void deleteRESTAURANT(String place_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANT, KEY_PLACE_ID + " = ?",
                new String[] { place_id });
    }

    // ------------------------ "Info" table methods ----------------//

    //--------------------------" Review" table methods ---------// 
    // closing database
    */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    public void insertCuisineAndType()
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	SharedPreferences settings=MainActivity.a.getSharedPreferences("Filter",0);
		 cuisine=settings.getString("Cuisine", null);
		    type=settings.getString("Category", null);
		    
		    ContentValues value = new ContentValues();
            value.put(KEY_CUISINE_NAME, cuisine);
            value.put(KEY_TYPE_NAME,type);

            long CNT=db.insert(TABLE_CUISINE_TYPE_BOOL,null,value);
            Log.v("mohit", "record review created");
            
    }
    public boolean getCuisineAndType(String cuisine,String type)
    {
    	SQLiteDatabase db = this.getReadableDatabase();
    	StringBuffer sq=new StringBuffer();
    	sq.append("SELECT * FROM "+TABLE_CUISINE_TYPE_BOOL+" WHERE ");
    	if(cuisine==null && type==null)
    		return false;
    	else if(cuisine!=null && type==null)
    		sq.append(KEY_CUISINE_NAME+"='"+cuisine+"'");
    	else if(cuisine==null && type!=null)
    		sq.append(KEY_TYPE_NAME+"='"+type+"'");
    	else if(cuisine!=null && type!=null)
    		sq.append(KEY_CUISINE_NAME+"='"+cuisine+"' AND "+KEY_TYPE_NAME+"='"+type+"'");    	
    	
    	Cursor c = db.rawQuery(sq.toString(), null);
   	 int count=c.getCount();
   	 if(c!=null&&count>0)
        {
   		 return true;
        }
   	 else
   		 return false;
    	
    	
    }

	public String[] getPhotoReference(String placeId) {
		// TODO Auto-generated method stub
		String str[]=null;
		String selectQuery;
		SharedPreferences settings=MainActivity.a.getSharedPreferences("Filter",0);
		SharedPreferences settings1=MainActivity.a.getSharedPreferences("GoogleMap",0);
 		
		 cuisine=settings.getString("Cuisine", null);
		    type=settings.getString("Category", null);
		
    	SQLiteDatabase db = this.getReadableDatabase();
    	if(cuisine!=null || type!=null)
    	selectQuery = "SELECT "+KEY_PHOTO_REFERENCE+" FROM " + TABLE_PHOTO_REFERENCES+" WHERE "+ KEY_PLACE_ID + "='"+placeId+"' AND "+KEY_FILTER_BOOLEAN+"='true';";
    	else if(settings1.getBoolean("SEARCH",false))
    		selectQuery = "SELECT "+KEY_PHOTO_REFERENCE+" FROM " + TABLE_PHOTO_REFERENCES+" WHERE "+ KEY_PLACE_ID + "='"+placeId+"' AND "+KEY_FILTER_BOOLEAN+"='search';";
    	
    	else
    		selectQuery = "SELECT "+KEY_PHOTO_REFERENCE+" FROM " + TABLE_PHOTO_REFERENCES+" WHERE "+ KEY_PLACE_ID + "='"+placeId+"' AND "+KEY_FILTER_BOOLEAN+"='false';";
    	 Cursor c = db.rawQuery(selectQuery, null);
    	 int count=c.getCount();
    	 if(c!=null&&count>0)
         {
    	 str=new String[count];
    	int i=0;
    	 
         
         c.moveToFirst();
         do
         {
        	 str[i]=c.getString(c.getColumnIndex(KEY_PHOTO_REFERENCE));
         	i++;
         	
         	
         }while(c.moveToNext());
         
         	
         	
         	
         }
    	 else
    	 {
    		 return null;
    	 }
    	 return str;
	}
	
	

    
    public String[][] getList()
    {
    	String[][] str=null;
    	SharedPreferences settings1=MainActivity.a.getSharedPreferences("GoogleMap",0);
    	String selectQuery;
    	SQLiteDatabase db = this.getReadableDatabase();
    	if(settings1.getBoolean("SEARCH",false))
    	selectQuery = "SELECT "+KEY_NAME+","+KEY_PLACE_ID+" FROM " + TABLE_INFO+" WHERE "+KEY_FILTER_BOOLEAN+"='search';";
    	else
    	 selectQuery = "SELECT "+KEY_NAME+","+KEY_PLACE_ID+" FROM " + TABLE_INFO+" WHERE "+KEY_FILTER_BOOLEAN+"='false';";
    	Cursor c = db.rawQuery(selectQuery, null);
    	int i=0;
    	int count=c.getCount();
    	if(c!=null && count!=0)
        {
    
    	str=new String[count][4];
    	 c.moveToFirst();
         do
         {
         	str[i][0]=c.getString(0);
         	str[i][1]=c.getString(1);
         	i++;
         	
         	
         }while(c.moveToNext());
         }
    	 
    	 return str;
    }

	

    

	
	 public String[][] getNearbySearch()
	    {
	    	SharedPreferences settings=MainActivity.a.getSharedPreferences("GoogleMap",0);
	    	SharedPreferences.Editor editor=settings.edit();
	    	String[][] str=null;
	    	String selectQuery=null;
	    	SharedPreferences settings1=MainActivity.a.getSharedPreferences("Filter",0);
			
	    	
	    	SQLiteDatabase db = this.getReadableDatabase();
	    	if(settings.getBoolean("SORTSELECTED",false))
	    	{
	    		String selection=settings.getString("SELECTION",null);
	    		if(selection!=null)
	    		{
	    			if(selection.equals("By Distance"))
	    			selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='search'" + " ORDER BY R."+KEY_DISTANCE+";";
	    			
	    			if(selection.equals("By Rating"))
	        			selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='search'" + " ORDER BY R."+KEY_RATING+" DESC"+";";
	    			
	    			if(selection.equals("By Cost"))
	        			selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='search'" + " ORDER BY R."+KEY_PRICE_LEVEL+";";
	    			
	    		
	    			 if(selection.equals("By Likes"))
                         selectQuery = "SELECT I."+KEY_NAME+",I."+KEY_VICINITY+",R."+KEY_RATING+",R."+KEY_DISTANCE+",R."+KEY_PLACE_ID+",R."+KEY_LIKES+",R."+KEY_DISLIKES+" fROM Info I,Restaurant R WHERE I."+KEY_PLACE_ID+"=R."+KEY_PLACE_ID+" AND "+"I."+KEY_FILTER_BOOLEAN+"='search'" + " ORDER BY R."+KEY_LIKES+" DESC;";


	    			 Cursor c = db.rawQuery(selectQuery, null);
	    	    	 int i=0;
	    	    	 if(c!=null)
	    	         {
	    	    	 int count=c.getCount();
	    	    	 str=new String[count][7];
	    	    	 
	    	         
	    	         c.moveToFirst();
	    	         do
	    	         {
	    	         	str[i][0]=c.getString(0);
	    	         	str[i][1]=c.getString(1);
	    	         	str[i][2]=""+c.getDouble(3);
	    	         	str[i][3]=""+c.getDouble(2);
	    	         	str[i][4]=c.getString(4);
	    	         	str[i][5]=c.getString(5);
	    	         	str[i][6]=c.getString(6);
	    	         	i++;
	    	         	
	    	         	
	    	         }while(c.moveToNext());
	    	         }
	    		
	    		}
	    		
	    	}
	    	else
	    	{
	    	selectQuery = "SELECT "+KEY_NAME+","+KEY_VICINITY+" FROM " + TABLE_INFO+" WHERE "+KEY_FILTER_BOOLEAN+"='search';";
	    	 Cursor c = db.rawQuery(selectQuery, null);
	    	 int i=0;
	    	 if(c!=null)
	         {
	    	 int count=c.getCount();
	    	 str=new String[count][7];
	    	 
	         
	         c.moveToFirst();
	         do
	         {
	         	str[i][0]=c.getString(c.getColumnIndex(KEY_NAME));
	         	str[i][1]=c.getString(c.getColumnIndex(KEY_VICINITY));
	         	
	         	i++;
	         	
	         	
	         }while(c.moveToNext());
	         }
	    	 String selectQuery1 = "SELECT "+KEY_RATING+","+KEY_DISTANCE+","+KEY_PLACE_ID+","+KEY_LIKES+","+KEY_DISLIKES+" FROM " + TABLE_RESTAURANT+" WHERE "+KEY_FILTER_BOOLEAN+"='search';";
	   c = db.rawQuery(selectQuery1, null);
	    	 int j=0;
	    	 if(c!=null)
	         {
	    	 
	         
	         c.moveToFirst();
	         do
	         {
	         	str[j][2]=""+c.getDouble(1);
	         	str[j][3]=""+c.getDouble(0);
	         	str[j][4]=""+c.getString(2);
	         	str[j][5]=""+c.getString(3);
	         	str[j][6]=""+c.getString(4);
	         	j++;
	         	
	         	
	         }while(c.moveToNext());
	         }
	    	}
	    	
	    	 return str;
	    	
	               
	    }
	
	 public String getLD(String placeId){
	        SQLiteDatabase db = this.getReadableDatabase();
	        String sq = "SELECT "+KEY_LIKES+","+KEY_DISLIKES+","+KEY_LIKES_USER+","+KEY_DISLIKES_USER+" FROM " + TABLE_RESTAURANT+" WHERE "+KEY_PLACE_ID+"='"+placeId+"';";
	        
	         Cursor c2 = db.rawQuery(sq, null);
	        int likes=0,dislikes=0,l_user=0,d_user=0;
	        if(c2!=null)
	             {
	          c2.moveToFirst();
	        likes=c2.getInt(0);      
	        dislikes=c2.getInt(1);      
	        l_user=c2.getInt(2);
	        d_user=c2.getInt(3);
	        Log.v("dislike - likes", ""+likes);
	             }
	return likes+" "+dislikes+" "+l_user+" "+d_user;
	    }
	public void like(String placeId,int l,int d,int l_user,int d_user){
	    
	    SQLiteDatabase db = this.getWritableDatabase();
	    //String sq = "SELECT "+KEY_LIKES+" FROM " + TABLE_RESTAURANT+" WHERE "+KEY_PLACE_ID+"='"+placeId+"';";
	    
	     //Cursor c2 = db.rawQuery(sq, null);
	    /*int likes=0,i=0;
	    if(c2!=null)
	         {
	      c2.moveToFirst();
	    likes=c2.getInt(0);      

	    Log.v("dislike - likes", ""+likes);
	         }
	   */
	    String    selectQuery ="UPDATE "+TABLE_RESTAURANT+" SET "+KEY_LIKES+"="+(l)+","+KEY_DISLIKES+"="+(d)+","+KEY_LIKES_USER+"="+(l_user)+","+KEY_DISLIKES_USER+"="+(d_user)+" WHERE "+KEY_PLACE_ID+"='"+placeId+"';"; 
	    //Cursor c3 = db.execQuery(selectQuery, null);
	    db.execSQL(selectQuery);
	Log.v("mom", ""+selectQuery);
	}
	public void dislike(String placeId,int d){
	    

	    SQLiteDatabase db = this.getWritableDatabase();
	    /*
	    String sq = "SELECT "+KEY_DISLIKES+" FROM " + TABLE_RESTAURANT+" WHERE "+KEY_PLACE_ID+"='"+placeId+"';";
	    
	     Cursor c2 = db.rawQuery(sq, null);
	   int dislikes=0,i=0;
	   if(c2!=null)
	        {
	     c2.moveToFirst();
	   dislikes=c2.getInt(0);      
	    Log.v("dislike  -dislikes", ""+dislikes);
	        }
	  */
	   String    selectQuery ="UPDATE "+TABLE_RESTAURANT+" SET "+KEY_DISLIKES+"="+(d)+" WHERE "+KEY_PLACE_ID+"='"+placeId+"';"; 
	   db.execSQL(selectQuery);
	   Log.v("mom", ""+selectQuery);

	}


	public void DEClike(String placeId){
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    String sq = "SELECT "+KEY_LIKES+" FROM " + TABLE_RESTAURANT+" WHERE "+KEY_PLACE_ID+"='"+placeId+"';";
	    
	     Cursor c2 = db.rawQuery(sq, null);
	    int likes=0,i=0;
	    if(c2!=null)
	         {
	      c2.moveToFirst();
	    likes=c2.getInt(0);      

	    Log.v("dislike- dec likes", ""+likes);
	         }
	   if(likes!=0)
	   { String    selectQuery ="UPDATE "+TABLE_RESTAURANT+" SET "+KEY_LIKES+"="+(likes-1)+" WHERE "+KEY_PLACE_ID+"='"+placeId+"';"; 
	    Cursor c3 = db.rawQuery(selectQuery, null);
	   }
	  }
	public void DECdislike(String placeId){
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    String sq = "SELECT "+KEY_DISLIKES+" FROM " + TABLE_RESTAURANT+" WHERE "+KEY_PLACE_ID+"='"+placeId+"';";
	    
	     Cursor c2 = db.rawQuery(sq, null);
	   int dislikes=0,i=0;
	   if(c2!=null)
	        {
	     c2.moveToFirst();
	   dislikes=c2.getInt(0);      

	   Log.v("dislike - dec dislikes", ""+dislikes);
	        }
	  if(dislikes!=0){
	   String    selectQuery ="UPDATE "+TABLE_RESTAURANT+" SET "+KEY_DISLIKES+"="+(dislikes-1)+" WHERE "+KEY_PLACE_ID+"='"+placeId+"';"; 
	    Cursor c3 = db.rawQuery(selectQuery, null);
	  }
	}
	
	
	
	
	
	
	
	

}