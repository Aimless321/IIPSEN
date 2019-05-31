package counterfeiters.views;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuView implements Observer {
    private Stage stage;

    //Need an empty constructor for FXML
    public MainMenuView() {

    }

    public MainMenuView(Stage primaryStage) {
        this.stage = primaryStage;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/main-menu.fxml", stage);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/with-money-and-logo.png"));

        //Show it on the screen
        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
    }

    @FXML
    public void pressJoinLobby(MouseEvent mouseEvent) {
        System.out.println("Join button pressed");
    }

    @FXML
    public void pressCreateLobby(MouseEvent mouseEvent) {
        System.out.println("Create button pressed");

        new LobbyView(stage);
    }

    @FXML
    public void pressLoadGame(MouseEvent mouseEvent) {
        System.out.println("Load button pressed");
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
