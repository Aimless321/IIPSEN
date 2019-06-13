package counterfeiters.controllers;

import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.Board;
import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import counterfeiters.views.RulesView;
import javafx.scene.image.Image;

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

    //TODO: remove hardcode, place game.
    public Game loadScores() {

        app.gameController.game.getScores(app.gameController.game.getPlayers());
        FirebaseService fb = FirebaseService.getInstance();


        return fb.get("games", "dtoKv6O75rwX94mXvm2g").toObject(Board.class).game;
    }

    public Map<String,Integer> getScores(ArrayList<Player> players)
    {
        return app.gameController.game.getScores(players);
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
