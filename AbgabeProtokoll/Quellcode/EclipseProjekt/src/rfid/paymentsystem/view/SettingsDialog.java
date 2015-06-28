package rfid.paymentsystem.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import rfid.paymentsystem.controller.SerialController;

public class SettingsDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 3354223037760087885L;

	private final JPanel contentPanel = new JPanel();

	private JTextField deviceText;

	private JTextField baudText;

	private JTextField stopText;

	private JTextField dataText;

	private JTextField parityText;

	private JTextField delayText;

	private JButton cancelButton, okButton;

	private SerialController serialConnection;

	/**
	 * Create the dialog.
	 */
	public SettingsDialog() {
		this.serialConnection = SerialController.getInstance();
		initLayout();
		deviceText.setText(serialConnection.getDevice());
		baudText.setText(new Integer(serialConnection.getBaudRate()).toString());
		stopText.setText(new Integer(serialConnection.getStopBit()).toString());
		dataText.setText(new Integer(serialConnection.getDatabits()).toString());
		parityText.setText(new Integer(serialConnection.getParityBit())
				.toString());
		delayText.setText(new Integer(serialConnection.getDelay()).toString());
	}

	private void initLayout() {
		setTitle("Settings");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 30, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 50, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblDevice = new JLabel("Device");
			GridBagConstraints gbc_lblDevice = new GridBagConstraints();
			gbc_lblDevice.insets = new Insets(0, 0, 5, 5);
			gbc_lblDevice.gridx = 0;
			gbc_lblDevice.gridy = 0;
			contentPanel.add(lblDevice, gbc_lblDevice);
		}
		{
			deviceText = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 0);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 2;
			gbc_textField.gridy = 0;
			contentPanel.add(deviceText, gbc_textField);
			deviceText.setColumns(10);
		}
		{
			JLabel lblBaudrate = new JLabel("Baudrate");
			GridBagConstraints gbc_lblBaudrate = new GridBagConstraints();
			gbc_lblBaudrate.insets = new Insets(0, 0, 5, 5);
			gbc_lblBaudrate.gridx = 0;
			gbc_lblBaudrate.gridy = 1;
			contentPanel.add(lblBaudrate, gbc_lblBaudrate);
		}
		{
			baudText = new JTextField();
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(0, 0, 5, 0);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 2;
			gbc_textField_1.gridy = 1;
			contentPanel.add(baudText, gbc_textField_1);
			baudText.setColumns(10);
		}
		{
			JLabel lblDatabits = new JLabel("Databits");
			GridBagConstraints gbc_lblDatabits = new GridBagConstraints();
			gbc_lblDatabits.insets = new Insets(0, 0, 5, 5);
			gbc_lblDatabits.gridx = 0;
			gbc_lblDatabits.gridy = 2;
			contentPanel.add(lblDatabits, gbc_lblDatabits);
		}
		{
			dataText = new JTextField();
			GridBagConstraints gbc_textField_3 = new GridBagConstraints();
			gbc_textField_3.insets = new Insets(0, 0, 5, 0);
			gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_3.gridx = 2;
			gbc_textField_3.gridy = 2;
			contentPanel.add(dataText, gbc_textField_3);
			dataText.setColumns(10);
		}
		{
			JLabel lblStopbit = new JLabel("Stopbit");
			GridBagConstraints gbc_lblStopbit = new GridBagConstraints();
			gbc_lblStopbit.insets = new Insets(0, 0, 5, 5);
			gbc_lblStopbit.gridx = 0;
			gbc_lblStopbit.gridy = 3;
			contentPanel.add(lblStopbit, gbc_lblStopbit);
		}
		{
			stopText = new JTextField();
			GridBagConstraints gbc_textField_2 = new GridBagConstraints();
			gbc_textField_2.insets = new Insets(0, 0, 5, 0);
			gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_2.gridx = 2;
			gbc_textField_2.gridy = 3;
			contentPanel.add(stopText, gbc_textField_2);
			stopText.setColumns(10);
		}
		{
			JLabel lblParity = new JLabel("Parity");
			GridBagConstraints gbc_lblParity = new GridBagConstraints();
			gbc_lblParity.insets = new Insets(0, 0, 5, 5);
			gbc_lblParity.gridx = 0;
			gbc_lblParity.gridy = 4;
			contentPanel.add(lblParity, gbc_lblParity);
		}
		{
			parityText = new JTextField();
			GridBagConstraints gbc_textField_4 = new GridBagConstraints();
			gbc_textField_4.insets = new Insets(0, 0, 5, 0);
			gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_4.gridx = 2;
			gbc_textField_4.gridy = 4;
			contentPanel.add(parityText, gbc_textField_4);
			parityText.setColumns(10);
		}
		{
			JLabel lblDelay = new JLabel("Delay");
			GridBagConstraints gbc_lblDelay = new GridBagConstraints();
			gbc_lblDelay.insets = new Insets(0, 0, 0, 5);
			gbc_lblDelay.gridx = 0;
			gbc_lblDelay.gridy = 6;
			contentPanel.add(lblDelay, gbc_lblDelay);
		}
		{
			delayText = new JTextField();
			GridBagConstraints gbc_textField_5 = new GridBagConstraints();
			gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_5.gridx = 2;
			gbc_textField_5.gridy = 6;
			contentPanel.add(delayText, gbc_textField_5);
			delayText.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == okButton) {
			serialConnection.setBaudRate(Integer.parseInt(baudText.getText()));
			serialConnection.setDatabits(Integer.parseInt(dataText.getText()));
			serialConnection.setDelay(Integer.parseInt(delayText.getText()));
			serialConnection.setDevice(deviceText.getText());
			serialConnection
					.setParityBit(Integer.parseInt(parityText.getText()));
			serialConnection.setStopBit(Integer.parseInt(stopText.getText()));
			setVisible(false);
			dispose();
		}
		if (arg0.getSource() == cancelButton) {
			setVisible(false);
			dispose();
		}
	}

}
