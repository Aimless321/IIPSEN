package counterfeiters.controllers;

public class ApplicationController {
    //Store all controllers
    public MainMenuController mainMenuController;

    public ApplicationController() {
        //Create all controllers
        mainMenuController = new MainMenuController(this);
    }

}
