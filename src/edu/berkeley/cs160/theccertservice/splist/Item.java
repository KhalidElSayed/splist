package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Item {
	int _id;
	String _name = null;
	Boolean _shared;
	Boolean _shareAccepted; //When dealing with items that don't belong to you it lets you know if you have accepted
							//When dealing with your own items, it lets you know if someone has accepted your offer
	ArrayList<Integer> _peopleSharing;
	ArrayList<Integer> _peopleInvitedToShare;
	Double _price;
	ShoppingList _list;
	
	public Item(String name) {
		_name = name;
		_shared = false;
		_price = 0.0;
	}
	
	public Item(String name, Boolean shared, Double price) {
		_name = name;
		_shared = shared;
		_price = price;
		ArrayList<Integer> _peopleSharing = new ArrayList<Integer>();
	}
	
	public Item(JSONObject item) {
		
		try {
			_shareAccepted = item.getBoolean("shareAccepted");
		} catch (JSONException e) {
			_shareAccepted = false;
		}

		_peopleSharing = new ArrayList<Integer>();
		_peopleInvitedToShare = new ArrayList<Integer>();
		try {
			_id = item.getInt("id");
			_name = item.getString("name");
			_price = item.getDouble("price");
			_shared = item.getBoolean("shared");
		
			String list = item.getString("list");
			int owner = item.getInt("owner");

			ShoppingList sList = ShoppingList.getShoppingList(list);
			if (sList == null) {
				sList = new ShoppingList(list, owner);
			}
			_list = sList;		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray peopleSharing;
		try {
			peopleSharing = item.getJSONArray("pplSharing");
		
		for (int i = 0; i < peopleSharing.length(); i++) {
			JSONObject p = peopleSharing.getJSONObject(i);
			boolean isSharing = p.getBoolean("accepted");
			if (isSharing) {
				_peopleSharing.add(p.getInt("user_id"));
				_shareAccepted = true;
			} else {
				_peopleInvitedToShare.add(p.getInt("user_id"));
			}			
		}
		} catch (JSONException e) {
			
		}
		
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public Boolean getShared() {
		return _shared;
	}

	public void setShared(Boolean _shared) {
		this._shared = _shared;
	}
	
	public int getNumPeopleSharing() {
		return _peopleSharing.size() + 1;
	}
	
	
	public void setPrice(double price) {
		this._price = price;
	}
	
	public double getPrice() {
		return _price;
	}
	
	public ArrayList<String> getPeopleSharing() {
		ArrayList<String> pplSharing = new ArrayList<String>();
		for (Integer pId : _peopleSharing) {
			Friend f = Friend.getFriend(pId);
			if (f != null) {
				pplSharing.add(f.name);
			}
		}
		return pplSharing;
	}
	
	@Override
	public String toString(){
		return getName();
	}
}
