package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingList {
	
	String _name;
	ArrayList<Item> _items;
	static HashMap<String, ShoppingList> hm = new HashMap<String, ShoppingList>();
	
	public ShoppingList(String name) {
		_name = name;
		_items = new ArrayList<Item>();
		hm.put(name, this);
	}
	
	public void addItem(Item item) {
		_items.add(item);
	}
	
	public Item deleteItem(int pos) {		
		return _items.remove(pos);
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
	
	public static ShoppingList getShoppingList(String name) {
		return hm.get(name);
	}
}
