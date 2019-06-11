package counterfeiters.views;

import counterfeiters.controllers.LobbyController;
import counterfeiters.models.Game;
import counterfeiters.models.Observable;
import counterfeiters.models.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class LobbyView implements Observer {
    //The lobby title
    @FXML
    public Text title;

    //VBOX to add the other players to
    @FXML
    public VBox players;

    @FXML
    public Button startButton;

    private Stage stage;
    private LobbyController controller;

    //Need an empty constructor for FXML
    public LobbyView() {

    }

    public LobbyView(Stage primaryStage, Object lobbyController) {
        this.stage = primaryStage;
        this.controller = (LobbyController)lobbyController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/lobby.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/standard.png"));

        //Show it on the screen
        Scene scene = new Scene(root, ViewUtilities.screenWidth, ViewUtilities.screenHeight);
        stage.setScene(scene);
    }

    @FXML
    public void pressStart() {
        controller.startButtonPressed();
    }

    @FXML
    public void pressLeave() {
        controller.leaveButtonPressed();
    }

    @FXML
    public void pressRules() {
        controller.rulesButtonPressed();
    }

    @Override
    public void start() {
        startButton.setVisible(false);

        controller.registerObserver(this);
    }

    @Override
    public void update(Observable observable) {
        Game game = (Game)observable;

        //Remove all players
        Platform.runLater(() -> players.getChildren().clear());

        //Add new players
        List<Player> playerList = game.getPlayers();

        for(Player player : playerList) {
            //We cannot update it on this thread, so we run it later
            Platform.runLater(() -> insertPlayerBox(player));
        }

        //TODO: Check if this player is the host and if there are 3 or more players
        if(game.checkHost()) {

            if (playerList.size()>=3){
                startButton.getText().replace("Waiting for players...","Start");
                //startButton.setText("Start");
                startButton.setDisable(false);
            }
            else {
                startButton.setText("Waiting for players...");
                startButton.setDisable(true);
            }
            startButton.visibleProperty().set(true);
        }

        if (game.getRound() == 1) {
            System.out.println("i got the round");
            Platform.runLater(() -> controller.startTheGame());
        }
        else {
            System.out.println("i didnt get the round");

        }

        //For testing
        startButton.setVisible(true);
        startButton.setDisable(false);
    }

    public void insertPlayerBox(Player player) {
        Text number = new Text(player.getPlayerId() + ".");
        number.setFont(new Font(30));
        number.setFill(Color.WHITE);
        number.setWrappingWidth(50);

        ImageView playerImage = new ImageView(player.getCharacterImagePath());
        playerImage.setFitHeight(135);
        playerImage.setFitWidth(135);
        playerImage.setPreserveRatio(true);
        playerImage.setPickOnBounds(true);

        Text name = new Text(player.getUserName());
        name.setFont(new Font(30));
        name.setFill(Color.WHITE);
        name.setWrappingWidth(380);

        HBox playerBox = new HBox();
        playerBox.setAlignment(Pos.CENTER_LEFT);
        playerBox.setSpacing(25);
        playerBox.setPrefSize(637, 135);

        playerBox.getChildren().addAll(number, playerImage, name);

        players.getChildren().add(playerBox);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        LobbyController lobbyController = (LobbyController)controller;
        this.controller = lobbyController;
    }
}
