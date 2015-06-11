package rfid.paymentsystem.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class mainView extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField txtId, txtName, txtAmount;
	private JLabel lblCustomerName, lblAmount, lblSuccessMessage;
	private JPanel panel, panel_1, panel_2;
	private JButton btnPay, btnRecharge;
	private JMenuBar menuBar;
	private JMenu mnFile, mnEdit;
	private JMenuItem mntmOpenConnection, mntmIsConnected, mntmCloseConnection, mntmExit,
			mntmSettings;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainView frame = new mainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainView() {
		initGui();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				closeOperation();
			}
		});
	}

	private void initGui() {
		setTitle("Cash Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblCustomerId = new JLabel("Customer ID");
		panel.add(lblCustomerId);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		panel.add(txtId);
		txtId.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);
		
		lblCustomerName = new JLabel("Name");
		panel.add(lblCustomerName);
		
		txtName = new JTextField();
		txtName.setEditable(false);
		panel.add(txtName);
		txtName.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 20, 0, 20));
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		panel_1.add(verticalGlue_1);
		
		lblAmount = new JLabel("Amount");
		lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblAmount);
		
		txtAmount = new JTextField();
		txtAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		txtAmount.setBorder(new EmptyBorder(5, 5, 5, 5));
		txtAmount.setMaximumSize(new Dimension(2147483647, 19));
		panel_1.add(txtAmount);
		txtAmount.setColumns(10);
		
		Component verticalGlue = Box.createVerticalGlue();
		panel_1.add(verticalGlue);
		
		panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnPay = new JButton("Make Payment [-]");
		panel_2.add(btnPay);
		
		btnRecharge = new JButton("Make Deposit [+]");
		panel_2.add(btnRecharge);
		btnRecharge.addActionListener(this);
		
		Component verticalGlue_2 = Box.createVerticalGlue();
		panel_1.add(verticalGlue_2);
		
		lblSuccessMessage = new JLabel("Success Message");
		lblSuccessMessage.setPreferredSize(new Dimension(125, 45));
		lblSuccessMessage.setMinimumSize(new Dimension(125, 45));
		lblSuccessMessage.setOpaque(true);
		lblSuccessMessage.setBackground(Color.RED);
		lblSuccessMessage.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblSuccessMessage, BorderLayout.SOUTH);
		
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

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(this);
		mnFile.add(mntmExit);

		mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		mntmSettings = new JMenuItem("Settings");
		mntmSettings.addActionListener(this);
		mnEdit.add(mntmSettings);
	}
	
	public void closeOperation() {
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPay) {

		}
		if (e.getSource() == btnRecharge) {
			
		}
		if (e.getSource() == mntmOpenConnection) {

		}
		if (e.getSource() == mntmCloseConnection) {

		}
		if (e.getSource() == mntmIsConnected) {

		}
		if (e.getSource() == mntmExit) {
			closeOperation();
		}
		if (e.getSource() == mntmSettings) {
			Settings dialog = new Settings();
			dialog.setLocationRelativeTo(this);
			dialog.setVisible(true);
		}
	}

}
