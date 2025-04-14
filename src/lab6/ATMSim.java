package lab6;

import java.util.Scanner;

public class ATMSim {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean login = true;
		CheckingAccount checking = new CheckingAccount(0);
		SavingsAccount savings = new SavingsAccount(0);
		UtilityCompany utility = null;

		System.out.println("Please choose one of the options below");
		System.out.println("1. Sign Up");
		System.out.println("2. Login");
		System.out.println("3. Exit");

		// Login and Signup Process
		while (login) {
			int num = scanner.nextInt();
			if (num == 1) {
				System.out.println("Great, Please Enter the following:");
				System.out.println("Username:");
				String username = scanner.next();
				System.out.println("Password: ");
				String password = scanner.next();

				utility = new UtilityCompany(username, password);
				System.out.println("Thanks for signing up, here is your Account Number: " + utility.getAccountNumber());

				login = false;
			}
			if (num == 2) {
				System.out.println("Great, Please Enter your Username and Password Below:");
				while (login) {
					System.out.println("Username:");
					String username = scanner.next();
					System.out.println("Account Number:");
					int accountNum = scanner.nextInt();
					System.out.println("Password: ");
					String password = scanner.next();

					utility = new UtilityCompany(username, password, accountNum);

					if (scanner.nextInt() == 0) { // utilAccount.userLogin(username, password)
						login = false;
					} else {
						System.out.println("Please enter a valid Username and Password");
					}
				}
			}
			if (num == 3) {
				System.out.println("Exiting");
				return;
			} else {
				System.out.println("please enter a valid opiton");
			}
		}
		boolean running = true;
		while (running) {
			System.out.println("\n===== ATM Menu =====");
			System.out.println("1. View Checking Balance");
			System.out.println("2. Deposit to Checking");
			System.out.println("3. Withdraw from Checking");
			System.out.println("4. Transfer from Checking to Saving");

			System.out.println("5. View Saving Balance");
			System.out.println("6. Deposit to Saving");
			System.out.println("7. Transfer from Saving to Checking");

			System.out.println("8. Pay Utility Bill");
			System.out.println("9. View Utility Bill History");
			System.out.println("10. View Next Utility Bill");

			System.out.println("11. Exit");
			System.out.print("Choose an option: ");

			int choice = scanner.nextInt();

			switch (choice) {
			// Account Balance
			case 1: {
				System.out.println("Checking Account Balance: $" + checking.getBalance());
			}
			// Deposit money in Checking
			case 2: {
				System.out.print("Enter amount to deposit to Checking: ");
				double amount = scanner.nextDouble();
				if (checking.deposit(amount)) {
					System.out.println("Deposit successful.");
				} else {
					System.out.println("Deposit failed. Limit exceeded or invalid amount.");
				}
				break;
			}

			// Withdraw in Checking
			case 3: {
				System.out.print("Enter amount to withdraw from Checking: ");
				double amount = scanner.nextDouble();
				if (checking.withdraw(amount)) {
					System.out.println("Withdrawal successful.");
				} else {
					System.out.println("Withdrawal failed. Limit exceeded or insufficient funds.");
				}
				break;
			}

			// Transfer money from Checking to Savings
			case 4: {
				System.out.print("Enter amount to transfer to Saving: ");
				double amount = scanner.nextDouble();
				if (checking.withdraw(amount) && savings.deposit(amount)) {
					System.out.println("Transfer successful.");
				} else {
					System.out.println("Transfer failed.");
				}
				break;
			}

			// Getting savings Balance
			case 5: {
				System.out.println("Saving balance: $" + savings.getBalance());
				break;
			}

			// Depositing money to Savings
			case 6: {
				System.out.print("Enter amount to deposit to Saving: ");
				double amount = scanner.nextDouble();
				if (savings.deposit(amount)) {
					System.out.println("Deposit successful.");
				} else {
					System.out.println("Deposit failed. Limit exceeded or invalid amount.");
				}
				break;
			}

			// Transferring money from Savings to checking
			case 7: {
				System.out.print("Enter amount to transfer to Checking: ");
				double amount = scanner.nextDouble();
				if (savings.transferToChecking(checking, amount)) {
					System.out.println("Transfer successful.");
				} else {
					System.out.println("Transfer failed2. Limit exceeded or insufficient funds.");
				}
				break;
			}

			// Paying next bill
			case 8: {
				double billAmount = utility.getNextBillAmount();
				System.out.println("Paying bill of $" + billAmount + " due on " + utility.getDueDate());
				if (checking.withdraw(billAmount)) {
					utility.addBillPayment(billAmount);
					System.out.println("Payment successful.");
				} else {
					System.out.println("Payment failed. Not enough balance.");
				}
				break;
			}

			// Finding Bill History
			case 9: {
				System.out.println("Util1ity Bill History:");
				for (String record : utility.getBillHistory()) {
					System.out.println(record);
				}
				break;
			}

			// Finding next Bill
			case 10: {
				System.out.println("Next Bill Amount: $" + utility.getNextBillAmount());
				System.out.println("Due Date: " + utility.getDueDate());
				break;
			}
			// Exiting
			case 11: {
				running = false;
				System.out.println("Thank you for using the ATM!");
				break;
			}
			default: {
				System.out.println("Invalid option. Please try again.");
			}
			}

		}
	}
}
