package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

public class ShoppingList {
	
	String _name;
	ArrayList<Item> _items = new ArrayList<Item>();
	
	public ShoppingList(String name) {
		_name = name;
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
}
