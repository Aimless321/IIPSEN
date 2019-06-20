package counterfeiters.views;

import counterfeiters.controllers.ScoreboardController;
import counterfeiters.managers.SoundManager;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

/**
 * This view shows the scores of the players, after the game has ended.
 *
 * @author Robin van den Berg
 */

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

    public ImageView muteButton;



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

        pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                SoundManager.toggleMute();
            }
        });

        //Show it on the screen
        stage.getScene().setRoot(pane);
    }

    public void showScores() {

        Map<String, Integer> data = controller.getScores(controller.loadScores().getPlayers());

        Image imageFirst = controller.loadScores().getPlayerFromUserName((String)data.keySet().toArray()[0]).getCharacterImagePath();
        Image imageSecond = controller.loadScores().getPlayerFromUserName((String)data.keySet().toArray()[1]).getCharacterImagePath();
        Image imageThird = controller.loadScores().getPlayerFromUserName((String)data.keySet().toArray()[2]).getCharacterImagePath();

        firstPlayer.setImage(imageFirst);
        secondPlayer.setImage(imageSecond);
        thirdPlayer.setImage(imageThird);

        firstPlayerName.setText((String)data.keySet().toArray()[0]);
        secondPlayerName.setText((String)data.keySet().toArray()[1]);
        thirdPlayerName.setText((String)data.keySet().toArray()[2]);

        firstPlayerCash.setText("$"+ data.values().toArray()[0]);
        secondPlayerCash.setText("$"+ data.values().toArray()[1]);
        thirdPlayerCash.setText("$"+ data.values().toArray()[2]);

    }

    @FXML
    public void pressBackMenu() {
        controller.menuButtonPressed();
    }

    @FXML
    public void pressExitGame() { controller.exitButtonPressed(); }

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

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {

        ScoreboardController scoreboardController = (ScoreboardController) controller ;
        this.controller = scoreboardController;
        scoreboardController.registerObserver(this);
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void start() {
        showScores();

    }
}