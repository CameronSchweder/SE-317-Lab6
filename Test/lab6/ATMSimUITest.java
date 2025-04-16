package lab6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;

import org.junit.jupiter.api.Test;

class ATMSimUITest {

	private UserManager userManager;
    private CheckingAccount checking;
    private UserData userData;
    
    @BeforeEach
    public void setup() {
        userManager = new UserManager("test_users.txt");
        UtilityCompany user = new UtilityCompany("john", "secret", 123456);
        checking = new CheckingAccount(100); // assign to class field
        SavingsAccount savings = new SavingsAccount(200);
        userData = new UserData("john", "secret", checking, savings, user, 123456);
        userManager.addUser(userData);
    }


    // TR-1: Login with correct username and password
    @Test
    public void testLoginValidCredentials() {
        assertTrue(userManager.authenticate("john", "secret"));
    }

    // TR-2: Login with incorrect username
    @Test
    public void testLoginInvalidUsername() {
        assertFalse(userManager.authenticate("jack", "secret"));
    }

    // TR-3: Login with incorrect password
    @Test
    public void testLoginInvalidPassword() {
        assertFalse(userManager.authenticate("john", "wrongpass"));
    }
    


    // TR-1: Deposit $0
    @Test
    public void testDepositZero() {
        assertFalse(userData.getChecking().deposit(0));
        assertEquals(100, userData.getChecking().getBalance());
    }

    // TR-2: Deposit -$1
    @Test
    public void testDepositNegative() {
        assertFalse(checking.deposit(-1));
        assertEquals(100, checking.getBalance());
    }

    // TR-3: Deposit maximum allowed value
    @Test
    public void testDepositMaximum() {
        assertTrue(checking.deposit(5000));
        assertEquals(5100, checking.getBalance());
    }

    // TR-4: Deposit valid small value ($1)
    @Test
    public void testDepositSmallValue() {
        assertTrue(checking.deposit(1));
        assertEquals(101, checking.getBalance());
    }


}
