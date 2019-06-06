package counterfeiters.views;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import counterfeiters.controllers.LobbyListController;
import counterfeiters.models.Game;
import counterfeiters.models.Observable;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.util.Callback;

public class LobbyListView extends ListView<String> implements Observer,Initializable {

    @FXML
    public ListView<String> list;

    //ObservableList<String> listView = FXCollections.observableArrayList("one", "two", "three");

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

        ObservableList<ListRow> listRows = FXCollections.observableArrayList();

        // Add a sample Chess piece, a queen in this case
        listRows.addAll(new ListRow(
                "12345",
                new ImageView("icons/player.png"),"3")
        ,new ListRow("1243567",new ImageView("icons/player.png"),"2"));

        ListView<ListRow> lvListRow = new ListView<>();
        lvListRow.setLayoutX(460.0);
        lvListRow.setLayoutY(390.0);
        lvListRow.setPrefHeight(400);
        lvListRow.setPrefWidth(1000);

        // Setup the CellFactory
        lvListRow.setCellFactory(listView -> new ListCell<ListRow>() {
            @Override
            protected void updateItem(ListRow row, boolean empty) {
                super.updateItem(row, empty);

                if (empty) {
                    setGraphic(null);
                } else {


                    Region region1 = new Region();
                    HBox.setHgrow(region1, Priority.ALWAYS);

                    Region region2 = new Region();
                    HBox.setHgrow(region2, Priority.ALWAYS);

                    HBox horBox = new HBox(new Label(row.getId()), region1, region2, row.getIcon(),new Label(" " + row.getPlayerAmount()+ "/4"));

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
        controller.backButtonPressed(stage);
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
    }

    @Override
    public void start() {

    }

  //  @Override
   // public void update(java.util.Observable observable) {

  //  }

    public void updateObservableList(List<QueryDocumentSnapshot> lobbies){
        //listView.removeAll();

       // for(int i=0; i<lobbies.size();i++) {
       //     listView.add(lobbies.get(i).getId());
       // }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //list.setItems(listView);
        //list.getSelectionModel().selectedItemProperty().addListener((ObservableValue<?extends String>ov, String old,String newV)->{});

    }

    class ListRow {
        private String id;
        private ImageView playerIcon;
        private String playerAmount;

        public ListRow(String id, ImageView playerIcon, String playerAmount) {
            this.id = id;
            this.playerIcon = playerIcon;
            this.playerAmount = playerAmount;

            //resize pic
            this.playerIcon.setFitHeight(30);
            this.playerIcon.setFitWidth(30);
        }

        public String getId() {
            return id;
        }

        public ImageView getIcon() {
            return playerIcon;
        }

        public String getPlayerAmount() {
            return playerAmount;
        }
    }
}
