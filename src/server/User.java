package server;

import java.util.ArrayList;

public class User {
	private String userName;
	private String salt;
	private String password;
	private String token;
	public ArrayList<ToDo> toDos = new ArrayList<>();

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.token = "";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;

	}

	public void addToDo(ToDo toDo) {
		this.toDos.add(toDo);
	}

	public ToDo getToDo(int id) {
		ToDo toDo = null;
		int i = -1;
		int z = -1;
		int size = this.toDos.size();
		if (size > 0) {
			for (ToDo t : this.toDos) {
				i++;
				if (t.getID() == id) {
					z = i;
				}
			}
			if (z > -1) {
				toDo = this.toDos.get(z);
			}
		}
		return toDo;
	}

	public ArrayList<ToDo> getToDos() {
		return this.toDos;
	}

	public Boolean deleteToDo(int id) {
		int i = -1;
		int z = -1;
		Boolean deleted = false;
		int size = this.toDos.size();
		if (size > 0) {
			for (ToDo t : this.toDos) {
				i++;
				if (t.getID() == id) {
					z = i;
				}
			}
			if (z > -1) {
				this.toDos.remove(z);
				deleted = true;
			}
		}
		return deleted;
	}

}
