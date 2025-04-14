package lab6;

public class UserData {
	private String username;
	private String password;
	private CheckingAccount checking;
	private SavingsAccount savings;
	private UtilityCompany utility;

	public UserData() {
	}

	public UserData(String username, String password, CheckingAccount checking, SavingsAccount savings,
			UtilityCompany utility) {
		this.username = username;
		this.password = password;
		this.checking = checking;
		this.savings = savings;
		this.utility = utility;
	}

	// Getters and Setters

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CheckingAccount getChecking() {
		return checking;
	}

	public void setChecking(CheckingAccount checking) {
		this.checking = checking;
	}

	public SavingsAccount getSavings() {
		return savings;
	}

	public void setSavings(SavingsAccount savings) {
		this.savings = savings;
	}

	public UtilityCompany getUtility() {
		return utility;
	}

	public void setUtility(UtilityCompany utility) {
		this.utility = utility;
	}
}
