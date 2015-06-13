package rfid.paymentsystem.controller;

import java.awt.EventQueue;

import rfid.paymentsystem.view.MainView;

public class MainController {

	private UserController userController;

	private ValueController valueController;
	
	private SerialController serialController;

	public MainController() {
		userController = UserController.getInstance();
		valueController = ValueController.getInstance();
		serialController = SerialController.getInstance();
		//TODO: Soll MainView Referrenz auf MainController/UserCtr/ValueCtr bekommen?!
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					//frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
