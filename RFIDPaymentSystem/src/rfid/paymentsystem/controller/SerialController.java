package rfid.paymentsystem.controller;

import jssc.SerialPort;
import jssc.SerialPortException;
import rfid.paymentsystem.view.MainFrame;

public class SerialController {

	private int baudRate, stopBit, parityBit, databits, counter;

	private static int delay;

	private String device, readTag;

	private boolean connected;

	static SerialPort serialPort;

	private static SerialController serialController;
	

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
		delay = 2000;
		device = "/dev/ttyUSB0";
		connected = false;
		readTag = "xxx";
	}

	public int getCounter() {
		return counter;
	}

	public synchronized String getReadTag() {
		return readTag;
	}

	/**
	 * Sets ReadTag Member and publishes it to MainView
	 * @param readTag
	 */
	public synchronized void setReadTag(String readTag) {
		if (this.readTag.equals(readTag)) {
			counter++;
		} else {
			counter = 0;
			this.readTag = readTag;
			MainFrame.getInstance().publishTag(readTag);
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
//		serialPort = new SerialPort(device);
//		try {
//			System.out.println("Port opened: " + serialPort.openPort());
//			System.out.println("Params set: "
//					+ serialPort.setParams(baudRate, databits, stopBit,
//							parityBit));
//		} catch (SerialPortException ex) {
//			System.out.println(ex);
//		}
		// TODO: check ReadersID - Setup reader
		connected = true;
		(new Thread(new SerialRunner(serialPort))).start();
		return connected;
	}

	public boolean close() {
//		try {
//			System.out.println("Port closed: " + serialPort.closePort());
//		} catch (SerialPortException e) {
//			e.printStackTrace();
//		}
		readTag = "xxx";
		connected = false;
		return connected;
	}

	/**
	 * This thread is supposed to run in endless loop until connection closes.
	 * Delays between reads and writes are handled within this function. Read
	 * tags are submitted to SerialController
	 * 
	 * @author moritz
	 * 
	 */
	public static class SerialRunner implements Runnable {
		private SerialPort serialPort;

		SerialRunner(SerialPort ser) {
			this.serialPort = ser;
		}

		@Override
		public void run() {
//			while (serialPort.isOpened()) {
//				try {
//					System.out
//							.println("\"Hello Serial!!!\" successfully writen to port: "
//									+ serialPort.writeBytes("Hello Serial!!!"
//											.getBytes()));
//					Thread.sleep(2 * delay);
//					byte[] buffer = serialPort.readBytes();
//					if (buffer != null) {
//						System.out.println("Read from Serialport: "
//								+ new String(buffer));
//						SerialController.getInstance().setReadTag(
//								new String(buffer));
//						Thread.sleep(delay);
//					}
//				} catch (SerialPortException | InterruptedException ex) {
//					System.out.println(ex);
//				}
//			}
			while (SerialController.getInstance().isConnected()) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SerialController.getInstance().setReadTag("moritzTagId");
			}
		}
	}
}
