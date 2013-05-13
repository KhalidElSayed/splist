package edu.berkeley.cs160.theccertservice.splist;

import java.util.ArrayList;

public class Friend {
	public String name;
	public String email;
	public int id;
	public boolean haveAcceptedRequest;
	public String asker;
	public static ArrayList<Friend> allFriends = new ArrayList<Friend>();
	
	public Friend(String name, String email, int id, boolean haveAcceptedRequest, String asker) {
		this.name = name;
		this.email = email;
		this.id = id;
		this.haveAcceptedRequest = haveAcceptedRequest;
		this.asker = asker;
		for (ShoppingList l : ShoppingList.hmShared.values()) {
			if (l._owner == null && l.getItems().get(0)._owner_id == id) {
				l._owner = name;
			}
		}
		Friend.allFriends.add(this);
	}
	
	public void unfriend(Friend f) {
		Friend.allFriends.remove(f);
	}
	
	public static String friendsAsJSONArrayString() {
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
	
	public static Friend getFriend(int id) {
		for (Friend f : allFriends) {
			if (f.id == id) {
				return f;
			}
		}
		return null;
	}
}
