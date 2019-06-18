package counterfeiters.managers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class SoundManager {
    private static boolean muteSound = false;
    private static MediaPlayer player;
    private static MediaPlayer backgroundPlayer;

    public static void playSound(String fileName){
        Media sound = new Media(new File("./././././resources/sounds/" + fileName).toURI().toString());
        player = new MediaPlayer((sound));
        player.play();
        player.setMute(muteSound);
//        return player;
    }

    public static void playLoop(String fileName){
        Media sound = new Media(new File("./././././resources/sounds/" + fileName).toURI().toString());
        backgroundPlayer = new MediaPlayer((sound));
        backgroundPlayer.setAutoPlay(true);
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundPlayer.setMute(muteSound);
//        return player;
    }

    public static void muteSounds(){
        if (! muteSound){
            player.setMute(true);
            muteSound = true;
        }
    }

    public static void unmuteSounds(){
        if (muteSound){
            player.setMute(false);
            muteSound = false;
        }
    }

    public  static void toggleMute(){
        muteSound = !muteSound;
        player.setMute(muteSound);
        backgroundPlayer.setMute(muteSound);
    }
}