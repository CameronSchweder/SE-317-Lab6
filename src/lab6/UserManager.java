package lab6;

import java.io.*;
import java.util.*;

public class UserManager {

	private String FILE_NAME = "users.txt";
	private HashMap<String, UserData> users;

	public UserManager() {
		users = new HashMap<>();
		loadUsers();
	}
	
	public UserManager(String filePath) {
	    this.FILE_NAME = filePath;
	    this.users = new HashMap<>();
	    loadUsers();
	}

	public void addUser(UserData user) {
		users.put(user.getUsername(), user);
		saveUsers();
	}

	public UserData getUser(String username) {
		return users.get(username);
	}

	public boolean authenticate(String username, String password) {
		UserData user = users.get(username);
		return user != null && user.getPassword().equals(password);
	}

	public Set<String> getAllUsernames() {
		return users.keySet();
	}

	public void loadUsers() {
		users.clear();
		File file = new File(FILE_NAME);
		if (!file.exists())
			return;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length < 8)
					continue; // skip invalid lines

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

				UtilityCompany utility = new UtilityCompany(username, password, accountNumber);
				utility.getBillHistory().clear();
				utility.getBillHistory().addAll(billHistory);
				utility.setNextBillAmount(nextBillAmount); // You must implement this setter
				utility.setDueDate(dueDate); // And this setter

				CheckingAccount checking = new CheckingAccount(checkingBalance);
				SavingsAccount savings = new SavingsAccount(savingsBalance);

				UserData user = new UserData(username, password, checking, savings, utility, accountNumber);
				users.put(username, user);
			}
		} catch (IOException | NumberFormatException e) {
			System.out.println("Error loading users: " + e.getMessage());
		}
	}

	private void saveUsers() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
			for (UserData user : users.values()) {
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
			}
		} catch (IOException e) {
			System.out.println("Error saving users: " + e.getMessage());
		}
	}
}
