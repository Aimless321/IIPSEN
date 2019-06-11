package counterfeiters.controllers;

import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.Observer;
import java.util.Map;

/**
 * The controller of the game model.
 * Handles loading, joining and creating of a game.
 * @author Wesley Bijleveld
 */
public class GameController {
    private ApplicationController app;
    public Game game;

    public GameController(ApplicationController applicationController) {
        this.app = applicationController;

        game = new Game();
    }

    public void registerObserver(Observer observer) {
        game.registerObserver(observer);
    }

    public void deleteGame() {
        game = new Game();
    }

    public Map<String, Integer> loadScores()
    {
        return game.loadScores();

    }

    public void createGame(Player player) {
        game.createNewGame(player);

        app.lobbyController.registerListeners();

        //Give the game to the board aswell
        app.boardController.board.game = game;
    }

    public void updateData(Game updateGame) {
        game.updateData(updateGame);
    }
}
