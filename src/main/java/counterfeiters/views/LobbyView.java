package counterfeiters.views;

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

    //Need an empty constructor for FXML
    public LobbyView() {

    }

    public LobbyView(Stage primaryStage) {
        this.stage = primaryStage;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/lobby.fxml", stage);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/standard.png"));

        //Show it on the screen
        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
    }

    @FXML
    public void pressStart() {
        System.out.println("Start button pressed");
    }

    @FXML
    public void pressLeave() {
        System.out.println("Leave button pressed");

        new MainMenuView(stage);
    }

    @FXML
    public void pressRules() {
        System.out.println("Rules button pressed");
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
