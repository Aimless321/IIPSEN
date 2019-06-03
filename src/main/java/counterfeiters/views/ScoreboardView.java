package counterfeiters.views;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ScoreboardView implements Observer {
    private Stage stage;

    @FXML
    private Text name;

    //Need an empty constructor for FXML
    public ScoreboardView() {

    }

    public ScoreboardView(Stage primaryStage) {
        this.stage = primaryStage;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/scoreboard.fxml", stage);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/scoreboard.png"));

        //Show it on the screen
        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
    }

    @FXML
    public void pressBackMenu() {
        System.out.println("Start button pressed");

        new MainMenuView(stage);
    }

    @FXML
    public void pressExitGame() {
        System.out.println("Leave button pressed");

        System.exit(0);

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
