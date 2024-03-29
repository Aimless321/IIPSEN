package counterfeiters.controllers;

import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.QuerySnapshot;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.Board;
import counterfeiters.models.FirebaseModel;
import counterfeiters.views.LobbyView;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import counterfeiters.views.RulesView;

import javax.annotation.Nullable;

/**
 * This controller loads the linked views and directs the chosen game(when game clicked) to the gamecontroller adn the lobbycontroller
 *
 * @author Melissa basgol
 * */

public class GameListController {

    private ApplicationController app;
    public FirebaseModel firebaseModel;


    public GameListController(ApplicationController applicationController) {
        this.app = applicationController;
        firebaseModel = new FirebaseModel();
    }

    public void registerObserver(Observer observer) {
        firebaseModel.registerObserver(observer);
    }

    /**
     * Create the firebase listener, for the collection games.
     */
    public void registerListeners() {
        FirebaseService fb = FirebaseService.getInstance();

        fb.listenToCollection("games", new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirestoreException e) {
                if (e != null) {
                    System.err.println("Listen failed:" + e);
                    return;
                }
                //To the model for update
                updateGamesModel();


                // }
            }
        });
    }

    public void updateGamesModel() {
        firebaseModel.updateGames(app.accountController.getUsername());
    }

    public void leaveButtonPressed() {
        app.loadView(MainMenuView.class, app.mainMenuController);
    }

    public void gameSelected(String gameid){
        Board board = app.boardController.createFromSaved(gameid);

        app.gameController.loadFromSavedGame(board.game);

        app.loadView(LobbyView.class, app.lobbyController);
    }

    public void rulesButtonPressed() {
        app.loadView(RulesView.class, app.rulesController);
    }

}







