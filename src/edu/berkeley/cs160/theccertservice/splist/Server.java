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
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
			public void onPostExecute(JSONObject data) {
				if (data != null)
					Log.d("Json data", data.toString());
				a.onUserCreation();
			}
		}
		CreateAcct c = new CreateAcct("/users");
		c.execute(p);
	}
	
	public void login(Map p, final MainActivity a) {
		class AcctLogin extends JsonTask {
			public AcctLogin(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONObject data) {
				if (data != null)
					Log.d("Json data", data.toString());
					String token = null;
					String id = null;
					JSONObject items = null;
					try {
						token = (String) data.get("token");
						id = String.valueOf(data.get("id"));
					} catch (JSONException e) {
						// The login request failed so we can do something here if needed
						e.printStackTrace();
					}
					if (token != null) {
					    SharedPreferences.Editor editor = MainActivity.settings.edit();
					    editor.putString("token", token);
					    editor.putString("id", id);
					    editor.commit();
					    MainActivity.authToken = token;
					    MainActivity.userId = id;
					    //Process Initial Shopping Lists
					    try {
							items = data.getJSONObject("items");
						} catch (JSONException e) {
							e.printStackTrace();
						}				    
					    a.afterLoginAttempt(true);
					    return;
					}
					a.afterLoginAttempt(false);
				Log.d("Json data", "Request failed");					
			}
		}
		AcctLogin c = new AcctLogin("/tokens/create");
		c.execute(p);
	}

	public void logout(Map p) {
		class AcctLogout extends JsonTask {
			public AcctLogout(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONObject data) {
				if (data != null)
					Log.d("Json data", data.toString());
				//a.onUserCreation();
			}
		}
		AcctLogout c = new AcctLogout("/tokens/delete");
		c.execute(p);
	}
	
	public void getItems(Map p) {
		class gItems extends JsonTask {
			public gItems(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONObject data) {
				if (data != null)
					Log.d("Json data getItems", data.toString());
					JSONArray items = null;
					ArrayList<ShoppingList> lists = new ArrayList<ShoppingList>();
					try {
						items = (JSONArray) data.get("items");
						for (int i = 0; i < items.length(); i++) {
							JSONObject item = (JSONObject) items.get(i);
							Item sItem = new Item(item);
							sItem._list.addItem(sItem);
							if (!lists.contains(sItem._list))
								lists.add(sItem._list);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
	
					ArrayList<Item> itemsFriendsWillSplit = new ArrayList<Item>();
					for (ShoppingList l: lists) {
						for (Item i : l.getItems()) {
							if (i._shareAccepted) {
								itemsFriendsWillSplit.add(i);
							}
						}
					}
					synchronized (FeedAdapter.itemsIWillSplit) {
						FeedAdapter.itemsFriendsWillSplit = itemsFriendsWillSplit;
					}
			}
		}
		gItems c = new gItems("/item/getItems");
		c.execute();
		
	}
	
	public void getSharedItems(Map p) {
		class gSharedItems extends JsonTask {
			public gSharedItems(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONObject data) {
				if (data != null) {
					Log.d("Json data getSharedItems", data.toString());
					JSONArray items = null;
					ArrayList<ShoppingList> lists = new ArrayList<ShoppingList>();
					try {
						items = (JSONArray) data.get("items");
						for (int i = 0; i < items.length(); i++) {
							JSONObject item = (JSONObject) items.get(i);
							Item sItem = new Item(item);
							sItem._list.addItem(sItem);
							if (!lists.contains(sItem._list))
								lists.add(sItem._list);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
	
					ArrayList<Item> itemsIWillSplit = new ArrayList<Item>();
					ArrayList<Item> itemsFriendsWantToSplit = new ArrayList<Item>();
					for (ShoppingList l: lists) {
						for (Item i : l.getItems()) {
							if (i._shareAccepted) {
								itemsIWillSplit.add(i);
							} else {
								itemsFriendsWantToSplit.add(i);
							}
						}
					}
					
					synchronized (FeedAdapter.itemsIWillSplit) {
						FeedAdapter.itemsIWillSplit = itemsIWillSplit;
					}
					synchronized (FeedAdapter.itemsFriendsWantToSplit) {
						FeedAdapter.itemsFriendsWantToSplit = itemsFriendsWantToSplit;
					}
				}
			}
		}
		gSharedItems c = new gSharedItems("/item/getSharedItems");
		c.execute(p);
		
	}
	
	public void getFriends(Map p) {
		class gFriend extends JsonTask {
			public gFriend(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONObject data) {
				if (data != null) {
					Log.d("Json data", data.toString());
					JSONArray friends = null;
					try {
						friends = (JSONArray) data.get("friends");
					} catch (JSONException e) {
						//something happened!
						e.printStackTrace();
					}
					
					for (int i = 0; i < friends.length(); i++) {
						JSONObject f = null;
						try {
							f = friends.getJSONObject(i);
						} catch (JSONException e) {
							//Oh noes!
							e.printStackTrace();
						}
						if (f != null) {
							try {
								new Friend( f.getString("name"), 
										    f.getString("email"), 
										    Integer.valueOf((String) f.get("id")), 
										    Boolean.getBoolean((String) f.get("accepted")),
										    f.getString("asker"));
							} catch (NumberFormatException e) {
								//It done go wrong
								e.printStackTrace();
							} catch (JSONException e) {
								//It be even more wrong
								e.printStackTrace();
							}
						}
						
						
					}
				}
			}
		}
		gFriend c = new gFriend("/user/getFriends");
		c.execute(p);
		
	}
		
	public void requestFriend(Map p) {
		class rFriend extends JsonTask {
			public rFriend(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONObject data) {
				if (data != null) {
					Log.d("Json data", data.toString()); 
				}
			}
		}
		rFriend c = new rFriend("/user/makeFriendReq");
		c.execute(p);
				
	}
	
	public void acceptFriend(Map p) {
		class aFriend extends JsonTask {
			public aFriend(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONObject data) {
				if (data != null)
					Log.d("Json data", data.toString()); 
				//a.onUserCreation();
			}
		}
		aFriend c = new aFriend("/user/acceptFriendReq");
		c.execute(p);
				
	}
	
	public void addItem(Map p) {
		class aItem extends JsonTask {
			public aItem(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONObject data) {
				if (data != null)
					Log.d("Json data", data.toString());
				//a.onUserCreation();
			}
		}
		aItem c = new aItem("/item/add");
		c.execute(p);
	}
	
	public void editItem(Map p) {
		class eItem extends JsonTask {
			public eItem(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONObject data) {
				if (data != null)
					Log.d("Json data", data.toString());
				//a.onUserCreation();
			}
		}
		eItem c = new eItem("/item/edit");
		c.execute(p);
		
	}
	
	public void deleteItem(Map p) {
		class dItem extends JsonTask {
			public dItem(String _path) {
				super(SERVER + _path);
			}
			@Override
			public void onPostExecute(JSONObject data) {
				if (data != null)
					Log.d("Json data", data.toString());
				//a.onUserCreation();
			}
		}
		dItem c = new dItem("/item/delete");
		c.execute(p);
		
	}
	
	
}






