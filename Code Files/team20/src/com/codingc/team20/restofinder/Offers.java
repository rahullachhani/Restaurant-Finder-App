package com.codingc.team20.restofinder;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Offers extends Fragment {

	 DatabaseHelper db;
	 String[][] list;
	 ArrayList<TextView> t;
	 ArrayList<ImageButton> ib;
	 
	public Offers() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.offers, container,
				false);

				t=new ArrayList<TextView>();
				t.add((TextView)view.findViewById(R.id.t1));
				t.add((TextView)view.findViewById(R.id.t2));
				t.add((TextView)view.findViewById(R.id.t3));
				t.add((TextView)view.findViewById(R.id.t4));
				t.add((TextView)view.findViewById(R.id.t5));
				t.add((TextView)view.findViewById(R.id.t6));
				t.add((TextView)view.findViewById(R.id.t7));
				t.add((TextView)view.findViewById(R.id.t8));
				t.add((TextView)view.findViewById(R.id.t9));
		

ib=new ArrayList<ImageButton>();
				ib.add((ImageButton)view.findViewById(R.id.ib1));
				ib.add((ImageButton)view.findViewById(R.id.ib2));
				ib.add((ImageButton)view.findViewById(R.id.ib3));
				ib.add((ImageButton)view.findViewById(R.id.ib4));
				ib.add((ImageButton)view.findViewById(R.id.ib5));
				ib.add((ImageButton)view.findViewById(R.id.ib6));
				ib.add((ImageButton)view.findViewById(R.id.ib7));
				ib.add((ImageButton)view.findViewById(R.id.ib8));
				ib.add((ImageButton)view.findViewById(R.id.ib9));
							
		
			 db = new DatabaseHelper(MainActivity.a);
			 list=db.getList();
			 
			 
			 Random r=new Random();
			 int i=r.nextInt(9);
			 int k=0,j=0;
			 
			 if(list!=null)
			 for(j=0;j<(list.length);j++){
				 t.get(j%9).setText(list[k][0]);
				 t.get(j%9).setTextColor(Color.WHITE);
					
				 if(k==0)
				 ib.get(j%9).setBackgroundResource(R.drawable.offer1);
				 else if(k==1)
					 ib.get(j%9).setBackgroundResource(R.drawable.offer3);
				 else if(k==2)
					 ib.get(j%9).setBackgroundResource(R.drawable.offer4);
				 else if(k==3)
					 ib.get(j%9).setBackgroundResource(R.drawable.offer2);
				 else if(k==4)
					 ib.get(j%9).setBackgroundResource(R.drawable.offer5);
				 else if(k==5)
					 ib.get(j%9).setBackgroundResource(R.drawable.offer6);
				 else if(k==6)
					 ib.get(j%9).setBackgroundResource(R.drawable.offer7);
				 else if(k==7)
					 ib.get(j%9).setBackgroundResource(R.drawable.offer8);
				 else if(k==8)
					 ib.get(j%9).setBackgroundResource(R.drawable.offer_9);
				 else if(k==9)
					 ib.get(j%9).setBackgroundResource(R.drawable.offer1);
				 
			 final int f=k;
			 k++;
			ib.get(j%9).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				
					String placeid=list[f][1];
					 Intent i=new Intent(MainActivity.a,Restaurant.class);
		        	 i.putExtra("placeid", placeid);
		        	 startActivity(i);
		        		}
			});
			 }
			 for(;j<9;j++)
             {
                 View myView = ib.get(j);
                 View t2=t.get(j);
                 ViewGroup parent = (ViewGroup) myView.getParent();
                // parent.removeView(myView);
                // parent.removeView(t2);
             parent.setVisibility(LinearLayout.INVISIBLE);
             
             }
				return view;
	}

}
