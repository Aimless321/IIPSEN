package counterfeiters;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /*
     * Rigorous Test :-)
     */
    @Test
    public void firebaseCredsDoExist()
    {
        assertNotNull(getClass().getResourceAsStream("/firebase-creds.json"));
    }
}
