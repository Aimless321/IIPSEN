package counterfeiters.views;

import counterfeiters.controllers.LoginController;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginView implements Observer {
    private Stage stage;
    private LoginController controller;

    //Need an empty constructor for FXML
    public LoginView() {

    }

    public LoginView(Stage primaryStage, Object loginController) {
        this.stage = primaryStage;
        this.controller = (LoginController) loginController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/login.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/with-money-and-logo.png"));

        //Show it on the screen
        Scene scene = new Scene(root, ViewUtilities.screenWidth, ViewUtilities.screenHeight);

        stage.setScene(scene);
    }

    @FXML
    public void pressLogIn() {
        System.out.println("LogIn button pressed");
    }

    @FXML
    public void pressRegister() {
        System.out.println("Register button pressed");
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        this.controller = (LoginController) controller;
    }

    @Override
    public void update(Observable observable) {

    }
}
