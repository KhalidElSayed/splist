package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

public class Item {
	String _name;
	Boolean _shared;
	Integer _numPeopleSharing;
	Double _price;
	ArrayList<Person> _peopleSharing;
	ShoppingList _list;
	
	public Item(String name) {
		_name = name;
		_shared = false;
		_price = 0.0;
		ArrayList<Person> _peopleSharing = new ArrayList<Person>();
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
	
	public Integer getNumPeopleSharing() {
		return _peopleSharing.size() + 1;
	}
	
	public void setPrice(Double price) {
		this._price = price;
	}
	
	public Double getPrice() {
		return _price;
	}
	
	public ArrayList<Person> getPeopleSharing() {
		return _peopleSharing;
	}
}
