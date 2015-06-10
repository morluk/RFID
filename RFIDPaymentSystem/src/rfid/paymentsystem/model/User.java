package rfid.paymentsystem.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User extends SQLConnection {

	private Integer id;

	private String tagId;

	private String name;

	private Double balance;

	private ArrayList<Transaction> transactions;

	public User(int id) {
		transactions = new ArrayList<Transaction>();
		this.id = id;
		balance = 0.0;
		loadFromDatabase();
		loadTransactions();
		calculateBalance();
	}

	public User(String name, String tagId) {
		transactions = new ArrayList<Transaction>();
		this.name = name;
		this.tagId = tagId;
		balance = 0.0;
		insertIntoDatabase();
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
		updateToDatabase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		updateToDatabase();
	}

	public Integer getId() {
		return id;
	}

	@Override
	protected void loadFromDatabase() {
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM user WHERE id = ?");
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();
			if (result.next()) {
				tagId = result.getString("tagid");
				name = result.getString("name");
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
					.prepareStatement("UPDATE user SET tagid = ?, name = ? WHERE id = ?");
			statement.setString(1, tagId);
			statement.setString(2, name);
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
					"INSERT INTO user (tagid, name) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, tagId);
			statement.setString(2, name);
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
		StringBuilder builder = new StringBuilder();
		builder.append("###########" + name
				+ "#######################################\n");
		builder.append("USER=ID:" + id + "; Name:" + name + "; TagId:" + tagId);
		builder.append("\n\n---------Transactions-----------------------------------");
		ArrayList<Transaction> tansactions = getAllTransactions();
		if (transactions.isEmpty()) {
			builder.append("\nNo Transactions!!!");
		}
		for (Transaction trans : tansactions) {
			builder.append("\n" + trans.toString());
		}
		builder.append("\n---------------------------------------------------------\n");
		builder.append("Balance:" + balance);
		builder.append("\n##########################################################\n\n\n");
		return builder.toString();
	}

	private void loadTransactions() {
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM trans WHERE userid = ?");
			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int transId = result.getInt("id");
				Transaction transaction = new Transaction(transId, this);
				transactions.add(transaction);
			}
			result.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void calculateBalance() {
		for (Transaction trans : getAllTransactions()) {
			Value value = trans.getValue();
			if (trans.isPositiv()) {
				balance += value.getValue();
			} else {
				balance -= value.getValue();
			}
		}
	}

	public ArrayList<Transaction> getAllTransactions() {
		return transactions;
	}

	public double getBalance() {
		return balance;
	}

	public void addTransaction(String name, Value value, boolean isPositiv) {
		Transaction trans = new Transaction(name, this, value, isPositiv);
		transactions.add(trans);
		if (isPositiv) {
			balance += value.getValue();
		} else {
			balance -= value.getValue();
		}
	}
}
