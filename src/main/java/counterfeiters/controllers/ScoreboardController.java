package counterfeiters.controllers;

import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import counterfeiters.views.RulesView;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.*;

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

    public void menuButtonPressed() {
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
