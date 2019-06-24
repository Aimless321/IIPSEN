package counterfeiters;

import counterfeiters.models.Account;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for the Account model
 */
public class AccountTest {

    @Test
    public void registerCheckCredentialsEmpty() {
        Account account = new Account();

        account.checkCredentials("", "", "");

        String expectedError = "Incorrect Entry!";
        assertEquals(expectedError, account.getTextField());
    }


    @Test
    public void registerCheckPasswordsDontMatch() {
        Account account = new Account();

        account.checkCredentials("JUnit Test", "password", "abcdefghijk");

        String expectedError = "Password does not match!";
        assertEquals(expectedError, account.getTextField());
    }

    @Test
    public void registerUsernameExists() {
        Account account = new Account();

        account.checkCredentials("hashuser", "password", "password");

        String expectedError = "Username already exist!";
        assertEquals(expectedError, account.getTextField());
    }

    @Test
    public void loginWrongUsername() {
        Account account = new Account();

        account.checkCredentials("qwertyuiopasdfghjklmnbvcxz", "password");

        String expectedError = "Username not found";
        assertEquals(expectedError, account.getTextField());
    }

    @Test
    public void loginWrongPassword() {
        Account account = new Account();

        account.checkCredentials("hashuser", "123456789");

        String expectedError = "Password incorrect";
        assertEquals(expectedError, account.getTextField());
    }
}
