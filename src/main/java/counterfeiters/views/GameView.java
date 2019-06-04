package counterfeiters.views;

import counterfeiters.controllers.GameController;
import counterfeiters.controllers.MainMenuController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameView implements Observer {
    private Stage stage;
    private GameController controller;

    //Need an empty constructor for FXML
    public GameView() {

    }

    public GameView(Stage primaryStage, Object GameController) {
        this.stage = primaryStage;
        this.controller = (counterfeiters.controllers.GameController) GameController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/game.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/with-money-and-logo.png"));

        //Show it on the screen
        Scene scene = new Scene(root, ViewUtilities.screenWidth, ViewUtilities.screenHeight);
        stage.setScene(scene);
    }

    @FXML
    public void blackMarket(MouseEvent mouseEvent) {
        System.out.println("Black market pressed");
    }

    @FXML
    public void actionFieldLaunder(MouseEvent mouseEvent) {
        System.out.println("Launder button pressed");
    }

    @FXML
    public void actionFieldFraud(MouseEvent mouseEvent) {
        System.out.println("Fraud button pressed");
    }
    @FXML
    public void actionFieldFly(MouseEvent mouseEvent) {
        System.out.println("Fly button pressed");
    }

    @FXML
    public void actionFieldHealer(MouseEvent mouseEvent) {
        System.out.println("Healer button pressed");
    }

    @FXML
    public void actionFieldPrint(MouseEvent mouseEvent) {
        System.out.println("Print button pressed");
    }

    @FXML
    public void pressRules(MouseEvent mouseEvent) {
        System.out.println("Rules button pressed");
    }

    @FXML
    public void pressCards(MouseEvent mouseEvent) {
        System.out.println("Cards button pressed");
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        this.controller = (GameController) controller;
    }
}
