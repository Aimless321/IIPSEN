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
        blackmarket.refill();
        notifyAllObservers();

        updateFirebase();
    }

    public void placeHenchman(double posX, double posY, String player, String buttonId) {
        henchmen.add(new Henchman(posX, posY, player, buttonId));
    }


    /**
     * This method checks whether the user has enough money, If this is the case, the money wil de deducted from his account.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 11-06-2019
     * */
    public boolean checkMoney(MoneyType type, int bedrag){
        if (game.localPlayer.realMoney.getTotalMoney() >= bedrag){
            game.localPlayer.updateMoneyReduce(type, bedrag);
            return true;
        }

        return false;
    }


    public int[] printMoney()
    {

        int[] print = new int[2];
        boolean upgrade = true;
        int printerCounter = 0;
        int upgradeCounter = 0;

        ArrayList<Card> cards = game.localPlayer.getCards();
        for (Card card : cards) {
            if(card.getName().equals("printer")) {
                printerCounter = printerCounter + 2;
            }
            if(upgrade) {
                if (card.getName().equals("upgrade")) {
                    upgradeCounter++;
                }
            }

            if (upgradeCounter == 3)
            {
                upgrade = false;
            }

        }

        print[0] = upgradeCounter;
        print[1] = printerCounter;

        return print;

    }

    public void giveMoneyOnEnd(String cardName){
        ArrayList<Card> cards = game.localPlayer.getCards();

        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                game.localPlayer.updateMoneyPlus(MoneyType.REAL, 50);
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

    public void updateData(Board updateBoard) {
        this.henchmen = updateBoard.getHenchmen();

        //Update all the other models aswell
        game.updateData(updateBoard.game);
        blackmarket.updateData(updateBoard.blackmarket);
        policePawn.updateData(updateBoard.policePawn);
        firstPlayerPawn.updateData(updateBoard.firstPlayerPawn);

        notifyAllObservers();
    }

    public void checkEndRound() {
        if(!game.checkEndRound()) {
            return;
        }

        //Remove the henchmen
        this.henchmen = new ArrayList<>();

        //Set turn back to 0, and add 1 to the round
        game.setTurn(0);
        game.setRound(game.getRound()+1);

        EventHandler.getInstance().endRound();
    }

    public void transferMoneySupermarket(MoneyType qualityId, int qualityOne, int qualityTwo, int qualityThree) {
        int result = (qualityOne + qualityTwo + qualityThree) * 50;

        game.localPlayer.updateMoneyPlus(qualityId, result);
        game.localPlayer.updateMoneyReduce(MoneyType.FAKE_ONE, qualityOne);
        game.localPlayer.updateMoneyReduce(MoneyType.FAKE_TWO, qualityTwo);
        game.localPlayer.updateMoneyReduce(MoneyType.FAKE_THREE, qualityThree);
        notifyAllObservers();
    }

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
        notifyAllObservers();
    }

    public void makeFirstPlayer(){
        firstPlayerPawn.setFirstPlayer(game.localPlayer);
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

    public boolean checkQualityQuantity(int amount, String quality) {

        if (game.localPlayer.getFakeMoney().getQuality(quality) == amount){
            return false;
        }
        else
            return true;
    }

    public void prepareFirstPlayer() {
        Player host = game.getPlayers().get(0);
        firstPlayerPawn.setFirstPlayer(host);
    }

    @Exclude
    public void setPlayersAndCards() {
        for (Player player : game.getPlayers()) {
            hmap.put(String.valueOf(player.getPlayerId()), player.getUserName());
            //String charachtertext = player.getCharacterName() + "player";
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