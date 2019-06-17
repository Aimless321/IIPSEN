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

    public void registerObserver(PopUpBahamasView popUpBahamasView) {
    }
}
