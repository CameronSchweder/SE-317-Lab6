package lab6;

import java.util.Scanner;

public class ATMSim {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankDataManager dataManager = new BankDataManager();
        UserManager userManager = new UserManager();

        boolean login = true;
        UtilityCompany utility = null;
        CheckingAccount checking = null;
        SavingsAccount savings = null;

        System.out.println("Please choose one of the options below");
        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        System.out.println("3. Exit");

        while (login) {
            int num = scanner.nextInt();
            scanner.nextLine(); // Clear newline

            if (num == 1) {
                System.out.println("Great, Please Enter the following:");
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                utility = new UtilityCompany(username, password);
                checking = new CheckingAccount(0);
                savings = new SavingsAccount(0);

                // Save user and account data
                userManager.addUser(utility);
                dataManager.saveAccounts(username, checking, savings);

                System.out.println("Thanks for signing up, here is your Account Number: " + utility.getAccountNumber());
                login = false;

            } else if (num == 2) {
                System.out.println("Great, Please Enter your Username and Password Below:");
                while (login) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Account Number: ");
                    int accountNum = scanner.nextInt();
                    scanner.nextLine(); // clear newline
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    utility = new UtilityCompany(username, password, accountNum);

                    if (userManager.login(username, password)) {
                        checking = dataManager.loadCheckingAccount(username);
                        savings = dataManager.loadSavingsAccount(username);
                        if (checking == null) checking = new CheckingAccount(0);
                        if (savings == null) savings = new SavingsAccount(0);
                        login = false;
                    } else {
                        System.out.println("Invalid login. Try again.");
                    }
                }

            } else if (num == 3) {
                System.out.println("Exiting...");
                return;
            } else {
                System.out.println("Please enter a valid option.");
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
                case 1:
                    System.out.println("Checking Account Balance: $" + checking.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit to Checking: ");
                    double deposit = scanner.nextDouble();
                    if (checking.deposit(deposit)) {
                        System.out.println("Deposit successful.");
                    } else {
                        System.out.println("Deposit failed. Limit exceeded or invalid amount.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw from Checking: ");
                    double withdraw = scanner.nextDouble();
                    if (checking.withdraw(withdraw)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Withdrawal failed. Limit exceeded or insufficient funds.");
                    }
                    break;
                case 4:
                    System.out.print("Enter amount to transfer to Saving: ");
                    double toSaving = scanner.nextDouble();
                    if (checking.transferToSavings(savings, toSaving)) {
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer failed.");
                    }
                    break;
                case 5:
                    System.out.println("Saving balance: $" + savings.getBalance());
                    break;
                case 6:
                    System.out.print("Enter amount to deposit to Saving: ");
                    double saveDeposit = scanner.nextDouble();
                    if (savings.deposit(saveDeposit)) {
                        System.out.println("Deposit successful.");
                    } else {
                        System.out.println("Deposit failed. Limit exceeded or invalid amount.");
                    }
                    break;
                case 7:
                    System.out.print("Enter amount to transfer to Checking: ");
                    double toChecking = scanner.nextDouble();
                    if (savings.transferToChecking(checking, toChecking)) {
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer failed. Limit exceeded or insufficient funds.");
                    }
                    break;
                case 8:
                    double billAmount = utility.getNextBillAmount();
                    System.out.println("Paying bill of $" + billAmount + " due on " + utility.getDueDate());
                    if (checking.withdraw(billAmount)) {
                        utility.addBillPayment(billAmount);
                        System.out.println("Payment successful.");
                    } else {
                        System.out.println("Payment failed. Not enough balance.");
                    }
                    break;
                case 9:
                    System.out.println("Utility Bill History:");
                    for (String record : utility.getBillHistory()) {
                        System.out.println(record);
                    }
                    break;
                case 10:
                    System.out.println("Next Bill Amount: $" + utility.getNextBillAmount());
                    System.out.println("Due Date: " + utility.getDueDate());
                    break;
                case 11:
                    userManager.addUser(utility);
                    dataManager.saveAccounts(utility.getUsername(), checking, savings);
                    System.out.println("Thank you for using the ATM!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}