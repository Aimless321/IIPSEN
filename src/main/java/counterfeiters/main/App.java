package counterfeiters.main;

import counterfeiters.controllers.ApplicationController;
import counterfeiters.managers.SoundManager;
import counterfeiters.views.ViewUtilities;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Start with a null scene
        Parent nullroot = new Pane();
        Scene scene = new Scene(nullroot, ViewUtilities.screenWidth, ViewUtilities.screenHeight);
        primaryStage.setScene(scene);

        new ApplicationController(primaryStage);

        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Counterfeiters");
        primaryStage.show();

        SoundManager.playLoop("background_music.mp3");
    }
}
