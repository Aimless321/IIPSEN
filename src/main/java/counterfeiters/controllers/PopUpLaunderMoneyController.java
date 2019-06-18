package counterfeiters.controllers;

import counterfeiters.models.MoneyType;
import counterfeiters.views.Observer;
import counterfeiters.views.PopUpLaunderMoneyView;

public class PopUpLaunderMoneyController {
    private ApplicationController app;

    public enum LaunderType {HEALER, SUPERMARKET}
    private LaunderType type;

    public PopUpLaunderMoneyController(ApplicationController app) {
        this.app = app;
    }

    public void launderMoney(LaunderType type) {
        this.type = type;

        app.loadView(PopUpLaunderMoneyView.class, app.popUpLaunderMoneyController);
    }

    public void transferMoney(MoneyType qualityId, int qualityOne, int qualityTwo, int qualityThree){
        if (type == LaunderType.SUPERMARKET){
            app.boardController.transferMoneySupermarket(qualityId, qualityOne, qualityTwo, qualityThree);
        }

        if (type == LaunderType.HEALER){
            app.boardController.transferMoneyHealer(qualityId, qualityOne, qualityTwo, qualityThree);
        }
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

    public boolean checkAmount(int curAmount) {
        return curAmount <= getMaxAmount();
    }
}
