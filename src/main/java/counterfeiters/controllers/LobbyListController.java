package counterfeiters.controllers;

import com.google.cloud.firestore.*;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.FirebaseModel;
import counterfeiters.models.Game;
import counterfeiters.views.LobbyView;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This controller loads the linked views and directs the chosen game(when lobby clicked) to the gamecontroller adn the lobbycontroller
 *
 * @author Melissa basgol
 * */

public class LobbyListController {
    private ApplicationController app;
    public FirebaseModel firebaseModel;
    public Game chosenGame ;
    private ListenerRegistration listener;

    public LobbyListController(ApplicationController applicationController) {
        this.app = applicationController;
        firebaseModel = new FirebaseModel();
    }

    public void registerObserver(Observer observer) {
        firebaseModel.registerObserver(observer);
    }

    /**
     * Create the firebase listener, for the collection lobbies.
     */
    public void registerListeners() {
        FirebaseService fb = FirebaseService.getInstance();

        listener = fb.listenToCollection("lobbies", new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirestoreException e) {
                    if (e != null) {
                        System.err.println("Listen failed:" + e);
                        return;
                    }

                    List<DocumentChange> changes = querySnapshot.getDocumentChanges();
                    for(DocumentChange change : changes) {
                        System.out.println(change.getDocument().getData().toString());
                    }

                    //if(querySnapshot != null && !querySnapshot.isEmpty()) {
                        //To the model for update
                     //   updateLobbiesModel();

                    //}

               // }
            }
        });
    }

    public void updateLobbiesModel() {
        firebaseModel.updateLobbies();
    }

    public void leaveButtonPressed() {
        listener.remove();

        //TODO: Go to the MainMenuView
        app.loadView(MainMenuView.class, app.mainMenuController);
    }

    public void clickLobby(String chosenGame){
        listener.remove();

        FirebaseService fb = FirebaseService.getInstance();
        Game game = fb.get("lobbies", chosenGame).toObject(Game.class);

            app.gameController.joinGame(chosenGame);
            app.loadView(LobbyView.class, app.lobbyController);

    }

    public void rulesButtonPressed() {
        //TODO: Go to the rules view
    }

}





