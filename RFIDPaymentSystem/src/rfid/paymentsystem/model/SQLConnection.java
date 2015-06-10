package rfid.paymentsystem.model;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class SQLConnection {

	protected Connection connection;

	public SQLConnection() {
		connectToDatabase();
	}

	private void connectToDatabase() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager
					.getConnection("jdbc:sqlite:database/database.db");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected abstract void loadFromDatabase();

	protected abstract void updateToDatabase();

	protected abstract void insertIntoDatabase();
}
