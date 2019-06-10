package counterfeiters.models;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;

import java.util.ArrayList;
import java.util.Date;

public class Game implements Observable {
    private String gameId;
    private String lobbyName;
    private int numPlayers = 0;
    private ArrayList<Player> players = new ArrayList<>();
    private int round = 0;
    @Exclude
    public Player localPlayer;
    private Date startTime;
    private ArrayList<Observer> observers = new ArrayList<>();

    public Game() {

    }

    public void createNewGame(Player player) {

        //Get the firebase service
        FirebaseService fb = FirebaseService.getInstance();

        //Insert a new document
        DocumentReference lobbyDoc = fb.insert("lobbies");

        //Initialize variables
        this.gameId = lobbyDoc.getId();
        addPlayer(player);

        this.lobbyName = player.getUserName() + "'s Lobby";
        this.localPlayer = player;
        fb.setClass("lobbies", gameId, this);

        notifyAllObservers();
    }

    public void addPlayer(Player player) {
        numPlayers++;
        players.add(player);

        player.setPlayerId(numPlayers);

        updateFirebase();
    }

    public void removePlayer(Player player) {
        players.remove(player);

        numPlayers--;

        updateFirebase();
    }

    public void updateFirebase() {
        FirebaseService fb = FirebaseService.getInstance();
        fb.setClass("lobbies", gameId, this);
    }

    public void delete() {
        FirebaseService fb = FirebaseService.getInstance();
        fb.delete("lobbies", gameId);

        players.clear();
    }

    public boolean checkHost() {
        Player host = players.get(0);

        return host == localPlayer;
    }

    public void updateData(Game updateGame) {
        this.players = updateGame.getPlayers();
        this.localPlayer = getPlayerFromUserName(localPlayer.getUserName());

        notifyAllObservers();
    }

    @Exclude
    private Player getPlayerFromUserName(String username) {
        for (Player player : players) {
            if(player.getUserName().equals(username)) {
                return player;
            }
        }

        return null;
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

    /**
     * Based on the indicated character, the correct method will be called for updating the money.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 09-06-2019
     * */
    public void updateMoney(int qId, String character, int amount){
        if (character.equals("+")){
            localPlayer.updateMoneyPlus(qId, amount);
        }
        if (character.equals("-")){
            localPlayer.updateMoneyReduce(qId, amount);
        }
        notifyAllObservers();
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

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }
}
