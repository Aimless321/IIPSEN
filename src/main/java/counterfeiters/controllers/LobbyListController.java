package counterfeiters.controllers;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.FirebaseModel;
import counterfeiters.models.Game;
import counterfeiters.views.Observer;

public class LobbyListController {

    private ApplicationController app;
    public FirebaseModel lobbyList;

    public LobbyListController(ApplicationController applicationController) {
        this.app = applicationController;
        lobbyList = new FirebaseModel();
    }

    public void registerObserver(Observer observer) {
        lobbyList.registerObserver(observer);
    }

    public void updateData(Game updateGame) {
        //lobbyList.updateData();
    }


    public void registerListeners() {
        FirebaseService fb = FirebaseService.getInstance();

        //Listen for changes in the lobby
        //fb.listen("lobbies", app.gameController.game.getGameId(), new EventListener<DocumentSnapshot>() {

        };
    }


