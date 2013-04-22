package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.graphics.PorterDuff;

public class ListActivity extends Activity implements View.OnClickListener {
    private ListView myList;
    private static ArrayList<String> sharedLists = new ArrayList<String>();
    Spinner currentList;
    ArrayAdapter<String> arrayAdapter;
    private Button shareButton;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		Button button = (Button) findViewById(R.id.delete_list);
		button.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
		
		shareButton = (Button) findViewById(R.id.share_with_others);
		shareButton.setOnClickListener(this);

		currentList = (Spinner) findViewById(R.id.lists);
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sharedLists);
		currentList.setAdapter(arrayAdapter);
		currentList.setOnItemSelectedListener(new ChooseListListener());
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
		arrayAdapter.notifyDataSetChanged();
//		currentList.setAdapter(arrayAdapter);
	}
	
	public Dialog deleteDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this list?");
    	builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int which) {
    			String chosenList = currentList.getSelectedItem().toString();
    			sharedLists.remove(chosenList);
    			arrayAdapter.notifyDataSetChanged();
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

	public void onClick(View view) {
	    switch (view.getId()) {
	    case R.id.share_with_others:
	    	showShareMessageDialog(view);
			break;
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
	
	public static ArrayList<String> getLists(){
		return sharedLists;
	}
	
	public void showShareMessageDialog(View v) {
	    DialogFragment newFragment = new ShareMessageDialog();
	    newFragment.show(getFragmentManager(), "shareMessage");
	}
	
    public void onFinishShareMessageDialog(String inputText) {
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
    }
}
