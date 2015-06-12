package jSSC_example;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialTest {
	static SerialPort serialPort;

	public static void main(String[] args) {
		serialPort = new SerialPort("/dev/ttyACM1");
		try {
			System.out.println("Port opened: " + serialPort.openPort());
			System.out.println("Params set: "
					+ serialPort.setParams(9600, 8, 1, 0));
			/* Ohne Threads */
			for (int i = 0; i < 20; i++) {
				System.out
						.println("\"Hello Serial!!!\" successfully writen to port: "
								+ serialPort.writeBytes("Hello Serial!!!"
										.getBytes()));
				Thread.sleep(4000);
				byte[] buffer = serialPort.readBytes();
				if (buffer != null) {
					System.out.println("Read from Serialport: "
							+ new String(buffer));
					Thread.sleep(2000);
				}
			}

			/* Mit Threads */
			// Thread t1 = new Thread(new SerialWriter(serialPort));
			// t1.start();
			// Thread t2 = new Thread(new SerialReader(serialPort));
			// t2.start();
			//
			// t1.join();
			// t2.join();

			System.out.println("Port closed: " + serialPort.closePort());
		} catch (SerialPortException | InterruptedException ex) {
			System.out.println(ex);
		}
	}

	public static class SerialWriter implements Runnable {
		private SerialPort serialPort;

		SerialWriter(SerialPort ser) {
			this.serialPort = ser;
		}

		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(5000);
					System.out
							.println("\"Hello Serial!!!\" successfully writen to port: "
									+ serialPort.writeBytes("Hello Serial!!!"
											.getBytes()));
				}
			} catch (SerialPortException | InterruptedException ex) {
				System.out.println(ex);
			}
		}
	}

	public static class SerialReader implements Runnable {
		private SerialPort serialPort;

		SerialReader(SerialPort ser) {
			this.serialPort = ser;
		}

		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(2000);
					byte[] buffer = serialPort.readBytes();
					if (buffer != null) {
						System.out.println("Read from Serialport: "
								+ new String(buffer));
					}
				}
			} catch (SerialPortException | InterruptedException ex) {
				System.out.println(ex);
			}
		}

	}

}
