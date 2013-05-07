package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.TextView;

public class EditItem extends DialogFragment {
	private boolean isOldItem;
	
	private EditText mEditText;
	private String msgString;
	private EditText costEdit;
	private Double cost;
	private CheckBox checkView;
	private boolean isChecked;
	private Item item;
	private ArrayAdapter itemsAdapter;
	private ShoppingList currentItems;
	private Context context;
	
	public EditItem(Item item, ArrayAdapter itemsAdapter, ShoppingList list, Context context){
		this.msgString = item.getName();
		this.cost = item.getPrice();
		this.isChecked = item.getShared();
		this.isOldItem = true;
		this.item = item;
		this.itemsAdapter = itemsAdapter;
		this.currentItems = list;
		this.context = context;
	}
	
	public void shareMessage() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.edit_item, container);
		mEditText = (EditText) view.findViewById(R.id.item);
		costEdit = (EditText) view.findViewById(R.id.item_cost);
		checkView = (CheckBox) view.findViewById(R.id.checkbox_share);
		Button add = (Button) view.findViewById(R.id.add_item);
		Button cancel = (Button) view.findViewById(R.id.cancel_item);
		Button delete = (Button) view.findViewById(R.id.delete_item);
		
		if(isOldItem){
			setValues();
		}
		
		add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	ListActivity parentAct = (ListActivity) getActivity();
            	msgString = mEditText.getText().toString();
            	String costInput = costEdit.getText().toString();
            	if (costInput == null || costInput.isEmpty()){
            		cost = 0.00;
            	}else{
            		cost = Double.parseDouble(costInput);
            	}
            	item.setName(msgString);
            	item.setPrice(cost);
            	item.setShared(checkView.isChecked());
            	itemsAdapter.notifyDataSetChanged();
                done();
            }
        });
		delete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				currentItems.deleteItem(item);
				itemsAdapter.notifyDataSetChanged();
				done();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				done();
			}
		});
		getDialog().setTitle("Item Info");

		return view;
	}
	
	public void done() {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		this.dismiss();
	}
	
	private void setValues(){
    	mEditText.setText(msgString, TextView.BufferType.EDITABLE);
    	checkView.setChecked(isChecked);
    	costEdit.setText(cost.toString(), TextView.BufferType.EDITABLE);
	}
}
