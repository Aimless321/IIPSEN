package counterfeiters.views;

import counterfeiters.controllers.RegisterController;
import counterfeiters.models.Account;
import counterfeiters.models.Observable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterView implements Observer {
    public TextField username;
    public PasswordField password;
    public PasswordField passwordCheck;
    public Text textField;

    private String name;
    private String psword;
    private String pswordCheck;

    private Stage stage;
    private RegisterController controller;

    //Need an empty constructor for FXML
    public RegisterView() {

    }

    /**
     * Hier wordt de RegisterView aangemaakt door primarystage en registerController mee te geven als argumenten.
     * Omdat registerController als Object wordt aangegeven moet het binnen de methode nog geconverteerd worden.
     * De registerController is eerder aangemaakt als object. Hiermee kan de view getoond worden.
     *
     * @author Ali Rezaa Ghariebiyan
     * version 03-06-2019
     * @return RegisterView
     * @param
     * */
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

        //Show it on the screen
        Scene scene = new Scene(root, ViewUtilities.screenWidth, ViewUtilities.screenHeight);

        stage.setScene(scene);
    }

    @FXML
    public void pressRegister() {
        name = username.getText().toLowerCase().trim();
        psword = password.getText().trim();
        pswordCheck = passwordCheck.getText().trim();

        System.out.println("Register button pressed");

        controller.registerButtonPressed(name, psword, pswordCheck);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller) {
        System.out.println("IN");
        RegisterController registerController = (RegisterController) controller;
        this.controller = registerController;
        registerController.registerObserver(this);
    }

    @Override
    public void update(Observable observable) {
        Account account = (Account)observable;
        textField.setText(account.getTextField());
    }

    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){pressRegister();}
    }

    @Override
    public void start() {

    }
}
