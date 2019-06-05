package counterfeiters.views;

import counterfeiters.controllers.LobbyController;
import counterfeiters.models.Game;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LobbyView implements Observer {
    //The lobby title
    @FXML
    public Text title;

    //VBOX to add the other players to
    @FXML
    public VBox players;

    //First player name
    @FXML
    public Text name;

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
        System.out.println("Start button pressed");
    }

    @FXML
    public void pressLeave() {
        System.out.println("Leave button pressed");
    }

    @FXML
    public void pressRules() {
        System.out.println("Rules button pressed");
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        LobbyController lobbyController = (LobbyController)controller;
        this.controller = lobbyController;

        lobbyController.registerObserver(this);
    }

    @Override
    public void update(Observable observable) {
        Game game = (Game)observable;

        String playerName = game.getPlayers().get(0).getUserName();

        name.setText(playerName);
        title.setText(playerName + "'s Lobby");
    }

    @Override
    public void initialize() {

    }
}
