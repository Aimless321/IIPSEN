package counterfeiters.views;

import counterfeiters.controllers.PopUpBahamasController;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The PopUpBahamasView will be created by giving the primarystage and PopUpBahamasController as arguments.
 *
 * With this view it will be possible to Fly money to the Bahamas from the boardview.
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 20-06-2019
 * */

public class PopUpBahamasView implements Observer{

    private Stage stage;
    private PopUpBahamasController controller;

    // FXML values.
    public AnchorPane anchorpane;
    public TextField txtValue;
    public Text textField;
    public Button btnAdd;
    public Button btnReduce;

    //Need an empty constructor for FXML
    public PopUpBahamasView() {

    }

    //CONSTRUCTOR
    public PopUpBahamasView(Stage primaryStage, Object popUpBahamasController) {
        this.stage = primaryStage;
        this.controller = (PopUpBahamasController) popUpBahamasController;

        show();
    }

    /**
     * Reduces the amount from the Bahamas account and adds it to the realMoney.
     * If the player does not have enough money he will get an notification.
     * Else it will be done and the view will be closed.
     *
     * @author Ali Rezaa
     * @version 20-06-2019
     * */
    @FXML
    public void pressReduce() {
        int value = Integer.parseInt(txtValue.getText());
        if(!controller.reduceAmountFromBahamas(value)){
            textField.setText("You don't have enough money on your bankaccount");
        }else{
            Stage stage = (Stage) anchorpane.getScene().getWindow();
            stage.close();

            controller.placeHenchman();
        }
    }

    /**
     * Reduces the amount from the Real money and adds it to the Bahamas account.
     * If the player does not have enough money he will get an notification.
     * Else it will be done and the view will be closed.
     *
     * @author Ali Rezaa
     * @version 20-06-2019
     * */
    @FXML
    public void pressAdd() {
        int value = Integer.parseInt(txtValue.getText());
        if(!controller.addAmountToBahamas(value)){
            textField.setText("You dont have enough 'real money' at the moment");
        }else{
            Stage stage = (Stage) anchorpane.getScene().getWindow();
            stage.close();

            controller.placeHenchman();
        }

    }


    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/popup_bahamas.fxml", stage, controller);

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
        PopUpBahamasController popUpBahamasController = (PopUpBahamasController) controller;
        this.controller = popUpBahamasController;
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void start() {

    }

}
