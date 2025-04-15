package lab6;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String FILE_NAME = "users.json";
    private HashMap<String, UserData> users;

    public UserManager() {
        users = loadUsers();
    }

    public void addUser(UserData user) {
        users.put(user.getUsername(), user);
        saveUsers();
    }

    public UserData getUser(String username, String password) {
        UserData user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (UserData user : users.values()) {
                String userData = user.getUsername() + "|"
                        + user.getPassword() + "|"
                        + user.getChecking().getBalance() + "|"
                        + user.getSavings().getBalance() + "|"
                        + user.getUtility().getAccountNumber() + "|"
                        + String.join(",", user.getUtility().getBillHistory()) + "|"
                        + user.getUtility().getNextBill() + "|"
                        + user.getUtility().getDueDate();

                writer.write(userData);
                writer.newLine(); // write each user on a new line
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private HashMap<String, UserData> loadUsers() {
        HashMap<String, UserData> loadedUsers = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\|");
                
                String username = userData[0];
                String password = userData[1];
                double checkingBalance = Double.parseDouble(userData[2]);
                double savingsBalance = Double.parseDouble(userData[3]);
                int utilityAccountNumber = Integer.parseInt(userData[4]);
                
                // Parsing the bill history (comma-separated)
                String[] billHistoryStr = userData[5].split(",");
                List<Double> billHistory = new ArrayList<>();
                for (String bill : billHistoryStr) {
                    billHistory.add(Double.parseDouble(bill));
                }

                double nextBill = Double.parseDouble(userData[6]);
                String dueDate = userData[7];

                // Create a new User object and add to the map
                UserData user = new UserData(username, password, utilityAccountNumber);
                user.getChecking().setBalance(checkingBalance);
                user.getSavings().setBalance(savingsBalance);
                user.getUtility().setBillHistory(billHistory);
                user.getUtility().setNextBill(nextBill);
                user.getUtility().setDueDate(dueDate);

                loadedUsers.put(username, user);
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        return loadedUsers;
    }
}
