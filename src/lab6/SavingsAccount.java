package lab6;

/**
 * Represents a savings account with limits on daily deposits and transfers to checking accounts.
 */
public class SavingsAccount {
    
    // Constants for daily transaction limits
    private static final double DAILY_DEPOSIT_LIMIT = 5000.0;
    private static final double DAILY_TRANSFER_LIMIT = 100.0;
    
    // Fields to track balance and daily usage
    private double balance;
    private double dailyDepositTotal = 0;
    private double dailyTransferTotal = 0;

    /**
     * Constructs a savings account with an initial balance.
     * @param balance the starting balance (must be non-negative)
     * @throws IllegalArgumentException if the initial balance is negative
     */
    public SavingsAccount(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.balance = balance;
    }

    /**
     * Deposits the specified amount into the savings account if within daily deposit limit.
     * @param amount the amount to deposit
     * @return true if the deposit was successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return false;
        }
        if (dailyDepositTotal + amount > DAILY_DEPOSIT_LIMIT) {
            System.out.println("Deposit exceeds daily $5000 limit.");
            return false;
        }

        balance += amount;
        dailyDepositTotal += amount;
        return true;
    }

    /**
     * Transfers the specified amount from this savings account to the given checking account.
     * @param checking the checking account to transfer to
     * @param amount the amount to transfer
     * @return true if transfer is successful, false otherwise
     */
    public boolean transferToChecking(CheckingAccount checking, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid transfer amount.");
            return false;
        }
        if (dailyTransferTotal + amount > DAILY_TRANSFER_LIMIT) {
            System.out.println("Transfer exceeds daily $100 limit.");
            return false;
        }
        if (balance - amount < 0) {
            System.out.println("Insufficient funds. Cannot overdraft.");
            return false;
        }

        balance -= amount;
        try {
            checking.receiveTransfer(amount);
        } catch (IllegalArgumentException e) {
            balance += amount; // rollback
            System.out.println("Transfer failed: " + e.getMessage());
            return false;
        }

        dailyTransferTotal += amount;
        return true;
    }

    /**
     * Returns the current balance of the savings account.
     * @return current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Increases the balance when receiving a transfer.
     * @param amount the amount received
     * @throws IllegalArgumentException if the received amount is non-positive
     */
    public void receiveTransfer(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Received transfer amount must be positive.");
        }
        balance += amount;
    }

    /**
     * Resets the daily deposit and transfer counters.
     * Typically called at the start of a new day.
     */
    public void resetDailyLimits() {
        dailyDepositTotal = 0;
        dailyTransferTotal = 0;
    }
}