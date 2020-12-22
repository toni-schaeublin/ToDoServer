package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerModel extends Thread {

	private Integer port;
	private final String SEPARATOR = "\\|";
	Accounts accounts = new Accounts();

	private String reply = "";

	public ServerModel(int port) {
		super("ServerSocket");
		this.port = port;
	}

	@Override
	public void run() {
		try (ServerSocket listener = new ServerSocket(port, 10, null)) {

			while (true) {
				try (Socket socket = listener.accept();
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());) {
					String message = in.readLine();
					/*
					 * Message vom Client in Teile zerlegen: MessageType, token, Data, Data, Data,
					 * Data... und in Array speichern
					 * 
					 * Folgende Teile müssen vom Client gesendet werden:
					 * 
					 * CreateLogin, Username, Password Login, Username, Password ChangePasword,
					 * token, New password Logout, - CreateToDo, token, Title, Priority,
					 * Description, DueDate GetToDo, token, ID DeleteToDo, token, ID ListToDos,
					 * token, List of IDs Ping, (Token)
					 */

					String[] messageParts = message.split(SEPARATOR);
					String messageType = messageParts[0];
					switch (messageType) {
					case "CreateLogin":
						int size = messageParts.length;
						if (size == 3) {
							if (createLogin(messageParts[1], messageParts[2]) && Checker.checkEmail(messageParts[1])
									&& Checker.checkPassword(messageParts[2])) {
								reply = "Result|true";
							}
						} else {
							reply = "Result|false";
						}
						break;
					case "Login":
						int sizeLogin = messageParts.length;
						if (sizeLogin == 3) {
							if (login(messageParts[1], messageParts[2])) {
								reply = "Result|true|" + accounts.getUser(messageParts[1]).getToken();
							}
						} else {
							reply = "Result|false";
						}
						break;
					case "ChangePassword":
						int sizePassword = messageParts.length;
						if (sizePassword == 3) {
							if (changePassword(messageParts[1], messageParts[2])
									&& Checker.checkPassword(messageParts[2])) {
								reply = "Result|true";
							}
						} else {
							reply = "Result|false";
						}

						break;
					case "Logout":
						int sizeLogout = messageParts.length;
						if (sizeLogout == 3) {
							logout(messageParts[1], messageParts[2]);
						} else {
							reply = "Result|false";
						}

						break;
					case "CreateToDo":
						int sizeCreate = messageParts.length;
						if (sizeCreate == 6) {
							// createToDo(messageParts[2], messageParts[3], messageParts[4],
							// messageParts[5]);
						}

						break;
					case "DeleteToDo":
						int sizeDelete = messageParts.length;
						if (sizeDelete == 3) {
							// deleteToDo(messageParts[3]);
						}

						break;
					case "ListToDos":
						int sizeList = messageParts.length;
						if (sizeList == 3) {
							// ListToDo(messageParts [3]);
						}
						System.out.println("ListToDos");
						break;
					case "Ping":
						System.out.println("Ping");
						break;
					default:
						out.write("false\n");
						out.flush();
						System.out.println("false\n");
					}
//reply wird in den jeweiligen Methoden des Switchteils erstellt...
					String reply = ("");
					out.write(reply);
					out.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private Boolean createLogin(String userName, String password) {
		Boolean valid = false;
		try {
			User user = new User(userName, password);
			accounts.setUser(user);
			valid = true;
		} catch (Exception e) {
			System.out.println("Da ist etwas schief gelaufen! " + e);
		}
		return valid;
	}

	private Boolean login(String userName, String password) {
		Boolean valid = false;
		try {
			valid = accounts.getUser(userName).getPassword().equals(password);
			String token = Checker.createToken();
			accounts.getUser(userName).setToken(token);
		} catch (Exception e) {
			System.out.println("Da ist etwas schief gelaufen! " + e);
		}
		return valid;
	}

	private Boolean changePassword(String token, String password) {
		Boolean valid = false;
		try {
			accounts.getUserFromToken(token).setPassword(password);
			valid = true;
		} catch (Exception e) {
			System.out.println("Da ist etwas schief gelaufen! " + e);
		}
		return valid;
	}

	private void logout(String userName, String password) {
		// User vom Array löschen?
		User user = new User(userName, password);
		accounts.removeUser(user);

	}

	private void createToDo(String title, String priority, String description, DueDate dueDate) {
		// User user = new User(title, priority, description, dueDate);
		// toDos.add(toDo);

	}

	private void deleteToDo(ToDo toDo) {
		// toDos.remove(toDo);
	}

	// private ListToDo(String title, String priority, String description, DueDate
	// dueDate) {
	// ToDo toDos = new ToDo(title, priority, description, dueDate);
	// return toDos;
	// }

}
