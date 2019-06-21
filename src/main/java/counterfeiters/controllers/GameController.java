package counterfeiters.controllers;

import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.*;
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

    /**
     * Create a new game model, so all of the old data is gone.
     */
    public void deleteGame() {
        game = new Game();
    }

    /**
     * Creates a new game with one player in it.
     * @param player the host of the new game
     */
    public void createGame(Player player) {
        //Add cards to host
        givePlayerCards(player);

        game.createNewGame(player);

        app.lobbyController.registerListeners();

        //Give the game to the board as well
        app.boardController.board.game = game;
    }

    /**
     * Gives the standard cards to the player.
     * All players receive one printer and a random printerupgrade at the start.
     * @param player
     */
    private void givePlayerCards(Player player) {
        player.addCard(app.boardController.givePlayerCard(new Printer()));
        player.addCard(app.boardController.givePlayerCard(new PrinterUpgrade()));
    }

    /**
     * Load a saved game, and open a lobby for it.
     * @param game the game that has to be loaded.
     */
    public void loadFromSavedGame(Game game) {
        this.game = game;

        //Firebase doesnt load the localplayer, so we have to set it
        this.game.localPlayer = game.getPlayers().get(0);

        //Reset the round back to 0, so the game isn't started yet
        this.game.setRound(0);
        this.game.setNumPlayers(1);

        FirebaseService fb = FirebaseService.getInstance();
        fb.setClass("lobbies", game.getGameId(), game);

        app.lobbyController.registerListeners();
    }

    /**
     * Join a game (lobby), and update the firebase.
     * Lets the localplayer join a lobby, and pushes the changes to firebase.
     * @param gameid
     */
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

    /**
     * Called when a loaded game is joined.
     * The difference with joining a normal game, is that the player does not need cards
     * and has a fixed playerid from lastgame.
     * @param gameid
     */
    public void joinLoadedGame(String gameid) {
        FirebaseService fb = FirebaseService.getInstance();
        Board board = fb.get("games", gameid).toObject(Board.class);

        this.game = board.game;
        app.boardController.board = board;

        game.localPlayer = game.getPlayerFromUserName(app.accountController.getUsername());

        app.lobbyController.registerListeners();
    }

    /**
     * Update all of the data that is stored in firebase in the model.
     * @param updateGame
     */
    public void updateData(Game updateGame) {
        game.updateData(updateGame);
    }

    /**
     * Called when the start button is pressed on the lobbyview.
     */
    public void setStartRound() {
        game.startGame();
    }
}
