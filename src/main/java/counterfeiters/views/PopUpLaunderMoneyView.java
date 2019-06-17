package counterfeiters.views;

import counterfeiters.controllers.PopUpLaunderMoneyController;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUpLaunderMoneyView implements Observer{

    // FXML values.
    public TextField txtQualityOne;
    public TextField txtQualityTwo;
    public TextField txtQualityThree;
    public AnchorPane anchorpane;
    public Text txtField;

    // Values from textfield.
    private int qualityOne;
    private int qualityTwo;
    private int qualityThree;
    private int currentAmount;

    // As arguments for the methods.
    private String strQualityOne = "qualityOne";
    private String strQualityTwo = "qualityTwo";
    private String strQualityThree = "qualityThree";
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

    /**
     * Retrieves the values, adds them and returns the total.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 17-06-2019
     * @return total
     * */
    public int getValues(){
        qualityOne = Integer.parseInt(txtQualityOne.getText());
        qualityTwo = Integer.parseInt(txtQualityTwo.getText());
        qualityThree = Integer.parseInt(txtQualityThree.getText());

        int total = qualityOne + qualityTwo + qualityThree;
        return total;
    }

    /**
     * Calculates how much 'fakemoney' the user has and ensures that the user cannot add more than what is indicated.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 17-06-2019
     * */
    public void counterPlus(String quality, TextField textfield){
        currentAmount = Integer.parseInt(textfield.getText());

        if(controller.checkQualityQuantity(quality, currentAmount)){
            currentAmount++;
            textfield.setText(String.valueOf(currentAmount));
            txtField.setText("");
        }
        else{
            txtField.setText("Can't select more than your current amount!");
        }
    }

    /**
     * Ensures that you cannot click lower than 0
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 17-06-2019
     * */
    public void counterMin(TextField textfield){
        currentAmount = Integer.parseInt(textfield.getText());

        if(currentAmount != 0){
            currentAmount--;
            textfield.setText(String.valueOf(currentAmount));
            txtField.setText("");
        }
        else{
            txtField.setText("Can't select lower dan 0!");
        }
    }

    @FXML
    public void pressLaunder() {
        if (controller.checkAmount(getValues())){
            controller.transferMoney(realMoney, qualityOne, qualityTwo, qualityThree);

            Stage stage = (Stage) anchorpane.getScene().getWindow();
            stage.close();
        } else{
            txtField.setText("You can only launder " + controller.getMaxAmount() + " bills!");
        }
    }

    @FXML
    public void plusQualityOne() {
        counterPlus(strQualityOne, txtQualityOne);
    }

    @FXML
    public void minQualityOne() {
        counterMin(txtQualityOne);
    }

    @FXML
    public void plusQualityTwo() {
        counterPlus(strQualityTwo, txtQualityTwo);
    }

    @FXML
    public void minQualityTwo() {
        counterMin(txtQualityTwo);
    }

    @FXML
    public void plusQualityThree() {
        counterPlus(strQualityThree, txtQualityThree);
    }

    @FXML
    public void minQualityThree() {
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
    }

    @Override
    public void start() {
    }
}
