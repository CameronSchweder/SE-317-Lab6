package lab6;

import java.io.*;
import java.util.*;

/**
 * The UserManager class manages user accounts, including loading from and saving to a file,
 * authenticating users, and retrieving user data.
 */
public class UserManager {

	private static final String FILE_NAME = "users.txt";
	private HashMap<String, UserData> users;

	// Constructor: Initializes the user map and loads users from the file.
	public UserManager() {
		users = new HashMap<>();
		loadUsers();
	}

	/**
	 * Adds a new user to the system and saves all users to file.
	 * @param user The UserData object to be added.
	 */
	public void addUser(UserData user) {
		users.put(user.getUsername(), user);
		saveUsers();
	}

	/**
	 * Retrieves a user by username.
	 * @param username The username to search for.
	 * @return The corresponding UserData object, or null if not found.
	 */
	public UserData getUser(String username) {
		return users.get(username);
	}

	/**
	 * Authenticates a user with given credentials.
	 * @param username The user's username.
	 * @param password The user's password.
	 * @return True if authentication succeeds; false otherwise.
	 */
	public boolean authenticate(String username, String password) {
		UserData user = users.get(username);
		return user != null && user.getPassword().equals(password);
	}

	/**
	 * Gets a set of all registered usernames.
	 * @return A Set of all usernames in the system.
	 */
	public Set<String> getAllUsernames() {
		return users.keySet();
	}

	/**
	 * Loads users from the "users.txt" file. If the file does not exist, it does nothing.
	 * Each line is parsed to reconstruct UserData and associated account objects.
	 */
	private void loadUsers() {
		users.clear();
		File file = new File(FILE_NAME);
		if (!file.exists())
			return;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				try {
					String[] parts = line.split("\\|");
					if (parts.length < 8)
						continue; // Skip malformed lines

					String username = parts[0];
					String password = parts[1];
					int accountNumber = Integer.parseInt(parts[2]);

					ArrayList<String> billHistory = new ArrayList<>();
					if (!parts[3].equals("null")) {
						Collections.addAll(billHistory, parts[3].split(","));
					}

					double nextBillAmount = Double.parseDouble(parts[4]);
					String dueDate = parts[5];

					double checkingBalance = Double.parseDouble(parts[6]);
					double savingsBalance = Double.parseDouble(parts[7]);

					// Create account objects
					UtilityCompany utility = new UtilityCompany(username, password, accountNumber);
					utility.getBillHistory().clear();
					utility.getBillHistory().addAll(billHistory);
					utility.setNextBillAmount(nextBillAmount);
					utility.setDueDate(dueDate);

					CheckingAccount checking = new CheckingAccount(checkingBalance);
					SavingsAccount savings = new SavingsAccount(savingsBalance);

					UserData user = new UserData(username, password, checking, savings, utility, accountNumber);
					users.put(username, user);
				} catch (NumberFormatException e) {
					System.out.println("Skipping user entry due to format issue: " + e.getMessage());
				} catch (Exception e) {
					System.out.println("Error processing a line in user file: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println("Error loading users from file: " + e.getMessage());
		}
	}

	/**
	 * Saves the current user data to the "users.txt" file.
	 * Each line represents one user's data, separated by | delimiters.
	 */
	private void saveUsers() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
			for (UserData user : users.values()) {
				try {
					UtilityCompany utility = user.getUtility();
					StringBuilder sb = new StringBuilder();
					sb.append(user.getUsername()).append("|");
					sb.append(user.getPassword()).append("|");
					sb.append(user.getAccountNum()).append("|");

					List<String> history = utility.getBillHistory();
					if (history != null && !history.isEmpty()) {
						sb.append(String.join(",", history));
					} else {
						sb.append("null");
					}

					sb.append("|").append(utility.getNextBillAmount());
					sb.append("|").append(utility.getDueDate());
					sb.append("|").append(user.getChecking().getBalance());
					sb.append("|").append(user.getSavings().getBalance());

					writer.write(sb.toString());
					writer.newLine();
				} catch (Exception e) {
					System.out.println("Error writing a user to file: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println("Error saving users to file: " + e.getMessage());
		}
	}
}
