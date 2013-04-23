package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

public class Person {
	
	String _name;
	Integer _phoneNumber;
	String _email;
	
	public Person(String name, String email) {
		_name = name;
		_phoneNumber = 0;
		_email = email;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setEmail(String email) {
		_email = email;
	}
	
	public String getEmail() {
		return _email;
	}

}
