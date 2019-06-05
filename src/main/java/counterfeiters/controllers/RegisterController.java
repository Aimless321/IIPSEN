package counterfeiters.controllers;

import counterfeiters.views.LoginView;
import counterfeiters.views.Observer;
import javafx.stage.Stage;

public class RegisterController {
    private ApplicationController app;
    public LoginController loginController;

    public RegisterController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void registerButtonPressed(String username, String password, String passwordCheck, Stage stage) {
//        app.accountController.checkCredentials(username, password, passwordCheck);
        if (app.accountController.checkCredentials(username, password, passwordCheck)){
//            app.accountController.checkCredentials(username, password, passwordCheck);
            app.loadView(LoginView.class, stage, loginController);
        }
    }

    public void registerObserver(Observer observer) {
        app.accountController.registerObserver(observer);
    }
}