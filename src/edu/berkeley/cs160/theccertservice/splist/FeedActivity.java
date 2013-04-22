package edu.berkeley.cs160.theccertservice.splist;

import edu.berkeley.cs160.theccertservice.splist.ListActivity.CreateListDialog;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedActivity extends Activity {

	////String[] items={"Potatoes (Eric)", "Toilet Paper (Joyce)", "Coca Cola (Brian)"}; 
	String[] items={"Potatoes", "Toilet Paper", "Coca Cola"}; 
	String[] names={"Eric", "Joyce", "Brian"}; 
	String[] feed;
	Button logout;
	SharedPreferences settings;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed);
		//
		logout = (Button)findViewById(R.id.logoutButton);
		logout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				settings = getSharedPreferences(MainActivity.SETTING_INFO, 0);  
			    SharedPreferences.Editor editor = settings.edit();
			    editor.putBoolean("hasLoggedIn", false);
			    editor.commit();
			    
				Intent intent = new Intent(FeedActivity.this, MainActivity.class);
				startActivity(intent);
			}
        	
        });
		
		displayFeeds();
		
	}
	
	private void displayFeeds(){
		feed = new String[items.length];
		for(int i =0; i<items.length; i++){
			feed[i]= items[i]+" ("+names[i]+")";
		}
		ListView feedList = (ListView) findViewById(R.id.feedList);
		ArrayAdapter <String> adapter = new ArrayAdapter <String> (
			this,
			android.R.layout.simple_list_item_1,
			android.R.id.text1,
			feed
		);
		
		feedList.setAdapter(adapter);
		
		feedList.setOnItemClickListener(new OnItemClickListener () {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				//Toast.makeText(v.getContext(), items[position], Toast.LENGTH_SHORT).show();
				showFeedDialog(position);
			}
			
		});
	}
	
	public void showFeedDialog(int position) {
	    DialogFragment newFragment = new CreateFeedDialog();
	    newFragment.show(getFragmentManager(), "createFeedDialog");
	}
	
	public class CreateFeedDialog extends DialogFragment {
		private EditText feedMessage;
		private TextView feedName;
		private TextView feedItem;
		int position;
		public CreateFeedDialog() {
			// Empty constructor required for DialogFragment
			this.position = position;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.create_feed, container);
			feedMessage = (EditText) view.findViewById(R.id.feedMessage);
			feedMessage.setBackgroundColor(Color.WHITE);
			
			feedName = (TextView) view.findViewById(R.id.feedName);
			feedName.setText(names[position]);
			feedName.setTextColor(Color.RED);
			
			feedItem = (TextView) view.findViewById(R.id.feedItem);
			feedItem.setText(items[position]);
			feedItem.setTextColor(Color.RED);
			
			Button feedSend = (Button) view.findViewById(R.id.feedSend);
			Button feedCancel = (Button) view.findViewById(R.id.feedCancel);
			
			feedSend.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	//ListActivity parentAct = (ListActivity) getActivity();
	                //parentAct.onFinishCreateList(mEditText.getText().toString());   
	            	Toast.makeText(v.getContext(), "Your Message has been sent", Toast.LENGTH_SHORT).show();
	                done();
	            }
	        });
			
			feedCancel.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					done();
				}
			});
			//getDialog().setTitle("Create List");

			return view;
		}
		
		public void done() {
			this.dismiss();
		}
	}
}