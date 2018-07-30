package com.codingc.team20.restofinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Tab1AdminFragment extends Fragment {
	ImageView dp;
	Button selectPic;
	String tagdapath=null;
	
//
	private static final int REQUEST_CAMERA = 0;
	private static final int SELECT_FILE = 1;
	ImageView ivImage;
	Button btnSelectPhoto1;
	public static String tempPath;
	SharedPreferences share;
	SharedPreferences.Editor edit;
	private ImageView iv;
	String onClick;
	protected String dirC;
	private View rootView;
	private File f1;
	private FileOutputStream outi;
	private File thinput;
	Tab1AdminFragment(){
		
	}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
         rootView= inflater.inflate(R.layout.upload , container, false);
   onClick=null;
     	String dirG,picG,dirC,picC;
		share=adminMainActivity.a.getSharedPreferences("menu",0);
		edit=share.edit();
		adminMainActivity.a.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
			
			
			
			
		// Buttons and checkbox working
		dp=(ImageView)rootView.findViewById(R.id.pic);
		
		iv=(ImageView)rootView.findViewById(R.id.picdp);
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			/*	Bitmap bm=null;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(share.getString("menupath", null),
							btmapOptions);
				iv.setImageBitmap(bm);
		*/
				onClick="menu";
				selectImage();
				
			}

			
		});

	
		// Selecting Pic
		
		
		
		
		
		dp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				onClick="offer";
				selectImage();
			
			
		
									
			}		
		});

         
         
         
         
         
    return rootView;
    }

    void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		
		AlertDialog.Builder builder = new AlertDialog.Builder(adminMainActivity.a);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				
				if (items[item].equals("Take Photo")) {
					  ContextWrapper cw = new ContextWrapper(adminMainActivity.a);
				      
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
				        // Create imageDir
				  //      File mypath=new File(directory,"menu.jpg");
				    
					File f ;
					
					if(onClick.equals("menu")){
					f= new File(android.os.Environment
							.getExternalStorageDirectory(), "menu2.jpg");
					}
					else{
						f= new File(android.os.Environment
								.getExternalStorageDirectory(), "menu.jpg");
							
						
					}
					//   dirC=directory.getAbsolutePath();
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
					startActivityForResult(intent, REQUEST_CAMERA);
					
				}
				
				
				else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE);
				} 
				
				else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
				
				
			}
		});
		builder.show();
	}
	
	
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			
			
			
			
			if (resultCode == android.app.Activity.RESULT_OK) {
				
				
				
				
				
				if (requestCode == REQUEST_CAMERA) {
		//			File f = new File(share.getString("MENU","null" ));
		
			//		File f = new File(dirC);
					File f = new File(Environment.getExternalStorageDirectory()
							.toString());
					if(onClick.equals("menu")){
					for (File temp : f.listFiles()) {
						if (temp.getName().equals("menu2.jpg")) {
							f = temp;
							break;
						}
					}
					}
					else{
						for (File temp : f.listFiles()) {
							if (temp.getName().equals("menu.jpg")) {
								f = temp;
								break;
							}
						}
						
					}
					try {
						Bitmap bm=null;
						BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
						tempPath=f.getAbsolutePath();
						tagdapath=tempPath;
						if(onClick.equals("menu")){
						edit.putString("menupath2", tempPath);
						edit.commit();
						}
						else{
							edit.putString("menupath", tempPath);
							edit.commit();
								
							
						}
						bm = BitmapFactory.decodeFile(tempPath,
									btmapOptions);

						 int width = bm.getWidth();

		                   int height = bm.getHeight();

		                   float scaleWidth = ((float) 530) / width;

		                   float scaleHeight = ((float) 600) / height;
		                   Matrix matrix = new Matrix();
		                   matrix.postScale(scaleWidth, scaleHeight);
		                   Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
		                           matrix, false);
		 
		           			 
						 
						 if(onClick.equals("menu"))
							 iv.setImageBitmap(resizedBitmap);
						 else 
							dp.setImageBitmap(resizedBitmap);
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
							
		                	   
		                	   
		                   }
							
						//	}
					 
				//	}
					 
					 catch (Exception e) {
						e.printStackTrace();
					}
				} 
				
				else if (requestCode == SELECT_FILE) {
					Uri selectedImageUri = data.getData();

						tempPath = getPath(selectedImageUri,adminMainActivity.a);
						
						tagdapath=tempPath;
						
						if(onClick.equals("menu")){
						edit.putString("menupath2", tempPath);
						edit.commit();
						}
						else{
							edit.putString("menupath", tempPath);
							edit.commit();
								
							
						}
						Bitmap bm;
						BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
						bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
					 
						
							 int width = bm.getWidth();

			                   int height = bm.getHeight();

			                   float scaleWidth = ((float) 130) / width;

			                   float scaleHeight = ((float) 130) / height;
			                   Matrix matrix = new Matrix();
			                   matrix.postScale(scaleWidth, scaleHeight);
			                   Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
			                           matrix, false);
			                   
								 if(onClick.equals("menu"))
									 iv.setImageBitmap(resizedBitmap);
								 else 
									dp.setImageBitmap(resizedBitmap);
								    }
					
				//  String currentPath=Environment.getExternalStorageDirectory().toString()+"/TasteBudsApp";
					
				  String currentPath=share.getString("MENU", null);
					
					File newFile= new File(tagdapath);
					try {
						newFile.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try{
				//		Toast.makeText(adminMainActivity.a, tagdapath.toString(), Toast.LENGTH_LONG).show();
					InputStream ini = new FileInputStream(newFile);
		
					 if(onClick.equals("menu"))
					 {
					f1=new File(currentPath,"menu2.jpg");
					 }
					 else{
						 f1=new File(currentPath,"menu.jpg");
					 }
						f1.createNewFile();
						 
						
						 if(onClick.equals("menu"))
						 {
							outi = new FileOutputStream(currentPath+"/menu2.jpg");
			        		 	 }
						 else{
							outi = new FileOutputStream(currentPath+"/menu.jpg");
			        		 }
					
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
	        		//	 Toast.makeText(adminMainActivity.a, e.toString(), Toast.LENGTH_LONG).show();
	        		 }
					
					 
					///// creating thumbnail...
					try{
						
						

						 if(onClick.equals("menu"))
						 {
							 thinput=new File(currentPath+"/menu2.jpg");
							 FileInputStream thfis = new FileInputStream(thinput);
					            Bitmap imageBitmap = BitmapFactory.decodeStream(thfis);
					            FileOutputStream thoutput = new FileOutputStream(currentPath+"menu_th2.jpg");
					            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 25, thoutput);
					            thoutput.close();
					           
										 	 }
						 else{
					 thinput=new File(currentPath+"/menu.jpg");
								
					 FileInputStream thfis = new FileInputStream(thinput);
			            Bitmap imageBitmap = BitmapFactory.decodeStream(thfis);
			            FileOutputStream thoutput = new FileOutputStream(currentPath+"menu_th.jpg");
			            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 25, thoutput);
			            thoutput.close();
			           
						 }
					
					 
					}
					
					catch(Exception e)
					{
						Log.e("Error Compressing",e.toString());
					}
					

				}
			
			
			
		}
		
		
		public String getPath(Uri uri, Activity activity) {
			String[] projection = { MediaColumns.DATA };
			Cursor cursor = activity
					.managedQuery(uri, projection, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		



}
