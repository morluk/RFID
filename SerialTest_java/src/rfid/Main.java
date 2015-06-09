package rfid;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener,
		SerialConnection_iface {

	private JPanel contentPane;
	private JTextField inputTextField, outputTextField;
	private JMenuBar menuBar;
	private JMenu mnFile, mnEdit;
	private JMenuItem mntmOpenConnection, mntmIsConnected, mntmWriteData,
			mntmReadData, mntmCustomProgram, mntmCloseConnection, mntmExit,
			mntmSettings;
	private JButton btnSend;
	private JPanel panel, panel_1;
	private Component verticalGlue_1;
	private SerialConnection serialConnection;
	private final Semaphore sem = new Semaphore(0);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * CTOR
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 422, 453);

		serialConnection = new SerialConnection(this);

		initLayout();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				closeOperation();
			}
		});
	}

	private void initLayout() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmOpenConnection = new JMenuItem("Open Connection");
		mntmOpenConnection.addActionListener(this);
		mnFile.add(mntmOpenConnection);

		mntmCloseConnection = new JMenuItem("Close Connection");
		mntmCloseConnection.addActionListener(this);
		mnFile.add(mntmCloseConnection);

		mntmIsConnected = new JMenuItem("Is Connected");
		mntmIsConnected.addActionListener(this);
		mnFile.add(mntmIsConnected);

		mntmWriteData = new JMenuItem("Write Data");
		mntmWriteData.addActionListener(this);
		mnFile.add(mntmWriteData);

		mntmReadData = new JMenuItem("Read Data");
		mntmReadData.addActionListener(this);
		mnFile.add(mntmReadData);

		mntmCustomProgram = new JMenuItem("Custom Program");
		mntmCustomProgram.addActionListener(this);
		mnFile.add(mntmCustomProgram);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(this);
		mnFile.add(mntmExit);

		mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		mntmSettings = new JMenuItem("Settings");
		mntmSettings.addActionListener(this);
		mnEdit.add(mntmSettings);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblSerialOutput = new JLabel("Write to Serial");
		panel.add(lblSerialOutput);

		outputTextField = new JTextField();
		panel.add(outputTextField);
		outputTextField.setColumns(30);

		btnSend = new JButton("Send");
		btnSend.addActionListener(this);
		panel.add(btnSend);

		Component verticalGlue = Box.createVerticalGlue();
		contentPane.add(verticalGlue);

		panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblSerialInput = new JLabel("Read from Serial");
		panel_1.add(lblSerialInput);

		inputTextField = new JTextField();
		panel_1.add(inputTextField);
		inputTextField.setEditable(false);
		inputTextField.setColumns(30);

		verticalGlue_1 = Box.createVerticalGlue();
		contentPane.add(verticalGlue_1);
	}

	/**
	 * ActionListener
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnSend) {
			writeData();
		}
		if (arg0.getSource() == mntmOpenConnection) {
			try {
				if (serialConnection.connect()) {
					JOptionPane.showMessageDialog(this,
							"Connection established.");
				}
			} catch (Exception e) {
				this.writeLog(e.getMessage());
				e.printStackTrace();
			}
		}
		if (arg0.getSource() == mntmCloseConnection) {
			try {
				if (serialConnection.close()) {
					JOptionPane.showMessageDialog(this, "Connection closed.");
				}
			} catch (Exception e) {
				this.writeLog(e.getMessage());
				e.printStackTrace();
			}
		}
		if (arg0.getSource() == mntmIsConnected) {
			if (serialConnection.isConnected()) {
				JOptionPane.showMessageDialog(this, "Connection open.");
			} else {
				JOptionPane.showMessageDialog(this, "Connection closed.");
			}
		}
		if (arg0.getSource() == mntmWriteData) {
			writeData();
		}
		if (arg0.getSource() == mntmReadData) {
			JOptionPane.showMessageDialog(this, "No Polling please.");
		}
		if (arg0.getSource() == mntmCustomProgram) {
			customProgram();
		}
		if (arg0.getSource() == mntmExit) {
			closeOperation();
		}
		if (arg0.getSource() == mntmSettings) {
			Settings dialog = new Settings(serialConnection);
			dialog.setLocationRelativeTo(this);
			dialog.setVisible(true);
		}
	}

	private void customProgram() {
		/* eventuell als eigener Thread weil er auf Antwort warten muss */
		(new Thread(new customProgThread())).start();
	}

	public class customProgThread implements Runnable {

		public customProgThread() {
		}

		public void run() {
			try {
				/* init routine */
				serialConnection.connect();
				/* set UART Parameters for RFID Chip */
				serialConnection.writeSerial("Hello UART.");
				sem.acquire(); // lock sem - wait for parseInput() to release
				/* check response */

				/* read readers id */
				serialConnection.writeSerial("UART Hello again.");
				sem.acquire(); // lock sem - wait for parseInput() to release
				/* check response */

				/* logic routine */
				/* check if chip is in area OR read now OR write now */

				/* check response */

				// Thread.sleep(3000);
				/* closing routine */
				// serialConnection.close();
				// JOptionPane.showMessageDialog(this, "Programm finished.");

			} catch (Exception e) {
				writeLog(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void writeData() {
		try {
			if (serialConnection.writeSerial(outputTextField.getText())) {
				outputTextField.setText("");
				JOptionPane.showMessageDialog(this, "Message sent.");
			}
		} catch (Exception e) {
			this.writeLog(e.getMessage());
			e.printStackTrace();
		}
	}

	public void closeOperation() {
		if (serialConnection.isConnected()) {
			try {
				serialConnection.close();
			} catch (Exception e) {
				this.writeLog(e.getMessage());
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

	/**
	 * this cold emulate middleware and filter input (if Tag ID hasnt changed)
	 */
	@Override
	public void parseInput(String msg) {
		inputTextField.setText("");
		inputTextField.setText(msg);
		// falls send von GUI direkt aufgerufen wurde
		if (sem.availablePermits() > 0)
			return;
		// ansonsten kommuniziere mit customThread
		sem.release();// release sem
	}

	@Override
	public void writeLog(String text) {
		JOptionPane.showMessageDialog(this, text);
	}
}
