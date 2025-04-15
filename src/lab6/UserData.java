package lab6;

/**
 * UserData class stores all relevant account information for a user including
 * username, password, checking account, savings account, utility account, and account number.
 */
public class UserData {
    private String username;
    private String password;
    private CheckingAccount checking;
    private SavingsAccount savings;
    private UtilityCompany utility;
    private int accountNum;

    // Default constructor
    public UserData() {
    }

    /**
     * Constructor to initialize all user account data.
     *
     * @param username   the user's username
     * @param password   the user's password
     * @param checking   the user's checking account
     * @param savings    the user's savings account
     * @param utility    the user's utility account
     * @param accountNum the user's unique account number
     */
    public UserData(String username, String password, CheckingAccount checking, SavingsAccount savings,
                    UtilityCompany utility, int accountNum) {
        this.username = username;
        this.password = password;
        this.checking = checking;
        this.savings = savings;
        this.utility = utility;
        this.accountNum = accountNum;
    }

    // === Getters and Setters with basic validation where applicable ===

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        this.password = password;
    }

    public CheckingAccount getChecking() {
        return checking;
    }

    public void setChecking(CheckingAccount checking) {
        if (checking == null) {
            throw new IllegalArgumentException("Checking account cannot be null.");
        }
        this.checking = checking;
    }

    public SavingsAccount getSavings() {
        return savings;
    }

    public void setSavings(SavingsAccount savings) {
        if (savings == null) {
            throw new IllegalArgumentException("Savings account cannot be null.");
        }
        this.savings = savings;
    }

    public UtilityCompany getUtility() {
        return utility;
    }

    public void setUtility(UtilityCompany utility) {
        if (utility == null) {
            throw new IllegalArgumentException("Utility account cannot be null.");
        }
        this.utility = utility;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(int accountNum) {
        if (accountNum <= 0) {
            throw new IllegalArgumentException("Account number must be positive.");
        }
        this.accountNum = accountNum;
    }
}
