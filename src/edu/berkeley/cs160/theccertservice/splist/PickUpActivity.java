package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.berkeley.cs160.theccertservice.splist.ListActivity.ChooseListListener;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class PickUpActivity extends Activity {

	ArrayList<myTime> pickupTimes = new ArrayList<myTime>();
	boolean hackyFlag = false;
	Spinner currentList;
	ArrayAdapter<String> arrayAdapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pickup);
		
		updateTimesList();
		
		currentList = (Spinner) findViewById(R.id.spinner1);
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListActivity.getLists());
		currentList.setAdapter(arrayAdapter);
		currentList.setOnItemSelectedListener(new ChooseListListener());

	}
	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new PickUpTimeDialog();
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	public void showPickUpDialog(View v) {
	    DialogFragment newFragment = new PickUpDialog();
	    newFragment.show(getFragmentManager(), "PickUpMsg");
	}
	
    public void onFinishPickUpDialog(String inputText) {
        Toast.makeText(this, "Message Sent - " + inputText, Toast.LENGTH_LONG).show();
    }

	public void onFinishAddPickUpTime(int hourOfDay, int minute) {
		myTime time = new myTime(hourOfDay, minute);
		if (hackyFlag) {
			hackyFlag = false;
		} else {
			pickupTimes.add(time);
			hackyFlag = true;
		}
		updateTimesList();
	}

	private void updateTimesList() {
		final ListView listview = (ListView) findViewById(R.id.pickupTimeListView);
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
		        android.R.layout.simple_list_item_1, pickupTimes);
		
		listview.setAdapter(adapter);

	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	      @Override
	      public void onItemClick(AdapterView<?> parent, final View view,
	          int position, long id) {
	        final myTime item = (myTime) parent.getItemAtPosition(position);
	        view.animate().setDuration(2000).alpha(0)
	            .withEndAction(new Runnable() {
	              @Override
	              public void run() {
	                pickupTimes.remove(item);
	                adapter.notifyDataSetChanged();
	                view.setAlpha(1);
	              }         
	            });
	      }

	    });
		
	}
	
	private class StableArrayAdapter extends ArrayAdapter<myTime> {

	    HashMap<myTime, Integer> mIdMap = new HashMap<myTime, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId, 
	    		List<myTime> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      myTime item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }
	
	private class myTime {
		
		int _hour, _minute;
		
		public myTime(int hour, int minute) {
			_hour = hour;
			_minute = minute;
		}
		@Override
		public String toString() {
			String hour = String.valueOf(_hour);
			String minute = String.valueOf(_minute);
			String period = "a.m.";
			if (_hour == 0) {
				hour = "12"; 
			} else if (_hour > 11) {
				period = "p.m.";
				if (_hour > 12) {
					hour = String.valueOf(_hour - 12);
				}
			}
			
			if (minute.length() == 1) {
				minute = "0" + minute;
			}
			
			return hour + ":" + minute + " " + period;
		}
		
	}

	public class ChooseListListener extends Activity implements OnItemSelectedListener {
		
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			String chosenList = ((Spinner) parent).getSelectedItem().toString();
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	}
	
}
