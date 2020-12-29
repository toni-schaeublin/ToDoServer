package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerModel extends Thread {

	private Integer port;
	private Accounts accounts = new Accounts();
	private ServerSocket listener;
	public static int ID = 0;
	private static String ACCOUNTS_FILE = ("accounts.txt");
	private static String TODOS_FILE = ("toDos.txt");

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

	public void saveData() {
		File accountsFile = new File(ACCOUNTS_FILE);
		String line = "";
		try (Writer writer = new FileWriter(accountsFile)) {
			ArrayList<User> users = new ArrayList<>();
			users = this.accounts.getUsers();
			int size = users.size();
			if (size > 0) {
				for (User u : users) {
					String userName = u.getUserName();
					String password = u.getPassword();
					line = userName + "|" + password;
					writer.write(line);
					writer.write(System.lineSeparator());
				}
			}

		} catch (Exception e) {
		}
		File toDosFile = new File(TODOS_FILE);
		String toDosLine = "";
		try (Writer writer = new FileWriter(toDosFile)) {
			ArrayList<User> users = new ArrayList<>();
			users = this.accounts.getUsers();
			int size = users.size();
			if (size > 0) {
				for (User u : users) {
					String userName = u.getUserName();
					toDosLine = userName + "|";
					ArrayList<ToDo> toDos = new ArrayList<>();
					size = toDos.size();
					if (size > 0) {
						for (ToDo t : toDos) {
							String id = Integer.toString(t.getID());
							String title = t.getTitle();
							String priority = t.getPriority();
							String description = t.getDescription();
							String dueDate = t.getDueDate();
							line = id + "|" + title + "|" + priority + "|" + description + "|" + dueDate;
							String writeLine = toDosLine.concat(line);
							writer.write(writeLine);
							writer.write(System.lineSeparator());
						}
					}
				}
			}

		} catch (Exception e) {
		}
	}

	public void loadData() {
		// Daten aus den txt-File in Arrays speichern
	}
}