package rfid.paymentsystem.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author lukas
 * Value represents and simulates money
 */
public class Value extends SQLConnection {

	private Integer id;

	private String tagId;

	private Double value;

	public Value(int id) {
		this.id = id;
		loadFromDatabase();
	}

	public Value(String tagId, Double value) {
		this.tagId = tagId;
		this.value = value;
		insertIntoDatabase();
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
		updateToDatabase();
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
		updateToDatabase();
	}

	public Integer getId() {
		return id;
	}

	@Override
	protected void loadFromDatabase() {
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM valuetag WHERE id = ?");
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();
			if (result.next()) {
				tagId = result.getString("tagid");
				String valueString = result.getString("value");
				value = Double.valueOf(valueString);
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
					.prepareStatement("UPDATE valuetag SET tagid = ?, value = ? WHERE id = ?");
			statement.setString(1, tagId);
			statement.setString(2, value.toString());
			statement.setInt(3, id);
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
					"INSERT INTO valuetag (tagid, value) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, tagId);
			statement.setString(2, value.toString());
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
		return "VALUE=ID:" + id + "; TagID:" + tagId + "; Value:" + value;
	}
}
