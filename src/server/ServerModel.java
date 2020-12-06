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
					//Message vom Client in Teile zerlegen:
					
					/*MessageType, Data, Data, Data, Data...
					 CreateLogin, Username, Password
					 Login, Username, Password
					 ChangePasword, New password 
					 Logout, -
					 CreateToDo, Title, Priority, Description, DueDate
					 GetToDo, ID
					 DeleteToDo, ID
					 ListToDos, List of IDs
					 Ping, (Token)
					 
					
					 */
					String[] messageParts = message.split(SEPARATOR);
					int length = messageParts.length;
					// Testausgabe zum debuggen...
					for (int i = 0; i < length; i++) {
						System.out.println(messageParts[i]);
					}
					// Der Rückgabe-String muss noch entsprechend generiert werden. Idee: Switchen auf
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
