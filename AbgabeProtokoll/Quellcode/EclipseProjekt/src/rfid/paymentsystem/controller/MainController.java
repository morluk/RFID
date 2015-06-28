package rfid.paymentsystem.controller;

import java.awt.EventQueue;

import rfid.paymentsystem.view.MainFrame;

public class MainController {

	public MainController() {
		initGUI();
	}

	private void initGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = MainFrame.getInstance();
					// frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
