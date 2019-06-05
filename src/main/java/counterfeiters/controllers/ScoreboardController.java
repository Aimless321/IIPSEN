package counterfeiters.controllers;

import counterfeiters.views.MainMenuView;
import counterfeiters.views.RulesView;
import javafx.stage.Stage;

public class ScoreboardController {
    private ApplicationController app;

    public ScoreboardController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void menuButtonPressed(Stage stage)
    {
        app.loadView(MainMenuView.class, stage, app.mainMenuController);
    }
    public void exitButtonPressed()
    {
        app.quit();
    }

    public void rulesButtonPressed(Stage stage)
    {
        app.loadView(RulesView.class, stage, app.rulesController);
    }
}
