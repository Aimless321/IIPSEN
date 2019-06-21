package counterfeiters;

import counterfeiters.models.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the player model
 */
public class PlayerTest {

    @Test
    public void printMoneyTest() {
        Player player = new Player("user");

        //Add printer cards
        player.addCard(new PrinterUpgrade(PrinterUpgrade.UpgradeType.PAINT));
        player.addCard(new PrinterUpgrade(PrinterUpgrade.UpgradeType.PAPER));
        player.addCard(new Printer());
        player.addCard(new Printer());

        //Print money
        player.printMoney();

        //2 upgrades with 2 printers should be 4 of quality two
        assertEquals(4, player.getFakeMoney().getQualityTwo());

        //Add printer and upgrade and print again
        player.addCard(new PrinterUpgrade(PrinterUpgrade.UpgradeType.HOLOGRAM));
        player.addCard(new Printer());

        player.printMoney();

        //3 upgrades with 3 printers should be 6 of quality three
        assertEquals(6, player.getFakeMoney().getQualityThree());
    }

    @Test
    public void duplicateUpgradeTest() {
        Player player = new Player("user");

        //Add printer cards
        player.addCard(new PrinterUpgrade(PrinterUpgrade.UpgradeType.PAINT));
        player.addCard(new PrinterUpgrade(PrinterUpgrade.UpgradeType.PAINT));
        player.addCard(new Printer());

        player.printMoney();

        //Two of the same upgrades gives only one quality point
        assertEquals(2, player.getFakeMoney().getQualityOne());
    }

    @Test
    public void characterTest() {
        Player player = new Player();

        player.setPlayerId(1);

        assertEquals("croc", player.getCharacterName());

        player.setPlayerId(2);
        assertEquals("deer", player.getCharacterName());

        player.setPlayerId(3);
        assertEquals("herron", player.getCharacterName());

        player.setPlayerId(4);
        assertEquals("hippo", player.getCharacterName());

        player.setPlayerId(10);
        assertEquals("croc", player.getCharacterName());
    }

    @Test
    public void updateMoneyPlusTest() {
        Player player = new Player("user");

        player.getFakeMoney().setQualityOne(1);
        player.getFakeMoney().setQualityTwo(1);
        player.getFakeMoney().setQualityThree(1);

        player.updateMoneyPlus(MoneyType.FAKE_ONE, 1);
        assertEquals(2, player.getFakeMoney().getQualityOne());

        player.updateMoneyPlus(MoneyType.FAKE_TWO, 1);
        assertEquals(2, player.getFakeMoney().getQualityTwo());

        player.updateMoneyPlus(MoneyType.FAKE_THREE, 1);
        assertEquals(2, player.getFakeMoney().getQualityThree());

        player.updateMoneyPlus(MoneyType.REAL, 10);
        //player start with 40
        assertEquals(50, player.getRealMoney().getTotalMoney());

        //Set bahamas
        player.getBahamasBank().setTotalBankMoney(50);

        player.updateMoneyPlus(MoneyType.BAHAMAS, 50);
        assertEquals(100, player.getBahamasBank().getTotalBankMoney());
    }

    @Test
    public void updateMoneyReduceTest() {
        Player player = new Player("user");

        player.getFakeMoney().setQualityOne(5);
        player.getFakeMoney().setQualityTwo(5);
        player.getFakeMoney().setQualityThree(5);

        player.updateMoneyReduce(MoneyType.FAKE_ONE, 1);
        assertEquals(4, player.getFakeMoney().getQualityOne());

        player.updateMoneyReduce(MoneyType.FAKE_TWO, 1);
        assertEquals(4, player.getFakeMoney().getQualityTwo());

        player.updateMoneyReduce(MoneyType.FAKE_THREE, 1);
        assertEquals(4, player.getFakeMoney().getQualityThree());

        player.updateMoneyReduce(MoneyType.REAL, 10);
        //player start with 40
        assertEquals(30, player.getRealMoney().getTotalMoney());

        //Set bahamas
        player.getBahamasBank().setTotalBankMoney(100);

        player.updateMoneyReduce(MoneyType.BAHAMAS, 50);
        assertEquals(50, player.getBahamasBank().getTotalBankMoney());
    }
}
