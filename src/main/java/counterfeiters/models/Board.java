package counterfeiters.models;

import com.google.cloud.firestore.annotation.Exclude;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
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

    //Hier gaat iets fout
    public boolean checkMoney(int id, int bedrag){
        System.out.println("checkMoney");

        if (game.localPlayer.realMoney.getTotalMoney() >= bedrag){
            System.out.println("Old player money: " + game.localPlayer.realMoney.getTotalMoney());
            game.localPlayer.updateMoneyReduce(id, bedrag);
            System.out.println("New player money: " + game.localPlayer.realMoney.getTotalMoney());
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

    public boolean checkActionField(int id, int bedrag) {
        System.out.println("Board.checkActionField");

        if(checkMoney(id, bedrag)){
            return true;
        }
        else {
//            soundWrong();
            return false;
        }
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

    public ArrayList<Henchman> getHenchmen() {
        return henchmen;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}