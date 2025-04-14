package lab6;

public class SavingsAccount {
	private static final double DAILY_DEPOSIT_LIMIT = 5000.0;
	private static final double DAILY_TRANSFER_LIMIT = 100.0;
	
	private double balance;
	private double dailyDepositTotal = 0;
	private double dailyTransferTotal = 0;
	
	public SavingsAccount(double balance) {
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
	
	public boolean transferToChecking(CheckingAccount checking, double amount) {
		if (amount <= 0)
			return false;
		
		if (dailyTransferTotal + amount > DAILY_TRANSFER_LIMIT) {
			System.out.println("Transfer exceeds $100 daily limit");
			return false;
		}
		
		if (balance - amount < 0) {
			System.out.println("Insufficient funds. Cannot overdraft");
			return false;
		}
		
		balance -= amount;
		checking.receiveTransfer(amount);
		dailyTransferTotal += amount;
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
		dailyTransferTotal = 0;
	}
}

