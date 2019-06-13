package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;
import javafx.application.Platform;

import java.util.ArrayList;

public class Board implements Observable{
    private ArrayList<Observer> observers = new ArrayList<>();
    @Exclude
    public BlackMarket blackmarket = new BlackMarket();
    public PolicePawn policePawn = new PolicePawn(4);
    public FirstPlayerPawn firstPlayerPawn = new FirstPlayerPawn();
    private ArrayList<Henchman> henchmen = new ArrayList<>();
    public Game game;

    public Board() {

    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
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
    }

    public void placeHenchman(double posX, double posY, String player) {
        henchmen.add(new Henchman(posX, posY, player));

        notifyAllObservers();
        updateFirebase();
    }


    /**
     * This method checks whether the user has enought money, If this is the case, the money wil de deducted from his account.
     *
     * @author Ali Rezaa Ghariebiyan
     * @version 11-06-2019
     * */
    public boolean checkMoney(int id, int bedrag){
        if (game.localPlayer.realMoney.getTotalMoney() >= bedrag){
            game.localPlayer.updateMoneyReduce(id, bedrag);
            return true;
        }
            return false;
    }

    public void updateFirebase() {
        FirebaseService fb = FirebaseService.getInstance();
        fb.setClass("games", game.getGameId(), this);
    }

    public void updateData(Board updateBoard) {
        this.henchmen = updateBoard.getHenchmen();
        notifyAllObservers();
    }



    public void makePurchase(int cardNumber) {
        Card card = blackmarket.cardRow.get(cardNumber);
        System.out.println("gotten Card: " + card);
        game.localPlayer.addCard(card);
        System.out.println("added card to player");
        blackmarket.makeCardPurchased(cardNumber);
        System.out.println("card removed");
    }
//
//    public void actionFieldAction() {
//
//    }
//
//    public void actionFieldMethod() {
//
//    }
//
//    public String checkFieldType() {
//
//    }
//

//    public void soundWrong(){
//        String musicFile = "/sounds/wrong.mp3";
//
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
//    }

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

    public void setGame(Game game) {
        this.game = game;
    }
}