package counterfeiters.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewUtilities {
    public static double screenWidth = Screen.getPrimary().getBounds().getWidth();
    public static double screenHeight = Screen.getPrimary().getBounds().getHeight();

    public static Background getBackground(String path) {
        //Load image with classloader
        Image image = new Image(ViewUtilities.class.getResourceAsStream(path));

        //Set background size
        BackgroundSize backgroundSize = new BackgroundSize(1920, 1080, false, false, false, true);

        //Init backgroundImage
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        //Return the actual Background with all the settings in it
        return new Background(backgroundImage);
    }

    public static Parent loadFxml(String path, Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.<Parent>load(ViewUtilities.class.getResourceAsStream(path));

            Observer mainMenuView = (Observer)fxmlLoader.getController();
            mainMenuView.setStage(stage);

            return root;
        } catch (IOException e) {
            System.out.println("MainMenuView cannot find the fxml file");
            e.printStackTrace();
        }

        return null;
    }
}
