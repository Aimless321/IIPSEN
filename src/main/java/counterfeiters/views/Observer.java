package counterfeiters.views;

import counterfeiters.models.Observable;
import javafx.stage.Stage;

public interface Observer {
    public void setStage(Stage stage);
    public void setController(Object controller);
    public void update(Observable observable);

    /**
     * Called after the View has been fully filled with all of the data.
     * Can be used for registering the observer and initializing a screen
     */
    public void start();
}
