package client;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
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
	private Label ipLbl = new Label("Bitte geben sie die IP ein:");
	private TextField ipTxt = new TextField();
	private Label portLbl = new Label("Bitte geben sie die Port-Nummer ein:");
	private TextField portTxt = new TextField();
	private Button connectBtn = new Button("Verbinden");
	private Button pingBtn = new Button("Ping");
	private Label titleLbl = new Label("Titel:");
	private Label priorityLbl = new Label("Priorität:");
	private Label dateLbl = new Label("Zu erledigen bis:");
	private Label descriptionLbl = new Label("Beschreibung:");
	private TextField titleTxt = new TextField();
	private TextField priorityTxt = new TextField();
	private TextField dueDateTxt = new TextField();
	private TextArea descriptionTxt = new TextArea();
	private Button createToDoBtn = new Button("To Do erstellen");
	private Button deleteToDoBtn = new Button("To Do löschen");
	private Button listToDoBtn = new Button("To Do's auflisten");
	private Label statusLbl = new Label("Status");

	protected ClientView(Stage stage, ClientModel model) {
		this.stage = stage;
		this.model = model;
		stage.setTitle("Client");
		statusLbl.setId("status");
		descriptionTxt.setId("txtDescription");
		descriptionTxt.setWrapText(true);
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("Account");
		Tab tab2 = new Tab("ToDo");
		Tab tab3 = new Tab("Connect");

		HBox btnBox1 = new HBox();
		btnBox1.getChildren().addAll(createUserBtn, loginBtn, logoutBtn);
		VBox vBox1 = new VBox();
		vBox1.getChildren().addAll(createAccountPane(), btnBox1);

		HBox btnBox2 = new HBox();
		btnBox2.getChildren().addAll(createToDoBtn, deleteToDoBtn, listToDoBtn); 
		VBox vBox2 = new VBox();
		vBox2.getChildren().addAll(createToDoPane(), btnBox2);

		HBox btnBox3 = new HBox();
		btnBox3.getChildren().addAll(connectBtn, pingBtn);
		VBox vBox3 = new VBox();
		vBox3.getChildren().addAll(createConnectPane(), btnBox3);

		tab1.setClosable(false);
		tab2.setClosable(false);
		tab3.setClosable(false);
		tab1.setContent(vBox1);
		tab2.setContent(vBox2);
		tab3.setContent(vBox3);
		tabPane.getTabs().addAll(tab1, tab2, tab3);
		HBox statusBox = new HBox();
		statusBox.getChildren().add(statusLbl);
		VBox mainWindow = new VBox();
		mainWindow.getChildren().add(tabPane);
		mainWindow.getChildren().add(statusBox);
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

		return connectPane;
	}

	private Pane createToDoPane() {
		GridPane toDoPane = new GridPane();
		toDoPane.add(titleLbl, 0, 0);
		toDoPane.add(priorityLbl, 0, 1);
		toDoPane.add(dateLbl, 0, 2);
		toDoPane.add(titleTxt, 1, 0);
		toDoPane.add(priorityTxt, 1, 1);
		toDoPane.add(dueDateTxt, 1, 2);
		toDoPane.setHalignment(descriptionLbl, HPos.LEFT);
		toDoPane.setValignment(descriptionLbl, VPos.TOP);
		toDoPane.add(descriptionLbl, 0, 3);
		toDoPane.add(descriptionTxt, 1, 3);

		return toDoPane;
	}

	public Pane createAccountPane() {
		GridPane createAccountPane = new GridPane();
		createAccountPane.add(createUserLbl, 0, 0);
		createAccountPane.add(createPasswordLbl, 0, 1);
		createAccountPane.add(createUserTxt, 1, 0);
		createAccountPane.add(createPasswordTxt, 1, 1);
		return createAccountPane;
	}

}
