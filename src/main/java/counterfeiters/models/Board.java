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
        blackmarket.prepareView();
        notifyAllObservers();
    }

    public void placeHenchman(double posX, double posY, String player) {
        henchmen.add(new Henchman(posX, posY, player));

        notifyAllObservers();
        updateFirebase();
    }

    public void updateFirebase() {
        FirebaseService fb = FirebaseService.getInstance();
        fb.setClass("games", game.getGameId(), this);
    }

    public void updateData(Board updateBoard) {
        this.henchmen = updateBoard.getHenchmen();
        System.out.println("Board data updated");
        notifyAllObservers();
    }

//    public void checkActionField() {
//
//    }
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
    public void advancePolice() {
        policePawn.advance();
        notifyAllObservers();
    }

    public ArrayList<Henchman> getHenchmen() {
        return henchmen;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}