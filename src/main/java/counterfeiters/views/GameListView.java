package counterfeiters.views;

import counterfeiters.controllers.GameListController;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * View for the game load view screen
 * @author Melissa Basgol
 */

public class GameListView implements Observer{
    @FXML
    public Pane pane;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox vBox;
    public ImageView muteButton;


    private Stage stage;
    private GameListController controller;
    private List<Game> games = new ArrayList<>();

    private int counter = 0;
    private String chosenGame;


    //Need an empty constructor for FXML
    public GameListView() {

    }

    public GameListView(Stage primaryStage, Object gameListController) {
        this.stage = primaryStage;
        this.controller = (GameListController) gameListController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/game_list.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/standard.png"));

        pane.setOnKeyPressed(event -> {
            if (event.equals(KeyCode.M)) {
                SoundManager.toggleMute();
            }
        });

        pane.setOnKeyPressed(event -> {
            if (event.equals(KeyCode.M)) {
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

    @FXML
    public void clickOnLobby() {
        //lobbyList.getItem()
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        this.controller = (GameListController) controller;
    }

    @Override
    public void update(Observable observable) {
        FirebaseModel firebaseModel  = (FirebaseModel) observable;
        if (firebaseModel.lobbyOrGame().equals("game")) {
            Platform.runLater(() -> vBox.getChildren().clear());
            games.clear();


            //Add new rows for lobbylist
            ArrayList<Game> updatedGames = firebaseModel.getGames();





            if (updatedGames.size() != 0) {
                for (Game game : updatedGames) {
                    Platform.runLater(() ->
                            addGameInView(game));
                }
            } else {
                noLobbies();
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
        noLobby.setFont(new Font(30));
        noLobby.setTextFill(Color.WHITE);

        HBox horBox = new HBox(region1, noLobby,  region2);
        horBox.setStyle("-fx-pref-height: 60");
        horBox.setStyle("-fx-pref-width: 980");
        horBox.setStyle("-fx-background-image: transparent");

    }

    public void addGameInView(Game game){

        vBox.setStyle("-fx-background-color: transparent");
        counter = vBox.getChildren().size() +1;
        Region region1 = new Region();
        region1.setStyle("-fx-pref-height: 120");
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        region2.setStyle("-fx-pref-height: 120");
        HBox.setHgrow(region2, Priority.ALWAYS);

        Label lobbyName = new Label(" " + counter + ". " +game.getLobbyName());
        lobbyName.setFont(new Font(30));
        lobbyName.setTextFill(Color.WHITE);

        Label numPlayers = new Label(String.valueOf(game.getNumPlayers()));
        numPlayers.setFont(new Font(30));
        numPlayers.setTextFill(Color.WHITE);

        ImageView icon  = new ImageView("icons/player.png");
        icon.setFitHeight(25);
        icon.setFitWidth(25);

        HBox horBox = new HBox(lobbyName, region1, region2, numPlayers, icon, new Label("  "));
        horBox.setAlignment(Pos.CENTER_LEFT);


        horBox.setStyle("-fx-cursor: hand");
        horBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        controller.gameSelected(game.getGameId());
                    }
                }
            }
        });

        //Add hover effects (doesnt work in css)
        horBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                    }
                }
            });
        horBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                }
            }
        });


        horBox.setStyle("-fx-pref-height: 60");
        horBox.setStyle("-fx-pref-width: 980");

        if (counter %2 == 0) {
            horBox.setStyle("-fx-background-image: url(background/light_back.JPG)");
        }
        else {
            horBox.setStyle("-fx-background-image: url(background/dark_back.JPG)");
        }

        vBox.getChildren().add(horBox);
    }

    @Override
    public void start() {
       controller.registerObserver(this);
       controller.updateGamesModel();
       controller.registerListeners();
    }
}


