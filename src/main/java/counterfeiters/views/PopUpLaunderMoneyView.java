package counterfeiters.views;

import counterfeiters.controllers.PopUpLaunderMoneyController;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUpLaunderMoneyView implements Observer{

    public Button launder;
    public TextField txtQualityOne;
    public TextField txtQualityTwo;
    public TextField txtQualityThree;

    private int qualityOneMoney = 1;
    private int qualityTwoMoney = 2;
    private int qualityThreeMoney = 3;
    private int realMoney = 4;
    private int bahamasBank = 5;

    private int qualityOne;
    private int qualityTwo;
    private int qualityThree;

    private Stage stage;
    private PopUpLaunderMoneyController controller;

    //Need an empty constructor for FXML
    public PopUpLaunderMoneyView() {

    }

    //CONSTRUCTOR
    public PopUpLaunderMoneyView(Stage primaryStage, Object popUpLaunderMoneyController) {
        this.stage = primaryStage;
        this.controller = (PopUpLaunderMoneyController) popUpLaunderMoneyController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/popUp_Launder.fxml", stage, controller);

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

        PopUpLaunderMoneyController popUpLaunderMoneyController = (PopUpLaunderMoneyController) controller;
        this.controller = popUpLaunderMoneyController;
        popUpLaunderMoneyController.registerObserver(this);
    }

    @Override
    public void update(Observable observable) {
    }

    @Override
    public void start() {
    }

    @FXML
    public void pressLaunder(MouseEvent mouseEvent) {
        qualityOne = Integer.parseInt(txtQualityOne.getText());
        qualityTwo = Integer.parseInt(txtQualityTwo.getText());
        qualityThree = Integer.parseInt(txtQualityThree.getText());

        controller.countMoney(realMoney, qualityOne, qualityTwo, qualityThree);
    }
}
