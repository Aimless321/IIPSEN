package counterfeiters.controllers;

import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import counterfeiters.views.RegisterView;


/**
 * This controller is the link between the login view and the account controller, via the application controller, that contains the account model.
 */

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
