package server;

public class Server_Main {

	public static void main(String[] args) {
		int port = 50002;

		ServerModel serverModel = new ServerModel(port);
		serverModel.start();
	}

}
