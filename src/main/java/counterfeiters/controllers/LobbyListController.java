package counterfeiters.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.FirebaseModel;
import counterfeiters.models.Game;
import counterfeiters.views.Observer;
import javafx.stage.Stage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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

        ArrayList<QueryDocumentSnapshot> listOfLobbies = new ArrayList<QueryDocumentSnapshot>();
        listOfLobbies.addAll(fb.getAllDocumentsFromCollection("lobbies"));

        for(int i =0; i<listOfLobbies.size();i++) {
            fb.listen("lobbies", listOfLobbies.get(i).getId(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @Nullable FirestoreException e) {
                    if (e != null) {
                        System.err.println("Listen failed: " + e);
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Game updateGame = documentSnapshot.toObject(Game.class);

                        app.gameController.updateData(updateGame);
                    }
                }
            });
        }


    }

    public void backButtonPressed(Stage stage) {
        //TODO: Switch to the MainMenuView
    }
   // public void updateLobbies(FirebaseModel updateLobbyList) {lobbyList.updateLobbies(updateLobbyList);
   // }
}


