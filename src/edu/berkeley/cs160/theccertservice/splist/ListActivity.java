package edu.berkeley.cs160.theccertservice.splist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ListActivity extends Activity implements View.OnClickListener {
    private ListView myList;
//    private MyAdapter myAdapter;
    private Button shareButton;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		shareButton = (Button) findViewById(R.id.calculate);
		
		shareButton.setOnClickListener(this);
		
		
//        myList = (ListView) findViewById(R.id.MyList);
//        myList.setItemsCanFocus(true);
//        myAdapter = new MyAdapter();
//        myList.setAdapter(myAdapter);

	}
	
	public void onClick(View view) {
	    switch (view.getId()) {
	    case R.id.calculate:
	    	Intent myIntent = new Intent(view.getContext(), ShareListActivity.class);
	        startActivity(myIntent);
			break;
	    }

	}
	

	
	
}
