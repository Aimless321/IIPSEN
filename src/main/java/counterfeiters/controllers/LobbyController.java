package counterfeiters.controllers;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import counterfeiters.views.RulesView;
import counterfeiters.views.ViewUtilities;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.Nullable;
import java.util.Optional;

public class LobbyController {
    private ApplicationController app;

    public LobbyController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void registerObserver(Observer observer) {
        app.gameController.registerObserver(observer);
    }

    public void registerListeners() {
        FirebaseService fb = FirebaseService.getInstance();

        //Listen for changes in the lobby
        fb.listen("lobbies", app.gameController.game.getGameId(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirestoreException e) {
                if (e != null) {
                    System.err.println("Listen failed: " + e);
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Game updateGame = documentSnapshot.toObject(Game.class);

                    app.gameController.updateData(updateGame);
                } else {
                    //Lobby has been deleted in firebase
                    Platform.runLater(() -> gameDeleted());
                }
            }
        });
    }

    public void leaveButtonPressed() {
        Game game = app.gameController.game;

        String localUsername = app.accountController.getUsername();
        
        Player player = game.getLocalPlayer(localUsername);
        player.leaveLobby(game);

        //Remove the old game data, by creating a new game
        app.gameController.deleteGame();

        //TODO: Go to the lobbylistview, not the mainmenu
        app.loadView(MainMenuView.class, app.mainMenuController);
    }

    public void gameDeleted() {
        Alert popup = ViewUtilities.showPopup(Alert.AlertType.INFORMATION, "Info",
                                "The host left the lobby, sending you back to the main menu.");

        ButtonType button = new ButtonType("Ok");
        popup.getButtonTypes().setAll(button);

        popup.setOnCloseRequest(event -> {
            //Remove the old game data, by creating a new game
            app.gameController.deleteGame();

            //TODO: Go to the lobbylistview, not the mainmenu
            app.loadView(MainMenuView.class, app.mainMenuController);
        });

        popup.showAndWait();
    }

    public void rulesButtonPressed() {
        app.loadView(RulesView.class, app.rulesController);
    }

    public void startButtonPressed() {
        //TODO: Start game
    }
}
