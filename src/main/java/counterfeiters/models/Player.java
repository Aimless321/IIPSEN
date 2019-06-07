package counterfeiters.models;

import counterfeiters.firebase.FirebaseService;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Player {
    private String userName;
    private int playerId = 1;
    private int score;
    private ArrayList<Card> cards = new ArrayList<>();

    public Player() {

    }

    public Player(String userName) {
        this.userName = userName;
    }

    public void leaveLobby(Game game) {
        FirebaseService fb = FirebaseService.getInstance();

        //If the lobby will be empty after this or if this player is the host, remove the lobby from firebase
        //Else remove this player from the lobby and update firebase
        if(game.getNumPlayers() == 1 || game.getPlayers().get(0) == this) {
            game.delete();
        } else {
            game.removePlayer(this);
        }
    }

    public String getUserName() {
        return userName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getScore() {
        return score;
    }

    public Image findImagePath() {
        String imagePath;

        switch(playerId) {
            case 1:
                imagePath = "/players/croc.jpg";
                break;
            case 2:
                imagePath = "/players/deer.jpg";
                break;
            case 3:
                imagePath = "/players/herron.jpg";
                break;
            case 4:
                imagePath = "/players/hippo.jpg";
                break;
            default:
                imagePath = "/players/croc.jpg";
                System.err.println("Invalid player number passed to Player.findImagePath()");
                break;
        }

        return new Image(getClass().getResourceAsStream(imagePath));
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setScore(int score) {
        this.score = score;
    }


    //    public boolean hasPlaneTicket(){
//        return ;
//    }
}
