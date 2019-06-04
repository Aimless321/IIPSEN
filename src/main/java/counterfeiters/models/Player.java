package counterfeiters.models;

import counterfeiters.firebase.FirebaseService;

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
