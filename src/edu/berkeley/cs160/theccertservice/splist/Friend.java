package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

public class Friend {
	public String name;
	public String email;
	public int id;
	public boolean haveAcceptedRequest;
	public static ArrayList<Friend> allFriends = new ArrayList<Friend>();
	
	public Friend(String name, String email, int id, boolean haveAcceptedRequest) {
		this.name = name;
		this.email = email;
		this.id = id;
		this.haveAcceptedRequest = haveAcceptedRequest;
	}
	
	public String friendsAsJSONArrayString() {
		String result = "[";
		boolean start = true;
		for (Friend f : Friend.allFriends) {
			if (!start) {
				result += ",";
			}
			start = false;
			result += String.valueOf(f.id);	
		}
		return result + "]";
	}
}
