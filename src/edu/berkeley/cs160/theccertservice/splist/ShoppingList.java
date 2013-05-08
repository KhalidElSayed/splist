package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public class ShoppingList {
	
	String _name;
	String _owner;
	ArrayList<Item> _items;
	static HashMap<String, ShoppingList> hm = new HashMap<String, ShoppingList>();
	static HashMap<String, ShoppingList> hmShared = new HashMap<String, ShoppingList>();
	
	public ShoppingList(String name, int owner_id) {
		_name = name;
		_items = new ArrayList<Item>();
		if (owner_id == MainActivity.userId) {
			hm.put(name, this);
			_owner = MainActivity.userName;
		} else {
			hmShared.put(name, this);
			Friend f = Friend.getFriend(owner_id);
			if (f != null) {
				_owner = f.name;
			} else {
				Log.e("ShoppingList", "Friend ID could not be found in Friends.");
			}
		}	
	}
	
	public void setOwner(String _owner) {
		this._owner = _owner;
	}
	
	public String getOwner() {
		return _owner;
	}
	
	public void addItem(Item item) {
		if(item._id != -1){
			for (Item i : _items) {
				if (item._id == i._id || i._id == -1) {
					return;
				}
			}
		}
		_items.add(item);	
	}
	
	public Item deleteItem(int pos) {
		Item i = _items.remove(pos);
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("auth_token", MainActivity.authToken);
		data.put("id", String.valueOf(i._id));
		MainActivity.server.deleteItem(data);	
		return i;
	}

	public boolean deleteItem(Item item){
		return _items.remove(item);
	}
	
	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}
	
	public Integer getSize() {
		return _items.size();
	}
	
	public ArrayList<Item> getItems() {
		return _items;
	}
	
	public void deleteList(){
		for (int i = _items.size() - 1; i >= 0 ; i--) {
			deleteItem(i);
		}
		hm.remove(_name);
	}
	
	public static ShoppingList getShoppingList(String name) {
		return hm.get(name);
	}
	
	public static ShoppingList getSharedShoppingList(String name) {
		return hmShared.get(name);
	}
	
	public static ArrayList<String> allListNames(){
		return new ArrayList<String>(hm.keySet());
	}
	
	public static ArrayList<String> allSharedListNames(){
		return new ArrayList<String>(hmShared.keySet());
	}
}
