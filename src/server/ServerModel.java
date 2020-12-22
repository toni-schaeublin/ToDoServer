package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerModel extends Thread {

	private Integer port;
	private Accounts accounts = new Accounts();

	public ServerModel() {
		super("ServerSocket");
	}

	@Override
	public void run() {
		try (ServerSocket listener = new ServerSocket(port, 10, null)) {
			while (true) {
				Socket socket = listener.accept();
				ServerModelClientThread client = new ServerModelClientThread(socket, accounts);
				client.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	};

	public void setPort(Integer port) {
		this.port = port;
	}
	
}