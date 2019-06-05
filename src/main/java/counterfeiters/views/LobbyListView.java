package counterfeiters.views;

import counterfeiters.controllers.LobbyListController;
import counterfeiters.models.Game;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;



import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.util.Callback;

public class LobbyListView extends ListView<String> implements Observer,Initializable {

    @FXML
    public ListView<String> list;
    ObservableList<String> listView = FXCollections.observableArrayList("one", "two", "three");

    Image player_icon = new Image("icons/player.png");

    private Stage stage;
    private LobbyListController controller;
    private MouseEvent mouseEvent;


    //public ObservableList<Game> lobby = FXCollections.observableArrayList(
    //       "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");

    //Need an empty constructor for FXML

    public LobbyListView() {


    }

    public LobbyListView(Stage primaryStage, Object lobbyListController) {
        this.stage = primaryStage;
        this.controller = (LobbyListController) lobbyListController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/lobby_list.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane) root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/standard.png"));

        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
    }



    @FXML
    public void pressRules(MouseEvent mouseEvent) {
    }

    @FXML
    public void pressBackButton(MouseEvent mouseEvent) {
    }

    @FXML
    public void clickOnLobby(MouseEvent mouseEvent) {
        //lobbyList.getItem()
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        this.controller = (LobbyListController) controller;
    }

    @Override
    public void update(java.util.Observable observable) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.setItems(listView);
        list.getSelectionModel().selectedItemProperty().addListener((ObservableValue<?extends String>ov, String old,String newV)->{});

    }

}
