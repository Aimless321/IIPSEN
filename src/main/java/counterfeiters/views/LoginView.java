package counterfeiters.views;

import counterfeiters.controllers.LoginController;
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
 * This view contains the fields, that users has to use to login via the data from firebase.
 *
 * @author Robin van den Berg.
 */


public class LoginView implements Observer {
    public TextField username;
    public PasswordField password;
    public Text textField;
    public ImageView muteButton;

    public Pane pane;

    private Stage stage;
    private LoginController controller;


    private String name;
    private String psword;

    //Need an empty constructor for FXML
    public LoginView(){}
    public LoginView(Stage primaryStage, Object loginController) {
        this.stage = primaryStage;
        this.controller = (LoginController) loginController;

        show();
    }

    public void show() {
        Parent root = ViewUtilities.loadFxml("/views/login.fxml", stage, controller);

        //Find root pane and set background
        Pane pane = (Pane)root.lookup("AnchorPane");
        pane.setBackground(ViewUtilities.getBackground("/background/with-money-and-logo.png"));

        pane.setOnKeyPressed(event -> {
            if (event.equals(KeyCode.M)) {
                SoundManager.toggleMute();
            }
        });

        //Show it on the screen
        stage.getScene().setRoot(pane);
    }

    @FXML
    public void pressKey(KeyEvent event)
    {
        if(event.getCode() == KeyCode.ENTER)
        {
            pressLogIn();
        }

    }

    @FXML
    public void pressLogIn()
    {
        name = username.getText().toLowerCase().trim();
        psword = password.getText().trim();

        controller.loginButtonPressed(name, psword);

    }

    @FXML
    public void pressRegister()
    {
        controller.registerButtonPressed();
    }

    @FXML
    public void pressMute(MouseEvent mouseEvent) {
        SoundManager.toggleMute();

        if (SoundManager.muteSound) {
            muteButton.setOpacity(0.5);
        }
        else {
            muteButton.setOpacity(1);
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setController(Object controller)
    {
        LoginController loginController = (LoginController) controller;
        this.controller = loginController;
        loginController.registerObserver(this);
    }

    @Override
    public void update(Observable observable) {
        Account account = (Account)observable;
        textField.setText(account.getTextField());

    }

    @Override
    public void start() {

    }
}
