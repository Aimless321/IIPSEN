package counterfeiters.views;

import counterfeiters.controllers.LoginController;
import counterfeiters.controllers.RegisterController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegisterView implements Observer {
    private Stage stage;
    private RegisterController controller;

    //Need an empty constructor for FXML
    public RegisterView() {

    }

    public RegisterView(Stage primaryStage, Object registerController) {
        this.stage = primaryStage;
        this.controller = (RegisterController) registerController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/register.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/with-money-and-logo.png"));

        //Show it on the screen
        Scene scene = new Scene(root, ViewUtilities.screenWidth, ViewUtilities.screenHeight);

        stage.setScene(scene);
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

    }
}
