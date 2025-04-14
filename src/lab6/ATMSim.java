package lab6;

import java.util.Scanner;

public class ATMSim {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean login = true;
		UtilityCompany utilAccount = new UtilityCompany();

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

				utilAccount.createAccount(username, password);
				login = false;
			}
			if (num == 2) {
				System.out.println("Great, Please Enter your Username and Password Below:");
				while (login) {
//					System.out.println("Username:");
//					String username = scanner.next();
//					System.out.println("Password: ");
//					String password = scanner.next();

					if (scanner.nextInt() == 0) { //utilAccount.userLogin(username, password)
						login = false;
					} else {
						System.out.println("Please enter a valid Username and Password");
					}
				}
			}
			if (num == 3)
			{
				System.out.println("Exiting");
				return;
			}
			else {
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

		}
	}
}
