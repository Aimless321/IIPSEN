package counterfeiters.controllers;

import counterfeiters.models.MoneyType;
import com.google.cloud.firestore.annotation.Exclude;
import counterfeiters.views.PopUpLaunderMoneyView;
import javafx.scene.control.Button;

/**
 * This controller only loads the correct views and provides further link to the BoardController(class) and model.
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 05-06-2019
 * */

public class PopUpLaunderMoneyController {
    private ApplicationController app;

    public enum LaunderType {HEALER, SUPERMARKET}
    private LaunderType type;
    private Button btn;

    public PopUpLaunderMoneyController(ApplicationController app) {
        this.app = app;
    }

    public void launderMoney(LaunderType type, Button btn) {
        this.type = type;
        this.btn = btn;

        app.loadView(PopUpLaunderMoneyView.class, app.popUpLaunderMoneyController);
    }

    /**
     * Checks which launderType it is.
     *
     * @author Ali Rezaa
     * @version 20-06-2019
     * */
    public void transferMoney(MoneyType qualityId, int qualityOne, int qualityTwo, int qualityThree){
        if (type == LaunderType.SUPERMARKET){
            app.boardController.transferMoneySupermarket(qualityId, qualityOne, qualityTwo, qualityThree);
            app.boardController.advancePolice();
        }

        if (type == LaunderType.HEALER){
            app.boardController.transferMoneyHealer(qualityId, qualityOne, qualityTwo, qualityThree);

            if(btn.getStyleClass().contains("police")) {
                app.boardController.advancePolice();
            }
        }

        app.boardController.henchmanPlaced(btn);
    }

    public boolean checkQualityQuantity(String quality, int amount) {
        if(app.boardController.board.checkQualityQuantity(amount, quality)) {
            return true;
        }
        else
            return false;
    }

    /**
     * Checks wich Laundertype it is.
     *
     * @author Ali Rezaa, Wesley Bijleveld
     * @version 20-06-2019
     * */
    public int getMaxAmount() {
        if(type == LaunderType.SUPERMARKET) {
            return 3;
        } else if(type == LaunderType.HEALER) {
            return 8;
        }

        return 0;
    }
    public int qualityCheck() {

        return app.boardController.board.policePawn.qualityCheck();
    }

    public boolean checkAmount(int curAmount) {
        return curAmount <= getMaxAmount();
    }

    @Exclude
    public LaunderType getLaunderType() {
        return type;
    }

    public int policePosition() {
        return app.boardController.board.policePawn.getPawnPosition();
    }
}
