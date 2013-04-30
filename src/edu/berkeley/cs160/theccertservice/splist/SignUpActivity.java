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
	
	public void onUserCreation() {
		Intent intent = new Intent(SignUpActivity.this, MainActivity.class);					
		startActivity(intent);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		
		final EditText userSignup = (EditText)findViewById(R.id.name);
		final EditText emailSignup = (EditText)findViewById(R.id.email);
		final EditText password1 = (EditText)findViewById(R.id.password1);
		final EditText password2 = (EditText)findViewById(R.id.password2);
		final Button signUpButton = (Button)findViewById(R.id.signUpButton);
		final Button cancelButton = (Button)findViewById(R.id.cancelButton);
		final TextView showInfo = (TextView)findViewById(R.id.textView1);
		
		signUpButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(userSignup.getText().toString().equals("")){
					showInfo.setText("username can't be empty");
					showInfo.setTextColor(Color.RED);
				}
				else if(password1.getText().toString().equals(password2.getText().toString())){
					ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
					dialog.setMessage("Getting your data... Please wait...");
					dialog.show();
					
					HashMap<String,HashMap<String, String>> userData = new HashMap<String,HashMap<String, String>>();
					HashMap<String, String> data = new HashMap<String, String>();
					data.put("email", emailSignup.getText().toString());
					data.put("password", password1.getText().toString());
					data.put("password_confirmation", password2.getText().toString());
					data.put("name", userSignup.getText().toString());
					userData.put("user", data);
					MainActivity.server.createAccount(userData, SignUpActivity.this);				
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
