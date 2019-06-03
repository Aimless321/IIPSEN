package counterfeiters.views;

import javafx.stage.Stage;

public interface Observer {
    public void setStage(Stage stage);
    public void setController(Object controller);
}
