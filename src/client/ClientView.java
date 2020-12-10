package client;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientView {
	private final ClientModel model;
	private Stage stage;
	private Label createUserLbl = new Label("Bitte geben sie ihre E-Mailadresse ein:");
	private TextField createUserTxt = new TextField();
	private Label createPasswordLbl = new Label("Bitte geben sie ein Passwort ein:");
	private TextField createPasswordTxt = new TextField();
	private Button createUserBtn = new Button("Account erstellen");
	private Button loginBtn = new Button("Login");
	private Button logoutBtn = new Button("Logout");
	private Label ipLbl= new Label("Bitte geben sie die IP ein:");
	private TextField ipTxt = new TextField();
	private Label portLbl = new Label("Bitte geben sie die Port-Nummer ein:");
	private TextField portTxt = new TextField();
	private Button connectBtn = new Button("Verbinden");

	

	protected ClientView(Stage stage, ClientModel model) {
		this.stage = stage;
		this.model = model;
		stage.setTitle("Client");
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("To Do");
		Tab tab2 = new Tab("Account");
		Tab tab3 = new Tab("Connect");
		tab1.setClosable(false);
		tab2.setClosable(false);
		tab3.setClosable(false);
		tab1.setContent(createToDoPane());
		tab2.setContent(createAccountPane());
		tab3.setContent(createConnectPane());

		tabPane.getTabs().addAll(tab1, tab2, tab3);
		VBox mainWindow = new VBox();
		mainWindow.getChildren().add(tabPane);

		Scene scene = new Scene(mainWindow);
		scene.getStylesheets().add(getClass().getResource("ClientMVC.css").toExternalForm());
		stage.setScene(scene);
	}

	public void start() {
		stage.show();
	}

	// getter
	public Stage getStage() {
		return stage;
	}

	private Pane createConnectPane() {
		GridPane connectPane = new GridPane();
		connectPane.add(ipLbl, 0, 0);
		connectPane.add(ipTxt, 1, 0);
		connectPane.add(portLbl, 0, 1);
		connectPane.add(portTxt, 1, 1);
		connectPane.add(connectBtn, 0, 2);

		return connectPane;
	}

	private Pane createToDoPane() {
		GridPane toDoPane = new GridPane();

		return toDoPane;
	}

	public Pane createAccountPane() {
		GridPane createAccountPane = new GridPane();
		createAccountPane.add(createUserLbl, 0, 0);
		createAccountPane.add(createPasswordLbl, 0, 1);
		createAccountPane.add(createUserTxt, 1, 0);
		createAccountPane.add(createPasswordTxt, 1, 1);
		createAccountPane.add(createUserBtn, 0, 2);
		createAccountPane.add(loginBtn, 0, 3);
		createAccountPane.add(logoutBtn, 0, 4);
		return createAccountPane;
	}

}
