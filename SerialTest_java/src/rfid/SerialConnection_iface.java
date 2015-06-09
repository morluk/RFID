package rfid;

/**
 * Interface for communication SerialConnection to Main. In this example could
 * have been just Main.
 * 
 * @author moritz
 * 
 */
public interface SerialConnection_iface {
	public void parseInput(String msg);

	public void writeLog(String text);
}
