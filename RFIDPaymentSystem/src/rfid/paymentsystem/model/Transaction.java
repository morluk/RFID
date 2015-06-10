package rfid.paymentsystem.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import rfid.paymentsystem.controller.ValueController;

public class Transaction extends SQLConnection {

	private Integer id;

	private String name;

	private User user;

	private Value value;

	private boolean positiv;

	public Transaction(int id, User user) {
		this.id = id;
		this.user = user;
		loadFromDatabase();
	}

	public Transaction(String name, User user, Value value, boolean isPositiv) {
		this.user = user;
		this.name = name;
		this.value = value;
		this.positiv = isPositiv;
		insertIntoDatabase();
	}

	public boolean isPositiv() {
		return positiv;
	}

	public Value getValue() {
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
				int valueTagId = result.getInt("valuetagid");
				int isPositiv = result.getInt("ispositiv");
				name = result.getString("name");
				value = ValueController.getInstance().getValueById(valueTagId);
				this.positiv = (isPositiv == 1 ? true : false);
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
					.prepareStatement("UPDATE trans SET name = ?, userid = ?, valuetagid = ?, ispositiv = ? WHERE id = ?");
			statement.setString(1, name);
			statement.setInt(2, user.getId());
			statement.setInt(3, value.getId());
			statement.setInt(4, (positiv == true ? 1 : 0));
			statement.setInt(5, id);
			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void insertIntoDatabase() {
		try {
			PreparedStatement statement = connection
					.prepareStatement(
							"INSERT INTO trans (name, userid, valuetagid, ispositiv) VALUES (?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, name);
			statement.setInt(2, user.getId());
			statement.setInt(3, value.getId());
			statement.setInt(4, (positiv == true ? 1 : 0));
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
				+ user.getId() + "; isPositiv:" + positiv + "\n"
				+ value.toString();
	}

}
