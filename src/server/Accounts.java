package server;

import java.util.ArrayList;

public class Accounts {

	ArrayList<User> users;

	public Accounts() {
	this.users = new ArrayList<>();
	}

	
	
	public void setUser(User user) {
		this.users.add(user);
	}

	public User getUser(String userName) {
		User user = null;
		int i = 0;
		for (User u : this.users) {
			if (u.getUserName().equals(userName)) {
				user = this.users.get(i);
			}
		}
		return user;
	}
}
