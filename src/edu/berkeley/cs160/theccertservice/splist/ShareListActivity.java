package edu.berkeley.cs160.theccertservice.splist;

import java.util.concurrent.atomic.AtomicBoolean;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/*
public class ShareListActivity extends Activity implements View.OnClickListener {
	private EditText msg;
	private Button shareButton;
	private String msgString;
	private Dialog currentDialog;
	private AtomicBoolean dialogIsVisible = new AtomicBoolean();

	/** Called when the activity is first created. */
	// @Override
/*
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentDialog = new Dialog(this);
		setContentView(R.layout.sharelist);
		msg = (EditText) findViewById(R.id.msg);
		shareButton = (Button) findViewById(R.id.share);
		
		shareButton.setOnClickListener(this);
		msgString = "";
	}
	
	public String getMessage() {
		return msgString;
	}
	
	
	public void onClick(View view) {
	    switch (view.getId()) {
	    case R.id.share:
			msgString = msg.getText().toString();
			Context context = getApplicationContext();
			CharSequence text = "Your message has been sent!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			break;
	    }

	}
	
*/


/*
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
			dialogIsVisible.set(false);
			currentDialog.dismiss();
			currentDialog = null;
			
			//share with friends
			
			//go to list screen
			Intent myIntent = new Intent(v.getContext(), ListActivity.class);
            startActivity(myIntent);

		}
	};
	
	private OnClickListener cancelButtListener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			dialogIsVisible.set(false);
			currentDialog.dismiss();
			currentDialog = null;
			Intent myIntent = new Intent(v.getContext(), ShareListActivity.class);
            startActivity(myIntent);
		}
	};
*/
//}