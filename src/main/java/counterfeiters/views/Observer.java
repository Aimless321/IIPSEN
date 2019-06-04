package counterfeiters.views;

import counterfeiters.models.Observable;
import javafx.stage.Stage;

public interface Observer {
    public void setStage(Stage stage);
    public void setController(Object controller);
    public void update(Observable observable);
}
