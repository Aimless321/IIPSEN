package counterfeiters.controllers;

import counterfeiters.models.Game;

public class GameController {
    private ApplicationController app;
    public Game game;

    public GameController(ApplicationController applicationController) {
        this.app = applicationController;

        game = new Game();
    }

    public void deleteGame() {
        game = new Game();
    }
}
