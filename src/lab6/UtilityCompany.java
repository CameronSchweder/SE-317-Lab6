package lab6;

import java.util.ArrayList;
import java.util.Random;

public class UtilityCompany {
    private String username;
    private String password;
    private int accountNumber;
    private ArrayList<String> billHistory;
    private double nextBillAmount;
    private String dueDate;

    public UtilityCompany(String username, String password, int accountNumber) {
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
        this.billHistory = new ArrayList<>();
        generateNextBill();
    }

    public UtilityCompany(String username, String password) {
        this(username, password, generateAccountNumber());
    }

    private static int generateAccountNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public void addBillPayment(double amount) {
        if (billHistory.size() >= 3) {
            billHistory.remove(0); 
        }
        String entry = String.format("Paid $%.2f on %s", amount, java.time.LocalDate.now());
        billHistory.add(entry);
        generateNextBill(); 
    }

    private void generateNextBill() {
        Random random = new Random();
        this.nextBillAmount = 50 + random.nextInt(150);
        this.dueDate = java.time.LocalDate.now().plusDays(30).toString();
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public ArrayList<String> getBillHistory() {
        return billHistory;
    }

    public double getNextBillAmount() {
        return nextBillAmount;
    }

    public String getDueDate() {
        return dueDate;
    }
}
