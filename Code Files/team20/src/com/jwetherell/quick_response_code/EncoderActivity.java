/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jwetherell.quick_response_code;

import com.codingc.team20.restofinder.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.jwetherell.quick_response_code.data.Contents;
import com.jwetherell.quick_response_code.qrcode.QRCodeEncoder;

/**
 * Example Encoder Activity.
 * 
 * @author Justin Wetherell (phishman3579@gmail.com)
 */
public final class EncoderActivity extends Activity {
 int width;
 int height; 
 ImageView view ;
 TextView contents;
    private static final String TAG = EncoderActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.encoder);

        // This assumes the view is full screen, which is a good assumption
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
         width= display.getWidth();
        height = display.getHeight();
      
            // qrCodeEncoder = new QRCodeEncoder("AT", null, Contents.Type.TEXT,
            // BarcodeFormat.CODABAR.toString(), smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("HI", null, Contents.Type.TEXT,
            // BarcodeFormat.CODE_39.toString(), smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("Hello", null,
            // Contents.Type.TEXT, BarcodeFormat.CODE_128.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("1234567891011", null,
            // Contents.Type.TEXT, BarcodeFormat.EAN_13.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("12345678", null,
            // Contents.Type.TEXT, BarcodeFormat.EAN_8.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("1234", null,
            // Contents.Type.TEXT, BarcodeFormat.ITF.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("2345", null,
            // Contents.Type.TEXT, BarcodeFormat.PDF_417.toString(),
            // smallerDimension);
            // qrCodeEncoder = new QRCodeEncoder("12345678910", null,
            // Contents.Type.TEXT, BarcodeFormat.UPC_A.toString(),
            // smallerDimension);
            final EditText et = (EditText) findViewById(R.id.et);
            Button  b = (Button) findViewById(R.id.b1);
             view = (ImageView) findViewById(R.id.image_view);
           contents = (TextView) findViewById(R.id.contents_text_view);
                  


            b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				display(et.getText().toString());
				}
			});    }
    
    
    public void display(String name)
    {
    	
    	  int smallerDimension = width < height ? width : height;
	        smallerDimension = smallerDimension * 7 / 8;

    try {
   QRCodeEncoder qrCodeEncoder = null;

		qrCodeEncoder = new QRCodeEncoder(name, null, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), smallerDimension);
    	

    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
    view.setImageBitmap(bitmap);

  contents.setText(qrCodeEncoder.getDisplayContents());
  //  setTitle(getString(R.string.app_name) + " - " + qrCodeEncoder.getTitle());
} catch (WriterException e) {
    Log.e(TAG, "Could not encode barcode", e);
} catch (IllegalArgumentException e) {
    Log.e(TAG, "Could not encode barcode", e);
}
    	
    }
    
}
