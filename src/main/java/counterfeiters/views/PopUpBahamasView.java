package counterfeiters.views;

import counterfeiters.controllers.PopUpBahamasController;
import counterfeiters.models.Observable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUpBahamasView implements Observer{

    private Stage stage;
    private PopUpBahamasController controller;

    //Need an empty constructor for FXML
    public PopUpBahamasView() {

    }

    //CONSTRUCTOR
    public PopUpBahamasView(Stage primaryStage, Object popUpLaunderMoneyController) {
        this.stage = primaryStage;
        this.controller = (PopUpBahamasController) popUpLaunderMoneyController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/popUp_bahamas.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("AnchorPane");
        pane.setBackground(ViewUtilities.getBackground("/background/standard.png"));

        //Show it on the screen
        Scene scene = new Scene(root, 400, 200);

        Stage popupStage = new Stage(StageStyle.DECORATED);
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(scene);
        popupStage.show();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        PopUpBahamasController popUpLaunderMoneyController = (PopUpBahamasController) controller;
        this.controller = popUpLaunderMoneyController;
        popUpLaunderMoneyController.registerObserver(this);
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void start() {

    }
}
