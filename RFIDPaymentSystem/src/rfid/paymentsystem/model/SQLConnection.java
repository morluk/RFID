package rfid.paymentsystem.model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author lukas
 * handles the connection between the programm
 * and the sqlite database for User, Value and Transaction
 */
public abstract class SQLConnection {

	protected Connection connection;

	public SQLConnection() {
		connectToDatabase();
	}

	/**
	 * loads the jdbc driver and connects to the database.db
	 * in the database folder
	 */
	private void connectToDatabase() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager
					.getConnection("jdbc:sqlite:database/database.db");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * User, Value and Transaction inherit SQLConnection
	 * and have to implements these abstact methods
	 */
	protected abstract void loadFromDatabase();

	protected abstract void updateToDatabase();

	protected abstract void insertIntoDatabase();
}
