package edu.berkeley.cs160.theccertservice.splist;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;  
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity{  
	public static final String SETTING_INFO = "MyPrefsFile";
	private EditText username;
	private EditText password;
	private Button logIn;
	private TextView signUp;
	private TextView noMatch;

	static SharedPreferences settings;
	static String userId = null;
	static String authToken = null;

	static Server server = new Server("http://agile-hamlet-9112.herokuapp.com");
	
    /** Called when the activity is first created. */  
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        logIn = (Button)findViewById(R.id.logIn);
        signUp = (TextView)findViewById(R.id.signUp);
        noMatch = (TextView)findViewById(R.id.noMatch);
        
        settings = getSharedPreferences(SETTING_INFO, 0);
        MainActivity.authToken = settings.getString("token", null);
        MainActivity.userId = settings.getString("id", null);
        
        if(MainActivity.authToken != null)
        {
            //Go directly to main activity
        	Intent intent = new Intent(MainActivity.this, TabHostActivity.class);
			startActivity(intent);
        }
        
        logIn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) { 
				
				ProgressDialog dialog = new ProgressDialog(MainActivity.this);
				
				HashMap<String, String> p = new HashMap<String,String>();
				p.put("email",username.getText().toString());
				p.put("password", password.getText().toString());
				MainActivity.server.login(p, MainActivity.this);
								
				dialog.setMessage("Getting your data... Please wait...");
				dialog.show();		
			}       	
        });
        
        signUp.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ProgressDialog dialog = new ProgressDialog(MainActivity.this);
				dialog.setMessage("Please wait...");
				dialog.show();
				Intent intent = new Intent (MainActivity.this, SignUpActivity.class);
				startActivity(intent);
			}    	
        });
    }
	
	public void afterLoginAttempt(boolean success) {
		if (success) {
			Intent intent = new Intent(MainActivity.this, TabHostActivity.class);
			startActivity(intent);
		} else {
			noMatch.setText("Inccorect username OR password!");
			noMatch.setTextColor(Color.RED);
		}
	}
}  
