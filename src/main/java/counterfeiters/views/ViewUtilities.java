package counterfeiters.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Helper functions for the views, handles the loading of FXML and backgrounds.
 * @author Wesley Bijleveld
 */
public class ViewUtilities {
    public static double screenWidth = Screen.getPrimary().getBounds().getWidth();
    public static double screenHeight = Screen.getPrimary().getBounds().getHeight();

    /**
     *
     * @param path
     * @return
     */
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

    /**
     *
     * @param path the (class)path to the fxml file
     * @param stage stage where the view needs to be shown
     * @param controller the controller of the view
     * @return the parent loaded from the fxml file
     */
    public static Parent loadFxml(String path, Stage stage, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.<Parent>load(ViewUtilities.class.getResourceAsStream(path));

            Observer observer = (Observer)fxmlLoader.getController();
            observer.setStage(stage);
            observer.setController(controller);

            //Tell the view it can now initialize, because it now contains all the data

            return root;
        } catch (IOException e) {
            System.out.println("MainMenuView cannot find the fxml file");
            e.printStackTrace();
        }

        return null;
    }
}
