package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.event.ActionEvent;

public class ClientController {
	private final ClientModel model;
	private final ClientView view;
	private String userName;
	private Boolean userNameValid = false;
	private String password;
	private Boolean passwordValid = false;
	private String message = "";
	private String ipAddress = "";
	private Boolean ipAddressValid = false;
	private String portNumber = "";
	private Boolean portNumberValid = false;
	private String answer = null;

	protected ClientController(ClientModel model, ClientView view) {
		this.model = model;
		this.view = view;

		view.createUserTxt.textProperty().addListener((observable, oldValue, newValue) -> {
			this.userNameValid = false;
			this.userName = newValue;
			if (Checker.checkEmail(this.userName)) {
				this.userNameValid = true;
			}
		});
		view.createPasswordTxt.textProperty().addListener((observable, oldValue, newValue) -> {
			this.passwordValid = false;
			this.password = newValue;
			if (Checker.checkPassword(this.password)) {
				this.passwordValid = true;
			}
		});

		view.ipTxt.textProperty().addListener((observable, oldValue, newValue) -> {
			this.ipAddressValid = false;
			this.ipAddress = newValue;
			if (Checker.checkIP(ipAddress)) {
				this.ipAddressValid = true;
			}
		});

		view.portTxt.textProperty().addListener((observable, oldValue, newValue) -> {
			this.portNumberValid = false;
			this.portNumber = newValue;
			if (Checker.checkPort(portNumber)) {
				this.portNumberValid = true;
			}
		});

		view.createUserBtn.setOnAction(this::addUser);
		view.connectBtn.setOnAction(this::connectToServer);
		view.pingBtn.setOnAction(this::ping);

	}

	public void addUser(ActionEvent e) {
		if (userNameValid && passwordValid) {
			message = "CreateLogin" + "|" + userName + "|" + password;
			view.statusLbl.setText("Account erstellt");
			System.out.println(message);
		} else {
			if (!userNameValid) {
				view.statusLbl.setText("Die E-Mailadresse entspricht nicht den Vorgaben!");
			} else {
				view.statusLbl.setText("Das Passwort entspricht nicht den Vorgaben! (3-20 Zeichen)");
			}
		}

	}
	
	public void ping(ActionEvent e) {
		message="ping";
	
		
	}
	
	

	// Stellt die Verbindung zum Server her
	public void connectToServer(ActionEvent e) {
		if (ipAddressValid && portNumberValid) {
			view.statusLbl.setText("Port und IP gültig!");
			ipAddress = view.ipTxt.getText();
			int integerPortNumber = Integer.parseInt(view.portTxt.getText());
			try (Socket socket = new Socket(ipAddress, integerPortNumber);
					BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					OutputStreamWriter socketOut = new OutputStreamWriter(socket.getOutputStream())) {
				view.statusLbl.setText("Verbunden");
				Runnable r = new Runnable() {
					@Override
					public void run() {

						while (answer != null) {
							try {
								answer = socketIn.readLine();
							} catch (IOException e) {
								answer = null;
							}
						}
					}
				};
				
				
				Thread t = new Thread(r);
				t.start();
				
				
				
				
				
				
				
				
				
				
				
				
				socketOut.write(message);
				socketOut.flush();
			} catch (IOException ex) {
				view.statusLbl.setText("Da ist etwas schief gelaufen");

			}

		} else {
			if (ipAddressValid) {
				view.statusLbl.setText("Portnummer ungültig!");
			} else {
				view.statusLbl.setText("IP-Adresse ungültig!");

			}
		}

	}
}
