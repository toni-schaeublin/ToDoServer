package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerModelClientThread extends Thread {
	private static int nextClientID = 0;
	private final String SEPARATOR = "\\|";
	private String reply;
	private ServerModel server;
	private BufferedReader in;
	private Accounts accounts;
	private Socket socket;
	private int specificID;

	public ServerModelClientThread(ServerModel server, Socket socket) {
		super("Client " + nextClientID++);
		this.server = server;
		this.socket = socket;
		this.accounts = server.getAccounts();

	}

	@Override
	public void run() {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream());) {
			while (true) {
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
				String[] messageParts = message.split(SEPARATOR, -1);
				String messageType = messageParts[0];
				switch (messageType) {
				case "CreateLogin":
					int size = messageParts.length;
					if (size == 3 && Checker.freeUsername(messageParts[1], this.accounts)
							&& Checker.checkEmail(messageParts[1]) && Checker.checkPassword(messageParts[2])) {
						if (createLogin(messageParts[1], messageParts[2])) {
							reply = "Result|true\n";
						} else {
							reply = "Result|false\n";
						}
					} else {
						reply = "Result|false\n";
					}
					break;
				case "Login":
					int sizeLogin = messageParts.length;
					if (sizeLogin == 3) {
						if (login(messageParts[1], messageParts[2])) {
							reply = "Result|true|" + accounts.getUser(messageParts[1]).getToken() + "\n";
						} else {
							reply = "Result|false\n";
						}
					} else {
						reply = "Result|false\n";
					}
					break;
				case "ChangePassword":
					int sizePassword = messageParts.length;
					if (sizePassword == 3 && Checker.checkPassword(messageParts[2])) {
						if (changePassword(messageParts[1], messageParts[2])) {
							reply = "Result|true\n";
						} else {
							reply = "Result|false\n";
						}
					} else {
						reply = "Result|false\n";
					}

					break;
				case "Logout":
					int sizeLogout = messageParts.length;
					if (sizeLogout == 2) {
						if (logout(messageParts[1])) {
							reply = "Result|true\n";
						} else {
							reply = "Result|false\n";
						}
					} else {
						reply = "Result|false\n";
					}

					break;
				case "CreateToDo":
					int sizeCreate = messageParts.length;
					if (sizeCreate == 5 && Checker.checkStringIsBetween(3, 20, messageParts[2])
							&& Checker.checkPriority(messageParts[3])
							&& Checker.checkStringIsBetween(0, 255, messageParts[4])) {
						System.out.println("Methode CreateToDo wird aufgerufen");
						if (createToDo(messageParts[1], messageParts[2], messageParts[3], messageParts[4])) {
							reply = "Result|true|" + this.specificID + "\n";
						} else {
							reply = "Result|false\n";
						}
					} else {
						System.out.println("Methode CreateToDo wird nicht aufgerufen");
						reply = "Result|false\n";
					}

					break;
				case "GetToDo":
					int sizeGet = messageParts.length;
					if (sizeGet == 3) {
						// ListToDo(messageParts [3]);
					}
					break;
				case "DeleteToDo":
					int sizeDelete = messageParts.length;
					if (sizeDelete == 3) {
						try {
							int id = Integer.parseInt(messageParts[2]);
							if (deleteToDo(messageParts[1], id)) {
								reply = "Result|true\n";
							} else {
								reply = "Result|false\n";
							}
						} catch (Exception e) {
							System.out.println("Da ist etwas schief gelaufen! " + e);
							reply = "Result|false\n";
						}
					} else {
						reply = "Result|false\n";
					}
					break;
				case "ListToDos":
					int sizeList = messageParts.length;
					if (sizeList == 3) {
						// ListToDo(messageParts [3]);
					}
					break;
				case "Ping":
					System.out.println("Ping");
					break;
				default:
					reply = "Result|false\n";
				}
				// reply wird in den jeweiligen Methoden des Switchteils erstellt...
				out.write(reply);
				out.flush();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
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
		int size = accounts.getUsers().size();
		if (size > 0) {
			try {
				valid = accounts.getUser(userName).getPassword().equals(password);
				if (valid) {
					String token = Checker.createToken();
					accounts.getUser(userName).setToken(token);
				}
			} catch (Exception e) {
				System.out.println("Da ist etwas schief gelaufen! " + e);
			}
		}
		return valid;
	}

	private Boolean changePassword(String token, String password) {
		Boolean valid = false;
		int size = accounts.getUsers().size();
		if (size > 0) {
			try {
				accounts.getUserFromToken(token).setPassword(password);
				valid = true;
			} catch (Exception e) {
				System.out.println("Da ist etwas schief gelaufen! " + e);
			}
		}
		return valid;
	}

//Beim Logout muss der Token mitgegeben werden, damit der User identifiziert werden kann!

	private Boolean logout(String token) {
		Boolean valid = false;
		int size = accounts.getUsers().size();
		if (size > 0) {
			try {
				/*
				 * Beim Logout wird dem User ein unbekannter und zufälliger Token zugewiesen.
				 * Somit ist sichergestellt, dass man nicht mit einem leeren String oder null
				 * auf die Daten des Users zugreifen kann!
				 */
				accounts.getUserFromToken(token).setToken(Checker.createToken());
				valid = true;
			} catch (Exception e) {
				System.out.println("Da ist etwas schief gelaufen! " + e);
			}
		}
		return valid;

	}

	private Boolean createToDo(String token, String title, String priority, String description) {
		Boolean valid = false;
		int size = accounts.getUsers().size();
		if (size > 0) {
			try {
				System.out.println("Im try CreateToDo");
				ServerModel.ID++;
				specificID = ServerModel.ID;
				ToDo toDo = new ToDo(specificID, title, priority, description);
				accounts.getUserFromToken(token).addToDo(toDo);
				valid = true;
			} catch (Exception e) {
				System.out.println("Da ist etwas schief gelaufen! " + e);
			}
		}
		return valid;
	}

	private Boolean deleteToDo(String token, int id) {
		Boolean valid = false;

		return valid;
	}

// private ListToDo(String title, String priority, String description, DueDate
// dueDate) {
// ToDo toDos = new ToDo(title, priority, description, dueDate);
// return toDos;
// }

}
