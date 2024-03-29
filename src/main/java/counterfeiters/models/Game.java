package counterfeiters.models;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;
import javafx.application.Platform;

import java.util.*;

/**
 * The game model stores all of the game data, like turn, round, player etc.
 * Used for the lobbyview and is also connected to the board.
 */
public class Game implements Observable {
    private String gameId;
    private String lobbyName;
    private int numPlayers = 0;
    private ArrayList<Player> players = new ArrayList<>();
    private int round = 0;
    private int turn = 0;
    private Player firstPlayer = null;
    /**
     * The player that is playing on this client.
     */
    @Exclude
    public Player localPlayer;
    private Date startTime;
    private ArrayList<Observer> observers = new ArrayList<>();

    //Empty constructor for firebase
    public Game() {

    }

    /**
     * Create a new game with the player as host.
     * Sets the lobby name correctly, and pushes it to the firebase so other can join it.
     * @param player player that is the host.
     */
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

    /**
     * This method loads the scores in the scoreboard view
     * The scores are stored in a hashmap.
     * The hashmap is stored in an arraylist and sorted.
     * The arraylist is stored in a linkedHashMap.
     * The linkedHashMap returns to the scoreboard view.
     *
     * @author Robin van den Berg
     */

    public Map<String, Integer> getScores(ArrayList<Player> players) {
        Map<String, Integer> scores = new HashMap<>();
        LinkedHashMap<String, Integer> sortedScores = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            String name = players.get(i).getUserName();
            int score = players.get(i).getRealMoney().getTotalMoney() + players.get(i).getBahamasBank().getTotalBankMoney();
            scores.put(name, score);
        }

        for (Map.Entry<String, Integer> keys : scores.entrySet()) {
            list.add(keys.getValue());
        }
        list.sort(Collections.reverseOrder());

        for (Integer score : list) {
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                if (entry.getValue().equals(score)) {
                    sortedScores.put(entry.getKey(), score);
                }
            }
        }

        return sortedScores;
    }

    /**
     * Adds a player to the game and updates the firebase.
     * @param player
     */
    public void addPlayer(Player player) {
        numPlayers++;
        players.add(player);

        player.setPlayerId(numPlayers);

        updateFirebase();
    }

    /**
     * Start the game, pass a startTime and set the round to 1.
     */
    public void startGame() {
        //Set the starttime
        this.startTime = new Date();

        //Set round to 1, so the game starts for everyone
        this.round = 1;
        updateFirebase();
    }

    /**
     * Remove a player from the game.
     * @param player the player to remove.
     */
    public void removePlayer(Player player) {
        players.remove(player);

        numPlayers--;

        resetPlayerNumbers();

        updateFirebase();
    }

    /**
     * Called when a player leaves, moves all of the players to the lowest playerid possible.
     * Without this if for example the 2nd player leaves, and someone else joins you would get two player 3's.
     */
    private void resetPlayerNumbers() {
        //Start from the first player
        for(int i = 1; i < players.size(); i++) {
            Player player = players.get(i);

            //Playernumber starts from 1, so do it +1
            player.setPlayerId(i+1);
        }
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
        this.round = updateGame.getRound();
        this.turn = updateGame.getTurn();
        this.numPlayers = updateGame.numPlayers;
        this.startTime = updateGame.startTime;

        notifyAllObservers();
    }

    /**
     * Get a player from a username.
     * @param username username of the player.
     * @return Player or null if the players has not been found
     */
    @Exclude
    public Player getPlayerFromUserName(String username) {
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

    public void notifyAllObservers() {
        for(Observer obs : observers) {
            Platform.runLater(() -> obs.update(this));
        }
    }

    /**
     * Gets the player who's turn it is.
     * @param firstPlayerPawn the firstplayerpawn where the player that starts the round is stored.
     * @return player who's turn it is.
     */
    @Exclude
    public Player getCurrentPlayer(FirstPlayerPawn firstPlayerPawn) {
        //Store the current first player, so it only updates at round end
        if(firstPlayer == null) {
            this.firstPlayer = firstPlayerPawn.getFirstPlayer();
        }

        if(turn == 0) {
            this.firstPlayer = firstPlayerPawn.getFirstPlayer();
            return firstPlayerPawn.getFirstPlayer();
        }

        //Start at the first player index plus the turn, then modulo it for the amount of players
        int firstPlayerIndex = firstPlayer.getPlayerId()-1;
        Player current = players.get((turn + firstPlayerIndex) % players.size());

        return current;
    }

    /**
     * Checks if it is the localplayer's turn
     * @param firstPlayerPawn the firstplayerpawn where the player that starts the round is stored.
     * @return true/false
     */
    public boolean checkYourTurn(FirstPlayerPawn firstPlayerPawn) {
        return localPlayer.getPlayerId() == getCurrentPlayer(firstPlayerPawn).getPlayerId();
    }

    public void nextTurn() {
        turn++;
    }

    /**
     * The correct method will be called for updating the money.
     * The moneyType will always be real money.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 09-06-2019
     * */
    public void updateMoney(int amount){
        localPlayer.updateMoneyPlus(MoneyType.REAL, amount);
    }

    /**
     * Checks if all of the players have set 3 henchmen.
     * @return true/false
     */
    public boolean checkEndRound() {
        return turn == players.size() * 3;
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

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
