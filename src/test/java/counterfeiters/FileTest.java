package counterfeiters;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

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
