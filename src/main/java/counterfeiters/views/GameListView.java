package counterfeiters.views;

import counterfeiters.controllers.GameListController;
import counterfeiters.controllers.LobbyListController;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameListView implements Observer{
    @FXML
    public ListView listlobby;

    private Stage stage;
    private GameListController controller;
    private MouseEvent mouseEvent;

    //Need an empty constructor for FXML
    public GameListView() {

    }

    public GameListView(Stage primaryStage, Object gameListController) {
        this.stage = primaryStage;
        this.controller = (GameListController)gameListController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/game_list.fxml", stage, controller);

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
        this.controller = (GameListController)controller;
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void start() {

    }

    @FXML
    public void pressRules(MouseEvent mouseEvent) {
    }

    @FXML
    public void pressBackButton(MouseEvent mouseEvent) {
    }
}


