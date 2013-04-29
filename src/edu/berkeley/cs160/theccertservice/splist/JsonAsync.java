package edu.berkeley.cs160.theccertservice.splist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;

class JsonTask extends AsyncTask<Map, Void, JSONArray> {
	
	public String path;
	
	public JsonTask(String _path) {
		this.path = _path;
	}

	@Override
	protected JSONArray doInBackground(Map... arg0) {
		HttpResponse resp = this.makeRequest(arg0[0] ,this.path);
		if (resp != null) {
			return processResp(resp);
		}
		return null;
		
	}
	
	private JSONObject getJsonObjectFromMap(Map params) throws JSONException {

		//all the passed parameters from the post request
		//iterator used to loop through all the parameters
		//passed in the post request
		Iterator iter = params.entrySet().iterator();

		//Stores JSON
		JSONObject holder = new JSONObject();

		//using the earlier example your first entry would get email
		//and the inner while would get the value which would be 'foo@bar.com' 
		//{ fan: { email : 'foo@bar.com' } }

		//While there is another entry
		while (iter.hasNext()) 
		{
			//gets an entry in the params
			Map.Entry pairs = (Map.Entry)iter.next();

			//creates a key for Map
			String key = (String)pairs.getKey();

			//Create a new map
			Map m = (Map)pairs.getValue();   

			//object for storing Json
			JSONObject data = new JSONObject();

			//gets the value
			Iterator iter2 = m.entrySet().iterator();
			while (iter2.hasNext()) 
			{
				Map.Entry pairs2 = (Map.Entry)iter2.next();
				data.put((String)pairs2.getKey(), (String)pairs2.getValue());
			}

			//puts email and 'foo@bar.com'  together in map
			holder.put(key, data);
		}
		return holder;
	}
	private HttpResponse makeRequest(Map params, String path) {
		
		//instantiates httpclient to make request
		DefaultHttpClient httpclient = new DefaultHttpClient();

		//url with the post data
		HttpPost httpost = new HttpPost(path);

		//convert parameters into JSON object
		JSONObject holder = null;
		try {
			holder = getJsonObjectFromMap(params);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//passes the results to a string builder/entity
		StringEntity se = null;
		try {
			se = new StringEntity(holder.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		//sets the post request as the resulting string
		httpost.setEntity(se);
		//sets a request header so the page receiving the request
		//will know what to do with it
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");

		//Handles what is returned from the page 
		ResponseHandler responseHandler = new BasicResponseHandler();
		try {
			return httpclient.execute(httpost, responseHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	private JSONArray processResp (HttpResponse resp) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String json = null;
		try {
			json = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONTokener tokener = new JSONTokener(json);
		JSONArray finalResult = null;
		try {
			finalResult = new JSONArray(tokener);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return finalResult;
	}
	
}
