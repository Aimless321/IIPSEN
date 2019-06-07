package counterfeiters.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.FirebaseModel;
import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.LobbyView;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import javafx.stage.Stage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LobbyListController {
    private ApplicationController app;
    public FirebaseModel firebaseModel;

    public LobbyListController(ApplicationController applicationController) {
        this.app = applicationController;
        firebaseModel = new FirebaseModel();
    }

    public void registerObserver(Observer observer) {
        firebaseModel.registerObserver(observer);
    }

    public void registerListeners() {
        FirebaseService fb = FirebaseService.getInstance();

        fb.listenToCollection("lobbies", new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirestoreException e) {
                    if (e != null) {
                        System.err.println("Listen failed:" + e);
                        return;
                    }
                    //To the model for update
                    updateLobbiesModel(firebaseModel);
                    System.out.println("Something happened");

               // }
            }
        });
    }

    public void updateLobbiesModel(FirebaseModel firebaseModel) {
        firebaseModel.updateLobbies();
    }

    public void leaveButtonPressed(Stage stage) {
        //TODO: Go to the MainMenuView
        app.loadView(MainMenuView.class, stage, app.mainMenuController);
    }

    public void enterLobby(Stage stage){
        //TODO: Go to the LobbyView
        app.loadView(LobbyView.class, stage, app.lobbyController);
    }

    public void rulesButtonPressed() {
        //TODO: Go to the rules view
    }

}





