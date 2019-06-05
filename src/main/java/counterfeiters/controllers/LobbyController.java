package counterfeiters.controllers;

import counterfeiters.models.Game;
import counterfeiters.views.Observer;

public class LobbyController {

    private ApplicationController app;

    public LobbyController(ApplicationController applicationController) {
            this.app = applicationController;
        }

    public void registerObserver(Observer observer) {
     //   Game game = app.gameController.game;
     //   game.registerObserver(observer);
    }
}
