package counterfeiters.views;

import counterfeiters.controllers.PlayerCardController;
import counterfeiters.models.Observable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PlayerCardView implements Observer{

    private Stage stage;
    private PlayerCardController controller;
    public FlowPane playerCardView;
    public ScrollPane scrollPane;

    //Need an empty constructor for FXML
    public PlayerCardView() {

    }

    //CONSTRUCTOR
    public PlayerCardView(Stage primaryStage, Object playerCardController) {
        this.stage = primaryStage;
        this.controller = (PlayerCardController) playerCardController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/playercards.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("AnchorPane");
        pane.setBackground(ViewUtilities.getBackground("/background/standard.png"));

        //Show it on the screen
        Scene scene = new Scene(root, 1000, 650);


        Stage popupStage = new Stage(StageStyle.DECORATED);
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setResizable(false);
        popupStage.setScene(scene);
        popupStage.show();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {

        PlayerCardController playerCardController = (PlayerCardController) controller;
        this.controller = playerCardController;
        playerCardController.registerObserver(this);
    }

    @Override
    public void update(Observable observable) {
    }

    @Override
    public void start() {
        controller.makeCardView(playerCardView);
    }
}
