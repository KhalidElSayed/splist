package edu.berkeley.cs160.theccertservice.splist;

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
		_numPeopleSharing = 0;
		_price = 0;
		//ArrayList<Person> _peopleSharing;
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
		return _numPeopleSharing;
	}
	
	public void setPrice(String _name) {
		this._price = _price;
	}
	
	public Double getPrice() {
		return _price;
	}
	
	public ArrayList<Person> getPeopleSharing() {
		return _peopleSharing;
	}
}
