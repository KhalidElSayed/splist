package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ListActivity extends Activity implements SensorEventListener {
    private ListView myList;
    private static ArrayList<String> sharedLists = new ArrayList<String>();
    Spinner spinnerList;
    ShoppingList currentItems;
    ArrayList<Item> itemsArray;
    ArrayAdapter<String> listsAdapter;
    ArrayAdapter<Item> itemsAdapter;
    private Button shareButton;
    private long lastUpdate = System.currentTimeMillis();
    private SensorManager sensorManager;
    private float last_x, last_y, last_z;
    private final float SHAKE_THRESHOLD = 35;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		Button button = (Button) findViewById(R.id.delete_list);
		button.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
		
		spinnerList = (Spinner) findViewById(R.id.lists);
		sharedLists = ShoppingList.allListNames();
		listsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sharedLists);
		spinnerList.setAdapter(listsAdapter);
		spinnerList.setOnItemSelectedListener(new ChooseListListener());
		
		final class repeatTask extends TimerTask{

			@Override
			public void run() {
				HashMap<String, String> data = new HashMap<String, String>();
				data.put("auth_token", MainActivity.authToken);
				MainActivity.server.getSharedItems(data);
				MainActivity.server.getFriends(data);			
			}			
		}
		Timer t = new Timer(true);
		t.scheduleAtFixedRate(new repeatTask(), 0, 30000);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		updateItemsList();
	}
	
	public void showCreateListDialog(View v) {
	    DialogFragment newFragment = new CreateListDialog();
	    newFragment.show(getFragmentManager(), "createList");
	}
	public void showDeleteListDialog(View v){
		Dialog d = deleteDialog();
		d.show();
	}
	
	public void onFinishCreateList(String listName){
		sharedLists.add(listName);
		new ShoppingList(listName, "bob");
		listsAdapter.notifyDataSetChanged();
	}
	
	public Dialog deleteDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this list?");
    	builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			String chosenList = spinnerList.getSelectedItem().toString();
    			sharedLists.remove(chosenList);
    			//need to remove list from real list
    			listsAdapter.notifyDataSetChanged();
    		}
        });
    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
    	return builder.create();
	}
	
	public class CreateListDialog extends DialogFragment {
		private EditText mEditText;

		public CreateListDialog() {
			// Empty constructor required for DialogFragment
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.create_list, container);
			mEditText = (EditText) view.findViewById(R.id.list_name);
			Button create = (Button) view.findViewById(R.id.create_button);
			Button cancel = (Button) view.findViewById(R.id.cancel_button);
			create.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	ListActivity parentAct = (ListActivity) getActivity();
	                parentAct.onFinishCreateList(mEditText.getText().toString());                
	                done();
	            }
	        });
			cancel.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					done();
				}
			});
			getDialog().setTitle("Create List");

			return view;
		}
		
		public void done() {
			this.dismiss();
		}
	}
	
	public class ChooseListListener extends Activity implements OnItemSelectedListener {
		
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			String chosenList = ((Spinner) parent).getSelectedItem().toString();
			updateItemsList();
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	}
	
	public static ArrayList<String> getLists(){
		return sharedLists;
	}
	
	public void showShareMessageDialog(View view) {
	    DialogFragment newFragment = new ShareMessageDialog();
	    newFragment.show(getFragmentManager(), "shareMessage");
	}
	
    public void onFinishShareMessageDialog(String inputText) {
    	currentItems = ShoppingList.getShoppingList(spinnerList.getSelectedItem().toString());
    	for (Item item : currentItems._items) {
    		if (item._shared && !FeedAdapter.itemsFriendsWillSplit.contains(item))
    			FeedAdapter.itemsFriendsWillSplit.add(item);
    	}
    	
        Toast.makeText(this, "Message Sent!", Toast.LENGTH_SHORT).show();
    }
    
    
    public void addItemToList(View v){
    	if(currentItems == null){
    	    return;
    	}
    
    	EditText nameView = (EditText) findViewById(R.id.item);
    	CheckBox checkView = (CheckBox) findViewById(R.id.checkbox_share);
    	EditText costView = (EditText) findViewById(R.id.item_cost);
    	
    	String itemName = nameView.getText().toString();
    	Boolean isChecked = checkView.isChecked();
    	String costInput = costView.getText().toString();
    	Double itemCost;
    	if (costInput == null || costInput.isEmpty()){
    		itemCost = 0.0;
    	}else{
    		itemCost = Double.parseDouble(costInput);
    	}
    	
    	HashMap<String,String> data = new HashMap<String, String>();
    	data.put("auth_token", MainActivity.authToken);
    	data.put("owner", MainActivity.userId);
    	data.put("name", itemName);
    	data.put("description", "");
    	data.put("price", itemCost.toString());
    	data.put("quantity", "1");
    	data.put("shared", isChecked.toString());
    	data.put("list", currentItems._name);
    	MainActivity.server.addItem(data);
    	
    	Item newItem = new Item(itemName, isChecked, itemCost);
    	currentItems.addItem(newItem);
    	
    	
    	
    	//clear edit texts and checkbox
    	nameView.setText("", TextView.BufferType.EDITABLE);
    	checkView.setChecked(false);
    	costView.setText("", TextView.BufferType.EDITABLE);
    	
    	updateItemsList();
    }
    
	private void updateItemsList() {
		if(spinnerList.getSelectedItem() != null){
			currentItems = ShoppingList.getShoppingList(spinnerList.getSelectedItem().toString());
		}
		if(currentItems == null){
		    DialogFragment newFragment = new CreateListDialog();
		    newFragment.show(getFragmentManager(), "createList");
			return;
		}
		ListView listview = (ListView) findViewById(R.id.item_list);
		itemsArray = currentItems.getItems();
		itemsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsArray);
		listview.setAdapter(itemsAdapter);

	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	      @Override
	      public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
	    	  Item item = (Item) parent.getItemAtPosition(position);
	          showEditItemDialog(item, itemsAdapter);
	          view.setAlpha(1);
	      }
	    });
	    
	    listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
				        final Item item = (Item) parent.getItemAtPosition(position);
				        view.animate().setDuration(2000).alpha(0)
				            .withEndAction(new Runnable() {
				              @Override
				              public void run() {
				                currentItems.deleteItem(item);
				                itemsAdapter.notifyDataSetChanged();
				                view.setAlpha(1);
				              }         
				            });
						return false;
					}
		});
	}
	
	public void showEditItemDialog(Item item, ArrayAdapter adapter){
	    DialogFragment newFragment = new EditItem(item, adapter);
	    newFragment.show(getFragmentManager(), "editItem");
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		long t = System.currentTimeMillis();
		long diffTime = t -lastUpdate;
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && diffTime > 100) {
			lastUpdate = t;
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];

			float accelVal = Math.abs(x+y+z - last_x - last_y - last_z)
                    / diffTime * 10000;
			//Log.d("Sensor", "Accel was: " + String.valueOf(accelVal));
			if (accelVal >= SHAKE_THRESHOLD) {
				if (diffTime >= 200) {				
					Toast.makeText(this, "Speak to add item!", Toast.LENGTH_SHORT).show();
				}
			}		
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		for (Sensor s : sensorManager.getSensorList(Sensor.TYPE_ALL)) {
			if (s.getType() == Sensor.TYPE_ACCELEROMETER) {
				sensorManager.registerListener(this, 
						sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
						SensorManager.SENSOR_DELAY_NORMAL);
				return;
			}
		}
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		for (Sensor s : sensorManager.getSensorList(Sensor.TYPE_ALL)) {
			if (s.getType() == Sensor.TYPE_ACCELEROMETER) {
				sensorManager.unregisterListener(this);
				return;
			}
		}
	}
}
