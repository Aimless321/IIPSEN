package counterfeiters.controllers;

import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.Observer;

public class GameController {
    private ApplicationController app;
    public Game game;

    public GameController(ApplicationController applicationController) {
        this.app = applicationController;

        game = new Game();
    }

    public void registerObserver(Observer observer) {
        game.registerObserver(observer);
    }

    public void deleteGame() {
        game = new Game();
    }

    public void createGame(Player player) {
        game.createNewGame(player);

        app.lobbyController.registerListeners();
    }

    public void updateMoney(int qualityOneMoney, int qualityTwoMoney, int qualityThreeMoney, int totalRealMoney, int totalBankMoney, int qId){
        game.updateMoney(qualityOneMoney, qualityTwoMoney, qualityThreeMoney, totalRealMoney, totalBankMoney, qId);
    }

    public void updateData(Game updateGame) {
        game.updateData(updateGame);
    }
}
