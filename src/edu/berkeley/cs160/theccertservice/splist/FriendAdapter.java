package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

public class FriendAdapter extends BaseExpandableListAdapter {

	private Context context;
	static String[] parentList = { "Friend Requests", "Friends" };
	static String[][] childList = { { "Apollo", "Chantre", "Ghirshman", "Huni" },
			{ "Jack", "Carey", "Aline", "Fox" } };
	LayoutInflater inflater;

	public FriendAdapter(Context context) {
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
	public View getChildView(int groupPosition, int childPosition,
			boolean arg2, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(groupPosition ==0){
			// View v = null;
		     //if( convertView != null )
		    	// v = convertView;
		     //else
		    	// v = inflater.inflate(R.layout.friend_request, parent, false); 
		     //TextView tv = (TextView)v.findViewById( R.id.requestInfo );
		    // tv.setText("hello");
		     //return v;
			TextView tv = new TextView(context);
			tv.setText("You got a friend request from " + '"'+ childList[groupPosition][childPosition]+ '"');
			tv.setPadding(80, 10, 10, 10);
			tv.setTextSize(22);
			return tv;
		}else{
			TextView tv = new TextView(context);
			tv.setText(childList[groupPosition][childPosition]);
			tv.setPadding(80, 10, 10, 10);
			tv.setTextSize(22);
			return tv;
		}


	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childList[groupPosition].length;
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
	public View getGroupView(int groupPosition, boolean arg1, View arg2,
			ViewGroup arg3) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(context);
		tv.setText(parentList[groupPosition]);
		tv.setPadding(50, 10, 10, 10);
		tv.setTextSize(28);
		tv.setTextColor(Color.CYAN);
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
