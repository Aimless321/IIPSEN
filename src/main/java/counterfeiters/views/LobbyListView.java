package counterfeiters.views;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import counterfeiters.controllers.LobbyListController;
import counterfeiters.models.FirebaseModel;
import counterfeiters.models.Game;
import counterfeiters.models.Observable;
import counterfeiters.models.Player;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;



import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.util.ArrayList;
import java.util.List;

public class LobbyListView extends ListView<String> implements Observer {

    @FXML
    public ListView<String> list;

    //ObservableList<String> listView = FXCollections.observableArrayList("one", "two", "three");


    private Stage stage;
    private LobbyListController controller;
    private MouseEvent mouseEvent;
    private ListRow listRowObject;
    private ObservableList<ListRow> listRows = FXCollections.observableArrayList();

    private Image playerIcon = new Image("icons/player.png");

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

        ObservableList<ListRow> listRows = FXCollections.observableArrayList();

        listRows.addAll(new ListRow(
                        "12345"
                        ,"blba","3",new ImageView("icons/player.png"))
                ,new ListRow(
                        "12345"
                        ,"blba","3",new ImageView("icons/player.png")));

        ListView<ListRow> lvListRow = new ListView<>();
        lvListRow.setLayoutX(460.0);
        lvListRow.setLayoutY(390.0);
        lvListRow.setPrefHeight(400);
        lvListRow.setPrefWidth(1000);
        System.out.println("hallo");
        // Setup the CellFactory
        lvListRow.setCellFactory(listView -> new ListCell<ListRow>() {
            @Override
            protected void updateItem(ListRow row, boolean empty) {
                super.updateItem(row, empty);

                if (empty) {
                    System.out.println("bijna");
                    setGraphic(null);
                } else {

                    Region region1 = new Region();
                    HBox.setHgrow(region1, Priority.ALWAYS);

                    Region region2 = new Region();
                    HBox.setHgrow(region2, Priority.ALWAYS);

                    HBox horBox = new HBox(new Label(row.getId()), new Label(row.getLobbyName()), region1, region2, row.getIcon(),new Label(" " + row.getPlayerAmount()+ "/4"));

                    // Create a HBox to hold our displayed value
                  //  HBox horBox = new HBox(5);
                  //  horBox.setAlignment(Pos.CENTER_LEFT);

                    // Add the values from our piece to the HBox
                    //Label idInRow = new Label(row.getId());
                    //horBox.getChildren().addAll(
                     //       row.getIcon(),
                    //        idInRow
                           //new Label("x " + piece.getCount())
                    //);

                    // Set the HBox as the display
                    setGraphic(horBox);
                    System.out.println("bijna");
                }
            }
        });

        // Bind our list of pieces to the ListView
        lvListRow.setItems(listRows);

        //Find root pane and set background
        Pane pane = (Pane) root.lookup("Pane");
        pane.getChildren().add(lvListRow);
        pane.setBackground(ViewUtilities.getBackground("/background/standard.png"));

        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
    }



    @FXML
    public void pressRules(MouseEvent mouseEvent) {
        System.out.println("Rules button pressed");
    }

    @FXML
    public void pressBackButton(MouseEvent mouseEvent) {
        System.out.println("Leave button pressed");
        //controller.backButtonPressed(stage);
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
    public void update(Observable observable) {
        FirebaseModel firebaseModel  = (FirebaseModel) observable;

        //Platform.runLater(() -> listRows.clear());

        //Add new rows for lobbylist
        ArrayList<DocumentSnapshot> updatedLobbies = firebaseModel.getLobbies();
        System.out.println("moiiiii");
        listRows.clear();
        for(int i=0; i<updatedLobbies.size();i++) {
            listRows.add(new ListRow(updatedLobbies.get(i).getString("gameId"),updatedLobbies.get(i).getString("lobbyName"),updatedLobbies.get(i).getString("playerAmount"),new ImageView("icons/player.png") ));
        }

    }

    @Override
    public void start() {
       //controller.loadLobbies();
       controller.registerObserver(this);
       controller.registerListeners();
    }

    public void updateObservableList(List<QueryDocumentSnapshot> lobbies){
        //listView.removeAll();

       // for(int i=0; i<lobbies.size();i++) {
       //     listView.add(lobbies.get(i).getId());
       // }

    }


   // @Override
   // public void initialize(URL location, ResourceBundle resources) {

        //list.setItems(listView);
        //list.getSelectionModel().selectedItemProperty().addListener((ObservableValue<?extends String>ov, String old,String newV)->{});

    //}



}
