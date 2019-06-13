package counterfeiters.controllers;

import counterfeiters.views.Observer;
import counterfeiters.views.PopUpLaunderMoneyView;

public class PopUpLaunderMoneyController {
    private ApplicationController app;

    public PopUpLaunderMoneyController(ApplicationController app) {
        this.app = app;
    }

    public void launderMoney() {
        app.loadView(PopUpLaunderMoneyView.class, app.popUpLaunderMoneyController);
    }

    public void countMoney(int qualityId, int qualityOne, int qualityTwo, int qualityThree){
        app.boardController.countMoney(qualityId, qualityOne, qualityTwo, qualityThree);
    }

    public void registerObserver(Observer observer) {app.accountController.registerObserver(observer);
    }
}
