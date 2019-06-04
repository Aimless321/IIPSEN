package counterfeiters.controllers;

import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import javafx.stage.Stage;

public class LobbyController {
    private ApplicationController app;

    public LobbyController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void registerObserver(Observer observer) {
        Game game = app.gameController.game;
        game.registerObserver(observer);
    }

    public void leaveButtonPressed(Stage stage) {
        Game game = app.gameController.game;

        //TODO: Get real player
        Player player = game.getPlayers().get(0);

        player.leaveLobby(game);

        app.gameController.deleteGame();

        //TODO: Go to the lobbylistview, not the mainmenu
        app.loadView(MainMenuView.class, stage, app.mainMenuController);
    }

    public void rulesButtonPressed() {

    }

    public void startButtonPressed() {

    }
}
