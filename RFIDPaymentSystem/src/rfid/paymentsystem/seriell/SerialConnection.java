package rfid.paymentsystem.seriell;


public class SerialConnection {
	
	private int baudRate, stopBit, parityBit, databits, delay;
	private String device;
	private boolean connected;
	
	public SerialConnection() {
		baudRate = 9600;
		stopBit = 1;
		parityBit = 0;
		databits = 8;
		delay = 2000;
		device = "/dev/ttyUSB0";
		connected = false;
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
		this.delay = delay;
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
	 * Setup reader.
	 * 
	 * @return
	 */
	public boolean connect() {
		connected = true;
		return connected;
	}

	public boolean close() {
		connected = false;
		return connected;
	}

	/**
	 * This function is supposed to run in endless loop. Delays between reads
	 * and writes are handled within this function.
	 * 
	 * @return > 0 if successful and else -1
	 */
	public int readTag() {
		return 12345;
	}
}
