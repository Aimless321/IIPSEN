package counterfeiters.controllers;

import counterfeiters.views.LoginView;
import counterfeiters.views.Observer;
import javafx.stage.Stage;

/**
 * This controller only loads the correct views and provides further link to the AccountController (class).
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 05-06-2019
 * */

public class RegisterController {
    private ApplicationController app;

    //CONSTRUCTOR
    public RegisterController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    /**
     * If the checkCredentials method in 'accountController' returns true, the loginView will load. <br> Otherwise, it will be captured in 'Account'.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 05-06-2019
     * @return checkCredentials
     * */
    public void registerButtonPressed(String username, String password, String passwordCheck, Stage stage) {
        if (app.accountController.checkCredentials(username, password, passwordCheck)){
            app.loadView(LoginView.class, app.loginController);
        }
    }

    public void backToLoginButtonPressed(Stage stage){
        app.loadView(LoginView.class, app.loginController); //on click wordt de LoginView getoond.
    }

    public void registerObserver(Observer observer) {
        app.accountController.registerObserver(observer);
    }
}