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
	static ArrayList<Item> sharedItems = new ArrayList<Item>();
	String[] feed1= {"milk", "apple", "banana", "spam", "instant noodle"};
	String[] feed2= {"paper towel", "cookies", "bagels", "choolate bars"};
	String[] feed3;
	static String[] parentList={"Items Friends have agreed to split", "Items you agreed to split", "Items Friends would like to split"};
	static String[][] childList;
	
	static String[] feedPrice1={"$9.99", "$5.89", "$6.59", "$3.45", "$2.99"};
	static String[] feedNumber1={"2", "4", "2", "3", "2"};
	
	static String[] feedName2={"Jack", "Carey", "Aline","Fox"};
	static String[] feedPrice2={"$3.99", "$2.39", "$1.59", "$8.45"};
	static String[] feedNumber2={"4", "2", "3", "3"};

	public FeedAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		initialFeeds();
	}

	private void initialFeeds() {
		Item potato = new Item("potato");
		potato._numPeopleSharing = 2;
		potato._price = 10.00;
		ShoppingList pol = new ShoppingList("Potato List", "Eric");
		potato._list = pol;
		sharedItems.add(potato);

		Item paper = new Item("Toilet Paper");
		paper._numPeopleSharing = 3;
		paper._price = 18.99;
		ShoppingList pal = new ShoppingList("Toilet Paper List", "Joyce");
		paper._list = pal;
		sharedItems.add(paper);

		Item coca = new Item("Coca Cola");
		coca._numPeopleSharing = 4;
		coca._price = 5.99;
		ShoppingList col = new ShoppingList("Coca Cola List", "Brian");
		coca._list = col;
		sharedItems.add(coca);

		Item chips = new Item("Chips");
		chips._numPeopleSharing = 2;
		chips._price = 2.99;
		ShoppingList chl = new ShoppingList("Chips List", "Yuliang");
		chips._list = chl;
		sharedItems.add(chips);
			
		feed3 = new String[sharedItems.size()];
		for(int i =0; i<sharedItems.size(); i++){
			feed3[i]= sharedItems.get(i).getName() +" ("+sharedItems.get(i)._list.getOwner()+")";
		}
		
		childList = new String[3][];
		childList[0] = new String[feed1.length];
		childList[1] = new String[feed2.length];
		childList[2] = new String[sharedItems.size()];
		for(int i=0 ; i<childList.length ; i++) {
			if(i==0){
				for (int j = 0; j < childList[i].length; j++) {
					childList[i][j]= feed1[j];
				}
			}
			if(i==1){
				for (int j = 0; j < childList[i].length; j++) {
					childList[i][j]= feed2[j];
				}
			}
			if(i==2){
				for (int j = 0; j < childList[i].length; j++) {
					childList[i][j]= feed3[j];
				}
			}
			
		}
		
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
		tv.setText(childList[groupPosition][childPosition]);
		tv.setPadding(80, 10, 10, 10);
		tv.setTextSize(22);
		return tv;
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
	public View getGroupView(int groupPosition, boolean arg1, View arg2, ViewGroup arg3) {
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
