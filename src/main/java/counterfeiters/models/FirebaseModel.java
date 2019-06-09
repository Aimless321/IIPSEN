package counterfeiters.models;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.DocumentReference;

import counterfeiters.controllers.ApplicationController;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.LobbyListView;
import counterfeiters.views.Observer;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FirebaseModel implements Observable {

    FirebaseService fb = FirebaseService.getInstance();
    private ArrayList<DocumentSnapshot> lobbies = new ArrayList<DocumentSnapshot>();
    private ArrayList<Observer> observers = new ArrayList<>();
    private ArrayList<Game> games = new ArrayList<>();

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
        System.out.println("geldim updatelobbiese");

        lobbyOrGame = "lobby";
        //this.lobbies.clear();
        this.games.clear();
        System.out.println("after lobbies clear lobbies size:");
        //System.out.println(lobbies.size());
        // retrieve all documents in lobbies
        this.lobbies.addAll(fb.getAllDocumentsFromCollection("lobbies"));
        System.out.println("after get all  lobbies size:");
        System.out.println(lobbies.size());
        for (DocumentSnapshot doc: lobbies) {
            System.out.println("ik ben hiet");
            Game game =doc.toObject(Game.class);
            this.games.add(game);
        }
        notifyAllObservers();
    }

    public void updateGames() {
        FirebaseService fb = FirebaseService.getInstance();
        lobbyOrGame = "game";
        this.lobbies.clear();
        System.out.println("after lobbies clear lobbies size:");
        System.out.println(lobbies.size());
        // retrieve all documents in lobbies
        this.lobbies.addAll(fb.getAllDocumentsFromCollection("games"));
        System.out.println("after get all  lobbies size:");
        System.out.println(lobbies.size());
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
        return games;
    }


    public String lobbyOrGame() {
        return lobbyOrGame;
    }


}
