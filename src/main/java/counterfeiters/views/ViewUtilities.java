package counterfeiters.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;

public class ViewUtilities {
    public static Background getBackground(String path) {
        //Load image with classloader
        Image image = new Image(ViewUtilities.class.getResourceAsStream(path));

        //Set background size
        BackgroundSize backgroundSize = new BackgroundSize(1920, 1080, false, false, false, false);

        //Init backgroundImage
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        //Return the actual Background with all the settings in it
        return new Background(backgroundImage);
    }

    public static Parent loadFxml(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.<Parent>load(ViewUtilities.class.getResourceAsStream(path));

            return root;
        } catch (IOException e) {
            System.out.println("MainMenuView cannot find the fxml file");
            e.printStackTrace();
        }

        return null;
    }
}
