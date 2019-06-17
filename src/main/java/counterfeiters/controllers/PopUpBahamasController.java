package counterfeiters.controllers;

import counterfeiters.views.PopUpBahamasView;

public class PopUpBahamasController {
    private ApplicationController app;

    public PopUpBahamasController(ApplicationController app) {
        this.app = app;
    }

    public void bahamas () {
        app.loadView(PopUpBahamasView.class, app.popUpBahamasController);
    }

    public boolean addAmountToBahamas(int amount, int id){
        if (app.boardController.board.checkMoney(amount, id)){
            app.boardController.board.game.localPlayer.updateMoneyPlus(amount, id);
            return true;
        }
        return false;
    }

    public boolean reduceAmountFromBahamas(int amount, int id) {
        if (app.boardController.board.checkMoney(amount, id)){
            app.boardController.board.game.localPlayer.updateMoneyReduce(amount, id);
            return true;
        }
        return false;
    }

    public void registerObserver(PopUpBahamasView popUpBahamasView) {
    }
}
