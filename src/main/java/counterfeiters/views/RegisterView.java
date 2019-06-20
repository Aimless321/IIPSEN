package counterfeiters.views;

import counterfeiters.controllers.RegisterController;
import counterfeiters.managers.SoundManager;
import counterfeiters.models.Account;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The registerView will be created by giving the primarystage and registerController as arguments.
 *
 * Because the registerController is specified as an object, it must be converted within the method to be able to call it as an object.
 * This class makes it possible to invoke different methods to save a new user in the firebase.
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 20-06-2019
 * */

public class RegisterView implements Observer {

    // Variables for the view.
    public TextField username;
    public PasswordField password;
    public PasswordField passwordCheck;
    public Text textField;
    public ImageView muteButton;

    private String name;
    private String psword;
    private String pswordCheck;

    private Stage stage;
    private RegisterController controller;

    //Need an empty constructor for FXML
    public RegisterView() {

    }

    //CONSTRUCTOR
    public RegisterView(Stage primaryStage, Object registerController) {
        this.stage = primaryStage;
        this.controller = (RegisterController) registerController;

        show();
    }

     public void show() {
        Parent root = ViewUtilities.loadFxml("/views/register.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("Pane");
        pane.setBackground(ViewUtilities.getBackground("/background/with-money-and-logo.png"));

         pane.setOnKeyPressed(event -> {
             if (event.getCode() == KeyCode.M) {
                 SoundManager.toggleMute();
             }
         });

        //Show it on the screen
        stage.getScene().setRoot(pane);
    }

    /**
     * The values ​​entered in the input fields in the scene are retrieved.
     * The RegisterController is called and the retrieved values ​​are sent as an argument.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 05-06-2019
     * */
    @FXML
    public void pressRegister() {
        // Get values
        name = username.getText().toLowerCase().trim();
        psword = password.getText().trim();
        pswordCheck = passwordCheck.getText().trim();

        controller.registerButtonPressed(name, psword, pswordCheck);
    }

    @FXML
    public void pressMute(MouseEvent mouseEvent) {
        SoundManager.toggleMute();

        if (SoundManager.muteSound) {
            muteButton.setOpacity(1);
        }
        else {
            muteButton.setOpacity(0.5);
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {

        RegisterController registerController = (RegisterController) controller;
        this.controller = registerController;
        registerController.registerObserver(this);
    }

    /**
     * This method is called in the 'account' model by the 'notifyAllObservers' method if an update is required.
     *
     * @author Ali Rezaa Ghariebiyan
     * version 03-06-2019
     * */
    @Override
    public void update(Observable observable) {
        Account account = (Account)observable;
        textField.setText(account.getTextField());
    }

    // Makes it able to use 'enter'.
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){pressRegister();}
    }

    @Override
    public void start() {

    }

    @FXML
    public void backToMenu() {
        controller.backToLoginButtonPressed(this.stage); // Gaat terug naar het login scherm.
    }
}
