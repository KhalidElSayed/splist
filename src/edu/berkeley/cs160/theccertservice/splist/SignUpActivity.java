package edu.berkeley.cs160.theccertservice.splist;

import java.util.HashMap;
import java.util.Map;

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
	
	public void onUserCreation() {
		Intent intent = new Intent(SignUpActivity.this, MainActivity.class);					
		startActivity(intent);
	}
	
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
					
					HashMap<String,HashMap<String, String>> userData = new HashMap<String,HashMap<String, String>>();
					HashMap<String, String> data = new HashMap<String, String>();
					data.put("email", usernameSignup.getText().toString());
					data.put("password", password1.getText().toString());
					data.put("password_confirmation", password2.getText().toString());
					data.put("name", "Eric");
					userData.put("user", data);
					MainActivity.server.createAccount(userData, SignUpActivity.this);
					
					MainActivity.user=usernameSignup.getText().toString();
					MainActivity.pass=password1.getText().toString();	
					
					
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
