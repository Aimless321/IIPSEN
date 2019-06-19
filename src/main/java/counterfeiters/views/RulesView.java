package counterfeiters.views;

import counterfeiters.controllers.RulesController;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This view shows the rules of "counterfeiters".
 *
 * @author Robin van den Berg
 */


public class RulesView implements Observer {
    private Stage stage;
    private RulesController controller;

    @FXML
    private Pane pane;

    public RulesView(){}
    public RulesView(Stage primaryStage, Object controller) {
        this.stage = primaryStage;
        this.controller = (RulesController)controller;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/rules.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("AnchorPane");
        pane.setBackground(ViewUtilities.getBackground("/background/rulePage1.jpg"));

        //Show it on the screen
        Scene scene = new Scene(root, 1920, 1080);

        Stage popupStage = new Stage(StageStyle.UNDECORATED);
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(scene);
        popupStage.show();

    }

    public void pressClose()
    {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();


    }
    public void pressNext()
    {
        pane.setBackground(ViewUtilities.getBackground(controller.nextButtonPressed()));

    }


    public void pressPrevious()
    {
        pane.setBackground(ViewUtilities.getBackground(controller.previousButtonPressed()));

    }


    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        this.controller = (RulesController)controller;
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void start() {

    }
}
