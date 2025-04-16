package lab6;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerStorageTest {

    private static final String TEST_FILE = "test_users.txt";
    private UserManager userManager;

    @BeforeEach
    public void setup() throws IOException {
        // Replace file for isolated testing
        Files.deleteIfExists(Paths.get(TEST_FILE));
        Files.createFile(Paths.get(TEST_FILE));
        userManager = new UserManager(TEST_FILE); // constructor overloaded to accept file path
    }

    @AfterEach
    public void cleanup() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    // --- Null Storage Test ---

    @Test
    public void testEmptyStorageFile() {
        assertTrue(userManager.getAllUsernames().isEmpty());
    }

    // --- Null Element with Multiple Elements ---

    @Test
    public void testNullElementAmongValidUsers() throws IOException {
    	List<String> lines = Arrays.asList(
    		    "john|pass|123|null|40.0|2025-05-10|100.0|200.0",
    		    "invalid_line",
    		    "jane|pw123|321|100.0,200.0|60.0|2025-05-11|300.0|400.0"
    		);

        Files.write(Paths.get(TEST_FILE), lines);
        userManager = new UserManager(TEST_FILE);
        Set<String> usernames = userManager.getAllUsernames();
       assertTrue(usernames.contains("john"));
       assertTrue(usernames.contains("jane"));
        assertEquals(2, usernames.size()); // invalid_line should be skipped
    }

    // --- Null Single Element ---

    @Test
    public void testNullSingleLineOnly() throws IOException {
        Files.writeString(Paths.get(TEST_FILE), "invalid_line_with_no_delimiters");
        userManager = new UserManager(TEST_FILE);

        assertTrue(userManager.getAllUsernames().isEmpty());
    }

    // --- Incompatible Types (account number as string) ---

    @Test
    public void testIncompatibleAccountNumberType() throws IOException {
        Files.writeString(Paths.get(TEST_FILE), "bob|1234|abc|null|50.0|2025-05-12"); // abc instead of int
        userManager = new UserManager(TEST_FILE);

        assertTrue(userManager.getAllUsernames().isEmpty());
    }

    // --- Empty Elements ---

    @Test
    public void testEmptyFields() throws IOException {
        Files.writeString(Paths.get(TEST_FILE), "anna||1234|null|40.0|2025-05-13"); // missing password
        userManager = new UserManager(TEST_FILE);

        assertTrue(userManager.getAllUsernames().isEmpty());
    }

    // --- Normal Case: Single User ---

    @Test
    public void testSingleValidUserWithUserData() {
        UserManager userManager = new UserManager(TEST_FILE);

        UserData user = new UserData(
            "sam", "sampass",
            new CheckingAccount(500.0),
            new SavingsAccount(1000.0),
            new UtilityCompany("sam", "sampass", 101010),
            101010
        );

        userManager.addUser(user);

        UserManager reloadedManager = new UserManager(TEST_FILE);
        assertTrue(reloadedManager.authenticate("sam", "sampass"));
    }

    @Test
    public void testMultipleUsersWithUserData() {
        UserManager userManager = new UserManager(TEST_FILE);

        UserData user1 = new UserData(
            "alice", "pw1",
            new CheckingAccount(300.0),
            new SavingsAccount(800.0),
            new UtilityCompany("alice", "pw1", 111111),
            111111
        );

        UserData user2 = new UserData(
            "bob", "pw2",
            new CheckingAccount(400.0),
            new SavingsAccount(900.0),
            new UtilityCompany("bob", "pw2", 222222),
            222222
        );

        userManager.addUser(user1);
        userManager.addUser(user2);

        UserManager reloadedManager = new UserManager(TEST_FILE);

        assertTrue(reloadedManager.authenticate("alice", "pw1"));
        assertTrue(reloadedManager.authenticate("bob", "pw2"));
        assertEquals(2, reloadedManager.getAllUsernames().size());
    }
}