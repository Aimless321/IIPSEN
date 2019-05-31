package counterfeiters.views;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegisterView implements Observer {
    private Stage stage;

    //Need an empty constructor for FXML
    public RegisterView() {

    }

    public RegisterView(Stage primaryStage) {
        this.stage = primaryStage;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/register.fxml", stage);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/with-money-and-logo.png"));

        //Show it on the screen
        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
