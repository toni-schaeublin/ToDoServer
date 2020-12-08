package client;

public class ClientController {
	private final ClientModel model;
	private final ClientView view;

	protected ClientController(ClientModel model, ClientView view) {
		this.model = model;
		this.view = view;
	}

}
