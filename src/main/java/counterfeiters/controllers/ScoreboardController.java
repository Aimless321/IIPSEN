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
    public ApplicationController app;


    public ScoreboardController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void registerObserver(Observer observer) {
        app.gameController.registerObserver(observer);
    }


    public Game loadScores() {
        FirebaseService fb = FirebaseService.getInstance();


        return fb.get("games", "dtoKv6O75rwX94mXvm2g").toObject(Board.class).game;
    }

    public Map<String, Integer> getScores(ArrayList<Player> players) {
        Map<String, Integer> scores = new HashMap();
        LinkedHashMap<String, Integer> sortedScores = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            String name = players.get(i).getUserName();
            int score = (players.get(i).getScore());
            scores.put(name, score);

        }

        for (Map.Entry<String, Integer> keys : scores.entrySet()) {
            list.add(Integer.valueOf(keys.getValue()));
        }
        Collections.sort(list, Collections.reverseOrder());

        for (Integer score : list) {
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                if (entry.getValue().equals(score)) {
                    sortedScores.put(entry.getKey(), score);
                }
            }
        }

        return sortedScores;
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
