package counterfeiters;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test for simple App.
 */
public class FileTest
{
    /*
     * Test if the firebase credits do exist
     */
    @Test
    public void firebaseCredsDoExist()
    {
        assertNotNull(getClass().getResourceAsStream("/firebase-creds.json"));
    }
}
