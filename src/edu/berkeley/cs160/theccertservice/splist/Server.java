package edu.berkeley.cs160.theccertservice.splist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class Server {
	public String SERVER;
	public Server(String server) {
		this.SERVER = server;
	}
	public void createAccount(Map p, final SignUpActivity a) {
		class CreateAcct extends JsonTask {
			public CreateAcct(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONArray data) {
				if (data != null)
					Log.d("Json data", data.toString());
				a.onUserCreation();
			}
		}
		CreateAcct c = new CreateAcct("/users");
		c.execute(p);
	}
	public void login(Map p) {
		class AcctLogin extends JsonTask {
			public AcctLogin(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONArray data) {
				if (data != null)
					Log.d("Json data", data.toString());
				//a.onUserCreation();
			}
		}
		AcctLogin c = new AcctLogin("/tokens/create");
		c.execute(p);
	}
	
	public void getItems() {
		
	}
	public void getSharedItems() {
		
	}
	public void getFriends() {
		
	}
	public void addFriend() {
			
	}
	public void addItem() {
		
	}
	public void editItem() {
		
	}
	
	
}






