package counterfeiters.models;

import java.util.ArrayList;

public class Player {
    private boolean isLoggedIn;
    private String userName;
    private int playerId;
    private int score;
    private ArrayList<Card> cards = new ArrayList<>();

    public Player(boolean isLoggedIn, String userName, int playerId, int score) {
        this.isLoggedIn = isLoggedIn;
        this.userName = userName;
        this.playerId = playerId;
        this.score = score;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
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

    //    public boolean hasPlaneTicket(){
//        return ;
//    }
}
