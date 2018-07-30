package com.codingc.team20.restofinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Contacts.GroupsColumns;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

public class filterActivity extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	public HashMap<String,TextView> lt;
	SharedPreferences settings;
	List<String> lis;
	SharedPreferences.Editor editor;
	Button reset;
	Button close;
	Button apply;
	boolean expand[];
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		expand=new boolean[5];
		expand[0]=true;
		expand[1]=true;
		expand[2]=true;
		expand[3]=true;
		expand[4]=true;
		settings=this.getSharedPreferences("Filter",0);
		editor=settings.edit();
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		
		reset=(Button)findViewById(R.id.reset);
		 reset.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
            	 getApplicationContext().getSharedPreferences("Filter",0).edit().clear().commit();
            	 listAdapter.notifyDataSetChanged();
             }
         });
		 close=(Button)findViewById(R.id.button2);
		 close.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
            	 getApplicationContext().getSharedPreferences("Filter",0).edit().clear().commit();
            	 listAdapter.notifyDataSetChanged();
            
            	 
             }
         });
		 apply=(Button)findViewById(R.id.apply);
		 apply.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 // Perform action on click
            	 if(isFilter())
            	 {
            	 Intent intent=new Intent();
            	    intent.putExtra("Received", true);
            	    setResult(RESULT_OK, intent);
            	    finish();
            	 }
            	 else
            	 {
            		
 	    	            	 Intent intent=new Intent();
 	    	         	    intent.putExtra("Received", false); 
 	    	         	    setResult(RESULT_OK, intent);
 	    	         	    finish();
 	    	           
            }
             }
         });
		 
		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
		//		Toast.makeText(getApplicationContext(),
			//			listDataHeader.get(groupPosition) + " Expanded",
				//		Toast.LENGTH_SHORT).show();
				
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
			//	Toast.makeText(getApplicationContext(),
				//		listDataHeader.get(groupPosition) + " Collapsed",
					//	Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				int j=groupPosition;
				editor.putString(listDataHeader.get(groupPosition),listDataChild.get(
						listDataHeader.get(groupPosition)).get(
						childPosition));
				editor.commit();
		//		Toast.makeText(
			//			getApplicationContext(),
				//		listDataHeader.get(groupPosition)
					//			+ " : "
						//		+ listDataChild.get(
							//			listDataHeader.get(groupPosition)).get(
								//		childPosition), Toast.LENGTH_SHORT)
						//.show();
				listAdapter.notifyDataSetChanged();
				lis=new ArrayList<String>();
				lis=listDataChild.get(listDataHeader.get(groupPosition));
				if(lt!=null && lis!=null)
				{
					for(int i=0;i<lis.size();i++)
					{
					TextView text=lt.get(lis.get(i));
					if(lis.get(i).equals(listDataChild.get(
										listDataHeader.get(groupPosition)).get(
										childPosition)))
					{
						
						text.setTypeface(Typeface.DEFAULT_BOLD);
						Log.v("Rahul",text.getText().toString());
						text.setTextColor(getResources().getColor(R.color.Red));
					}
						
					else
					{
						text.setTypeface(Typeface.DEFAULT);
						text.setTextColor(getResources().getColor(R.color.Black));
					}
					}
					
				}
				
				return true;
			}
		});
	}
	@Override
	public void onBackPressed() {
		 Intent intent=new Intent();
 	    intent.putExtra("Received", false);
 	    setResult(RESULT_OK, intent);
 	    finish();

	}
	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Distance");
		listDataHeader.add("Rating");
		listDataHeader.add("Cost");
		listDataHeader.add("Cuisine");
		listDataHeader.add("Category");

		// Adding child data
		List<String> distance = new ArrayList<String>();
		distance.add("less than 500 m");
		distance.add("less than 300 m");
		distance.add("less than 150 m");
		
		

		List<String> rating = new ArrayList<String>();
		rating.add("4.0 - 5.0");
		rating.add("3.0 - 4.0");
		rating.add("2.0 - 3.0");
		rating.add("1.0 - 2.0");
		rating.add("0.0 - 1.0");
		
		
		List<String> cost = new ArrayList<String>();
		cost.add("Very Expensive");
		cost.add("Expensive");
		cost.add("Moderate");
		cost.add("Inexpensive");
		cost.add("Free");
		
		
		List<String> cuisine = new ArrayList<String>();
		cuisine.add("Indian");
		cuisine.add("Thai");
		cuisine.add("Italic");
		cuisine.add("Chinese");
		
		
		
		List<String> category = new ArrayList<String>();
		category.add("Pizza");
		category.add("Cakes and Pastery");
		category.add("Cafe");
		category.add("Meals");
		category.add("Bar");
		

		listDataChild.put(listDataHeader.get(0), distance); // Header, Child data
		listDataChild.put(listDataHeader.get(1), rating);
		listDataChild.put(listDataHeader.get(2), cost);
		listDataChild.put(listDataHeader.get(3), cuisine);
		listDataChild.put(listDataHeader.get(4), category);
	}
	
	public boolean isFilter()
	{
		String se=settings.getString("Category",null);
		if((settings.getString("Distance",null)!=null)||(settings.getString("Rating",null)!=null)||(settings.getString("Cost",null)!=null)||(settings.getString("Cuisine",null)!=null)||(settings.getString("Category",null)!=null))
		{
			return true;
		}
		else
			return false;
	}
	public class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context _context;
		private List<String> _listDataHeader; // header titles
		// child data in format of header title, child title
		private HashMap<String, List<String>> _listDataChild;
		

		public ExpandableListAdapter(Context context, List<String> listDataHeader,
				HashMap<String, List<String>> listChildData) {
			this._context = context;
			this._listDataHeader = listDataHeader;
			this._listDataChild = listChildData;
			lt=new HashMap<String,TextView>();
		}

		@Override
		public Object getChild(int groupPosition, int childPosititon) {
			return this._listDataChild.get(this._listDataHeader.get(groupPosition))
					.get(childPosititon);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			final String childText = (String) getChild(groupPosition, childPosition);
			String headerTitle = (String) getGroup(groupPosition);
			
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.list_item, null);
			
				

			TextView txtListChild = (TextView) convertView
					.findViewById(R.id.lblListItem);

			txtListChild.setText(childText);
			String chld=settings.getString(headerTitle,null);
			if(chld!=null)
			{
				if(chld.equals(childText))
				{
					txtListChild.setTypeface(Typeface.DEFAULT_BOLD);
					txtListChild.setTextColor(getResources().getColor(R.color.Red));
				}
			else
			{
				
					txtListChild.setTypeface(Typeface.DEFAULT);
					txtListChild.setTextColor(getResources().getColor(R.color.Black));
				
			}
			}
			
			lt.put(childText,txtListChild);
	          System.out.println(lt.get(childText));
	          
	          
	          
	          
	          
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return this._listDataChild.get(this._listDataHeader.get(groupPosition))
					.size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return this._listDataHeader.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return this._listDataHeader.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			String headerTitle = (String) getGroup(groupPosition);
			
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.list_group, null);
				if(expand[groupPosition])
				{
					expListView.expandGroup(groupPosition);
					expand[groupPosition]=false;
				}
				
				
			TextView lblListHeader = (TextView) convertView
					.findViewById(R.id.lblListHeader);
			lblListHeader.setTypeface(null, Typeface.BOLD);
			lblListHeader.setText(headerTitle);

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}
	public String getValue(String parent)
	{
		String str=settings.getString(parent,null);
		return str;
	}
	/*
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		this.getSharedPreferences("Filter",0).edit().clear().commit();
	}
*/
}
