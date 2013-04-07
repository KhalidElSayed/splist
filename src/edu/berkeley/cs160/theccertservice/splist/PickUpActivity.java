package edu.berkeley.cs160.theccertservice.splist;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;

public class PickUpActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pickup);

	}
	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new PickUpTimeDialog();
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
//	public void showPickUpDialog(View v) {
//	    DialogFragment newFragment = new PickUpDialog();
//	    newFragment.show(getFragmentManager(), "PickUpMsg");
//	}
}
