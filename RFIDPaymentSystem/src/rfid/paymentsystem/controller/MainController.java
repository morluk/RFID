package rfid.paymentsystem.controller;

import java.awt.EventQueue;

import rfid.paymentsystem.view.MainView;

public class MainController {

	public MainController() {
		initGUI();
	}

	private void initGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					// frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
