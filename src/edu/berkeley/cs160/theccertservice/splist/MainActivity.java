package edu.berkeley.cs160.theccertservice.splist;

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
	static String user = "g";
	static String pass = "123";
	SharedPreferences settings;
	
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
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        if(hasLoggedIn)
        {
            //Go directly to main activity
        	Intent intent = new Intent(MainActivity.this, TabHostActivity.class);
			startActivity(intent);
        }
        
        logIn.setOnClickListener(new OnClickListener(){
        	//////
			@Override
			public void onClick(View arg0) { 
				
				
				
				// TODO Auto-generated method stub
				if((username.getText().toString().equals(user)) && (password.getText().toString().equals(pass))){
					ProgressDialog dialog = new ProgressDialog(MainActivity.this);
					dialog.setMessage("Getting your data... Please wait...");
					dialog.show();
					
					settings = getSharedPreferences(SETTING_INFO, 0);  
				    SharedPreferences.Editor editor = settings.edit();
				    editor.putBoolean("hasLoggedIn", true);
				    editor.commit();
				    
					Intent intent = new Intent(MainActivity.this, TabHostActivity.class);
					startActivity(intent);

				}
				else{
					noMatch.setText("Inccorect username OR password!");
					noMatch.setTextColor(Color.RED);
				}
			}
        	
        });
        
        signUp.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ProgressDialog dialog = new ProgressDialog(MainActivity.this);
				dialog.setMessage("Please wait...");
				dialog.show();
				Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
        	
        });
    }  
}  
