package counterfeiters.controllers;

import counterfeiters.models.Account;
import counterfeiters.views.MainMenuView;
import javafx.stage.Stage;

public class AccountController {
    private Account account = new Account();
    private ApplicationController app;

    public AccountController(ApplicationController applicationController)
    {
        this.app = applicationController;
    }

    public void addUser(){}
    public void loginPressed(Stage stage)
    {

        app.loadView(MainMenuView.class, stage, app.mainMenuController);
        account.login();
    }

}
