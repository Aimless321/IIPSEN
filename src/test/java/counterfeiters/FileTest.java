package counterfeiters;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit test to see if files exist in the classpath
 */
public class FileTest
{
    /*
     * Test if the firebase credits do exist
     */
    @Test
    public void firebaseCredsExist()
    {
        assertNotNull(getClass().getResourceAsStream("/firebase-creds.json"));
    }

    @Test
    public void styleCssExist()
    {
        assertNotNull(getClass().getResourceAsStream("/style.css"));
    }

    @Test
    public void backgroundsExist()
    {
        assertNotNull(getClass().getResource("/background/"));
    }

    @Test
    public void cardsExist()
    {
        assertNotNull(getClass().getResource("/cards/"));
    }

    @Test
    public void henchmanExist()
    {
        assertNotNull(getClass().getResource("/henchman/"));
    }

    @Test
    public void iconsExist()
    {
        assertNotNull(getClass().getResource("/icons/"));
    }

    @Test
    public void moneyExist()
    {
        assertNotNull(getClass().getResource("/money/"));
    }

    @Test
    public void playersExist()
    {
        assertNotNull(getClass().getResource("/players/"));
    }

    @Test
    public void soundsExist()
    {
        assertNotNull(getClass().getResource("/sounds/"));
    }

    @Test
    public void viewsExist()
    {
        assertNotNull(getClass().getResource("/views/"));
    }
}
