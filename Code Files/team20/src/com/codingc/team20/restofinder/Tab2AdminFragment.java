package com.codingc.team20.restofinder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.jwetherell.quick_response_code.data.Contents;
import com.jwetherell.quick_response_code.qrcode.QRCodeEncoder;
public class Tab2AdminFragment extends Fragment {
private View rootview;

int width;
int height; 
ImageView view ;
//TextView contents;
String pathqr;

private SharedPreferences setting;

private Editor editor;
private String tempPath;
private String tagdapath;






public Tab2AdminFragment(){}


@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  
    setHasOptionsMenu(true);
}

@Override
public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
	super.onCreateOptionsMenu(menu,inflater);
  inflater = adminMainActivity.a.getMenuInflater();
  inflater.inflate(R.menu.main_share, menu);

} 
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId())
    {
    
    case R.id.action_share:
      /*  Intent intent = new Intent(Intent.ACTION_SEND);
    	intent.setType("text/plain");
    	intent.putExtra(Intent.EXTRA_TEXT, "I would like to recommend Restaurant "+info.get(4)+" . Must visit :)");
    	startActivity(Intent.createChooser(intent, "Share with"));	
        */
    	onClickShare();
    return super.onOptionsItemSelected(item);
    default:
   
    	return super.onOptionsItemSelected(item);
    }
    
    
    }



@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    try {
        rootview =  inflater.inflate(R.layout.encoder, container, false);
  	      } catch (InflateException e) {}
     setting=adminMainActivity.a.getSharedPreferences("menu", 0);
    editor=setting.edit();
    WindowManager manager = (WindowManager) adminMainActivity.a.getSystemService( android.content.Context.WINDOW_SERVICE);
    Display display = manager.getDefaultDisplay();
     width= display.getWidth();
    height = display.getHeight();
  
    
    
    
    
    /* map is already there, just return view as it is */
/* Button b=(Button)view.findViewById(R.id.bttt1);
 //b.setOnClickListener(new OnClickListener() {
	
	//@Override
//	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	    Toast.makeText(MainActivity.a, "click", Toast.LENGTH_SHORT).show();
	    Log.v("mohit","hi");
		Intent intent = new Intent(MainActivity.a,CaptureActivity.class);
		startActivity(intent); 
		
	//}
//});
  	      
  	      
  	      
  	      
  */	      

    final EditText et = (EditText) rootview.findViewById(R.id.et);
    Button  b = (Button) rootview.findViewById(R.id.b1);
     view = (ImageView) rootview.findViewById(R.id.image_view);
    // img= (ImageView) rootview.findViewById(R.id.image_share);

     
    // contents = (TextView) rootview.findViewById(R.id.contents_text_view);
         


    b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		display(et.getText().toString());
		}
	});   
    
    
    return rootview;	
}

public void onClickShare() {
	// TODO Auto-generated method stub
	//View content = rootview.findViewById(R.id.image_share);

if(view!=null){	
Bitmap    bm  = ( ( BitmapDrawable) view.getDrawable()).getBitmap();
//		Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(R.drawable.abc_ab_bottom_solid_dark_holo)).getBitmap();

	  String path=saveToInternalSorage(bm);
	    pathqr=path;
	  File f=new File(pathqr, "profileQR.jpg");
  try {
	Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
/* 
		File f2 = new File(android.os.Environment
				.getExternalStorageDirectory(), "qr.jpg");
	
		File f = new File(Environment.getExternalStorageDirectory()
				.toString());
		for (File temp : f.listFiles()) {
			if (temp.getName().equals("qr.jpg")) {
				f = temp;
				break;
			}
		}
		BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
		tempPath=f.getAbsolutePath();
		tagdapath=tempPath;
		editor.putString("qrpath", tempPath);
		editor.commit();
   			 
		 

				String path = android.os.Environment
						.getExternalStorageDirectory()
						+ File.separator
						+ "Phoenix" + File.separator + "default";
				//f.delete();
				OutputStream fOut = null;
				File file = new File(path, String.valueOf(System
						.currentTimeMillis()) + ".jpg");
				try {
					fOut = new FileOutputStream(file);
					bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
					fOut.flush();
					fOut.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}			 

	
				  String currentPath=setting.getString("MENU", null);
					
					File newFile= new File(tagdapath);
					try {
						newFile.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try{
					InputStream ini = new FileInputStream(newFile);
					File fl=new File(currentPath,"qr.jpg");
					fl.createNewFile();
					
	        		    OutputStream outi = new FileOutputStream(currentPath+"/qr.jpg");
	        		 
	        		    // Transfer bytes from in to out
	        		    byte[] buf = new byte[1024];
	        		    int len;
	        		    while ((len = ini.read(buf)) > 0) {
	        		        outi.write(buf, 0, len);
	        		    }
	        		    ini.close();
	        		    outi.close();
	        		 }
	        		 
	        		 catch(Exception e)
	        		 {
	        		 }
					
			
	
	
	
	
	
	
	
	
	
	/*content.setDrawingCacheEnabled(true);
        Bitmap bitmap = content.getDrawingCache();
        File root = Environment.getExternalStorageDirectory();
        File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/imageqrcode.jpg");
        try 
        {
            root.createNewFile();
            FileOutputStream ostream = new FileOutputStream(root);
            bitmap.compress(CompressFormat.JPEG, 100, ostream);
            ostream.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
*/
if(pathqr!=null){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
     //   Uri phototUri = Uri.parse((setting.getString("qrpath", "menupath1")));
       
        Uri phototUri = Uri.parse(pathqr);
        
        shareIntent.setData(phototUri);
        shareIntent.setType("image/png");
        shareIntent.putExtra(Intent.EXTRA_STREAM, phototUri);
        getActivity().startActivity(Intent.createChooser(shareIntent, "Use this for sharing"));
}
}
}

private String saveToInternalSorage(Bitmap bitmapImage){
    ContextWrapper cw = new ContextWrapper(adminMainActivity.a);
     // path to /data/data/yourapp/app_data/imageDir
    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
    // Create imageDir
    File mypath=new File(directory,"profileQR.jpg");
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






public void display(String name)
{
	
	  int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 7 / 8;

try {
QRCodeEncoder qrCodeEncoder = null;

	qrCodeEncoder = new QRCodeEncoder(name, null, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), smallerDimension);
	

Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
view.setImageBitmap(bitmap);

//contents.setText(qrCodeEncoder.getDisplayContents());
//  setTitle(getString(R.string.app_name) + " - " + qrCodeEncoder.getTitle());
} catch (WriterException e) {
Log.e("12", "Could not encode barcode", e);
} catch (IllegalArgumentException e) {
Log.e("12", "Could not encode barcode", e);
}
	
}


}
