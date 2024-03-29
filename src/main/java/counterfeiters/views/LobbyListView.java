package counterfeiters.views;

import counterfeiters.controllers.LobbyListController;
import counterfeiters.managers.SoundManager;
import counterfeiters.models.FirebaseModel;
import counterfeiters.models.Game;
import counterfeiters.models.Observable;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * View for the lobbylist screen
 * @author Melissa Basgol
 */

public class LobbyListView implements Observer {

    @FXML
    public Pane pane;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox vBox;
    public ImageView muteButton;


    private Stage stage;
    private LobbyListController controller;
    private ArrayList<Game> games = new ArrayList<>();


    private int counter = 0;

    //Need an empty constructor for FXML
    public LobbyListView() {

    }

    public LobbyListView(Stage primaryStage, Object lobbyListController) {
        this.stage = primaryStage;
        this.controller = (LobbyListController) lobbyListController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/lobbylist.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/standard.png"));

        pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                SoundManager.toggleMute();
            }
        });

        //Show it on the screen
        stage.getScene().setRoot(pane);
    }


    @FXML
    public void pressRules() {
        controller.rulesButtonPressed();

    }

    @FXML
    public void pressMute(MouseEvent mouseEvent) {
        SoundManager.toggleMute();

        if (SoundManager.muteSound) {
            muteButton.setOpacity(1);
        }
        else {
            muteButton.setOpacity(0.5);
        }
    }

    @FXML
    public void pressBackButton() {
        controller.leaveButtonPressed();
    }


    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        this.controller = (LobbyListController) controller;
    }

    @Override
    public void update(Observable observable) {
        FirebaseModel firebaseModel  = (FirebaseModel) observable;
        if (firebaseModel.lobbyOrGame().equals("lobby")) {
            Platform.runLater(() -> vBox.getChildren().clear());
            games.clear();


            //Add new games for lobbylist
            ArrayList<Game> updatedGames = firebaseModel.getGames();

            if (updatedGames.size() == 0 || updatedGames.isEmpty()) {
                noLobbies();

                return;
            }

            /*For game in the updatedGames list,
            if the game hasn't started (round is not 1) it will be added to the view.
            When the round is bigger than 1, it is already deleted from the collection "lobbies" in firebase
            */
            for (Game game : updatedGames) {
                if (game.getRound() != 1) {
                    Platform.runLater(() ->
                            addLobbyInView(game));
                }
            }
        }
    }

    public void noLobbies () {

        vBox.setStyle("-fx-background-color: transparent");
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        Label noLobby = new Label("No Lobbies available.");
        noLobby.getStyleClass().add("list-label");

        HBox horBox = new HBox(region1, noLobby,  region2);
        horBox.getStyleClass().add("hbox");
        horBox.setStyle("-fx-background-color: black");
        horBox.setAlignment(Pos.CENTER);

        vBox.getChildren().add(horBox);
    }

    /**
     * Inserts the game into the lobbylist as a row
     * @param game is the game that it is going to be inserted
     */
    public void addLobbyInView(Game game){

        vBox.setStyle("-fx-background-color: transparent");
        counter = vBox.getChildren().size() +1;
        Region region1 = new Region();
        region1.setStyle("-fx-pref-height: 120");
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        region2.setStyle("-fx-pref-height: 120");
        HBox.setHgrow(region2, Priority.ALWAYS);

        Label lobbyName = new Label(" " + counter + ". " +game.getLobbyName());
        lobbyName.getStyleClass().add("list-label");

        Label numPlayers = new Label(game.getNumPlayers() + "/4  ");
        lobbyName.getStyleClass().add("list-label");


        ImageView icon  = new ImageView("icons/player.png");
        icon.setFitHeight(25);
        icon.setFitWidth(25);

        HBox horBox = new HBox(lobbyName, region1, region2, numPlayers, icon, new Label("  "));
        horBox.getStyleClass().addAll("hbox","hbox:hover");
        horBox.setAlignment(Pos.CENTER);


        //When a hbox is double clicked, the game id will be retrieved so that the correct lobby can be loaded
        horBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!mouseEvent.getButton().equals(MouseButton.PRIMARY) || mouseEvent.getClickCount() != 2) {
                    return;
                }

                if(game.getNumPlayers() < game.getPlayers().size()) {
                    controller.clickFullLobby(game);
                } else {
                    controller.clickLobby(game.getGameId());
                }
            }
        });

        // Even and odd rows get different backgrounds
        if (counter %2 == 0) {
            horBox.setStyle("-fx-background-image: url(background/light_back.JPG)");
        }
        else {
            horBox.setStyle("-fx-background-image: url(background/dark_back.JPG)");
        }

        // When a lobby is full, the hbox becomes red
        if(game.getNumPlayers()>=4){
            horBox.setStyle("-fx-background-image: url(background/red-back.JPG)");
            numPlayers.setTextFill(Color.DARKRED);
        }

        vBox.getChildren().add(horBox);
    }

    @Override
    public void start() {
       controller.registerObserver(this);
       controller.updateLobbiesModel();
       controller.registerListeners();
    }
}

