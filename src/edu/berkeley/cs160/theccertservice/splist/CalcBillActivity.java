package edu.berkeley.cs160.theccertservice.splist;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.berkeley.cs160.theccertservice.splist.PickUpActivity.ChooseListListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CalcBillActivity extends Activity {
	

	private TextView MoneyOwed;
	private Button calcButton;
	private Spinner currentList;
	private static ArrayList<String> sharedLists = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    String chosenList = "";
    
    // NEED TO IMPLEMENT HOW TO SET THE CURRENT USER.
    private Person currentUser;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calcbill);
		MoneyOwed = (TextView) findViewById(R.id.money_owed);
		
		currentList = (Spinner) findViewById(R.id.lists);
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sharedLists);
		currentList.setAdapter(arrayAdapter);
		currentList.setOnItemSelectedListener(new ChooseListListener());

	}
	
	public Double calculateOwed(ShoppingList lst) {
		double owed = 0.0;
		ArrayList<Item> items = lst.getItems();
		for(int i = 0; i < lst.getSize(); i++) {
			Item curItem = items.get(i);
			for (int j = 0; i < curItem.getNumPeopleSharing(); i++) {
				ArrayList<Person> pplSharing = curItem.getPeopleSharing();
				
				//how to account for current user
				if (pplSharing.get(j).equals(currentUser) ) {
					double itemPrice = curItem.getPrice();
					double numPplSharing = curItem.getNumPeopleSharing();
					double priceFrac = roundTwoDecimalPlaces(itemPrice / numPplSharing);
					owed = owed + priceFrac;
				}
			}

		}
		return owed;
	}
	
	public double roundTwoDecimalPlaces(double d) {
        DecimalFormat twoPlaces = new DecimalFormat("#.##");
        return Double.valueOf(twoPlaces.format(d));
	}
	
	public void onClick(View view) {
		double totalBill;
		double people;
	    switch (view.getId()) {
	    	case R.id.calculate:
	    		if (chosenList.isEmpty()) {
	    	        Toast.makeText(this, "Please select a list.", Toast.LENGTH_LONG).show();
	    		} else {
		    		ShoppingList curList = ShoppingList.getShoppingList(chosenList);
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
	
	public static ArrayList<String> getLists(){
		return sharedLists;
	}
	
}