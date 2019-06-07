package counterfeiters.controllers;

import counterfeiters.models.Player;
import counterfeiters.views.LobbyView;
import javafx.stage.Stage;

public class MainMenuController {
    private ApplicationController app;

    public MainMenuController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void createLobbyPressed() {
        app.loadView(LobbyView.class, app.lobbyController);

        app.gameController.createGame(new Player(app.accountController.getUsername()));
    }

    public void joinLobbyPressed() {
        //TODO: Switch to the LobbyListView

    }

    public void loadGamePressed() {
        //TODO: Switch to the GameListView
    }
}
