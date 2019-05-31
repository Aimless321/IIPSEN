package counterfeiters.main;

import counterfeiters.views.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new MainMenuView(primaryStage);

        primaryStage.setMaximized(true);
        primaryStage.setTitle("Counterfeiters");
        primaryStage.show();
    }
}
