package counterfeiters.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuView {
    private Stage stage;

    //Need an empty constructor for FXML
    public MainMenuView() {

    }

    public MainMenuView(Stage primaryStage) {
        this.stage = primaryStage;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/main-menu.fxml");

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/with-money-and-logo.png"));

        //Show it on the screen
        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
    }

    @FXML
    public void join(MouseEvent mouseEvent) {
        System.out.println("Join button pressed");
    }

    @FXML
    public void create(MouseEvent mouseEvent) {
        System.out.println("Create button pressed");
    }

    @FXML
    public void load(MouseEvent mouseEvent) {
        System.out.println("Load button pressed");
    }
}
