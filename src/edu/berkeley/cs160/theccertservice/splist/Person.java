package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

public class Person {
	
	String _name;
	ArrayList<ShoppingList> _myLists;
	ArrayList<ShoppingList> _listsSharedWithMe;
	ArrayList<Person> _myFriends;
	Integer _phoneNumber;
	String _email;
	
	public Person(String name, String email) {
		_name = name;
		_myLists = new ArrayList<ShoppingList>();
		_listsSharedWithMe = new ArrayList<ShoppingList>();
		_myFriends = new ArrayList<Person>();
		_phoneNumber = 0;
		_email = email;
	}
	
	public String getName() {
		return _name;
	}
	
	public ArrayList<ShoppingList> getMyLists() {
		return _myLists;
	}
	
	public ArrayList<ShoppingList> getListsSharedWithMe() {
		return _listsSharedWithMe;
	}
	
	public ArrayList<Person> getFriends() {
		return _myFriends;
	}
	
	public void addFriend(Person person) {
		_myFriends.add(person);
	}
	
	public void removeFriend(Person person) {
		_myFriends.remove(person);
	}
	
	public void setEmail(String email) {
		_email = email;
	}
	
	public String getEmail() {
		return _email;
	}

}
