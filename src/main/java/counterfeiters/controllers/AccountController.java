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

    public boolean loginPressed(String username, String password)
    {
        account = new Account(username, password);
        if(account.checkCredentials(username, password))
        {
            return true;
        }
        else{return false;}

    }

}
