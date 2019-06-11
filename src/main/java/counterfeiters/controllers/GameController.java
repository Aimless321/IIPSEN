package counterfeiters.controllers;

import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.BoardView;
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

    public void loadFromSavedGame(Game game) {
        this.game = game;

        //Firebase doesnt load the localplayer, so we have to set it
        this.game.localPlayer = game.getPlayers().get(0);

        FirebaseService fb = FirebaseService.getInstance();
        fb.setClass("lobbies", game.getGameId(), game);

        app.lobbyController.registerListeners();
    }

    public void joinGame(String gameid) {
        FirebaseService fb = FirebaseService.getInstance();
        Game game = fb.get("lobbies", gameid).toObject(Game.class);
        this.game = game;

        Player client = new Player(app.accountController.getUsername());
        game.localPlayer = client;
        game.addPlayer(client);

        app.lobbyController.registerListeners();
    }

    public void updateData(Game updateGame) {
        game.updateData(updateGame);
    }

    public void updateMoney(int qId, String character, int amount) {
        game.updateMoney(qId, character, amount);
    }

    public void setStartRound(int numRound) {
        game.roundChanged(numRound);
    }
    public void startGame(){
        app.loadView(BoardView.class, app.boardController);
    }
}
