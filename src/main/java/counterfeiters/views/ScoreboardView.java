package counterfeiters.views;

import counterfeiters.controllers.ScoreboardController;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ScoreboardView implements Observer {
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

    @FXML
    public void pressBackMenu() {
        controller.menuButtonPressed(stage);
    }

    @FXML
    public void pressExitGame() {
        controller.exitButtonPressed();

    }

    @FXML
    public void pressRules() {
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