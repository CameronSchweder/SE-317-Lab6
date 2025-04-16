package lab6;

import java.util.Scanner;

public class ATMSim {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean login = true;
		UserManager userManager = new UserManager();
		UserData currentUser = null;


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

				 UtilityCompany utility = new UtilityCompany(username, password);
				    CheckingAccount checking = new CheckingAccount(0);
				    SavingsAccount savings = new SavingsAccount(0);

				    int accountNum = utility.getAccountNumber();
				    userManager.addUser(currentUser);
				System.out.println("Thanks for signing up, here is your Account Number: " + utility.getAccountNumber());

				login = false;
			}
			if (num == 2) {
				System.out.println("Great, Please Enter your Username, Account Number, and Password:");

				while (login) {
				    System.out.print("Username: ");
				    String username = scanner.next();

				    System.out.print("Account Number: ");
				    int enteredAccountNum = scanner.nextInt();

				    System.out.print("Password: ");
				    String password = scanner.next();

				    // fetch the stored user object
				    UserData user = userManager.getUser(username);

				    // verify username/password first...
				    if (user != null && userManager.authenticate(username, password)) {
				        // ...then verify the account number matches
				        int actualAccountNum = user.getUtility().getAccountNumber();
				        if (enteredAccountNum == actualAccountNum) {
				            currentUser = user;
				            System.out.println("Login successful!");
				            login = false;
				        } else {
				            System.out.println("Invalid account number. Please try again.");
				        }
				    } else {
				        System.out.println("Invalid username or password. Please try again.");
				    }
				}
			}
			if (num == 3) {
				System.out.println("Exiting");
				return;
			} else {
				System.out.println("Please enter a valid opiton");
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
				System.out.println("Checking Account Balance: $" + currentUser.getChecking().getBalance());
			}
			break;
			// Deposit money in Checking
			case 2: {
				System.out.print("Enter amount to deposit to Checking: ");
				double amount = scanner.nextDouble();
				if (currentUser.getChecking().deposit(amount)) {
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
				if (currentUser.getChecking().withdraw(amount)) {
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
				if (currentUser.getChecking().withdraw(amount) && currentUser.getSavings().deposit(amount)) {
					System.out.println("Transfer successful.");
				} else {
					System.out.println("Transfer failed.");
				}
				break;
			}

			// Getting savings Balance
			case 5: {
				System.out.println("Saving balance: $" + currentUser.getSavings().getBalance());
				break;
			}

			// Depositing money to Savings
			case 6: {
				System.out.print("Enter amount to deposit to Saving: ");
				double amount = scanner.nextDouble();
				if (currentUser.getSavings().deposit(amount)) {
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
				if (currentUser.getSavings().transferToChecking(currentUser.getChecking(), amount)) {
					System.out.println("Transfer successful.");
				} else {
					System.out.println("Transfer failed. Limit exceeded or insufficient funds.");
				}
				break;
			}

			// Paying next bill
			case 8: {
				double billAmount = currentUser.getUtility().getNextBillAmount();
				System.out.println("Paying bill of $" + billAmount + " due on " + currentUser.getUtility().getDueDate());
				if (currentUser.getChecking().withdraw(billAmount)) {
					currentUser.getUtility().addBillPayment(billAmount);
					System.out.println("Payment successful.");
				} else {
					System.out.println("Payment failed. Not enough balance.");
				}
				break;
			}

			// Finding Bill History
			case 9: {
				System.out.println("Utility Bill History:");
				for (String record : currentUser.getUtility().getBillHistory()) {
					System.out.println(record);
				}
				break;
			}

			// Finding next Bill
			case 10: {
				System.out.println("Next Bill Amount: $" + currentUser.getUtility().getNextBillAmount());
				System.out.println("Due Date: " + currentUser.getUtility().getDueDate());
				break;
			}
			// Exiting
			case 11: {
                userManager.addUser(currentUser); // Save any final updates
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