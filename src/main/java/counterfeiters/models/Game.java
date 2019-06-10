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

    public Map<String, Integer> loadScores() {
        FirebaseService fb = FirebaseService.getInstance();


        Game game = fb.get("games", "NsEoFJ2rkgbZtq8zXXex").toObject(Game.class);
        Map<String, Integer> scores = new HashMap();
        LinkedHashMap<String, Integer> sortedScores = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Player> players = game.getGame().getPlayers();

        for (int i = 0; i < players.size(); i++) {
            String name = players.get(i).getUserName();
            int score = (players.get(i).getScore());
            scores.put(name, score);

        }

        for (Map.Entry<String, Integer> keys : scores.entrySet())
        {
            list.add(Integer.valueOf(keys.getValue()));
        }
        Collections.sort(list, Collections.reverseOrder());

        for(Integer score : list){
            for (Map.Entry<String, Integer> entry : scores.entrySet())
            {
                if(entry.getValue().equals(score))
                {
                    sortedScores.put(entry.getKey(),score);
                }
            }
        }

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
