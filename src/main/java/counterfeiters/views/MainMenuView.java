package counterfeiters.views;

import counterfeiters.controllers.MainMenuController;
import counterfeiters.managers.SoundManager;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

    public ImageView muteButton;


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

        pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.M) {
                SoundManager.toggleMute();
            }
        });

        //Show it on the screen
        stage.getScene().setRoot(pane);
    }

    @FXML
    public void pressJoinLobby() {


        controller.joinLobbyPressed();
    }

    @FXML
    public void pressCreateLobby() {
        controller.createLobbyPressed();

    }

    @FXML
    public void pressLoadGame() {


        controller.loadGamePressed();
    }

    @FXML
    public void pressExitGame(MouseEvent mouseEvent) {
        controller.exitButtonPressed();
    }

    @FXML
    public void pressMute(MouseEvent mouseEvent) {
        SoundManager.toggleMute();

        if (SoundManager.muteSound) {
            muteButton.setOpacity(0.5);
        }
        else {
            muteButton.setOpacity(1);
        }
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
