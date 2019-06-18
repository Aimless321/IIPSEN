package counterfeiters.managers;

        import javafx.scene.media.Media;
        import javafx.scene.media.MediaPlayer;


public class SoundManager {
    public static boolean muteSound = false;
    private static MediaPlayer player;
    private static MediaPlayer backgroundPlayer;

    public static void playSound(String fileName){
        if(muteSound) {
            return;
        }

        Media sound = new Media(SoundManager.class.getResource("/sounds/" + fileName).toString());
        player = new MediaPlayer((sound));
        player.play();
        player.setMute(muteSound);
    }

    public static void playLoop(String fileName){
        Media sound = new Media(SoundManager.class.getResource("/sounds/" + fileName).toString());
        backgroundPlayer = new MediaPlayer((sound));
        backgroundPlayer.setAutoPlay(true);
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundPlayer.setMute(muteSound);
    }

    public static void muteSounds(){
        muteSound = true;
    }

    public static void unmuteSounds(){
        muteSound = false;
    }

    public  static void toggleMute(){
        muteSound = !muteSound;
        backgroundPlayer.setMute(muteSound);
    }
}