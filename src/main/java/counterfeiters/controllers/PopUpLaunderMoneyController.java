package counterfeiters.controllers;

import counterfeiters.models.MoneyType;
import com.google.cloud.firestore.annotation.Exclude;
import counterfeiters.views.PopUpLaunderMoneyView;
import javafx.scene.control.Button;

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

    public void transferMoney(MoneyType qualityId, int qualityOne, int qualityTwo, int qualityThree){
        if (type == LaunderType.SUPERMARKET){
            app.boardController.transferMoneySupermarket(qualityId, qualityOne, qualityTwo, qualityThree);
        }

        if (type == LaunderType.HEALER){
            app.boardController.transferMoneyHealer(qualityId, qualityOne, qualityTwo, qualityThree);
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
