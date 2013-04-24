package edu.berkeley.cs160.theccertservice.splist;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;  
import android.widget.TabHost;  
import android.widget.TabHost.TabSpec;  
  

public class TabHostActivity extends TabActivity {  
	
	TabHost mTabHost;
	
	//The selectable lists for the user someone put the global var here!
	
	
	
    /** Called when the activity is first created. */  
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.tab_host);  
        mTabHost = getTabHost();
        
        TabSpec listSpec = mTabHost.newTabSpec("List");
        listSpec.setIndicator("List", null);
        Intent listIntent = new Intent(this, ListActivity.class);
        listSpec.setContent(listIntent);
        
        TabSpec feedSpec = mTabHost.newTabSpec("Feed");
        feedSpec.setIndicator("Feed", null);
        Intent feedIntent = new Intent(this, FeedActivity.class);
        feedSpec.setContent(feedIntent);
        
        TabSpec friendsSpec = mTabHost.newTabSpec("Friends");
        friendsSpec.setIndicator("Friends", null);
        Intent friendsIntent = new Intent(this, FriendsActivity.class);
        friendsSpec.setContent(friendsIntent);
        
        TabSpec calcSpec = mTabHost.newTabSpec("Calc Bill");
        calcSpec.setIndicator("Calc Bill", null);
        Intent calcIntent = new Intent(this, CalcBillActivity.class);
        calcSpec.setContent(calcIntent);
        
        mTabHost.addTab(listSpec);
        mTabHost.addTab(feedSpec);
        mTabHost.addTab(friendsSpec);
        mTabHost.addTab(calcSpec);
        mTabHost.setCurrentTab(0);
    }  
}  

