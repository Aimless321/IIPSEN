package counterfeiters.controllers;

import counterfeiters.models.MoneyType;
import counterfeiters.models.Player;
import counterfeiters.views.PopUpBahamasView;
import javafx.scene.control.Button;

public class PopUpBahamasController {
    private ApplicationController app;
    private Button btn;

    public PopUpBahamasController(ApplicationController app) {
        this.app = app;
    }

    public void bahamas (Button btn) {
        this.btn = btn;

        app.loadView(PopUpBahamasView.class, app.popUpBahamasController);
    }

    public boolean addAmountToBahamas(int amount){
        Player player = app.boardController.board.game.localPlayer;

        if (player.checkAddBahamas(amount)){
            player.updateMoneyPlus(MoneyType.BAHAMAS, amount);
            player.updateMoneyReduce(MoneyType.REAL, amount);

            app.boardController.board.updateFirebase();
            app.boardController.board.notifyAllObservers();
            return true;
        }
        return false;
    }

    public boolean reduceAmountFromBahamas(int amount) {
        Player player = app.boardController.board.game.localPlayer;

        if (player.checkRedueBahamas(amount)){
            player.updateMoneyReduce(MoneyType.BAHAMAS, amount);
            player.updateMoneyPlus(MoneyType.REAL, amount);

            app.boardController.board.updateFirebase();
            app.boardController.board.notifyAllObservers();
            return true;
        }
        return false;
    }

    public void placeHenchman() {
        app.boardController.henchmanPlaced(btn);
    }
}
