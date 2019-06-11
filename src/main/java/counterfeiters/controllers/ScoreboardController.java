package counterfeiters.controllers;

import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import counterfeiters.views.RulesView;
import javafx.stage.Stage;


import java.util.*;

/**
 * This controller is the link between the scoreboard view and the game controller, via the applicationcontroller, that contains the game model.
 *
 * @author Robin van den Berg
 */

public class ScoreboardController {
    private ApplicationController app;


    public ScoreboardController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void registerObserver(Observer observer) {
        app.gameController.registerObserver(observer);
    }

    public Map<String, Integer> loadScores()
    {
        return app.gameController.loadScores();

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
