package counterfeiters.views;

import counterfeiters.controllers.MainMenuController;
import counterfeiters.models.Observable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * View for the main menu screen
 * @author Wesley Bijleveld
 */
public class MainMenuView implements Observer {
    private Stage stage;
    private MainMenuController controller;

    //Need an empty constructor for FXML
    public MainMenuView() {

    }

    public MainMenuView(Stage primaryStage, Object mainMenuController) {
        this.stage = primaryStage;
        this.controller = (MainMenuController) mainMenuController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/main-menu.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("AnchorPane");
        pane.setBackground(ViewUtilities.getBackground("/background/with-money-and-logo.png"));

        //Show it on the screen
        Scene scene = new Scene(root, ViewUtilities.screenWidth, ViewUtilities.screenHeight);
        stage.setScene(scene);
    }

    @FXML
    public void pressJoinLobby() {
        System.out.println("Join button pressed");

        controller.joinLobbyPressed();
    }

    @FXML
    public void pressCreateLobby() {
        controller.createLobbyPressed();
    }

    @FXML
    public void pressLoadGame() {
        System.out.println("Load button pressed");

        controller.loadGamePressed();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        this.controller = (MainMenuController) controller;
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void start() {

    }
}
