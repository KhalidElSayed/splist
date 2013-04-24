package edu.berkeley.cs160.theccertservice.splist;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.berkeley.cs160.theccertservice.splist.PickUpActivity.ChooseListListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CalcBillActivity extends ListActivity {
	

	private TextView MoneyOwed;
	private Button calcButton;
	private Spinner currentList;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItems = new ArrayList<String>();
    static ArrayList<String> sharedLists = ListActivity.getLists();
    
    String chosenList = "";
    
    // NEED TO IMPLEMENT HOW TO SET THE CURRENT USER.
    private Person currentUser;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calcbill);
		
	    View header = getLayoutInflater().inflate(R.layout.calcbill_header, null);
	    View footer = getLayoutInflater().inflate(R.layout.calcbill_footer, null);
	    ListView listView = (ListView) findViewById(R.id.listView1);
	    listView.addHeaderView(header);
	    listView.addFooterView(footer);

	    
	    adapter=new ArrayAdapter<String>(this,
	    		android.R.layout.simple_list_item_1, listItems);
	    listView.setAdapter(adapter);
		
		
		MoneyOwed = (TextView) header.findViewById(R.id.money_owed);
		
		sharedLists = new ArrayList<String>();
		for (Item i : FeedAdapter.itemsIWillSplit) {
			sharedLists.add(i._list._name);
		}
		
		currentList = (Spinner) header.findViewById(R.id.spinner1);

		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sharedLists);
		currentList.setAdapter(arrayAdapter);
		//currentList.setOnItemSelectedListener(new ChooseListListener());

	}
	
	public Double calculateOwed(ShoppingList lst) {
		double owed = 0.0;
		
		for (Item i : lst.getItems()) {
			ArrayList<String> pplSharing = i.getPeopleSharing();
			if (pplSharing.contains(MainActivity.user)) {
				String itemName = i.getName();
				double itemPrice = i.getPrice();
				double numPplSharing = i.getNumPeopleSharing();
				double priceFrac = roundTwoDecimalPlaces(itemPrice / numPplSharing);
				String priceFracStr = Double.toString(priceFrac);
				owed = owed + priceFrac;
				String nameAndPrice = itemName + " costs " + priceFracStr;
				listItems.add(nameAndPrice);
				adapter.notifyDataSetChanged();
			}
		}
		return owed;
	}
	
	public double roundTwoDecimalPlaces(double d) {
        DecimalFormat twoPlaces = new DecimalFormat("##.##");
        return Double.valueOf(twoPlaces.format(d));
	}

	public void onClick(View view) {
	    switch (view.getId()) {
	    	case R.id.calculate:
	    		if (chosenList.isEmpty()) {
	    	        Toast.makeText(this, "Please select a list.", Toast.LENGTH_LONG).show();
	    		} else {
	    			// how curList is obtained possibly needs to be changed
		    		ShoppingList curList = ShoppingList.getShoppingList(chosenList); // list of shared items for that shopping list
					MoneyOwed.setText(String.valueOf(calculateOwed(curList)));
	    		}
	    		break;
	    }
	}
	
	public class ChooseListListener extends Activity implements OnItemSelectedListener {
		
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			chosenList = ((Spinner) parent).getSelectedItem().toString();

		}
		
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	}
}
