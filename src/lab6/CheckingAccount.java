package lab6;

public class CheckingAccount {
	private static final double DAILY_DEPOSIT_LIMIT = 5000.0;
	private static final double DAILY_WITHDRAW_LIMIT = 500.0;
	
	private double balance;
	private double dailyDepositTotal = 0;
	private double dailyWithdrawTotal = 0;
	
	public CheckingAccount(double balance) {
		if (balance < 0) {
			throw new IllegalArgumentException("Initial balance cannot be negative.")
;		}
		this.balance = balance;
	}
	
	public boolean deposit(double amount) {
		if (amount <= 0)
			return false;
		if (dailyDepositTotal + amount > DAILY_DEPOSIT_LIMIT) {
			System.out.println("Deposit exceeds daily $5000 limit.");
			return false;
		}
		
		balance += amount;
		dailyDepositTotal += amount;
		return true;
	}
	
	public boolean withdraw(double amount) {
		if (amount <= 0)
			return false;
		if (dailyWithdrawTotal + amount > DAILY_WITHDRAW_LIMIT) {
			System.out.println("Withdrawal exceeds daily $500 limit");
			return false;
		}
		if (balance - amount < 0) {
			System.out.println("Insufficient funds. Cannot overdraft");
			return false;
		}
		
		balance -= amount;
		dailyWithdrawTotal += amount;
		return true;
	}
	
	public boolean transferToSavings(SavingsAccount savings, double amount) {
		if (amount <= 0 || balance - amount < 0)
			return false;
		
		balance -= amount;
		savings.deposit(amount);
		
		return true;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void receiveTransfer(double amount) {
		balance += amount;
	}
	
	public void resetDailyLimits() {
		dailyDepositTotal = 0;
		dailyWithdrawTotal = 0;
	}
}
