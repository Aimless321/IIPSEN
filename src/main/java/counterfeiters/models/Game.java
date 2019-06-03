package counterfeiters.models;

import com.google.cloud.firestore.DocumentReference;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Game implements Observable {
    private String gameId;
    private int numPlayers;
    private ArrayList<Player> players = new ArrayList<>();
    private int round;
    private Date startTime;
    private ArrayList<Observer> observers = new ArrayList<>();

    public void createNewGame(Player player) {
        //Get the firebase service
        FirebaseService fb = FirebaseService.getInstance();

        //Insert a new document
        DocumentReference lobbyDoc = fb.insert("lobbies");

        //Initialize variables
        gameId = lobbyDoc.getId();
        numPlayers = 1;
        round = 0;
        //TODO: Add player
        players.add(player);

        fb.setClass("lobbies", gameId, this);

        notifyAllObservers();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyAllObservers() {
        for(Observer obs : observers) {
            obs.update(this);
        }
    }

    public String getGameId() {
        return gameId;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getRound() {
        return round;
    }

    public Date getStartTime() {
        return startTime;
    }
}
