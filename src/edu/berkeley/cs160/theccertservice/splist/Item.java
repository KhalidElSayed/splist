package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Item {
	int _id;
	String _name = null;
	Boolean _shared;
	Boolean _shareAccepted;
	int _numPeopleSharing;
	Double _price;
	ArrayList<String> _peopleSharing;
	ShoppingList _list;
	
	public Item(String name) {
		_name = name;
		_shared = false;
		_price = 0.0;
		_peopleSharing = new ArrayList<String>();
	}
	
	public Item(String name, Boolean shared, Double price) {
		_name = name;
		_shared = shared;
		_price = price;
		ArrayList<Person> _peopleSharing = new ArrayList<Person>();
	}
	
	public Item(JSONObject item) {

		try {
			_id = item.getInt("id");
			_name = item.getString("name");
			_price = item.getDouble("price");
			_shared = item.getBoolean("shared");
			_numPeopleSharing = item.getInt("numSharing");
			String list = item.getString("list");
			int owner = item.getInt("owner");
			ShoppingList sList = ShoppingList.getShoppingList(list);
			if (sList == null) {
				for (Friend f : Friend.allFriends) {
					if (f.id == owner) {
						sList = new ShoppingList(list, f.name);
						break;
					}
				}		
			}
			if (sList == null) {
				if (_name != null) {
					Log.d("Item does not contain a list. Possibly corrupt...", _name);
				}
			}		
			sList.addItem(this);
			_list = sList;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			_shareAccepted = item.getBoolean("shareAccepted");
		} catch (JSONException e) {
			_shareAccepted = false;
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
	
	public double getNumPeopleSharing() {
		return (double) _peopleSharing.size() + 1;
	}
	
	
	public void setPrice(double price) {
		this._price = price;
	}
	
	public double getPrice() {
		return _price;
	}
	
	public ArrayList<String> getPeopleSharing() {
		return _peopleSharing;
	}
	
	@Override
	public String toString(){
		return getName();
	}
}
