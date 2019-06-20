package counterfeiters.controllers;

import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.*;
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

        //Give the game to the board as well
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

        //Reset the round back to 0, so the game isn't started yet
        this.game.setRound(0);
        this.game.setNumPlayers(1);

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

    public void joinLoadedGame(String gameid) {
        FirebaseService fb = FirebaseService.getInstance();
        Board board = fb.get("games", gameid).toObject(Board.class);

        this.game = board.game;
        app.boardController.board = board;

        game.localPlayer = game.getPlayerFromUserName(app.accountController.getUsername());

        app.lobbyController.registerListeners();
    }

    public void updateData(Game updateGame) {
        game.updateData(updateGame);
    }

    public void setStartRound(int numRound) {
        game.roundChanged(numRound);
    }
    public void startGame(){
        app.loadView(BoardView.class, app.boardController);
    }
}
