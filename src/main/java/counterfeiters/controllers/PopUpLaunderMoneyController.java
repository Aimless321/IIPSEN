package counterfeiters.controllers;

import counterfeiters.views.Observer;
import counterfeiters.views.PopUpLaunderMoneyView;

public class PopUpLaunderMoneyController {
    private ApplicationController app;

    public void launderMoney() {
        app.loadView(PopUpLaunderMoneyView.class, app.popUpLaunderMoneyController);
    }

    public PopUpLaunderMoneyController(ApplicationController app) {
        this.app = app;
    }

    public void registerObserver(Observer observer) {app.accountController.registerObserver(observer);
    }
}
