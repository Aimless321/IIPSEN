package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;
import counterfeiters.events.EventHandler;
import counterfeiters.events.EventListener;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;

public class Board implements Observable, EventListener {
    private ArrayList<Observer> observers = new ArrayList<>();
    public BlackMarket blackmarket = new BlackMarket();
    public PolicePawn policePawn = new PolicePawn(4);
    public FirstPlayerPawn firstPlayerPawn = new FirstPlayerPawn();
    private ArrayList<Henchman> henchmen = new ArrayList<>();
    private HashMap<String, String> hmap = new HashMap<String, String>();
    public Game game;

    public Board() {

    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);

        EventHandler.getInstance().registerListener(blackmarket);
        EventHandler.getInstance().registerListener(this);
    }

    @Override
    public void notifyAllObservers() {
        for(Observer obs : observers) {
            Platform.runLater(() -> obs.update(this));
        }
    }

    public void prepareBlackMarket() {
        if(blackmarket.cardRow.size() > 0) {
            return;
        }
        
        blackmarket.refill();
        notifyAllObservers();

        updateFirebase();
    }

    public void placeHenchman(double posX, double posY, String player, String buttonId) {
        henchmen.add(new Henchman(posX, posY, player, buttonId));
    }


    /**
     * This method check wheter the totalMoney is more than the give amount.
     * if its true the method 'updateMoneyReduce' will be called.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 20-06-2019
     * */
    public boolean checkMoney(MoneyType type, int amount){
        if (game.localPlayer.realMoney.getTotalMoney() >= amount){
            game.localPlayer.updateMoneyReduce(type, amount);
            return true;
        }

        return false;
    }


    /**
     * Checks if the user has a 'diner' at the end of the round. If true the user will get 50 real money.
     *
     * @author Ali Rezaa Ghariebiyan, Robin van den Berg
     * @version 20-06-2019
     * */
    public void giveMoneyOnEnd(String cardName){
        //Give money for each for each card
        for (Player player : game.getPlayers()) {
            for (Card card : player.getCards()) {
                if (card.getName().equals(cardName)) {
                    player.updateMoneyPlus(MoneyType.REAL, 50);
                }
            }
        }
    }
  
    public boolean checkYourTurn() {
        return game.checkYourTurn(firstPlayerPawn);
    }

    @Exclude
    public Player getCurrentPlayer() {
        return game.getCurrentPlayer(firstPlayerPawn);
    }

    public void updateFirebase() {
        FirebaseService fb = FirebaseService.getInstance();
        fb.setClass("games", game.getGameId(), this);
    }

    /**
     * Updates all the data for every model that is connected to this class.
     * Called when firebase has an update.
     * @param updateBoard the board that has the updated data.
     */
    public void updateData(Board updateBoard) {
        this.henchmen = updateBoard.getHenchmen();

        //Update all the other models aswell
        game.updateData(updateBoard.game);
        blackmarket.updateData(updateBoard.blackmarket);
        policePawn.updateData(updateBoard.policePawn);
        firstPlayerPawn.updateData(updateBoard.firstPlayerPawn);

        notifyAllObservers();
    }

    /**
     * Checks if the round has to be ended.
     * If so resets the turn back to 0, and increments the round.
     */
    public void checkEndRound() {
        if(!game.checkEndRound() ) {
            return;
        }

        //Remove the henchmen
        this.henchmen = new ArrayList<>();

        //Set turn back to 0, and add 1 to the round
        game.setTurn(0);
        game.setRound(game.getRound()+1);

        EventHandler.getInstance().endRound();
    }

    /**
     * Calculates the given bills and gives it to the player.
     *
     * @author Ali Rezaa
     * @version 20-06-2019
     * */
    public void transferMoneySupermarket(MoneyType qualityId, int qualityOne, int qualityTwo, int qualityThree) {
        int result = (qualityOne + qualityTwo + qualityThree) * 50;

        game.localPlayer.updateMoneyPlus(qualityId, result);
        game.localPlayer.updateMoneyReduce(MoneyType.FAKE_ONE, qualityOne);
        game.localPlayer.updateMoneyReduce(MoneyType.FAKE_TWO, qualityTwo);
        game.localPlayer.updateMoneyReduce(MoneyType.FAKE_THREE, qualityThree);
        notifyAllObservers();
    }

    /**
     * Calculates the given bills and gives it to the player.
     *
     * @author Ali Rezaa
     * @version 20-06-2019
     * */
    public void transferMoneyHealer(MoneyType qualityId, int qualityOne, int qualityTwo, int qualityThree) {
        int resultQualityOne = qualityOne * 20;
        int resultQualityTwo = qualityTwo * 30;
        int resultQualityThree = qualityThree * 40;

        int result = resultQualityOne + resultQualityTwo + resultQualityThree;

        game.localPlayer.updateMoneyPlus(qualityId, result);
        game.localPlayer.updateMoneyReduce(MoneyType.FAKE_ONE, qualityOne);
        game.localPlayer.updateMoneyReduce(MoneyType.FAKE_TWO, qualityTwo);
        game.localPlayer.updateMoneyReduce(MoneyType.FAKE_THREE, qualityThree);
        notifyAllObservers();
    }

    public boolean makePurchase(int cardNumber) {
        Card card;


        try {
            card = blackmarket.cardRow.get(cardNumber);
        }catch (IndexOutOfBoundsException e) {
            return false;
        }

        game.localPlayer.addCard(card);
        blackmarket.makeCardPurchased(cardNumber);

        return true;
    }

    public void advancePolice() {
        policePawn.advance();
        //Checking if the policepawn is on "Godfather", if so the player loses half of his realmoney
        if (policePawn.godfatherCheck()) {
            for(Player player : game.getPlayers()) {
                player.updateMoneyReduce(MoneyType.REAL,player.getRealMoney().getTotalMoney()/2);
            }
        }
        notifyAllObservers();
    }

    public void makeFirstPlayer(){
        firstPlayerPawn.setNextFirstPlayer(game.localPlayer);
    }

    public ArrayList<Henchman> getHenchmen() {
        return henchmen;
    }

    public void setHenchmen(ArrayList<Henchman> henchmen) {
        this.henchmen = henchmen;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    /**
     * This method checks whether the quality provided is equal to the number of bills the player has.
     * If this is true it will return false.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 20-06-2019
     * */
    public boolean checkQualityQuantity(int amount, String quality) {
        if (game.localPlayer.getFakeMoney().getQuality(quality) == amount){
            return false;
        }
        else
            return true;
    }

    /**
     * Sets the host as firstplayer if not done yet.
     */
    public void prepareFirstPlayer() {
        if(firstPlayerPawn.getFirstPlayer() != null) {
            return;
        }

        Player host = game.getPlayers().get(0);
        firstPlayerPawn.setFirstPlayer(host);
    }


    @Exclude
    public void setPlayersAndCards() {
        for (Player player : game.getPlayers()) {
            hmap.put(String.valueOf(player.getPlayerId()), player.getUserName());
        }
    }

    @Exclude
    public HashMap<String,String> getPlayersAndCards() {
        return hmap;
    }

    @Override
    public void onRoundStart() {

    }

    @Override
    public void onRoundEnd() {
        giveMoneyOnEnd("diner");
    }


    @Override
    public void onGameEnd() {
        giveMoneyOnEnd("scratchcard");

    }
}