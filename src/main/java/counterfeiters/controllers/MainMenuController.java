package counterfeiters.controllers;

import counterfeiters.models.Player;
import counterfeiters.views.GameListView;
import counterfeiters.views.LobbyListView;
import counterfeiters.views.LobbyView;

/**
 * Controller of the MainMenuView, passes all of the actions to the model, and loads the right views.
 * @author Wesley Bijleveld
 */
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
        app.loadView(LobbyListView.class, app.lobbyListController);

    }

    public void loadGamePressed() {
        app.loadView(GameListView.class, app.gameListController);
    }

    public void exitButtonPressed() {
        app.quit();
    }
}
