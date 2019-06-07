package counterfeiters.controllers;

import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.Observer;
import counterfeiters.views.RegisterView;
import javafx.stage.Stage;

public class LoginController {
    private ApplicationController app;

    public LoginController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void loginButtonPressed(String username, String password, Stage stage)
    {
        if(app.accountController.loginPressed(username, password))
        {
            app.loadView(MainMenuView.class, stage, app.mainMenuController);
        }


    }

    public void registerButtonPressed(Stage stage)
    {
        app.loadView(RegisterView.class, stage, app.registerController);
    }

    public void registerObserver(Observer observer) {
        app.accountController.registerObserver(observer);
    }

}
