package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
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
	private static String SEPARATOR = "\\|";

	public ServerModel() {
		super("ServerSocketThread");
		this.loadAccounts();
		this.loadToDos();
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

	public void saveAccounts() {
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
	}

	public void saveToDos() {
		File toDosFile = new File(TODOS_FILE);
		String toDosLine = "";
		ArrayList<User> users = new ArrayList<>();
		ArrayList<ToDo> toDos = new ArrayList<>();
		try (Writer writer = new FileWriter(toDosFile)) {
			users = this.accounts.getUsers();
			int size = users.size();
			if (size > 0) {
				for (User u : users) {
					String userName = u.getUserName();
					toDosLine = userName + "|";
					toDos = u.getToDos();
					size = toDos.size();
					if (size > 0) {
						for (ToDo t : toDos) {
							String id = Integer.toString(t.getID());
							String title = t.getTitle();
							String priority = t.getPriority();
							String description = t.getDescription();
							String line = id + "|" + title + "|" + priority + "|" + description;
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

	public void loadAccounts() {
		File accountsFile = new File(ACCOUNTS_FILE);
		try (Reader inReader = new FileReader(accountsFile)) {
			BufferedReader in = new BufferedReader(inReader);
			String line = in.readLine();
			while (line != null) {
				String[] attributes = line.split(SEPARATOR);
				int size=attributes.length;
				if(size>1) {
				String userName = attributes[0];
				String password = attributes[1];
				User user = new User(userName, password);
				this.accounts.users.add(user);
				line = in.readLine();
			}}
		} catch (Exception e) {
		}
	}
	
	
	
	public void loadToDos() {
		File toDoFile = new File(TODOS_FILE);
		try (Reader inReader = new FileReader(toDoFile)) {
			BufferedReader in = new BufferedReader(inReader);
			String line = in.readLine();
			while (line != null) {
				String[] attributes = line.split(SEPARATOR,-1);
				int size=attributes.length;
				if(size>1) {
				String userName = attributes[0];
				String idString = attributes[1];
				String titel = attributes[2];
				String priority = attributes[3];
				String description = attributes[4];
				int id=Integer.parseInt(idString);
				//Sicherstellen, dass die globale ID grösser wird als die grösste ID im ToDo-Array
				if(id>ServerModel.ID) {
					ServerModel.ID=id;
				}
				ToDo toDo = new ToDo(id,titel,priority,description);
				accounts.getUser(userName).addToDo(toDo);
				line = in.readLine();
				}
			}
		} catch (Exception e) {
		}
	}
}