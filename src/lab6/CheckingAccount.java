package lab6;

/**
 * Represents a checking account with daily deposit and withdrawal limits.
 * Supports basic banking operations such as deposit, withdraw, and transfers to savings.
 */
public class CheckingAccount {
    
    // Constants for daily transaction limits
    private static final double DAILY_DEPOSIT_LIMIT = 5000.0;
    private static final double DAILY_WITHDRAW_LIMIT = 500.0;
    
    // Fields to track balance and daily limits
    private double balance;
    private double dailyDepositTotal = 0;
    private double dailyWithdrawTotal = 0;

    /**
     * Constructs a checking account with an initial balance.
     * @param balance the starting balance (must be non-negative)
     * @throws IllegalArgumentException if the initial balance is negative
     */
    public CheckingAccount(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.balance = balance;
    }

    /**
     * Deposits the specified amount into the account if within daily limit.
     * @param amount the amount to deposit
     * @return true if deposit is successful, false otherwise
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
     * Withdraws the specified amount from the account if within daily limit and sufficient balance.
     * @param amount the amount to withdraw
     * @return true if withdrawal is successful, false otherwise
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }
        if (dailyWithdrawTotal + amount > DAILY_WITHDRAW_LIMIT) {
            System.out.println("Withdrawal exceeds daily $500 limit.");
            return false;
        }
        if (balance - amount < 0) {
            System.out.println("Insufficient funds. Cannot overdraft.");
            return false;
        }

        balance -= amount;
        dailyWithdrawTotal += amount;
        return true;
    }

    /**
     * Transfers funds from this checking account to the specified savings account.
     * @param savings the target savings account
     * @param amount the amount to transfer
     * @return true if transfer is successful, false otherwise
     */
    public boolean transferToSavings(SavingsAccount savings, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid transfer amount.");
            return false;
        }
        if (balance - amount < 0) {
            System.out.println("Transfer failed: Insufficient funds.");
            return false;
        }

        balance -= amount;
        try {
            savings.deposit(amount);
        } catch (IllegalArgumentException e) {
            // Rollback in case of error
            balance += amount;
            System.out.println("Transfer failed: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Returns the current balance of the checking account.
     * @return current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Increases the balance when receiving a transfer.
     * @param amount the amount received
     */
    public void receiveTransfer(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Received transfer amount must be positive.");
        }
        balance += amount;
    }

    /**
     * Resets the daily deposit and withdrawal counters.
     * Typically called at the start of a new day.
     */
    public void resetDailyLimits() {
        dailyDepositTotal = 0;
        dailyWithdrawTotal = 0;
    }
}