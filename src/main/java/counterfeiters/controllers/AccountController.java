package counterfeiters.controllers;

import counterfeiters.models.Account;
import counterfeiters.views.MainMenuView;
import javafx.stage.Stage;

public class AccountController {
    private Account account;
    private ApplicationController app;

    public AccountController(ApplicationController applicationController)
    {
        this.app = applicationController;
    }

    public void addUser(){}

    public void loginPressed(String username, String password)
    {

        account.login(username, password);


    }

}
