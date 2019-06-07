package counterfeiters.controllers;

import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import counterfeiters.views.RegisterView;
import javafx.stage.Stage;

public class LoginController {
    private ApplicationController app;

    public LoginController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void loginButtonPressed(String username, String password)
    {
        if(app.accountController.loginPressed(username, password))
        {
            app.loadView(MainMenuView.class, app.mainMenuController);
        }


    }

    public void registerButtonPressed()
    {
        app.loadView(RegisterView.class, app.registerController);
    }

    public void registerObserver(Observer observer) {
        app.accountController.registerObserver(observer);
    }

}
