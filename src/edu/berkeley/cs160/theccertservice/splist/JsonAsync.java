package edu.berkeley.cs160.theccertservice.splist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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
import android.util.Log;

class JsonTask extends AsyncTask<Map, Void, JSONObject> {
	
	public String path;
	
	public JsonTask(String _path) {
		this.path = _path;
	}

	@Override
	protected JSONObject doInBackground(Map... arg0) {
		if (arg0.length != 0) {
			HttpResponse resp = this.makeRequest(arg0[0] ,this.path);
			if (resp != null) {
				return processResp(resp);
			}
		}
		return null;
	}
	
	private JSONObject getJsonObjectFromMap(Map params) throws JSONException {
		Iterator iter = params.entrySet().iterator();
		JSONObject holder = new JSONObject();
		JSONObject data = new JSONObject();

		while (iter.hasNext()) 
		{
			Map.Entry pairs = (Map.Entry)iter.next();
			String key = (String)pairs.getKey();

			//If there is a subMap then iterate over that
			if (pairs != null && pairs.getValue() != null) {
				if (pairs.getValue().getClass() == HashMap.class) {
					Map m = (Map)pairs.getValue();
					Iterator iter2 = m.entrySet().iterator();
					while (iter2.hasNext()) {
						Map.Entry pairs2 = (Map.Entry)iter2.next();
						data.put((String)pairs2.getKey(), (String)pairs2.getValue());
						holder.put(key, data);
					}
				} else {
					holder.put(key, (String)pairs.getValue());
				}
			} else {
				Log.e("JSONAsync", "A Pair or value was empty");
				throw new JSONException("A Pair or value was empty");
			}
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
			return null;
		}

		//passes the results to a string builder/entity
		StringEntity se = null;
		try {
			se = new StringEntity(holder.toString());
		} catch (UnsupportedEncodingException e) {
			return null;
		}

		//sets the post request as the resulting string
		httpost.setEntity(se);
		//sets a request header so the page receiving the request
		//will know what to do with it
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");

		//Handles what is returned from the page 

		try {
			return httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	private JSONObject processResp (HttpResponse resp) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (IllegalStateException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		String json = null;
		try {
			json = reader.readLine();
		} catch (IOException e) {
			return null;
		}
		//Log.d("JSONASync", "json contained: " + json);
		JSONObject finalResult = null;
		if (json.length() > 0 && json.charAt(0) == '{') {
			JSONTokener tokener = new JSONTokener(json);
			try {
				finalResult = new JSONObject(tokener);
			} catch (JSONException e) {
				return null;
			}
		} else {
			Log.e("JSONAsync", "Got back non-JSON data: " + json);
		}
		return finalResult;
	}
	
}
