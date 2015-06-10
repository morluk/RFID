package rfid.paymentsystem.controller;

public class MainController {

	private UserController userController;

	private ValueController valueController;

	public MainController() {
		userController = UserController.getInstance();
		valueController = ValueController.getInstance();
	}
}
