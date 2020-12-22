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
	
	public void removeUser(User user) {
		this.users.remove(user);
		
	}

	public User getUser(String userName) {
		User user = null;
		int i = 0;
		for (User u : this.users) {
			if (u.getUserName().equals(userName)) {
				user = this.users.get(i);
				i++;
			}
		}
		return user;
	}
	
	
	public User getUserFromToken(String token) {
		User user = null;
		int i = 0;
		for (User u : this.users) {
			if (u.getToken().equals(token)) {
				user = this.users.get(i);
				i++;
			}
		}
		return user;
	}
	
	public ArrayList getUsers() {
		return users;
	}
	
}
