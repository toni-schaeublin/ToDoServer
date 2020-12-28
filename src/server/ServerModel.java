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
	private ServerSocket listener;
	public static int ID = 0;

	public ServerModel() {
		super("ServerSocketThread");
	}

	@Override
	public void run() {
		try {
			listener = new ServerSocket(port, 10, null);
			while (true) {
				Socket socket = listener.accept();
				ServerModelClientThread client = new ServerModelClientThread(this, socket);
				client.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	};

	public void setPort(Integer port) {
		this.port = port;
	}

	public Accounts getAccounts() {
		return this.accounts;
	}

	public void safeData() {
		// Daten aus den Arrays in txt-File speichern
	}

	public void loadData() {
		// Daten aus den txt-File in Arrays speichern
	}
}