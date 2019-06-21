package counterfeiters;

import counterfeiters.events.EventHandler;
import counterfeiters.models.Account;
import counterfeiters.models.FirstPlayerPawn;
import counterfeiters.models.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the FirstPlayerPawn model
 */
public class FirstPlayerPawnTest {

    @Test
    public void switchPlayerRoundEnd() {
        FirstPlayerPawn firstPlayerPawn = new FirstPlayerPawn();

        Player player1 = new Player("one");
        firstPlayerPawn.setFirstPlayer(player1);

        Player player2 = new Player("two");
        firstPlayerPawn.setNextFirstPlayer(player2);

        //Call the onRoundEnd event
        EventHandler.getInstance().endRound();

        assertEquals(player2, firstPlayerPawn.getFirstPlayer());
    }

}
