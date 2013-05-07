package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class FeedAdapter extends BaseExpandableListAdapter {

	private Context context;
	static String[] parentList = {"Shared Items Accepted by Friends", 
								  "Friends' Items I'm Splitting", 
								  "Items Friends Would Like to Split" };
	static ArrayList<Item> itemsFriendsWillSplit = new ArrayList<Item>();
	static ArrayList<Item> itemsIWillSplit = new ArrayList<Item>();
	static ArrayList<Item> itemsFriendsWantToSplit = new ArrayList<Item>();
	
	public FeedAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View arg3,
			ViewGroup arg4) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(context);
		
		switch (groupPosition) {
			case 0: tv.setText(itemsFriendsWillSplit.get(childPosition).toString());
					break;
			case 1: tv.setText(itemsIWillSplit.get(childPosition).toString());
					break;
			case 2: tv.setText(itemsFriendsWantToSplit.get(childPosition).toString());
			  		break;
		}
		
		tv.setPadding(80, 10, 10, 10);
		tv.setTextSize(16);
		return tv;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		switch (groupPosition) {
			case 0: return itemsFriendsWillSplit.size();
			case 1: return itemsIWillSplit.size();
			case 2: return itemsFriendsWantToSplit.size();		  		
		}
		return -1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return parentList.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean arg1, View arg2, ViewGroup arg3) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(context);
		tv.setText(parentList[groupPosition]);
		tv.setPadding(50, 10, 10, 10);
		tv.setTextSize(20);
		tv.setTextColor(Color.WHITE);
		return tv;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

}
