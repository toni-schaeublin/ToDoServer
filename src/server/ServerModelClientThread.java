package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerModelClientThread extends Thread {
	private static int nextClientID = 0;
	private final String SEPARATOR = "\\|";
	private String reply;
	private String userName;
	private String password;
	private String token;
	private ServerModel server;
	private BufferedReader in;
	private Accounts accounts;
	private Socket socket;
	private int specificID;
	private ToDo toDo = null;
	private ArrayList<ToDo> toDos = new ArrayList<>();
	private Boolean loggedIn = false;

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
				if (message != null) {
					String[] messageParts = message.split(SEPARATOR, -1);
					String messageType = messageParts[0];
					switch (messageType) {
					case "CreateLogin":
						int size = messageParts.length;
						if (size == 3 && Checker.freeUsername(messageParts[1], this.accounts)
								&& Checker.checkEmail(messageParts[1]) && Checker.checkPassword(messageParts[2])) {
							if (createLogin(messageParts[1], messageParts[2])) {
								reply = "Result|true\n";
								server.saveAccounts();
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
								loggedIn = true;
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
								server.saveAccounts();
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
							if (logout(messageParts[1]) && this.loggedIn) {
								reply = "Result|true\n";
								loggedIn = false;
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
							if (createToDo(messageParts[1], messageParts[2], messageParts[3], messageParts[4])) {
								reply = "Result|true|" + this.specificID + "\n";
								server.saveToDos();
							} else {
								reply = "Result|false\n";
							}
						} else {
							reply = "Result|false\n";
						}

						break;
					case "GetToDo":
						int sizeGet = messageParts.length;
						if (sizeGet == 3) {
							try {
								int id = Integer.parseInt(messageParts[2]);
								if (getToDo(messageParts[1], id)) {
									String returnId = Integer.toString(toDo.getID());
									String title = toDo.getTitle();
									String priority = toDo.getPriority();
									String description = toDo.getDescription();
									String dueDate = "";
									Boolean withDueDate = false;
									try {
										dueDate = toDo.getDueDate();
										withDueDate = true;
									} catch (Exception e) {
										System.out.println("ToDo ohne dueDate");
										withDueDate = false;
									}
									if (withDueDate) {
										reply = "Result|true|" + returnId + "|" + title + "|" + priority + "|"
												+ description + "|" + dueDate + "\n";
									} else {
										reply = "Result|true|" + returnId + "|" + title + "|" + priority + "|"
												+ description + "\n";
									}
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
					case "DeleteToDo":
						int sizeDelete = messageParts.length;
						if (sizeDelete == 3) {
							try {
								int id = Integer.parseInt(messageParts[2]);
								if (deleteToDo(messageParts[1], id)) {
									reply = "Result|true\n";
									server.saveToDos();
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
						if (sizeList == 2) {
							try {
								if (listToDos(messageParts[1])) {
									reply = "Result|true";
									for (ToDo t : this.toDos) {
										String part = ("|" + t.getID());
										reply = reply.concat(part);
									}
									reply = reply.concat("\n");
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
					case "Ping":
						int sizePing = messageParts.length;
						if (sizePing > 1 && loggedIn) {
							try {
								this.token = accounts.getUserFromToken(messageParts[1]).getToken();
								reply = "Result|true\n";
							} catch (Exception e) {
								System.out.println("Da ist etwas schief gelaufen! " + e);
								reply = "Result|false\n";
							}
						} else {
							reply = "Result|true\n";
						}
						break;
					default:
						reply = "Result|false\n";
					}
					// reply wird in den jeweiligen Methoden des Switchteils erstellt...
					out.write(reply);
					out.flush();
				}
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	private Boolean createLogin(String userName, String password) {
		this.userName = userName;
		this.password = password;
		Boolean valid = false;
		try {
			User user = new User(this.userName, this.password);
			accounts.setUser(user);
			valid = true;
		} catch (Exception e) {
			System.out.println("Da ist etwas schief gelaufen! " + e);
		}
		return valid;
	}

	private Boolean login(String userName, String password) {
		Boolean valid = false;
		this.userName = userName;
		this.password = password;
		int size = accounts.getUsers().size();
		if (size > 0) {
			try {
				valid = accounts.getUser(this.userName).getPassword().equals(this.password);
				if (valid) {
					this.token = Checker.createToken();
					accounts.getUser(userName).setToken(this.token);
				}
			} catch (Exception e) {
				System.out.println("Da ist etwas schief gelaufen! " + e);
			}
		}
		return valid;
	}

	private Boolean changePassword(String token, String password) {
		this.token = token;
		this.password = password;
		Boolean valid = false;
		int size = accounts.getUsers().size();
		if (size > 0) {
			try {
				accounts.getUserFromToken(this.token).setPassword(this.password);
				valid = true;
			} catch (Exception e) {
				System.out.println("Da ist etwas schief gelaufen! " + e);
			}
		}
		return valid;
	}

//Beim Logout muss der Token mitgegeben werden, damit der User identifiziert werden kann!

	private Boolean logout(String token) {
		this.token = token;
		Boolean valid = false;
		int size = accounts.getUsers().size();
		if (size > 0) {
			try {
				/*
				 * Beim Logout wird dem User ein unbekannter und zufälliger Token zugewiesen.
				 * Somit ist sichergestellt, dass man nicht mit einem leeren String oder null
				 * auf die Daten des Users zugreifen kann!
				 */
				accounts.getUserFromToken(this.token).setToken(Checker.createToken());
				valid = true;
			} catch (Exception e) {
				System.out.println("Da ist etwas schief gelaufen! " + e);
			}
		}
		return valid;

	}

	private Boolean createToDo(String token, String title, String priority, String description) {
		Boolean valid = false;
		this.token = token;
		int size = accounts.getUsers().size();
		if (size > 0) {
			try {
				ArrayList<ToDo> toDos = new ArrayList<>();
				// Die ID des ToDos auf die höchste ToDo-ID dieses Users setzen!
				toDos = accounts.getUserFromToken(token).getToDos();
				this.specificID = 0;
				int toDosSize = toDos.size();
				if (toDosSize > 0) {
					for (ToDo t : toDos) {
						int id = t.getID();
						if (id > this.specificID) {
							this.specificID = id;
						}
					}
				}
				specificID++;
				ToDo toDo = new ToDo(specificID, title, priority, description);
				accounts.getUserFromToken(this.token).addToDo(toDo);
				valid = true;
			} catch (Exception e) {
				System.out.println("Da ist etwas schief gelaufen! " + e);
			}
		}
		return valid;
	}

	private Boolean deleteToDo(String token, int id) {
		this.token = token;
		Boolean valid = false;
		try {
			valid = accounts.getUserFromToken(this.token).deleteToDo(id);
		} catch (Exception e) {
			System.out.println("Da ist etwas schief gelaufen! " + e);
		}
		return valid;
	}

	private Boolean getToDo(String token, int id) {
		this.token = token;
		Boolean valid = false;
		try {
			this.toDo = accounts.getUserFromToken(this.token).getToDo(id);
			valid = true;
		} catch (Exception e) {
			this.toDo = null;
			System.out.println("Da ist etwas schief gelaufen! " + e);
		}
		return valid;
	}

	private Boolean listToDos(String token) {
		this.token = token;
		Boolean valid = false;
		try {
			this.toDos = accounts.getUserFromToken(this.token).getToDos();
			valid = true;
		} catch (Exception e) {
			System.out.println("Da ist etwas schief gelaufen! " + e);
		}
		return valid;

	}

}
