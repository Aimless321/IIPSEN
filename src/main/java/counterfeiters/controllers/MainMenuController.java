package counterfeiters.controllers;

import counterfeiters.models.FakeMoney;
import counterfeiters.models.Player;
import counterfeiters.views.GameListView;
import counterfeiters.views.LobbyListView;
import counterfeiters.views.LobbyView;

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
        app.loadView(LobbyListView.class, app.lobbyListController);

    }

    public void loadGamePressed() {
        //TODO: Switch to the GameListView
        app.loadView(GameListView.class, app.gameListController);
    }
}
