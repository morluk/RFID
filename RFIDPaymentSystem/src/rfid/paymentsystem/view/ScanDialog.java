package rfid.paymentsystem.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import rfid.paymentsystem.controller.UserController;
import rfid.paymentsystem.model.User;

public class ScanDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 5645801402508358382L;

	private final JPanel contentPanel = new JPanel();

	private JButton cancelButton;

	private JButton okButton;

	private JTextArea txtArea;

	private double sum;

	private MainFrame mainFrame;

	private String userTagId;

	public ScanDialog(MainFrame mainFrame, String userTagId) {
		sum = 0;
		this.mainFrame = mainFrame;
		this.userTagId = userTagId;
		initGUI();
		txtArea.setText("0");
	}

	private void initGUI() {
		setTitle("Scanning...");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblPleaseScanValue = new JLabel(
					"Please Scan Value Tags and press OK");
			contentPanel.add(lblPleaseScanValue, BorderLayout.NORTH);
		}
		{
			txtArea = new JTextArea();
			JScrollPane scrollPane = new JScrollPane(txtArea);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(this);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}

	public void addValueTag(double value) {
		sum += value;
		txtArea.append("\n+ " + value + " = " + sum);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			setVisible(false);
			dispose();
		}
		if (e.getSource() == okButton) {
			User user = UserController.getInstance().getUserByTagId(userTagId);
			user.addTransaction("deposit", sum);
			setVisible(false);
			dispose();
			mainFrame.refreshBalance();
		}
	}

}
