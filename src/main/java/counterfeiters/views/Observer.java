package counterfeiters.views;

import counterfeiters.models.Observable;
import javafx.stage.Stage;

public interface Observer {
    /**
     * Called after the FXML has been loaded.
     * FXML creates a new instance, so it has to be passed to the new instance.
     * @param stage the main stage of the application
     */
    public void setStage(Stage stage);

    /**
     * Called after the FXML has been loaded.
     * FXML creates a new instance, so it has to be passed to the new instance.
     * @param controller the controller that was passed to the loadView method
     */
    public void setController(Object controller);

    /**
     * Called by an observable to let the observer know that it has to update.
     * @param observable the observable that updated.
     */
    public void update(Observable observable);

    /**
     * Called after the View has been fully filled with all of the data.
     * Can be used for registering the observer and initializing a screen
     */
    public void start();

}
