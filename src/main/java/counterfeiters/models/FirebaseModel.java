package counterfeiters.models;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.Observer;

import java.util.ArrayList;

/**
 * Firebasemodel retrieves and updates the lobbies and games from firebase
 * @author Melissa Basgol
 */

public class FirebaseModel implements Observable {

    FirebaseService fb = FirebaseService.getInstance();
    private ArrayList<DocumentSnapshot> lobbies = new ArrayList<DocumentSnapshot>();
    private ArrayList<Observer> observers = new ArrayList<>();
    private ArrayList<Game> gameslist = new ArrayList<>();

    private String lobbyOrGame;

    public FirebaseModel() {

    }

    public FirebaseModel(ArrayList<DocumentSnapshot> lobbies,ArrayList<Observer> observers) {
            this.lobbies = lobbies;

    }

    public DocumentSnapshot LoadGames(){
        return null;
    }


    public ArrayList<QueryDocumentSnapshot> loadLobbies(ArrayList<Observer> observers) {
        FirebaseService fb = FirebaseService.getInstance();

        return null;
    }

    public void updateLobbies() {
        FirebaseService fb = FirebaseService.getInstance();

        lobbyOrGame = "lobby";
        this.gameslist.clear();
        this.lobbies.clear();

        System.out.println("after lobbies clear lobbies size:");

        // retrieve all documents in lobbies
        this.lobbies.addAll(fb.getAllDocumentsFromCollection("lobbies"));
        System.out.println("after get all  lobbies size:");
        System.out.println(lobbies.size());
        for (DocumentSnapshot doc: lobbies) {
            Game game =doc.toObject(Game.class);
            this.gameslist.add(game);
        }
        notifyAllObservers();
    }

    public void updateGames() {
        FirebaseService fb = FirebaseService.getInstance();
        lobbyOrGame = "game";
        this.gameslist.clear();
        this.lobbies.clear();
        System.out.println("after lobbies clear lobbies size:");
        System.out.println(lobbies.size());
        // retrieve all documents in lobbies
        this.lobbies.addAll(fb.getAllDocumentsFromCollection("games"));
        System.out.println("after get all  lobbies size:");
        System.out.println(lobbies.size());
        for (DocumentSnapshot doc: lobbies) {
            Game game = doc.toObject(Board.class).game;
            this.gameslist.add(game);
        }
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

    public ArrayList<DocumentSnapshot> getLobbies(){
        return lobbies;
    }

    public ArrayList<Game> getGames(){
        return gameslist;
    }


    public String lobbyOrGame() {
        return lobbyOrGame;
    }


}
