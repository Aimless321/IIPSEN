package counterfeiters.models;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

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
    private Game game;

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

    public Map<String, String> loadScores() {
        FirebaseService fb = FirebaseService.getInstance();


        Game game = fb.get("games", "xRaEhTmY4vq83iCml19F").toObject(Game.class);
        Map<String, String> scores = new HashMap();
        ArrayList<Player> players = game.getGame().getPlayers();

        for (int i = 0; i < players.size(); i++) {
            String name = players.get(i).getUserName();
            String score = Integer.toString(players.get(i).getScore());
            scores.put(name, score);

        }

        for (String i : scores.keySet()) {
            System.out.println(i + " " + scores.get(i));
        }

        Map<String, String> sortedScores = scores
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println(sortedScores);
        System.out.println(scores);


        return sortedScores;
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

    /**
     * Write this object to the firebase.
     */
    public void updateFirebase() {
        FirebaseService fb = FirebaseService.getInstance();
        fb.setClass("lobbies", gameId, this);
    }

    /**
     * Delete the lobby.
     */
    public void delete() {
        FirebaseService fb = FirebaseService.getInstance();
        fb.delete("lobbies", gameId);

        players.clear();
    }

    /**
     * Check if the localclient is the host.
     * @return true/false
     */
    public boolean checkHost() {
        Player host = players.get(0);

        return host == localPlayer;
    }

    /**
     * Update the data in this model,
     * Is used to add/remove players to the view when someone joins/leaves.
     * @param updateGame
     */
    public void updateData(Game updateGame) {
        this.players = updateGame.getPlayers();
        this.localPlayer = getPlayerFromUserName(localPlayer.getUserName());

        notifyAllObservers();
    }

    /**
     * Get a player from a username.
     * @param username username of the player.
     * @return Player
     */
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

    public Game getGame(){return game;}

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
