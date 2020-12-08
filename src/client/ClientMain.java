package client;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClientMain extends Application {

	private ClientView view;
	private ClientController controller;
	private ClientModel model;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		model = new ClientModel();
		view = new ClientView(primaryStage, model);
		controller = new ClientController(model, view);
		view.start();
	}
}
