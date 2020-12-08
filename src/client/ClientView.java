package client;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
	private Label loginUserlbl = new Label("Bitte geben sie ihren Benutzernamen ein:");
	private TextField loginUserTxt = new TextField();
	private Label loginPasswordLbl = new Label("Bitte geben sie ihr Passwort ein:");
	private TextField loginPasswordTxt = new TextField();
	private Button loginBtn = new Button("Login");
	private GridPane accountGrid = new GridPane();

	protected ClientView(Stage stage, ClientModel model) {
		this.stage = stage;
		this.model = model;
		stage.setTitle("Client");
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("To Do", new Label("Work with to do`s"));
		Tab tab2 = new Tab("Account", new Label("Create an account or login"));
		Tab tab3 = new Tab("Connect", new Label("Connect with a server"));
		tab1.setClosable(false);
		tab2.setClosable(false);
		tab3.setClosable(false);
		tab1.setContent(createToDoGrid());
		tab2.setContent(createAccountGrid());
		tab3.setContent(createConnectGrid());

		tabPane.getTabs().addAll(tab1, tab2, tab3);
		VBox mainWindow = new VBox();
		mainWindow.getChildren().add(tabPane);

		Scene scene = new Scene(mainWindow);
		// scene.getStylesheets().add(getClass().getResource("ClientMVC.css").toExternalForm());
		stage.setScene(scene);
	}

	public void start() {
		stage.show();
	}

	// getter
	public Stage getStage() {
		return stage;
	}

	private Pane createConnectGrid() {
		GridPane connectGrid = new GridPane();

		return connectGrid;
	}

	private Pane createToDoGrid() {
		GridPane toDoGrid = new GridPane();

		return toDoGrid;
	}

	public Pane createAccountGrid() {
		GridPane accountGrid = new GridPane();
		accountGrid.add(createUserLbl, 0, 0);
		accountGrid.add(createUserTxt, 1, 0);
		accountGrid.add(createPasswordLbl, 0, 1);
		accountGrid.add(createPasswordTxt, 1, 1);
		accountGrid.add(createUserBtn, 2, 1);
		return accountGrid;
	}

}
