package rfid;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

public class SerialConnection implements SerialPortEventListener {

	private int baudRate, stopBit, parityBit, databits, delay;
	private String device;
	private boolean connected;
	private SerialConnection_iface contact;
	private SerialPort serialPort;
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;

	public SerialConnection(SerialConnection_iface contact) {
		this.contact = contact;
		baudRate = 9600;
		stopBit = SerialPort.STOPBITS_1;
		parityBit = SerialPort.PARITY_NONE;
		databits = SerialPort.DATABITS_8;
		delay = 2000;
		device = "/dev/ttyACM0";
		connected = false;
	}

	public boolean isConnected() {
		return connected;
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
		switch (stopBit) {
		case 1:
			this.stopBit = SerialPort.STOPBITS_1;
			break;
		case 2:
			this.stopBit = SerialPort.STOPBITS_2;
			break;
		case 3:
			this.stopBit = SerialPort.STOPBITS_1_5;
			break;
		default:
			this.stopBit = SerialPort.STOPBITS_1;
			break;
		}
	}

	public int getParityBit() {
		return parityBit;
	}

	public void setParityBit(int parityBit) {
		switch (parityBit) {
		case 0:
			this.parityBit = SerialPort.PARITY_NONE;
			break;
		case 1:
			this.parityBit = SerialPort.PARITY_ODD;
			break;
		case 2:
			this.parityBit = SerialPort.PARITY_EVEN;
			break;
		default:
			this.parityBit = SerialPort.PARITY_NONE;
			break;
		}
	}

	public int getDatabits() {
		return databits;
	}

	public void setDatabits(int databits) {
		switch (databits) {
		case 5:
			this.databits = SerialPort.DATABITS_5;
			break;
		case 6:
			this.databits = SerialPort.DATABITS_6;
			break;
		case 7:
			this.databits = SerialPort.DATABITS_7;
			break;
		case 8:
			this.databits = SerialPort.DATABITS_8;
			break;
		default:
			this.databits = SerialPort.DATABITS_8;
			break;
		}
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public boolean connect() throws Exception {
		boolean result = false;
		if (!connected) {
			// the next line is for Raspberry Pi and
			// gets us into the while loop and was suggested here was suggested
			// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
			System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

			CommPortIdentifier portId = null;
			Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

			// First, Find an instance of serial port as set in PORT_NAMES.
			while (portEnum.hasMoreElements()) {
				CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
						.nextElement();
				if (currPortId.getName().equals(device)) {
					portId = currPortId;
					break;
				}
			}

			if (portId == null) {
				contact.writeLog("Could not find COM port.");
				System.out.println("Could not find COM port.");
				return result;
			}
			if (portId.isCurrentlyOwned()) {
				contact.writeLog("COM Port is currently owned.");
				System.out.println("COM Port is currently owned.");
				return result;
			}
			CommPort commPort = portId
					.open(this.getClass().getName(), TIME_OUT);

			if (commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(baudRate, databits, stopBit,
						parityBit);
				// open streams
				input = new BufferedReader(new InputStreamReader(
						serialPort.getInputStream()));
				output = serialPort.getOutputStream();

				serialPort.addEventListener(this);
				serialPort.notifyOnDataAvailable(true);
				result = true;
			} else {
				contact.writeLog("Error: Only serial ports are handled by this example.");
				System.out
						.println("Error: Only serial ports are handled by this example.");
			}
			connected = true;
		}
		return result;
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public boolean close() throws Exception {
		if (connected) {
			// output.close();
			// input.close();
			serialPort.removeEventListener();
			serialPort.close();
			connected = false;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				/*callback fct*/
				contact.parseInput(inputLine);
			} catch (Exception e) {
				contact.writeLog(e.toString());
				e.printStackTrace();
			}
		}
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
	}

	public boolean writeSerial(String message) throws Exception {
		boolean success = false;
		if (connected) {
			output.write(message.getBytes());
			success = true;
		}
		return success;
	}
}
