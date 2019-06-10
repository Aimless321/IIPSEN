package counterfeiters.controllers;

import com.google.cloud.firestore.ListenerRegistration;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.models.Game;
import counterfeiters.models.Player;
import counterfeiters.views.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class LobbyController {
    private ApplicationController app;
    private ListenerRegistration listener;

    public LobbyController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void registerObserver(Observer observer) {
        app.gameController.registerObserver(observer);
    }

    public void registerListeners() {
        FirebaseService fb = FirebaseService.getInstance();

        System.out.println(app.gameController.game.getGameId());
        //Listen for changes in the lobby
        listener = fb.listen("lobbies", app.gameController.game.getGameId(),
        (documentSnapshot, e) -> {
            if (e != null) {
                System.err.println("Listen failed: " + e);
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                Game updateGame = documentSnapshot.toObject(Game.class);

                app.gameController.updateData(updateGame);
            } else {
                //Lobby has been deleted in firebase
                Platform.runLater(this::gameDeleted);
                listener.remove();
            }
        });
    }

    public void leaveButtonPressed() {
        //Remove listener
        listener.remove();

        Game game = app.gameController.game;

        String localUsername = app.accountController.getUsername();
        
        Player player = game.localPlayer;
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
        //Game game = app.gameController.game;

        app.gameController.setStartRound(1);
        System.out.println("round isset on 1");
       // app.loadView(BoardView.class, app.boardController);
    }

    public void startTheGame() {
        //TODO: load boardview
        app.loadView(BoardView.class, app.boardController);
    }
}
