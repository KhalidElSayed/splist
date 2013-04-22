package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

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
	//String[] items={"Potatoes", "Toilet Paper", "Coca Cola"}; 
	//String[] names={"Eric", "Joyce", "Brian"}; 
	ArrayList<Item> sharedItems = new ArrayList<Item>();
	String[] feed;
	
	Button logout;
	SharedPreferences settings;
	//
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
		//feed = new String[items.length];
		//for(int i =0; i<items.length; i++){
			//feed[i]= items[i]+" ("+names[i]+")";
		//}
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
		
		feed = new String[sharedItems.size()];
		for(int i =0; i<sharedItems.size(); i++){
			feed[i]= sharedItems.get(i).getName() +" ("+sharedItems.get(i)._list.getOwner()+")";
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
	    DialogFragment newFragment = new CreateFeedDialog(position);
	    newFragment.show(getFragmentManager(), "createFeedDialog");
	}
	
	public class CreateFeedDialog extends DialogFragment {
		//private EditText feedMessage;
		//private TextView feedName;
		//private TextView feedItem;
		private TextView feedName;
		private TextView feedItem;
		private TextView feedPrice;
		private TextView numPeopleSharing;
		Button feedSend;
		Button feedCancel;
		
		int position;
		public CreateFeedDialog(int position) {
			// Empty constructor required for DialogFragment
			this.position = position;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.create_feed, container);
			//feedMessage = (EditText) view.findViewById(R.id.feedMessage);
			//feedMessage.setBackgroundColor(Color.WHITE);
			
			//feedName = (TextView) view.findViewById(R.id.feedName);
			//feedName.setText(names[position]);
			//feedName.setTextColor(Color.RED);
			
			//feedItem = (TextView) view.findViewById(R.id.feedItem);
			//feedItem.setText(items[position]);
			//feedItem.setTextColor(Color.RED);
			
			feedName = (TextView) view.findViewById(R.id.feedName);
			feedName.setText(sharedItems.get(position)._list.getOwner());
			feedName.setTextColor(Color.RED);
			
			feedItem = (TextView) view.findViewById(R.id.feedItem);
			feedItem.setText(sharedItems.get(position).getName());
			feedItem.setTextColor(Color.RED);
			
			feedPrice = (TextView) view.findViewById(R.id.feedPrice);
			Double price = sharedItems.get(position).getPrice();
			feedPrice.setText("$"+String.valueOf(price));
			feedPrice.setTextColor(Color.RED);
			
			numPeopleSharing = (TextView) view.findViewById(R.id.numPeopleSharing);
			Integer number = sharedItems.get(position)._numPeopleSharing;
			numPeopleSharing.setText(String.valueOf(number));
			numPeopleSharing.setTextColor(Color.RED);
			
			feedSend = (Button) view.findViewById(R.id.feedSend);
			feedCancel = (Button) view.findViewById(R.id.feedCancel);
			
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