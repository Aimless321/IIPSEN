package counterfeiters.controllers;

import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.MainMenuView;
import counterfeiters.views.RegisterView;
import javafx.stage.Stage;

public class LoginController {
    private ApplicationController app;

    public LoginController(ApplicationController applicationController) {
        this.app = applicationController;
    }

    public void loginButtonPressed(Stage stage)
    {
        app.accountController.loginPressed(stage);
    }

    public void registerButtonPressed(Stage stage)
    {
        app.loadView(RegisterView.class, stage, app.registerController);
    }

}
