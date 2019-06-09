package counterfeiters.models;

import com.google.cloud.firestore.DocumentReference;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;
import static java.util.Map.Entry.*;
import static java.util.stream.Collectors.toMap;

public class Game implements Observable {
    private String gameId;
    private int numPlayers = 1;
    private ArrayList<Player> players = new ArrayList<>();
    private int round = 0;
    private Date startTime;
    private ArrayList<Observer> observers = new ArrayList<>();
    private Game game;

    public Game() {

    }

    public Game(String gameId, int numPlayers, ArrayList<Player> players, int round, Date startTime, ArrayList<Observer> observers) {
        this.gameId = gameId;
        this.numPlayers = numPlayers;
        this.players = players;
        this.round = round;
        this.startTime = startTime;
    }

    public void createNewGame(Player player) {
        //Get the firebase service
        FirebaseService fb = FirebaseService.getInstance();

        //Insert a new document
        DocumentReference lobbyDoc = fb.insert("lobbies");

        //Initialize variables
        gameId = lobbyDoc.getId();
        players.add(player);

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
        players.add(player);

        numPlayers++;

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

    public void updateData(Game updateGame) {
        this.players = updateGame.getPlayers();

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

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }
}
