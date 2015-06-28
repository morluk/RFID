package rfid.paymentsystem.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jssc.SerialPort;
import jssc.SerialPortException;
import rfid.paymentsystem.view.MainFrame;

/**
 * Controlls handling of SerialPort. Uses jSSC Library and can be used under Win/Linux/Mac.
 * @author moritz
 *
 */
public class SerialController {

	private int baudRate, stopBit, parityBit, databits, counter;

	private static int delay;

	private String device, readTag;

	private boolean connected;

	static SerialPort serialPort;

	private static SerialController serialController;
	/* Key:Reader Command - Value:Expected Reader Response */
	private Map<String, String> setupCmd, readCmd;

	public synchronized static SerialController getInstance() {
		if (SerialController.serialController == null) {
			SerialController.serialController = new SerialController();
		}
		return serialController;
	}

	private SerialController() {
		baudRate = 9600;
		stopBit = 1;
		parityBit = 0;
		databits = 8;
		delay = 300;
		device = "/dev/ttyUSB0";
		connected = false;
		readTag = "xxx";
		readConfig("configFile");
	}

	/**
	 * Reads congFile with specific RFIDReader commands.
	 * 
	 * @param file
	 */
	private void readConfig(String file) {
		setupCmd = new HashMap<String, String>();
		readCmd = new HashMap<String, String>();
		File inputFile = new File(file);

		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(inputFile));
			String line;

			while ((line = in.readLine()) != null) {
				if (line.contains("#SETUP READER")) {
					line = in.readLine();
					while (!line.contains("#READ CMD")) {
						String[] lineSplitted = line.split(";");
						setupCmd.put(lineSplitted[0], lineSplitted[1]);
						line = in.readLine();
					}
				} else {
					String[] lineSplitted = line.split(";");
					readCmd.put(lineSplitted[0], lineSplitted[1]);
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getCounter() {
		return counter;
	}

	public synchronized String getReadTag() {
		return readTag;
	}

	/**
	 * Sets ReadTag Member and publishes it to MainView
	 * 
	 * @param readTag
	 */
	public synchronized void setReadTag(String readTag) {
		String readTagTmp = readTag.substring(readTag.indexOf(' ')+1,readTag.indexOf(' ')+5);
		if (this.readTag.equals(readTagTmp)) {
			MainFrame.getInstance().publishCounter(++counter);
		} else {
			counter = 1;
			this.readTag = readTagTmp;
			/* use ONLY first 4 chars from ID without Header <OK0057 AFFE0042474C7A980000000000000000000000000000000000000000> -> <AFFE> */
			MainFrame.getInstance().publishTag(readTagTmp);
		}
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	public int getStopBit() {
		return stopBit;
	}

	public void setStopBit(int stopBit) {
		this.stopBit = stopBit;
	}

	public int getParityBit() {
		return parityBit;
	}

	public void setParityBit(int parityBit) {
		this.parityBit = parityBit;
	}

	public int getDatabits() {
		return databits;
	}

	public void setDatabits(int databits) {
		this.databits = databits;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		SerialController.delay = delay;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public boolean isConnected() {
		return connected;
	}

	/**
	 * Setup connection with all params and check if you can read ReadersId.
	 * Setup reader. And start Reader Thread
	 * 
	 * @return
	 */
	public boolean connect() {
		serialPort = new SerialPort(device);
		try {
			System.out.println("Port opened: " + serialPort.openPort());
			System.out.println("Params set: "
					+ serialPort.setParams(baudRate, databits, stopBit,
							parityBit));
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
		/* check ReadersID - Setup reader */
		for (Entry<String, String> key : setupCmd.entrySet()) {
			ioSerial(key.getKey(), key.getValue(), false);
		}
		connected = true;
		(new Thread(new SerialRunner(serialPort, readCmd))).start();
		return connected;
	}

	public boolean close() {
		try {
			System.out.println("Port closed: " + serialPort.closePort());
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
		readTag = "xxx";
		connected = false;
		return connected;
	}

	/**
	 * Writes cmd to SerialPort. Reads response from SerialPort and checks if it
	 * matches result. If setTag = true, setTag() will be called. -> configCmds
	 * dont need to be setted as tags.
	 * 
	 * @param cmd
	 * @param result
	 * @param callGui
	 */
	private static void ioSerial(String cmd, String result, boolean setTag) {
		try {
			System.out.println("\"" + cmd + "\" successfully writen to port: "
					+ serialPort.writeBytes((cmd + "\r\n").getBytes()));
			Thread.sleep(delay);
			byte[] buffer = serialPort.readBytes();
			if (buffer != null) {
				String response = new String(buffer);
				System.out.println("Read from Serialport: " + response);
				/*Check Response and inform Gui*/
				if (!response.startsWith(result)) {
					System.out.println("Error from Reader.");
					if (setTag) {
						SerialController.getInstance().setReadTag(
								"No Tag Present.");
					} else {
						SerialController.getInstance().setReadTag(
								"Error from Reader.");
					}
				}
				if (setTag) {
					SerialController.getInstance().setReadTag(
							response);
				}
			}
		} catch (SerialPortException | InterruptedException ex) {
			System.out.println(ex);
		}
	}

	/**
	 * This thread is supposed to run in endless loop until connection closes.
	 * calls ioSerial() with all readCmd entries.
	 * 
	 * @author moritz
	 * 
	 */
	public static class SerialRunner implements Runnable {

		private SerialPort serialPort;

		private Map<String, String> readCmd;

		SerialRunner(SerialPort ser, Map<String, String> readCmd) {
			this.serialPort = ser;
			this.readCmd = readCmd;
		}

		@Override
		public void run() {
			while (serialPort.isOpened()) {
				for (Entry<String, String> key : readCmd.entrySet()) {
					ioSerial(key.getKey(), key.getValue(), true);
				}
			}
			/* Debug */
			// while (SerialController.getInstance().isConnected()) {
			// try {
			// Thread.sleep(delay);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// SerialController.getInstance().setReadTag("moritzTagId");
			// }
		}
	}
}
