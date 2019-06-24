package counterfeiters;

import counterfeiters.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;

    @Before
    public void initGame() {
        game = new Game();

        Player host = new Player("host");

        game.createNewGame(host);
    }

    @Test
    public void localPlayerTest() {
        //Add player to see if it correctly updates
        Player player2 = new Player("player2");

        game.addPlayer(player2);
        game.updateData(game);

        //localplayer should have updated
        assertEquals(game.getPlayers().get(0), game.localPlayer);
    }

    @Test
    public void currentPlayerTest() {
        Player player2 = new Player("player2");
        game.addPlayer(player2);

        Player player3 = new Player("player3");
        game.addPlayer(player3);

        Player player4 = new Player("player4");
        game.addPlayer(player4);

        FirstPlayerPawn pawn = new FirstPlayerPawn();
        pawn.setFirstPlayer(player3);

        Player currentPlayer = game.getCurrentPlayer(pawn);
        assertEquals(player3, currentPlayer);

        game.nextTurn();
        currentPlayer = game.getCurrentPlayer(pawn);

        assertEquals(player4, currentPlayer);

        game.nextTurn();
        assertTrue(game.checkYourTurn(pawn));
    }

    @After
    public void deleteGameFromFb() {
        game.delete();
    }
}
