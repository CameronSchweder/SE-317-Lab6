package lab6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ATMSystemTest {

    private CheckingAccount checking;
    private SavingsAccount savings;
    private UtilityCompany utility;
    private UserManager userManager;
    private UserData currentUser;

    @BeforeEach
    public void setUp() {
        checking = new CheckingAccount(200);
        savings = new SavingsAccount(100);
        utility = new UtilityCompany("testUser", "password123", 123456);
        userManager = new UserManager();
	    currentUser = new UserData("testUser", "password123", checking, savings, utility, 123456);

    }

    // --- Checking Account Tests ---

    @Test
    public void testCheckingDepositValid() {
        assertTrue(checking.deposit(100));
        assertEquals(300, checking.getBalance(), 0.01);
    }

    @Test
    public void testCheckingDepositNegative() {
        assertFalse(checking.deposit(-50));
        assertEquals(200, checking.getBalance(), 0.01);
    }

    @Test
    public void testCheckingWithdrawValid() {
        assertTrue(checking.withdraw(100));
        assertEquals(100, checking.getBalance(), 0.01);
    }

    @Test
    public void testCheckingWithdrawInsufficientFunds() {
        assertFalse(checking.withdraw(300));
        assertEquals(200, checking.getBalance(), 0.01);
    }

    // --- Savings Account Tests ---

    @Test
    public void testSavingsDeposit() {
        assertTrue(savings.deposit(50));
        assertEquals(150, savings.getBalance(), 0.01);
    }

    @Test
    public void testTransferFromCheckingToSaving() {
        assertTrue(checking.withdraw(100));
        assertTrue(savings.deposit(100));
        assertEquals(100, checking.getBalance(), 0.01);
        assertEquals(200, savings.getBalance(), 0.01);
    }

    @Test
    public void testTransferFromSavingToChecking() {
        assertTrue(savings.transferToChecking(checking, 50));
        assertEquals(250, checking.getBalance(), 0.01);
        assertEquals(50, savings.getBalance(), 0.01);
    }

    // --- Utility Tests ---

    @Test
    public void testPayUtilityBillSufficientFunds() {
        double bill = utility.getNextBillAmount();
        assertTrue(checking.withdraw(bill));
        utility.addBillPayment(bill);
        assertEquals(1, utility.getBillHistory().size());
    }

    @Test
    public void testPayUtilityBillInsufficientFunds() {
        checking = new CheckingAccount(10);
        double bill = utility.getNextBillAmount();
        assertFalse(checking.withdraw(bill));
        assertEquals(0, utility.getBillHistory().size());
    }

    @Test
    public void testUtilityBillHistoryLimit() {
        for (int i = 1; i <= 4; i++) {
            utility.addBillPayment(i * 100);
        }
        assertEquals(3, utility.getBillHistory().size());
    }

}
