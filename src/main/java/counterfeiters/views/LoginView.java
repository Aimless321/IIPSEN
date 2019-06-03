package counterfeiters.views;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginView implements Observer {
    private Stage stage;

    //Need an empty constructor for FXML
    public LoginView() {

    }

    public LoginView(Stage primaryStage) {
        this.stage = primaryStage;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/login.fxml", stage);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/with-money-and-logo.png"));

        //Show it on the screen
        Scene scene = new Scene(root, 1920, 1080);
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
}
