package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerModel extends Thread {

	private Integer port;
	private final String SEPARATOR = "\\|";

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
					 * 
					 * MessageType, token, Data, Data, Data, Data... 
					 * 
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
						System.out.println("CreateLogin");
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

					// Der Rückgabe-String muss noch entsprechend generiert werden. Idee: Switchen
					// auf
					// messageType
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
	};

}
