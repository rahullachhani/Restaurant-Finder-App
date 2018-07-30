package com.codingc.team20.restofinder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

public class Tab1Fragment extends Fragment{
    DatabaseHelper db;  
    ArrayList<String> info;
    StringBuffer wishlist;
    String placeId="";
        ImageButton imgbtn6;
    ImageButton b1;
ImageButton wish;
    //ImageView imageView;
    ToggleButton like;
    ToggleButton dislike;
    TextView _like;
    TextView _dislike;
    InputStream is=null;
    String result=null;
    String line=null;
    boolean likes_user=true;
    boolean dislikes_user=true;
      SharedPreferences settings;
      SharedPreferences.Editor editor;
    View rootView;
    ViewFlipper viewFlipper=null;
    static int count=0;
    String str[];
    public boolean checklike=false;
    public boolean checkdislike=false;
    ProgressDialog progressDialog;
    int l,d;
	public ImageButton call;
    public Tab1Fragment() {
            }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setHasOptionsMenu(true);
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu,inflater);
      inflater = Restaurant.a.getMenuInflater();
      inflater.inflate(R.menu.main_share, menu);
    
    } 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
        
        case R.id.action_share:
            Intent intent = new Intent(Intent.ACTION_SEND);
        	intent.setType("text/plain");
        	intent.putExtra(Intent.EXTRA_TEXT, "I would like to recommend Restaurant "+info.get(4)+" . Must visit :)");
        	startActivity(Intent.createChooser(intent, "Share with"));	
                
        return super.onOptionsItemSelected(item);
        default:
       
        	return super.onOptionsItemSelected(item);
        }
        
        
        }


    

            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
                 rootView= inflater.inflate(R.layout.tab1 , container, false);
               //  progressDialog = ProgressDialog.show(MainActivity.a, "Loading...",
                //         "Please wait...", true);
                placeId=Restaurant.placeId;
                db=new DatabaseHelper(Restaurant.getContext());
                info=db.getRestaurantInfo(placeId);
                viewFlipper=(ViewFlipper)rootView.findViewById(R.id.imageButton4);
                
               
                 like=(ToggleButton)rootView.findViewById(R.id.ib1);
                dislike=(ToggleButton)rootView.findViewById(R.id.ib2);
                
                  like.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                    if(checklike)
                        {
                            checklike=false;
                        }
                        else
                            checklike=true;
                        
                        if(checkdislike)
                        {
                            dislike.setChecked(false);
                            checkdislike=false;
                        }
    
                    new sendLike().execute(null,null,null);                
                    }
                });
              /*    imgbtn6=(ImageButton)rootView.findViewById(R.id.imageButton6);
                    
                  imgbtn6.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, "I would like to recommend Restaurant "+info.get(4)+" . Must visit :)");
                        startActivity(Intent.createChooser(intent, "Share with"));    
                    }
                });
    */
                  b1=(ImageButton)rootView.findViewById(R.id.button1);
                    
                  b1.setOnClickListener(new OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new sendOrder().execute();
                            }
                });
    
                  
                  dislike.setOnClickListener(new OnClickListener() {
                    
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
    if(checkdislike)
                                {
                                    checkdislike=false;
                                }
                                else
                                    checkdislike=true;
                                
                                
                                if(checklike)
                                {
                                    like.setChecked(false);
                                    checklike=false;
                                }
                
                        new sendDisLike().execute(null,null,null);
                            }
                        });
              
                
            //    new Receive().execute(null,null,null);
                  String res=db.getLD(placeId);
                  String arr[]=res.split(" ");
                l=    Integer.parseInt(arr[0]);
               d=     Integer.parseInt(arr[1]);
                int l_user=Integer.parseInt(arr[2]);
                int d_user=Integer.parseInt(arr[3]);
                  if(l_user==1)
                  {
                          like.setChecked(true);
                          checklike=true;
                  }
              if(d_user==1)
         {
                 dislike.setChecked(true);
                 checkdislike=true;
         }
                       Log.v("like_rahul","likes="+l+" dislikes="+d+"    likes_user="+l_user+"    dislikes_user="+d_user);
                   TextView t=(TextView)rootView.findViewById(R.id.like);
                   t.setText("Likes :"+l);
                   TextView td=(TextView)rootView.findViewById(R.id.dislike);
                   td.setText("Dislikes :"+d);
                   
                   if(l_user==0)
                           likes_user=true;
                   else
                           likes_user=false;
                if(d_user==0)
                           dislikes_user=true;
                   else
                           dislikes_user=false;

          Log.v("likes","likes="+Integer.parseInt(arr[0])+" dislikes="+Integer.parseInt(arr[1]));
                
                wishlist=new StringBuffer();  
                wish=(ImageButton)rootView.findViewById(R.id.wishlist);
                call=(ImageButton)rootView.findViewById(R.id.call); 
                //if(info.get(1)!=null){
            //    TextView t1=(TextView)rootView.findViewById(R.id.textView15);
                TextView dis=(TextView)rootView.findViewById(R.id.textView9);
               // imageView=(ImageView)rootView.findViewById(R.id.imageButton4);
                
                
              
                /*if(db.getPhotoReference(placeId)!=null)
                {
                    ImageLoader imageLoader=new ImageLoader(getActivity());
                    imageLoader.DisplayImage(db.getPhotoReference(placeId),imageView);
                }*/
        //         t1.setText("Distance:");
                if(Double.parseDouble(info.get(1))>=1000)
                	dis.setText(""+(Double.parseDouble(info.get(1)))/1000+" km");
                else
                dis.setText(""+info.get(1)+" m");
                
               
             //   }else{
            //         View myView = rootView.findViewById(R.id.textView15);
              //       View t2=rootView.findViewById(R.id.textView16);
            //          ViewGroup parent = (ViewGroup) myView.getParent();
             //        parent.removeView(myView);
               //      parent.removeView(t2);
               // info.add(1, "0");
              //  }
                
                if(info.get(2)!=null){
                    TextView t1=(TextView)rootView.findViewById(R.id.textView11);
                    TextView t2=(TextView)rootView.findViewById(R.id.textView12);
                    t1.setText("Price Level:");
                    if(info.get(2).equals("2.0"))
                    {
                    t2.setText("Moderate");
                    }
                    if(info.get(2).equals("0.0"))
                    {
                    t2.setText("Free");
                    }
                    if(info.get(2).equals("1.0"))
                    {
                    t2.setText("InExpensive");
                    }
                    if(info.get(2).equals("3.0"))
                    {
                    t2.setText("Expensive");
                    }
                    if(info.get(2).equals("4.0"))
                    {
                    t2.setText("Very Expensive");
                    }
                }
                else{
                     View myView = rootView.findViewById(R.id.textView11);
                     View t2=rootView.findViewById(R.id.textView12);
                      ViewGroup parent = (ViewGroup) myView.getParent();
                      parent.removeAllViews();
                     parent.removeView(myView);
                     parent.removeView(t2);
                     info.add(2, "Moderate");
                     
                }
                TextView ig=(TextView)rootView.findViewById(R.id.view3);;
                if(info.get(3)!=null){
                	
                   // TextView t1=(TextView)rootView.findViewById(R.id.textView7);
                if(info.get(3).equals("true"))
                {
                  ig.setText("Open");
                  ig.setTextColor(Color.parseColor("#31B404"));
                  ig.setBackgroundResource(R.drawable.openclose);
                  
                }
                else
                {
                    ig.setText("Closed");
                    ig.setTextColor(Color.parseColor("#FF0000"));
                    ig.setBackgroundResource(R.drawable.openclosegreen);
                    
                  }
                
                }
                else
                {
                	ig.setText("Open");
                    ig.setTextColor(Color.parseColor("#31B404"));
                }
                if(info.get(4)!=null){
                    
                    TextView t1=(TextView)rootView.findViewById(R.id.textView4);
                    t1.setText(info.get(4));
                    Restaurant.restaurantName=info.get(4);
                    
                    }
                else{
                     View myView = rootView.findViewById(R.id.textView4);
                    ViewGroup parent = (ViewGroup) myView.getParent();
                    parent.removeAllViews();
                     parent.removeView(myView);
                     
                     
                }
                if(info.get(5)!=null){
                    TextView t1=(TextView)rootView.findViewById(R.id.textView19);
                       
                    TextView t2=(TextView)rootView.findViewById(R.id.textView20);
                    t1.setText("Open Timings:");
                    t2.setText(""+info.get(5));
                    }
                else{
                     View myView = rootView.findViewById(R.id.textView19);
                     View t2=rootView.findViewById(R.id.textView20);
                      ViewGroup parent = (ViewGroup) myView.getParent();
                      parent.removeAllViews();
                     parent.removeView(myView);
                     parent.removeView(t2);
                }
                if(info.get(6)!=null){
                    TextView t1=(TextView)rootView.findViewById(R.id.textView13);
                   
                    TextView t2=(TextView)rootView.findViewById(R.id.textView14);
                    t1.setText("Website:");
                    t2.setText(""+info.get(6));
                    }
                else{
                     View myView = rootView.findViewById(R.id.textView13);
                     View t2=rootView.findViewById(R.id.textView14);
                      ViewGroup parent = (ViewGroup) myView.getParent();
                      parent.removeAllViews();
                     parent.removeView(myView);
                     parent.removeView(t2);
                }
                if(info.get(7)!=null){
                    TextView t1=(TextView)rootView.findViewById(R.id.textView5);
                   
                    TextView t2=(TextView)rootView.findViewById(R.id.textView6);
                    
                    t1.setText("Address:");
                    t2.setText(""+info.get(7));
                    }
                else{
                     View myView = rootView.findViewById(R.id.textView5);
                     View t2=rootView.findViewById(R.id.textView6);
                      ViewGroup parent = (ViewGroup) myView.getParent();
                      parent.removeAllViews();
                     parent.removeView(myView);
                     parent.removeView(t2);
                }
        //        TextView t2=(TextView)rootView.findViewById(R.id.textView10);
                StringBuffer s=new StringBuffer();
                for(int i=8;i<info.size();i++)
            {if(info.get(i)!=null)
                s.append(info.get(i)+",");
            }
          //          t2.setText(s.toString());
                
                
                
                
                    wish.setOnClickListener(new OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            wishlist.append(info.get(1)+" # ");
                            wishlist.append(info.get(4)+" # ");
                            wishlist.append(info.get(7)+" # ");
                            wishlist.append(info.get(0)+" # ");
                            wishlist.append(l+" # ");
                            wishlist.append(d);
                              
                            MainActivity.key.add(wishlist.toString());
                     //       Toast.makeText(Restaurant.getContext(), info.get(4)+" has been added successfully.",
                       //             Toast.LENGTH_SHORT).show();
                            
                                        
                        }
                    });
                    
 call.setOnClickListener(new OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                        	 String phnum = db.call(placeId);
                        	 if(phnum!=null)
                        	 {
                        	    Intent callIntent = new Intent(Intent.ACTION_CALL);
                        	    callIntent.setData(Uri.parse("tel:" + phnum));
                        	    startActivity(callIntent);
                        	 }
                        	 else
                        	 {
                        		 Toast.makeText(Restaurant.getContext(), "No phone number available",
                                          Toast.LENGTH_SHORT).show();
                        	 }
                            
                                        
                        }
                    });
                    File extStore = Environment.getExternalStorageDirectory();
                    File myFile = new File(extStore.getAbsolutePath() + File.separator+ placeId);
                    
                    if(myFile.exists()){
                        rootView.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                        setFlipper();
                        
                        
                    }
                    else if(NetworkState.isNetworkAvailable(MainActivity.a))
                    {
                    
                   
                    
                    // Execute the task
                    str=db.getPhotoReference(placeId);
                    if(str!=null)
                    {
                        
                            GetXMLTask task = new GetXMLTask(); 
                            task.execute(new String[] { "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+str[0]+"&key=AIzaSyCobsTWPTHBul5zbvsnHLCGzVvnTpKGKVw" });
                        
                    }
                    else
                    {
                        rootView.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                        ImageView image = new ImageView(getActivity().getApplicationContext());
                        image.setBackgroundResource(R.drawable.no_image);
                        viewFlipper.addView(image);
                        
                        //imageView.setBackgroundResource(R.drawable.img2);
                    }
                    }
                    else
                    {
                        rootView.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                        ImageView image = new ImageView(getActivity().getApplicationContext());
                        image.setBackgroundResource(R.drawable.no_image);
                        viewFlipper.addView(image);
                    }
                     
               //     new Receive().execute(null,null,null);
                    
                    
                     
                    //   progressDialog.dismiss();
                
                return rootView;
            }
            private void setFlipper() {
                // TODO Auto-generated method stubb 
                 File extStore = Environment.getExternalStorageDirectory();
                    File myFile = new File(extStore.getAbsolutePath() + File.separator +placeId);
                    if(myFile!=null)
                    {
                File[] files = myFile.listFiles();
                int numberOfImages=files.length;
                for(int i=1;i<=numberOfImages;i++)
                {
                Bitmap bitmap=loadFromFile(Environment.getExternalStorageDirectory()
                        + File.separator+ placeId+File.separator+i+".jpg");
                if(bitmap!=null)
                {
                    ImageView imageView = new ImageView(getActivity());
                    int width = bitmap.getWidth();

                    int height = bitmap.getHeight();

                    float scaleWidth = ((float) 530) / width;

                    float scaleHeight = ((float) 250) / height;

                    // create a matrix for the manipulation
                    Matrix matrix = new Matrix();
                    // resize the bit map

                    matrix.postScale(scaleWidth, scaleHeight);
                    // matrix.postRotate(90);
                    // recreate the new Bitmap
                    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                            matrix, false);
                    
                     imageView.setImageBitmap(resizedBitmap);
                    viewFlipper.addView(imageView);
                }
                }
               
                }
                
            }
            
             private class Receive extends AsyncTask<String, String, String> {
                     protected String doInBackground(String... params) {
                       //  int count = urls.length;
                    
                         ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                              settings=Restaurant.a.getSharedPreferences("preference",0);

                              nameValuePairs.add(new BasicNameValuePair("placeid",placeId));
                              nameValuePairs.add(new BasicNameValuePair("user",""+settings.getString("name", "mohit")));

                                      try
                                          {
                                           HttpClient httpclient = new DefaultHttpClient();
                                           HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//likesreceiver.php");
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
                                          // Toast.makeText(Restaurant.getContext(), "Invalid IP Address",
                                          // Toast.LENGTH_LONG).show();
                                   }     
                                     
                                      try
                                       {
                                           BufferedReader reader = new BufferedReader
                                           (new InputStreamReader(is,"iso-8859-1"),8);
                                           StringBuilder sb = new StringBuilder();
                                           while ((line = reader.readLine()) != null)
                                       {
                                               sb.append(line + "\n");
                                           }
                                           is.close();
                                           result = sb.toString();
                                       Log.e("pass 2", "connection success ="+result);
                                   }
                                       catch(Exception e)
                                   {
                                           Log.e("Fail 2", e.toString());
                                   }     
                                     
                                      
                                  return result;

                     
                     
                     }

                     protected void onProgressUpdate(Integer... progress) {
                        // setProgressPercent(progress[0]);
                     }

                     protected void onPostExecute(String result) {
                        // showDialog("Downloaded " + result + " bytes");

                            try
                            {
                                    JSONObject json_data = new JSONObject(result);
                                l=     Integer.parseInt(json_data.getString("likes"));
                                d=  Integer.parseInt(json_data.getString("dislikes"));
                                   String l_user=json_data.getString("likes_user");
                                   String d_user=json_data.getString("dislikes_user");
                                   if(l_user.equals("1"))
                                   {
                                       like.setChecked(true);
                                       checklike=true;
                                   }
                               if(d_user.equals("1"))
                               {
                                   dislike.setChecked(true);
                                   checkdislike=true;
                               }
                                    Log.v("like","likes="+l+" dislikes="+d+"    likes_user="+likes_user+"    dislikes_user="+dislikes_user);
                                    TextView t=(TextView)rootView.findViewById(R.id.like);
                                    t.setText("Likes :"+l);
                                    TextView td=(TextView)rootView.findViewById(R.id.dislike);
                                    td.setText("Dislikes :"+d);
                                    
                                    if(l_user.equals("0"))
                                        likes_user=true;
                                    else
                                        likes_user=false;
                                 if(d_user.equals("0"))
                                        dislikes_user=true;
                                    else
                                        dislikes_user=false;
                                    
                            }
                            catch(Exception e)
                            {
                                    Log.e("Fail 3", e.toString());
                            }

                     
                     
                     }

                     }
             private class sendOrder extends AsyncTask<String, String, String> {
                 protected String doInBackground(String... params) {
                   //  int count = urls.length;
                
                     ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                          settings=Restaurant.a.getSharedPreferences("preference",0);

                          nameValuePairs.add(new BasicNameValuePair("placeid",placeId));
                          nameValuePairs.add(new BasicNameValuePair("user",""+settings.getString("name", "mohit")));
                          nameValuePairs.add(new BasicNameValuePair("placename",""+info.get(4)));

                                  try
                                      {
                                       HttpClient httpclient = new DefaultHttpClient();
                                       HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//sendOrder.php");
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
                                    //   Toast.makeText(Restaurant.getContext(), "Invalid IP Address",
                                    //   Toast.LENGTH_LONG).show();
                               }     
                                 
                                  try
                                   {
                                       BufferedReader reader = new BufferedReader
                                       (new InputStreamReader(is,"iso-8859-1"),8);
                                       StringBuilder sb = new StringBuilder();
                                       while ((line = reader.readLine()) != null)
                                   {
                                           sb.append(line + "\n");
                                       }
                                       is.close();
                                       result = sb.toString();
                                   Log.e("pass 2", "connection success ="+result);
                               }
                                   catch(Exception e)
                               {
                                       Log.e("Fail 2", e.toString());
                               }     
                                 
                                  
                              return result;

                 
                 
                 }

                 protected void onProgressUpdate(Integer... progress) {
                    // setProgressPercent(progress[0]);
                 }

                 protected void onPostExecute(String result) {
                    // showDialog("Downloaded " + result + " bytes");

                        try
                        {
                                JSONObject json_data = new JSONObject(result);
                            l=     Integer.parseInt(json_data.getString("code"));
                            
                            if(l==1)
                                Toast.makeText(Restaurant.a, "Order Request Sent", Toast.LENGTH_SHORT).show();
                                
                            if(l==0)
                                Toast.makeText(Restaurant.a, "Try Again", Toast.LENGTH_SHORT).show();
                               
                                   
                        }
                        catch(Exception e)
                        {
                                Log.e("Fail 3", e.toString());
                        }

                 
                 
                 }

                 }
             
                 
               private class sendLike extends AsyncTask<String, String, String> {
                     protected String doInBackground(String... str) {
                       //  int count = urls.length;
                               ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                               settings=Restaurant.a.getSharedPreferences("preference",0);

                              nameValuePairs.add(new BasicNameValuePair("placeid",placeId));
                              nameValuePairs.add(new BasicNameValuePair("user",settings.getString("name", "mohit")));
                              nameValuePairs.add(new BasicNameValuePair("like_user",""+likes_user));
                                
                              try
                                  {
                                   HttpClient httpclient = new DefaultHttpClient();
                                   HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//likesend.php");
                                  
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
                                //   Toast.makeText(Restaurant.a.getApplicationContext(), "Invalid IP Address",
                                 //  Toast.LENGTH_LONG).show();
                           }     
                             
                              try
                               {
                                   BufferedReader reader = new BufferedReader
                                   (new InputStreamReader(is,"iso-8859-1"),8);
                                   StringBuilder sb = new StringBuilder();
                                   while ((line = reader.readLine()) != null)
                               {
                                       sb.append(line + "\n");
                                   }
                                   is.close();
                                   result = sb.toString();
                               Log.e("pass 2", "connection success ="+result);
                           }
                               catch(Exception e)
                           {
                                   Log.e("Fail 2", e.toString());
                           }     
                           
                                            return result;
                     
                     }

                     protected void onProgressUpdate(Integer... progress) {
                        // setProgressPercent(progress[0]);
                     }

                     protected void onPostExecute(String result) {
                        // showDialog("Downloaded " + result + " bytes");
                            try
                               {
                                       JSONObject json_data = new JSONObject(result);
                                       int l=     Integer.parseInt(json_data.getString("likes"));
                                             int d=  Integer.parseInt(json_data.getString("dislikes"));
                                              String l_user=json_data.getString("likes_user");
                                              String d_user=json_data.getString("dislikes_user");
                                           db.like(placeId,l,d,Integer.parseInt(l_user),Integer.parseInt(d_user));
                                             //db.dislike(placeId,d);
                                      
                                               TextView t=(TextView)rootView.findViewById(R.id.like);
                                               t.setText("Likes :"+l);
                                               TextView td=(TextView)rootView.findViewById(R.id.dislike);
                                               td.setText("Dislikes :"+d);
                                               
                                             
                                               if(l_user.equals("0"))
                                                   likes_user=true;
                                               else
                                                   likes_user=false;
                                            if(d_user.equals("0"))
                                                   dislikes_user=true;
                                               else
                                                   dislikes_user=false;
                                               
                                     /*    if(this.likes_user)
                                               like.setBackground(getResources().getDrawable(R.drawable.like));
                                           else
                                              like.setBackground(getResources().getDrawable(R.drawable.liked));
                                            
                                        if(this.dislikes_user)
                                               dislike.setBackground(getResources().getDrawable(R.drawable.dislike));
                                           else
                                              dislike.setBackground(getResources().getDrawable(R.drawable.disliked));
                                        */    
                                                   }
                              
                               catch(Exception e)
                               {
                                       Log.e("Fail 3", e.toString());
                               }

                     
                     
                     
                     }

                     }
                 




               private class sendDisLike extends AsyncTask<String, String, String> {
                     protected String doInBackground(String... str) {
                       //  int count = urls.length;
                            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                               settings=Restaurant.a.getSharedPreferences("preference",0);

                              nameValuePairs.add(new BasicNameValuePair("placeid",placeId));
                              nameValuePairs.add(new BasicNameValuePair("user",settings.getString("name", "mohit")));
                              nameValuePairs.add(new BasicNameValuePair("dislikes_user",""+dislikes_user));
                                
                              try
                                  {
                                   HttpClient httpclient = new DefaultHttpClient();
                                   HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//dislikesend.php");
                                  
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
                                //   Toast.makeText(Restaurant.a.getApplicationContext(), "Invalid IP Address",
                                //   Toast.LENGTH_LONG).show();
                           }     
                             
                              try
                               {
                                   BufferedReader reader = new BufferedReader
                                   (new InputStreamReader(is,"iso-8859-1"),8);
                                   StringBuilder sb = new StringBuilder();
                                   while ((line = reader.readLine()) != null)
                               {
                                       sb.append(line + "\n");
                                   }
                                   is.close();
                                   result = sb.toString();
                               Log.e("pass 2", "connection success ="+result);
                           }
                               catch(Exception e)
                           {
                                   Log.e("Fail 2", e.toString());
                           }     
                           
                                                return result;
                     
                     }

                     protected void onProgressUpdate(Integer... progress) {
                        // setProgressPercent(progress[0]);
                     }

                     protected void onPostExecute(String result) {
                        // showDialog("Downloaded " + result + " bytes");
                         try
                               {
                                       JSONObject json_data = new JSONObject(result);
                                       int l=     Integer.parseInt(json_data.getString("likes"));
                                             int d=  Integer.parseInt(json_data.getString("dislikes"));
                                              String l_user=json_data.getString("likes_user");
                                              String d_user=json_data.getString("dislikes_user");
                                              db.like(placeId,l,d,Integer.parseInt(l_user),Integer.parseInt(d_user));
                                               //db.dislike(placeId,d);
                                      
                                               TextView t=(TextView)rootView.findViewById(R.id.like);
                                               t.setText("Likes :"+l);
                                               TextView td=(TextView)rootView.findViewById(R.id.dislike);
                                               td.setText("Dislikes :"+d);
                                               
                                               if(l_user.equals("0"))
                                                   likes_user=true;
                                               else
                                                   likes_user=false;
                                            if(d_user.equals("0"))
                                                   dislikes_user=true;
                                               else
                                                   dislikes_user=false;
                                               
                                        /* if(this.likes_user)
                                               like.setBackground(getResources().getDrawable(R.drawable.like));
                                           else
                                              like.setBackground(getResources().getDrawable(R.drawable.liked));
                                            
                                        if(this.dislikes_user)
                                               dislike.setBackground(getResources().getDrawable(R.drawable.dislike));
                                           else
                                              dislike.setBackground(getResources().getDrawable(R.drawable.disliked));
                                           */ 
                                                   }
                              
                               catch(Exception e)
                               {
                                       Log.e("Fail 3", e.toString());
                               }

                     }

                     }
                 





            
            
            
            
            
            
            private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
                
            	
            	
            	
                protected void onPreExecute()
                {
                    rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.imageButton4).setVisibility(View.INVISIBLE);
                   // rootView.findViewById(R.id.textView7).setVisibility(View.INVISIBLE);
                    rootView.findViewById(R.id.textView4).setVisibility(View.INVISIBLE);
                    if(count>=0 && count<4)
                    Toast.makeText(Restaurant.getContext(), "Please don't back press until the image loads.",
                            Toast.LENGTH_SHORT).show();
                }
                // Sets the Bitmap returned by doInBackground
                @Override
                protected void onPostExecute(Bitmap result) {
                    
                   // imageView.setImageBitmap(result);
                    count++;
                    int i=0;
                    int n=0;
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    result.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                    File direct = new File(Environment.getExternalStorageDirectory() + File.separator+ placeId);

                    if(!direct.exists())
                    {
                        if(direct.mkdir()) 
                          {
                            i=1;
                           //directory is created;
                          }

                    }
                    File f;
                    if(i==1)
                    {
                         f= new File(Environment.getExternalStorageDirectory()
                                + File.separator+placeId+File.separator+"1.jpg");
                         i=0;
                    }
                    else
                    {
                        File[] files=direct.listFiles();
                        n=files.length;
                        f = new File(Environment.getExternalStorageDirectory()
                                            + File.separator+placeId+File.separator+(n+1)+".jpg");
                    }
                    try {
                        f.createNewFile();
                        FileOutputStream fo = new FileOutputStream(f);
                        fo.write(bytes.toByteArray());

                        // remember close de FileOutput
                        fo.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                    //write the bytes in file
                    if((n+1)==str.length)
                    {
                        setFlipper();
                    rootView.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                    rootView.findViewById(R.id.imageButton4).setVisibility(View.VISIBLE);
                  //  rootView.findViewById(R.id.textView7).setVisibility(View.VISIBLE);
                    
                   
                    }
                    else
                    {
                        GetXMLTask task = new GetXMLTask(); 
                        task.execute(new String[] { "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+str[n+1]+"&key=AIzaSyCobsTWPTHBul5zbvsnHLCGzVvnTpKGKVw" });
                    }
                }
         
                // Creates Bitmap from InputStream and returns it
                private Bitmap downloadImage(String url) {
                    Bitmap bitmap = null;
                    InputStream stream = null;
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inJustDecodeBounds = true;
                    bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                    int imageHeight = bmOptions.outHeight;
                    int imageWidth = bmOptions.outWidth;

                    bmOptions.inJustDecodeBounds = false;
                    DisplayMetrics metrics = new DisplayMetrics();
                    MainActivity.a.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int screenWidth = metrics.widthPixels;
                    int screenHeight =metrics.heightPixels;
                    bmOptions.inSampleSize = calculateInSampleSize(
                            bmOptions, screenWidth,screenHeight);
         
                    try {
                        stream = getHttpConnection(url);
                        bitmap = BitmapFactory.
                                decodeStream(stream, null, bmOptions);
                        stream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    return bitmap;
                }
         
                public  int calculateInSampleSize(
                        BitmapFactory.Options options, int reqWidth, int reqHeight) {
                // Raw height and width of image
                final int height = options.outHeight;
                final int width = options.outWidth;
                int inSampleSize = 1;

                if (height > reqHeight || width > reqWidth) {

                    final int halfHeight = height / 2;
                    final int halfWidth = width / 2;

                    // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                    // height and width larger than the requested height and width.
                    while ((halfHeight / inSampleSize) > reqHeight
                            && (halfWidth / inSampleSize) > reqWidth) {
                        inSampleSize *= 2;
                    }
                }

                return inSampleSize;
            }
                // Makes HttpURLConnection and returns InputStream
                private InputStream getHttpConnection(String urlString)
                        throws IOException {
                    InputStream stream = null;
                    URL url = new URL(urlString);
                    URLConnection connection = url.openConnection();
         
                    try {
                        HttpURLConnection httpConnection = (HttpURLConnection) connection;
                        httpConnection.setRequestMethod("GET");
                        httpConnection.connect();
         
                        if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            stream = httpConnection.getInputStream();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return stream;
                }

                @Override
                protected Bitmap doInBackground(String... params) {
                    // TODO Auto-generated method stub
                    Bitmap map = null;
                    for (String url : params) {
                        map = downloadImage(url);
                    }
                    return map;
                    
                }
            }
            public static Bitmap loadFromFile(String filename) {
                try {
                    File f = new File(filename);
                    if (!f.exists()) { return null; }
                    Bitmap tmp = BitmapFactory.decodeFile(filename);
                    return tmp;
                } catch (Exception e) {
                    return null;
                }
            }
        
}