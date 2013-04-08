package edu.berkeley.cs160.theccertservice.splist;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ShareMessageDialog extends DialogFragment {
		private EditText mEditText;
		private String msgString;

		public void shareMessage() {
			// Empty constructor required for DialogFragment
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.sharelist, container);
			mEditText = (EditText) view.findViewById(R.id.msg);
			Button done = (Button) view.findViewById(R.id.shareButt);
			done.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	ListActivity parentAct = (ListActivity) getActivity();
	                parentAct.onFinishShareMessageDialog(mEditText.getText().toString());                
	                done();
	            }
	        });
			getDialog().setTitle(R.string.typemsg);

			return view;
		}
		
		public void done() {
			this.dismiss();
		}
		
		public String getMessage() {
			msgString = mEditText.getText().toString();
			return msgString;
		}
	}