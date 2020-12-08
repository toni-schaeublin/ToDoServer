package server;

public class User {
	private String userName;
	private String salt;
	private String password;
	private String token;

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.token = "";
	}

	public User() {
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

}
