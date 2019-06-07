package counterfeiters.controllers;

import counterfeiters.models.FirebaseModel;
import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.LobbyListView;
import counterfeiters.views.LobbyView;
import javafx.stage.Stage;

public class MainMenuController {
    private ApplicationController app;

    public MainMenuController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void createLobbyPressed(Stage stage) {
        app.loadView(LobbyView.class, stage, app.lobbyController);

        //TODO: Add the right player from the accountcontroller
        app.gameController.createGame(new Player(true, "WesleyBijleveld", 1, 10));
    }

    public void joinLobbyPressed(Stage stage) {

        //TODO: Switch to the LobbyListView
        app.loadView(LobbyListView.class,stage,app.lobbyListController);

    }

    public void loadGamePressed() {
        //TODO: Switch to the GameListView
    }
}
