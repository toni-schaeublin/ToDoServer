package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerModel extends Thread {

	private Integer port;
	private final String SEPARATOR = "\\|";
	Accounts accounts=new Accounts();
	
	private String reply = ("");
	
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
					/* Message vom Client in Teile zerlegen:
					 * MessageType, token, Data, Data, Data, Data... 
					 * und in Array speichern
					 * 
					 * Folgende Teile müssen vom Client gesendet werden:
					 * 
					 * CreateLogin, Username, Password 
					 * Login, Username, Password 
					 * ChangePasword, token, New password 
					 * Logout, - 
					 * CreateToDo, token, Title, Priority, Description, DueDate 
					 * GetToDo, token, ID 
					 * DeleteToDo, token, ID 
					 * ListToDos, token, List of IDs 
					 * Ping, (Token)
					 */
					
					String[] messageParts = message.split(SEPARATOR);
					int length = messageParts.length;
					System.out.println(length);
					// Testausgabe zum debuggen...
					String messageType = messageParts[0];
					switch (messageType) {
					case "CreateLogin":
						int size=messageParts.length;
						if(size==3) {
							createLogin(messageParts[1], messageParts[2]);
						}else {
							reply="false";
						}
						
						
						break;
					case "Login":
						System.out.println("Login");
						break;
					case "ChangePassword":
						System.out.println("ChangePassword");
						break;
					case "Logout":
						System.out.println("Logout");
						break;
					case "CreateToDo":
						System.out.println("CreateToDo");
						break;
					case "DeleteToDo":
						System.out.println("DeleteToDo");
						break;
					case "ListToDos":
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

	private void createLogin(String userName, String password) {
	User user = new User(userName, password);
	accounts.setUser(user);	
	};
}
