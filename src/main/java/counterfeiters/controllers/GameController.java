package counterfeiters.controllers;

import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.models.Printer;
import counterfeiters.models.PrinterUpgrade;
import counterfeiters.views.BoardView;
import counterfeiters.views.Observer;

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


    public void createGame(Player player) {
        //Add cards to host
        givePlayerCards(player);

        game.createNewGame(player);

        app.lobbyController.registerListeners();

        //Give the game to the board aswell
        app.boardController.board.game = game;
    }

    private void givePlayerCards(Player player) {
        player.addCard(app.boardController.givePlayerCard(new Printer()));
        player.addCard(app.boardController.givePlayerCard(new PrinterUpgrade()));
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
        app.boardController.board.game = game;

        Player client = new Player(app.accountController.getUsername());

        //Give the standard cards when the player joins, so they don't get overwritten
        givePlayerCards(client);

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
