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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;


import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXML;
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
            ListRow newListRow = new ListRow(doc.getString("gameId"), doc.getString("lobbyName"), new ImageView("icons/player.png"));
            listRows.add(newListRow);

            Platform.runLater(() -> addLobbyInView(newListRow));
        }

    }

    public void addLobbyInView(ListRow listRow){

        counter ++;
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        Label gameId = new Label(listRow.getId());
        gameId.setFont(new Font(30));
        gameId.setTextFill(Color.WHITE);

        Label lobbyName = new Label(listRow.getLobbyName());
        lobbyName.setFont(new Font(30));
        lobbyName.setTextFill(Color.WHITE);

        Label playerAmount = new Label(" " + "/4");
        playerAmount.setFont(new Font(30));
        playerAmount.setTextFill(Color.WHITE);

        HBox horBox = new HBox(gameId,lobbyName , region1, region2, listRow.getIcon(), playerAmount);


        horBox.setStyle("-fx-pref-height: 60");
        horBox.setStyle("-fx-pref-width: 900");
       // horBox.setStyle("-fx-border-style: ");
        horBox.setStyle("-fx-background-radius: 0.5");
        horBox.setSpacing(25);

        if (counter %2 == 0) {
            horBox.setStyle("-fx-background-color: rgb(77, 144, 156)");
        }
        else {
            horBox.setStyle("-fx-background-color: rgb(90, 115, 115)");
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
        //private String playerAmount;
        private ImageView playerIcon;

        public ListRow(String gameId,String lobbyName, ImageView playerIcon) {
            this.lobbyName = lobbyName;
            //.playerAmount = playerAmount;
            this.gameId = gameId;
            this.playerIcon = playerIcon;

            //resize pic
            this.playerIcon.setFitHeight(35);
            this.playerIcon.setFitWidth(35);

        }

        public String getLobbyName() {
            return lobbyName;
        }


        //public String getPlayerAmount() {
        //    return playerAmount;
        //}

        public String getId(){
            return gameId;
        }
        public ImageView getIcon() {
            return playerIcon;
        }
    }

}
