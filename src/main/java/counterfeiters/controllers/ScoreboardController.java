package counterfeiters.controllers;

import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.Board;
import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.Observer;
import counterfeiters.views.RulesView;

import java.util.ArrayList;
import java.util.Map;

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

    /**
     *  returns the game ID of the game, so the view knows what gamescores to load
     *
     * @author Robin van den Berg
     * @return the game of the players
     */

    public Game loadScores() {

        app.gameController.game.getScores(app.gameController.game.getPlayers());
        FirebaseService fb = FirebaseService.getInstance();


        return fb.get("games",app.boardController.board.game.getGameId()).toObject(Board.class).game;
    }

    /**
     * Used as bridge between the view and the game model, to load the map with scores and players.
     *
     * @author Robin van den Berg
     * @param players the players of the game
     * @return the scores of the players and themselves
     */

    public Map<String,Integer> getScores(ArrayList<Player> players) {
        return app.gameController.game.getScores(players);
    }

    public void exitButtonPressed() {
        //Delete game from fb
        FirebaseService fb = FirebaseService.getInstance();
        fb.delete("games", app.boardController.board.game.getGameId());

        app.quit();
    }

    public void rulesButtonPressed()
    {
        app.loadView(RulesView.class, app.rulesController);
    }
}
