package counterfeiters.controllers;

import counterfeiters.models.Board;
import counterfeiters.models.Board;
import counterfeiters.views.Observer;

public class BoardController {
    public ApplicationController app;
    public Board board;

    public BoardController(ApplicationController applicationController) {
        this.app = applicationController;

        board = new Board();
    }

    public void registerObserver(Observer observer) {
        board.registerObserver(observer);
        app.blackMarketController.registerObserver(observer);
    }


}
