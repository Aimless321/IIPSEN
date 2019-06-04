package counterfeiters.controllers;

import counterfeiters.views.MainMenuView;
import counterfeiters.views.RegisterView;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.lang.reflect.InvocationTargetException;

/**
 * The link between all the controllers.
 * So every controller can talk to any controller they want.
 * Also handles the switching of views.
 * @author Wesley Bijleveld
 */
public class ApplicationController {
    //Store all controllers
    public MainMenuController mainMenuController;
    public LoginController loginController;
    public RegisterController registerController;
    public LobbyController lobbyController;
    public ScoreboardController scoreboardController;
    public GameController gameController;
    public AccountController accountController;

    public ApplicationController(Stage stage) {
        //Create all controllers
        mainMenuController = new MainMenuController(this);
        lobbyController = new LobbyController(this);
        scoreboardController = new ScoreboardController(this);
        loginController = new LoginController(this);
        registerController = new RegisterController(this);
        gameController = new GameController(this);
        accountController = new AccountController(this);
        //Load first view
        loadView(RegisterView.class, stage, registerController);
    }

    /**
     * Handles the switching of views, and passes the controller to it.
     * Example usage:<br>
     * - loadView(LobbyView.class, stage, controller)
     * @param view The class of the view you want to show
     * @param stage The stage where the view needs to be shown
     * @param controller The controller that handles the interaction of the view
     */
    public void loadView(Class view, Stage stage, Object controller) {
        try {
            view.getDeclaredConstructor(Stage.class, Object.class).newInstance(stage, controller);
        } catch (InstantiationException e) {
            System.err.println("Cannot create instance for " + view.toString());
        } catch (IllegalAccessException e) {
            System.err.println("Cannot access " + view.toString());
        } catch (NoSuchMethodException e) {
            System.err.println("Constructor does not exist or has the wrong parameters in " + view.toString());
        } catch (InvocationTargetException e) {
            System.err.println("Exception thrown by " + view.toString());
            e.printStackTrace();
        }
    }
}
