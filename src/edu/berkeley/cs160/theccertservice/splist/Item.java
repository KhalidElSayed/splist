package edu.berkeley.cs160.theccertservice.splist;

public class Item {
	String _name;
	Boolean _shared;
	Integer _numPeopleSharing;
	
	public Item(String name) {
		_name = name;
		_shared = false;
		_numPeopleSharing = 0;
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
}
