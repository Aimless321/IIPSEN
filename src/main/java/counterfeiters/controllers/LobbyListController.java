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

                   // if(querySnapshot != null) {
                    System.out.println(querySnapshot.toString());
                    //Nu doorsturen naar de model voor update
                    updateLobbies(firebaseModel);
                System.out.println("check");

               // }
            }
        });

                //public void backButtonPressed(Stage stage) {
                //to do
                // }

    }

    public void updateLobbies(FirebaseModel firebaseModel) {firebaseModel.updateLobbies();
    }

}





