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
		for (User u : this.users) {
			if (u.getUserName().equals(userName)) {
				user = u;
			}
		}
		return user;
	}
	
	
	public User getUserFromToken(String token) {
		User user = null;
		for (User u : this.users) {
			if (u.getToken().equals(token)) {
				user = u;
			}
		}
		return user;
	}
	public ArrayList<User> getUsers(){
		return this.users;
	}
	
}
