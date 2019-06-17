package counterfeiters.views;

import counterfeiters.controllers.PopUpLaunderMoneyController;
import counterfeiters.models.Board;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUpLaunderMoneyView implements Observer{

    public TextField txtQualityOne;
    public TextField txtQualityTwo;
    public TextField txtQualityThree;
    public AnchorPane anchorpane;
    public Text TextField;

    private int qualityOne;
    private int qualityTwo;
    private int qualityThree;
    private int currentAmount;

    private String strQualityOne = "qualityOne";
    private String strQualityTwo = "qualityTwo";
    private String strQualityThree = "qualityThree";

    //Type money
    private int realMoney = 4;

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

    public int getValues(){
        qualityOne = Integer.parseInt(txtQualityOne.getText());
        qualityTwo = Integer.parseInt(txtQualityTwo.getText());
        qualityThree = Integer.parseInt(txtQualityThree.getText());

        int total = qualityOne + qualityTwo + qualityThree;
        return total;
    }

    public void counterPlus(String quality, TextField textfield){
        currentAmount = Integer.parseInt(textfield.getText());

        if(controller.checkQualityQuantity(quality, currentAmount)){
            currentAmount++;
            textfield.setText(String.valueOf(currentAmount));
        }
    }

    public void counterMin(TextField textfield){
        currentAmount = Integer.parseInt(textfield.getText());

        if(currentAmount != 0){
            currentAmount--;
            textfield.setText(String.valueOf(currentAmount));
        }
    }

    @FXML
    public void pressLaunder(MouseEvent mouseEvent) {
        if (getValues() < 8){
            controller.countMoney(realMoney, qualityOne, qualityTwo, qualityThree);

            Stage stage = (Stage) anchorpane.getScene().getWindow();
            stage.close();
        } else{
            TextField.setText("You can only launder 8 money!");
        }
    }

    @FXML
    public void plusQualityOne(MouseEvent mouseEvent) {
        counterPlus(strQualityOne, txtQualityOne);
    }

    @FXML
    public void minQualityOne(MouseEvent mouseEvent) {
        counterMin(txtQualityOne);
    }

    @FXML
    public void plusQualityTwo(MouseEvent mouseEvent) {
        counterPlus(strQualityTwo, txtQualityTwo);
    }

    @FXML
    public void minQualityTwo(MouseEvent mouseEvent) {
        counterMin(txtQualityTwo);
    }

    @FXML
    public void plusQualityThree(MouseEvent mouseEvent) {
        counterPlus(strQualityThree, txtQualityThree);
    }

    @FXML
    public void minQualitythree(MouseEvent mouseEvent) {
        counterMin(txtQualityThree);
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
//        Board board = (Board)observable;
//        textField.setText(board.getTextField());
    }

    @Override
    public void start() {
    }
}
