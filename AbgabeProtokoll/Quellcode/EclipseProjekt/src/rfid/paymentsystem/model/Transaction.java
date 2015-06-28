package rfid.paymentsystem.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction extends SQLConnection {

	private Integer id;

	private String name;

	private User user;

	private Double value;

	public Transaction(int id, User user) {
		this.id = id;
		this.user = user;
		loadFromDatabase();
	}

	public Transaction(String name, User user, Double value) {
		this.user = user;
		this.name = name;
		this.value = value;
		insertIntoDatabase();
	}

	public Double getValue() {
		return value;
	}

	@Override
	protected void loadFromDatabase() {
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM trans WHERE id = ?");
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();
			if (result.next()) {
				name = result.getString("name");
				value = Double.valueOf(result.getString("value"));
			}
			result.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void updateToDatabase() {
		try {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE trans SET name = ? WHERE id = ?");
			statement.setString(1, name);
			statement.setInt(2, id);
			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void insertIntoDatabase() {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO trans (name, userid, value) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, name);
			statement.setInt(2, user.getId());
			statement.setString(3, value.toString());
			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				this.id = generatedKeys.getInt(1);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "TRANSACTION=ID:" + id + "; Name:" + name + "; UserID:"
				+ user.getId() + "; Value:" + value.toString();
	}

}
