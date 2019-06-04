package counterfeiters.controllers;

import counterfeiters.models.Account;
import counterfeiters.views.Observer;

public class RegisterController {
    private ApplicationController app;
    public Account account;

    public RegisterController(ApplicationController applicationController) {
        this.app = applicationController;
        account = new Account();
    }

    public void registerButtonPressed(String username, String password, String passwordCheck) {
        app.accountController.addUser(username, password, passwordCheck);
    }

    public void registerObserver(Observer observer) {
        Account account = app.registerController.account;
        account.registerObserver(observer);
    }
}