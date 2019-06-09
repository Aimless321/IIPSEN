package counterfeiters.controllers;

import counterfeiters.views.MainMenuView;
import counterfeiters.views.RulesView;

public class ScoreboardController {
    private ApplicationController app;

    public ScoreboardController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void menuButtonPressed()
    {
        app.loadView(MainMenuView.class, app.mainMenuController);
    }
    public void exitButtonPressed()
    {
        app.quit();
    }

    public void rulesButtonPressed()
    {
        app.loadView(RulesView.class, app.rulesController);
    }
}
