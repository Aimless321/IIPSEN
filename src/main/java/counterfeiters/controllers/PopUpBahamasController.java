package counterfeiters.controllers;

import counterfeiters.models.MoneyType;
import counterfeiters.models.Player;
import counterfeiters.views.PopUpBahamasView;
import javafx.scene.control.Button;

/**
 * This controller only loads the correct views and provides further link to the BoardController(class) and model.
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 05-06-2019
 * */

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

    /**
     * If the given amount is less than the total amount of the Real money. The money will be reduced from the Real money,
     * and will be added to the Bahamas bank.
     *
     * @author Ali Rezaa
     * @version 20-06-2019
     * */
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

    /**
     * If the given amount is less than the total amount on the bank. The money will be reduced from the bank,
     * and will be added to the Real money.
     *
     * @author Ali Rezaa
     * @version 20-06-2019
     * */
    public boolean reduceAmountFromBahamas(int amount) {
        Player player = app.boardController.board.game.localPlayer;

        if (player.checkReduceBahamas(amount)){
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
