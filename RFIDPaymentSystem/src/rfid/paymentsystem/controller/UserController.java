package rfid.paymentsystem.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rfid.paymentsystem.model.SQLConnection;
import rfid.paymentsystem.model.User;

public class UserController extends SQLConnection {

	Map<String, User> users;

	private static UserController userController;

	public synchronized static UserController getInstance() {
		if (UserController.userController == null) {
			UserController.userController = new UserController();
		}
		return UserController.userController;
	}

	private UserController() {
		users = new HashMap<String, User>();
		loadFromDatabase();
	}

	public ArrayList<User> getAllUsers() {
		return new ArrayList<User>(users.values());
	}

	@Override
	protected void loadFromDatabase() {
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM user");

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int id = result.getInt("id");
				User user = new User(id);
				users.put(user.getTagId(), user);
			}
			result.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User getUserByName(String name) {
		for (User user : getAllUsers()) {
			if (user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}

	@Override
	protected void updateToDatabase() {
	}

	@Override
	protected void insertIntoDatabase() {
	}

	public void newUser(String name, String tagId) {
		User user = new User(name, tagId);
		users.put(user.getTagId(), user);
	}

	public void printAllUsers() {
		for (User user : users.values()) {
			System.out.println(user.toString());
		}
	}

	public User getUserByTagId(String tagId) {
		return users.get(tagId);
	}
}
