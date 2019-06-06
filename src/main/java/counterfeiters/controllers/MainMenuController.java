package counterfeiters.controllers;

import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.LobbyView;
import javafx.stage.Stage;

public class MainMenuController {
    private ApplicationController app;

    public MainMenuController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void createLobbyPressed(Stage stage) {
        app.loadView(LobbyView.class, stage, app.lobbyController);

        app.gameController.createGame(new Player(app.accountController.getUsername()));
    }

    public void joinLobbyPressed(Stage stage) {
        //TODO: Switch to the LobbyListView

    }

    public void loadGamePressed() {
        //TODO: Switch to the GameListView
    }
}
