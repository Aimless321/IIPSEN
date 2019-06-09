package counterfeiters.views;

import com.sun.jndi.toolkit.url.Uri;
import counterfeiters.controllers.ScoreboardController;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;


public class ScoreboardView implements Observer {
    public ImageView firstPlayer;
    public ImageView secondPlayer;
    public ImageView thirdPlayer;

    public Text firstPlayerName;
    public Text secondPlayerName;
    public Text thirdPlayerName;

    public Text firstPlayerCash;
    public Text secondPlayerCash;
    public Text thirdPlayerCash;


    private Stage stage;
    private ScoreboardController controller;

    @FXML
    private Text name;

    //Need an empty constructor for FXML
    public ScoreboardView() {}
    public ScoreboardView(Stage primaryStage, Object controller) {
        this.stage = primaryStage;
        this.controller = (ScoreboardController)controller;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/scoreboard.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("AnchorPane");
        pane.setBackground(ViewUtilities.getBackground("/background/scoreboard.png"));

        //Show it on the screen
        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);

    }

    public void setAccount() {
        Map<String, String> data = controller.loadScores();
        firstPlayer.setImage(new Image("/players/deer.jpg"));
        secondPlayer.setImage(new Image("/players/deer.jpg"));
        thirdPlayer.setImage(new Image("/players/deer.jpg"));

        firstPlayerName.setText((String)data.keySet().toArray()[0]);
        secondPlayerName.setText((String)data.keySet().toArray()[1]);
        thirdPlayerName.setText((String)data.keySet().toArray()[2]);

        firstPlayerCash.setText((String)data.values().toArray()[0]);
        secondPlayerCash.setText((String)data.values().toArray()[1]);
        thirdPlayerCash.setText((String)data.values().toArray()[2]);
    }


    @FXML
    public void pressBackMenu() {
        controller.menuButtonPressed(stage);
    }

    @FXML
    public void pressExitGame() { controller.exitButtonPressed(); }

    @FXML
    public void pressRules() {
        setAccount();
        controller.rulesButtonPressed(stage);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        this.controller = (ScoreboardController)controller;
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void start() {

    }
}