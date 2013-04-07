package edu.berkeley.cs160.theccertservice.splist;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class PickUpDialog extends DialogFragment {
	private EditText mEditText;

	public PickUpDialog() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pickup_dialog, container);
		mEditText = (EditText) view.findViewById(R.id.editTextPickUpMsg);
		Button done = (Button) view.findViewById(R.id.buttonPickUpAlert);
		done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	PickUpActivity parentAct = (PickUpActivity) getActivity();
                parentAct.onFinishPickUpDialog(mEditText.getText().toString());                
                done();
            }
        });
		getDialog().setTitle("Pick-Up Message");

		return view;
	}
	
	public void done() {
		this.dismiss();
	}
}
