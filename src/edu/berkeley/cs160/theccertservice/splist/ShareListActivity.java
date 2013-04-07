package edu.berkeley.cs160.theccertservice.splist;

import java.util.concurrent.atomic.AtomicBoolean;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Dialog;

public class ShareListActivity extends Activity implements View.OnClickListener {
	private EditText msg;
	private Button shareButton;
	private String msgString;
	private Dialog currentDialog;
	private AtomicBoolean dialogIsVisible = new AtomicBoolean();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharelist);
		msg = (EditText) findViewById(R.id.msg);
		shareButton = (Button) findViewById(R.id.share);
		msgString = "";
		
	}
	
	public void onClick(View view) {
	    switch (view.getId()) {
	    case R.id.share:
			msgString = msg.getText().toString();
			showDialog();
	    }

	}
	
	public void showDialog() {
		currentDialog = new Dialog(this);
		currentDialog.setContentView(R.layout.share_msg_dialog);
		currentDialog.setCancelable(true);
		
		Button yesButt = (Button) currentDialog.findViewById(R.id.yesB);
		Button cancelButt = (Button) currentDialog.findViewById(R.id.cancelB);
		
		yesButt.setOnClickListener(yesButtListener);
		cancelButt.setOnClickListener(cancelButtListener);
		
		dialogIsVisible.set(true);
		currentDialog.show();
	}
	
	private OnClickListener yesButtListener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			//send message to friends
			
			dialogIsVisible.set(false);
			currentDialog.dismiss();
			currentDialog = null;
			
			//go to list screen
		}
	};
	
	private OnClickListener cancelButtListener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			dialogIsVisible.set(false);
			currentDialog.dismiss();
			currentDialog = null;
		}
	};

	
}
