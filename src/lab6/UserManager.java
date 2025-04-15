package lab6;

import java.io.*;
import java.util.*;

public class UserManager {
	
    private static final String FILE_NAME = "users.txt";
    private HashMap<String, UtilityCompany> users;

    public UserManager() {
        users = new HashMap<>();
        loadUsers();
    }

    public void addUser(UtilityCompany user) {
        users.put(user.getUsername(), user);
        System.out.println("hi");
        saveUsers();
    }

    public UtilityCompany getUser(String username) {
        return users.get(username);
    }

    public boolean authenticate(String username, String password) {
        UtilityCompany user = users.get(username);
        return user != null && user.checkPassword(password);
    }

    private void loadUsers() {
        users.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 6) continue; // skip invalid

                String username = parts[0];
                String password = parts[1];
                int accountNumber = Integer.parseInt(parts[2]);

                ArrayList<String> billHistory = new ArrayList<>();
                if (!parts[3].equals("null")) {
                    Collections.addAll(billHistory, parts[3].split(","));
                }

                double nextBillAmount = Double.parseDouble(parts[4]);
                String dueDate = parts[5];

                UtilityCompany user = new UtilityCompany(username, password, accountNumber);
                for (String entry : billHistory) {
                    user.getBillHistory().add(entry);
                }

                // Override auto-generated bill info with saved data
                user.getBillHistory().clear();
                user.getBillHistory().addAll(billHistory);
                // nextBillAmount and dueDate are not directly settable, so you might
                // need to update UtilityCompany to allow restoring them

                users.put(username, user);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
        	System.out.println("Saving user: ");
            for (UtilityCompany user : users.values()) {
                StringBuilder sb = new StringBuilder();
                sb.append(user.getUsername()).append("|");
                sb.append(user.getPassword()).append("|"); // for security, skip real password writing
                sb.append(user.getAccountNumber()).append("|");

                List<String> history = user.getBillHistory();
                if (history != null && !history.isEmpty()) {
                    sb.append(String.join(",", history));
                } else {
                    sb.append("null");
                }

                sb.append("|").append(user.getNextBillAmount());
                sb.append("|").append(user.getDueDate());

                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public Set<String> getAllUsernames() {
        return users.keySet();
    }
}
