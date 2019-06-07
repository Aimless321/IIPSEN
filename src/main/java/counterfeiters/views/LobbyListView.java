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
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;


import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LobbyListView implements Observer {


    @FXML
    public Pane pane;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox vBox;

    //ObservableList<String> listView = FXCollections.observableArrayList("one", "two", "three");
    private Stage stage;
    private LobbyListController controller;
    private MouseEvent mouseEvent;
    private ListRow listRowObject;
    private List<ListRow> listRows = new ArrayList<>();
    private int counter = 0;

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
        Parent root = ViewUtilities.loadFxml("/views/lobbylist.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/standard.png"));

        //Show it on the screen
        Scene scene = new Scene(root, ViewUtilities.screenWidth, ViewUtilities.screenHeight);
        stage.setScene(scene);}


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

        Platform.runLater(() -> listRows.clear());

        //Add new rows for lobbylist
        ArrayList<DocumentSnapshot> updatedLobbies = firebaseModel.getLobbies();

        for(DocumentSnapshot doc: updatedLobbies) {
            ListRow newListRow = new ListRow(doc.getString("gameId"), doc.getString("lobbyName"), doc.getString("numPlayers"), new ImageView("icons/player.png"));
            listRows.add(newListRow);

            Platform.runLater(() -> addLobbyInView(newListRow));
        }

    }

    public void addLobbyInView(ListRow listRow){
        vBox.setStyle("-fx-background-color: transparent");
        counter = vBox.getChildren().size() +1;
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        Label lobbyName = new Label(" " + counter + ". " +listRow.getLobbyName());
        lobbyName.setFont(new Font(30));
        lobbyName.setTextFill(Color.WHITE);

        Label numPlayers = new Label("   " + listRow.getNumPlayers() + "/4  ");
        numPlayers.setFont(new Font(30));
        numPlayers.setTextFill(Color.WHITE);

        HBox horBox = new HBox(lobbyName , region1, region2, listRow.getIcon(), numPlayers);

        horBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        System.out.println("Double clicked");
                        System.out.println(listRow.getId());
                    }
                }
            }
        });
        horBox.setStyle("-fx-pref-height: 85");
        horBox.setStyle("-fx-pref-width: 980");
        //horBox.setStyle("-fx-background-radius: 0.5");

        //horBox.setSpacing(25);
        if (counter %2 == 0) {
            //horBox.setStyle("-fx-background-color: rgb(77, 144, 156)");
            horBox.setStyle("-fx-background-image: url(background/ligt_list.png)");
        }
        else {
            //horBox.setStyle("-fx-background-color: rgb(90, 115, 115)");
            horBox.setStyle("-fx-background-image: url(background/dark_list.png)");
        }

        vBox.getChildren().add(horBox);




    }

    @Override
    public void start() {
       //controller.loadLobbies();
       controller.registerObserver(this);
       controller.registerListeners();
    }


    class ListRow {
        private String gameId;
        private String lobbyName;
        private String numPlayers;
        private ImageView playerIcon;

        public ListRow(String gameId,String lobbyName, String numPlayers, ImageView playerIcon) {
            this.lobbyName = lobbyName;
            //.playerAmount = playerAmount;
            this.gameId = gameId;
            this.playerIcon = playerIcon;
            this.numPlayers = numPlayers;

            //resize pic
            this.playerIcon.setFitHeight(30);
            this.playerIcon.setFitWidth(30);

        }

        public String getLobbyName() {
            return lobbyName;
        }


        public String getNumPlayers() {
           return numPlayers;
        }

        public String getId(){
            return gameId;
        }
        public ImageView getIcon() {
            return playerIcon;
        }
    }

}
