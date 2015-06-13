package rfid.paymentsystem.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rfid.paymentsystem.model.SQLConnection;
import rfid.paymentsystem.model.Value;

public class ValueController extends SQLConnection {

	Map<String, Value> values;

	private static ValueController valueController;

	public synchronized static ValueController getInstance() {
		if (ValueController.valueController == null) {
			ValueController.valueController = new ValueController();
		}
		return ValueController.valueController;
	}

	private ValueController() {
		values = new HashMap<String, Value>();
		loadFromDatabase();
	}

	public Value getValueById(int valueTagId) {
		for (Value value : values.values()) {
			if (value.getId() == valueTagId) {
				return value;
			}
		}
		return null;
	}

	@Override
	protected void loadFromDatabase() {
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM valuetag");

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int id = result.getInt("id");
				Value value = new Value(id);
				values.put(value.getTagId(), value);
			}
			result.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void updateToDatabase() {
	}

	@Override
	protected void insertIntoDatabase() {
	}

	public ArrayList<Value> getAllValues() {
		return new ArrayList<Value>(values.values());
	}

	public void printAllValues() {
		for (Value value : values.values()) {
			System.out.println(value.toString());
		}
	}

	public Value getValueByTagId(String tagId) {
		return values.get(tagId);
	}
}
