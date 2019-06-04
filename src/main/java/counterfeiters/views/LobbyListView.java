package counterfeiters.views;

import counterfeiters.controllers.LobbyListController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class LobbyListView implements Observer{
    @FXML
    public ListView listlobby;

    private Stage stage;
    private LobbyListController controller;
    private MouseEvent mouseEvent;

    //Need an empty constructor for FXML
    public LobbyListView() {

    }

    public LobbyListView(Stage primaryStage, Object lobbyListController) {
        this.stage = primaryStage;
        this.controller = (LobbyListController)lobbyListController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/lobby_list.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/standard.png"));

        //Show it on the screen
        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
    }


    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        this.controller = (LobbyListController)controller;
    }

    @FXML
    public void pressRules(MouseEvent mouseEvent) {
    }

    @FXML
    public void pressBackButton(MouseEvent mouseEvent) {
    }
}


