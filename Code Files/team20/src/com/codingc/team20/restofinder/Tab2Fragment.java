package com.codingc.team20.restofinder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Tab2Fragment extends Fragment {
	 View rootView;
	 String placeId;
	 DatabaseHelper db;
	 ListView lv;
	 InputStream is;
	 String result;
	Button ib;
	ReviewAdapter ra;
    public Tab2Fragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
       rootView= inflater.inflate(R.layout.tab2, container, false);
        lv=(ListView)rootView.findViewById(R.id.review_list);
        placeId=Restaurant.placeId;
        db=new DatabaseHelper(Restaurant.getContext());
        String[][] inf=db.getReviews(placeId);
        try {
			String res[][]=new Receive().execute(inf).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       ib=(Button)rootView.findViewById(R.id.close);
       ib.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i=new Intent(Restaurant.a, sendHTTPData.class);
			startActivityForResult(i,0);
		}
	});
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
    	Log.v("mohit","Achieveddddddd");
     super.onActivityResult(requestCode, resultCode, data);
    

      updateAdapter();
    
    
    }
   public void updateAdapter()
   {
	   db=new DatabaseHelper(Restaurant.getContext());
       String[][] inf=db.getReviews(placeId);
	   new Receive().execute(inf);
   }
    
    // Params,Progress,Result
    private class Receive extends AsyncTask<String[][], Void, String[][]> {
        protected String[][] doInBackground(String[][]... str) {
          //  int count = urls.length;
        	String info[][]=str[0];
        	int length;
       	   	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             	nameValuePairs.add(new BasicNameValuePair("placeid",placeId));
             	  
             	try
              	   {
          		    HttpClient httpclient = new DefaultHttpClient();
          	        HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//receiveReview.php");
          	       
               httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
             	HttpResponse  response  = httpclient.execute(httppost); 
          	     HttpEntity entity = response.getEntity();
                   
          	     
          	     is = entity.getContent();
                   Log.d("mohit",entity.toString());
                   Log.d("mohit",is.toString());
                   Log.e("pass 1", "connection success ");
          	}
                  catch(Exception e)
          	{
                  	Log.e("Fail 1", e.toString());
          	    //	Toast.makeText(Restaurant.a.getApplicationContext(), "Invalid IP Address",
          			//Toast.LENGTH_LONG).show();
          	}     
                
                 try
                  {
                	 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
             	    StringBuilder sb = new StringBuilder();

             	    String line = null;
             	    try {
             	        while ((line = reader.readLine()) != null) {
             	            sb.append(line + "\n");
             	        }
             	    } catch (IOException e) {
             	        e.printStackTrace();
             	    } finally {
             	        try {
             	            is.close();
             	        } catch (IOException e) {
             	            e.printStackTrace();
             	        }
             	    }
                    result = sb.toString();
          	    Log.e("pass 2", "connection success ="+result);
          	}
                  catch(Exception e)
          	{
                      Log.e("Fail 2", e.toString());
          	}     
                 
                 String rs[][] = null;
             	try
        		{
             		
        	            JSONObject json_data = new JSONObject(result);
        	            int code=json_data.getInt("code");
        	            if(info!=null)
        	            	length=info.length;
        	            else
        	            	length=0;
        	            if(code==1){
        	            rs=new String[json_data.length()+length-1][4];
        	            
        	            
        	            	
        	            for(int j=0;j<length;j++)
        	            	for(int i=0;i<3;i++)
        	            		rs[j][i]=info[j][i];
        	            
        	            for(int j=0;j<(json_data.length()-1);j++){
        	            	
        	            	JSONArray ijson=(JSONArray)json_data.getJSONArray(""+j);
        	            	for(int i=0;i<4;i++){
        	            		rs[j+length][i]=ijson.getString(i);
        	            	 }
        	            }
             		}
             		else{
             			
             			  rs=new String[info.length][4];
          	            
          	            for(int j=0;j<info.length;j++)
          	            	for(int i=0;i<3;i++)
          	            		rs[j][i]=info[j][i];
          	    		
             		}
        	            
        	         
        	            
        	           // Intent i=new Intent(signup.this,Main_Activity.class);
        	           // startActivity(i);
        	            
        	    }
        		catch(Exception e)
        		{
        	            Log.e("Fail 3", e.toString());
        		}
    
          	        	     return rs;
        
        }

        protected void onProgressUpdate(Void param) {
           // setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String[][] result) {
           // showDialog("Downloaded " + result + " bytes");
        	String[][] info;
        	info=result;
        	try
    	       	{
       			
        		 if(info!=null)
        	        {
        	        ra=new ReviewAdapter(getActivity(), R.layout.review, info);
        	        lv.setAdapter(ra);
        	        }
        	     }
    	          
    	       	catch(Exception e)
    	       	{
    	                   Log.e("Fail 3", e.toString());
    	       	}

        
        return;
        
        }



    	 }

    private class ReviewAdapter extends ArrayAdapter<String> {
        public Context context;
        public int layoutResourceId;
       
        String[][] info;

        public ReviewAdapter(Context context,int layoutResourceId,String[][] info) {
            super(context, layoutResourceId);
            this.layoutResourceId = layoutResourceId;
            
            this.info=info;
        }

        @Override
        public View getView(int rowIndex, View convertView, ViewGroup parent) {
            View row = convertView;
            LayoutInflater layout=null;
            if(null == row) {
             layout = (LayoutInflater)getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE
                );
             row = layout.inflate(R.layout.review, null);
            }
               
                TextView name=(TextView)row.findViewById(R.id.name);
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Mark Simonson - Proxima Nova Soft Bold.otf");
                Typeface font3 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/empty.ttf");
                name.setTypeface(font);
                TextView rating=(TextView)row.findViewById(R.id.rating);
                rating.setTypeface(font3);
                TextView review=(TextView)row.findViewById(R.id.review);
                Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Mark Simonson - Proxima Nova Soft Regular.otf");
                review.setTypeface(font1);
                TextView rated=(TextView)row.findViewById(R.id.rated);
                Typeface font4 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
                rated.setTypeface(font4);
                TextView time=(TextView)row.findViewById(R.id.time);
                
               time.setTypeface(font3);
                if(info[rowIndex][0]!=null)
                name.setText(info[rowIndex][0]);
                if(info[rowIndex][1]!=null)
                rating.setText(info[rowIndex][1]);
                if(info[rowIndex][2]!=null)
                review.setText(info[rowIndex][2]);
                if(info[rowIndex][3]!=null)
                   time.setText(calculate(info[rowIndex][3]));
                else
                	time.setText("Google User");
                
           
            
            return row;
        }
        
        public int getCount()
        {
        	if(info!=null)
        	return info.length;
        	else
        		return 0;
        }
    }

    public String calculate(String date)
    {
    	if(date!=null)
    	{
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Date d=new Date();
    	Date date1=null;
    	Date date2=null;
    	Calendar cal1=null;
    	 Calendar cal2=null;
		try {
			//String dat=d.toLocaleString();
			String dte=df.format(d);
			date1 = df.parse(dte);
			 date2 = df.parse(date);
	        cal1 = Calendar.getInstance();
	        cal2 = Calendar.getInstance();
	        cal2.setTime(date1);
	        cal1.setTime(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        int numberOfDays = 0;
        while (cal1.before(cal2)) {
            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
               &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                numberOfDays++;
                cal1.add(Calendar.DATE,1);
            }else {
                cal1.add(Calendar.DATE,1);
            }
        }
        if(numberOfDays==0)
        	return "Today";
        else if(numberOfDays==1)
        	return "Yesterday";
        else
        	return numberOfDays+" days ago";
    	}
    	else
    		return "Google User";
    }

}
  class RoundedImageView extends ImageView {

public RoundedImageView(Context context) {
    super(context);
    // TODO Auto-generated constructor stub
}

public RoundedImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
}

public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
}

@Override
protected void onDraw(Canvas canvas) {

    Drawable drawable = getDrawable();

    if (drawable == null) {
        return;
    }

    if (getWidth() == 0 || getHeight() == 0) {
        return; 
    }
    Bitmap b =  ((BitmapDrawable)drawable).getBitmap() ;
    Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

    int w = getWidth(), h = getHeight();


    Bitmap roundBitmap =  getCroppedBitmap(bitmap, w);
    canvas.drawBitmap(roundBitmap, 0,0, null);

}

public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
    Bitmap sbmp;
    if(bmp.getWidth() != radius || bmp.getHeight() != radius)
        sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
    else
        sbmp = bmp;
    Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
            sbmp.getHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final int color = 0xffa19774;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

    paint.setAntiAlias(true);
    paint.setFilterBitmap(true);
    paint.setDither(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(Color.parseColor("#BAB399"));
    canvas.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f,
            sbmp.getWidth() / 2+0.1f, paint);
    paint.setXfermode (new PorterDuffXfermode (PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(sbmp, rect, rect, paint);


            return output;
}




}
