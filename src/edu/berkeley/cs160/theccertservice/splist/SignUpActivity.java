package edu.berkeley.cs160.theccertservice.splist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends Activity{

	EditText usernameSignup;
	EditText password1;
	EditText password2;
	Button signUpButton;
	Button cancelButton;
	TextView showInfo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		
		usernameSignup = (EditText)findViewById(R.id.usernameSignUp);
		password1 = (EditText)findViewById(R.id.password1);
		password2 = (EditText)findViewById(R.id.password2);
		signUpButton = (Button)findViewById(R.id.signUpButton);
		cancelButton = (Button)findViewById(R.id.cancelButton);
		showInfo = (TextView)findViewById(R.id.textView1);
		
		signUpButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(usernameSignup.getText().toString().equals("")){
					showInfo.setText("username can't be empty");
					showInfo.setTextColor(Color.RED);
				}
				else if(password1.getText().toString().equals(password2.getText().toString())){
					ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
					dialog.setMessage("Getting your data... Please wait...");
					dialog.show();
					MainActivity.user=usernameSignup.getText().toString();
					MainActivity.pass=password1.getText().toString();
					
					Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
					
					startActivity(intent);
				}
				else{
					showInfo.setText("two passwords are different!");
					showInfo.setTextColor(Color.RED);
					
				}
				
			}
			
		});
		
		cancelButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
				startActivity(intent);
			}
			
		});
		
		
	}
}
