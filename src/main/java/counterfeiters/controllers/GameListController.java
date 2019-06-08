package counterfeiters.controllers;

import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.QuerySnapshot;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.FirebaseModel;
import counterfeiters.views.LobbyView;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;

import javax.annotation.Nullable;

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
                updateLobbiesModel(firebaseModel);
                System.out.println("Something happened");

                // }
            }
        });
    }

    public void updateLobbiesModel(FirebaseModel firebaseModel) {
        firebaseModel.updateLobbies();
    }

    public void leaveButtonPressed() {
        //TODO: Go to the MainMenuView
        app.loadView(MainMenuView.class, app.mainMenuController);
    }

    public void enterLobby(){
        //TODO: Go to the LobbyView
        app.loadView(LobbyView.class, app.lobbyController);
    }

    public void rulesButtonPressed() {
        //TODO: Go to the rules view
    }

}






}
