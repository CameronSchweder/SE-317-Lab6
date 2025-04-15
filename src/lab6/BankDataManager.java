package lab6;

import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class BankDataManager {
	
	// ---------- CHECKING ACCOUNT ----------
    public static void saveCheckingAccounts(List<CheckingAccount> accounts, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("username,balance\n");
        for (CheckingAccount acc : accounts) {
            writer.write(acc.getUsername() + "," + acc.getBalance() + "\n");
        }
        writer.close();
    }

    public static List<CheckingAccount> loadCheckingAccounts(String filename) throws IOException {
        List<CheckingAccount> accounts = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine(); // Skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String username = parts[0];
            double balance = Double.parseDouble(parts[1]);
            CheckingAccount account = new CheckingAccount(balance);
            account.setUsername(username);
            accounts.add(account);
        }
        reader.close();
        return accounts;
    }

    // ---------- SAVINGS ACCOUNT ----------
    public static void saveSavingsAccounts(List<SavingsAccount> accounts, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("username,balance\n");
        for (SavingsAccount acc : accounts) {
            writer.write(acc.getUsername() + "," + acc.getBalance() + "\n");
        }
        writer.close();
    }

    public static List<SavingsAccount> loadSavingsAccounts(String filename) throws IOException {
        List<SavingsAccount> accounts = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine(); // Skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String username = parts[0];
            double balance = Double.parseDouble(parts[1]);
            SavingsAccount account = new SavingsAccount(balance);
            account.setUsername(username);
            accounts.add(account);
        }
        reader.close();
        return accounts;
    }

    // ---------- UTILITY COMPANY ----------
    public static void saveUtilityAccounts(List<UtilityCompany> accounts, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("username,password,accountNumber,nextBillAmount,dueDate,bill1,bill2,bill3\n");
        for (UtilityCompany uc : accounts) {
            StringBuilder sb = new StringBuilder();
            sb.append(uc.getUsername()).append(",")
              .append(uc.getPassword()).append(",")
              .append(uc.getAccountNumber()).append(",")
              .append(uc.getNextBillAmount()).append(",")
              .append(uc.getDueDate());

            List<String> history = uc.getBillHistory();
            for (int i = 0; i < 3; i++) {
                sb.append(",");
                if (i < history.size()) sb.append(history.get(i));
            }

            writer.write(sb.toString() + "\n");
        }
        writer.close();
    }

    public static List<UtilityCompany> loadUtilityAccounts(String filename) throws IOException {
        List<UtilityCompany> accounts = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine(); // Skip header
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String username = parts[0];
            String password = parts[1];
            int accountNumber = Integer.parseInt(parts[2]);
            double nextBillAmount = Double.parseDouble(parts[3]);
            String dueDate = parts[4];

            UtilityCompany uc = new UtilityCompany(username, password, accountNumber);
            uc.setNextBillAmount(nextBillAmount);
            uc.setDueDate(dueDate);

            for (int i = 5; i < parts.length; i++) {
                if (!parts[i].isEmpty()) {
                    uc.getBillHistory().add(parts[i]);
                }
            }

            accounts.add(uc);
        }
        reader.close();
        return accounts;
    }
}
