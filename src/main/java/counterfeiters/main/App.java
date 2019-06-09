package counterfeiters.main;

import counterfeiters.controllers.ApplicationController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class App extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new ApplicationController(primaryStage);

       // final ObservableList lobbies = FXCollections.observableArrayList("Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");


        primaryStage.setMaximized(true);
        primaryStage.setTitle("Counterfeiters");
        primaryStage.show();
    }
}
