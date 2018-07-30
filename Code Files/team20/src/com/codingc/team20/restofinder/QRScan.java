package com.codingc.team20.restofinder;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
public class QRScan extends Fragment {
private View view;
public QRScan(){}
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    try {
        view =  inflater.inflate(R.layout.qr, container, false);
  	      } catch (InflateException e) {}
        /* map is already there, just return view as it is */
 Button b=(Button)view.findViewById(R.id.bttt1);
 b.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	    Toast.makeText(MainActivity.a, "click", Toast.LENGTH_SHORT).show();
	    Log.v("mohit","hi");
		Intent intent = new Intent(MainActivity.a,CaptureActivity.class);
		startActivity(intent); 
	}
});
  	      
  	      
return view;	
}

}
