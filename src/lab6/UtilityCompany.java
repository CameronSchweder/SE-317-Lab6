package lab6;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a utility company account that tracks bill payments and generates new bills.
 */
public class UtilityCompany {

    private String username;
    private String password;
    private int accountNumber;
    private ArrayList<String> billHistory;
    private double nextBillAmount;
    private String dueDate;

    /**
     * Constructs a UtilityCompany account with given credentials and account number.
     * @param username the account username
     * @param password the account password
     * @param accountNumber the utility account number
     */
    public UtilityCompany(String username, String password, int accountNumber) {
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
        this.billHistory = new ArrayList<>();
        generateNextBill(); // generate the first bill
    }

    /**
     * Constructs a UtilityCompany account with auto-generated account number.
     * @param username the account username
     * @param password the account password
     */
    public UtilityCompany(String username, String password) {
        this(username, password, generateAccountNumber());
    }

    /**
     * Generates a random 6-digit utility account number.
     * @return randomly generated account number
     */
    private static int generateAccountNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    /**
     * Adds a bill payment to the account history and triggers the next bill.
     * Keeps only the 3 most recent entries.
     * @param amount the payment amount
     * @throws IllegalArgumentException if the payment amount is not positive
     */
    public void addBillPayment(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Bill payment amount must be positive.");
        }

        if (billHistory.size() >= 3) {
            billHistory.remove(0); // remove the oldest entry
        }

        String entry = String.format("Paid $%.2f on %s", amount, java.time.LocalDate.now());
        billHistory.add(entry);

        generateNextBill(); // simulate next month's bill
    }

    /**
     * Randomly generates the next bill amount and due date.
     */
    private void generateNextBill() {
        Random random = new Random();
        this.nextBillAmount = 50 + random.nextInt(150); // $50 to $199
        this.dueDate = java.time.LocalDate.now().plusDays(30).toString(); // due in 30 days
    }

    // --- Getters and setters ---

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Validates if a given password matches the account's password.
     * @param password the password to check
     * @return true if it matches, false otherwise
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public ArrayList<String> getBillHistory() {
        return new ArrayList<>(billHistory); // return a copy to preserve encapsulation
    }

    public double getNextBillAmount() {
        return nextBillAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        if (dueDate == null || dueDate.isEmpty()) {
            throw new IllegalArgumentException("Due date cannot be null or empty.");
        }
        this.dueDate = dueDate;
    }

    public void setNextBillAmount(double nextBillAmount) {
        if (nextBillAmount <= 0) {
            throw new IllegalArgumentException("Bill amount must be positive.");
        }
        this.nextBillAmount = nextBillAmount;
    }
}
