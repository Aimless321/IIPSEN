package counterfeiters.controllers;

import counterfeiters.models.Game;
import counterfeiters.views.Observer;

public class BoardController {
    private ApplicationController app;
    public Game game;

    public BoardController(ApplicationController applicationController) {
        this.app = applicationController;

        game = new Game();
    }

    public void registerObserver(Observer observer) {
        game.registerObserver(observer);
    }
}
