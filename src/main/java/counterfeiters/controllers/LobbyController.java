package counterfeiters.controllers;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import javafx.stage.Stage;

import javax.annotation.Nullable;

public class LobbyController {
    private ApplicationController app;

    public LobbyController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void registerObserver(Observer observer) {=
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
                }
            }
        });
    }

    public void leaveButtonPressed(Stage stage) {
        Game game = app.gameController.game;

        //TODO: Get real player
        Player player = game.getPlayers().get(0);

        player.leaveLobby(game);

        //Remove the old game data, by creating a new game
        app.gameController.deleteGame();

        //TODO: Go to the lobbylistview, not the mainmenu
        app.loadView(MainMenuView.class, stage, app.mainMenuController);
    }

    public void rulesButtonPressed() {
        //TODO: Open the rules view
    }

    public void startButtonPressed() {
        //TODO: Start game
    }
}
