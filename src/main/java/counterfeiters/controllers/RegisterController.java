package counterfeiters.controllers;

import counterfeiters.models.Account;
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
//        app.accountController.addUser(username, password, passwordCheck);
        if (app.accountController.addUser(username, password, passwordCheck)){
//            app.accountController.addUser(username, password, passwordCheck);
            app.loadView(LoginView.class, stage, loginController);
        }
    }

    public void registerObserver(Observer observer) {
        app.accountController.registerObserver(observer);
    }
}