package edu.berkeley.cs160.theccertservice.splist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class FriendsActivity extends Activity {

	ExpandableListView exv;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		displayFeeds();
	}
	
	private void displayFeeds(){
		exv = (ExpandableListView)findViewById(R.id.expandableListView2);
		exv.setAdapter(new FriendAdapter(this));


	}
}