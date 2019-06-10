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
    Player player = new Player();
    Game game = new Game();


    public ScoreboardController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void registerObserver(Observer observer) {
        app.gameController.registerObserver(observer);
    }

    public Map<String, Integer> loadScores()
    {
        return app.gameController.game.loadScores();

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
