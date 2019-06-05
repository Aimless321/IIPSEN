package counterfeiters.models;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.DocumentReference;

import com.google.firebase.internal.FirebaseService;
import counterfeiters.controllers.ApplicationController;
import counterfeiters.views.Observer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FirebaseModel implements Observable {

    private ArrayList<Map> lobbies = new ArrayList<>();
    private ArrayList<Observer> observers = new ArrayList<>();

    public DocumentSnapshot LoadGames(){
        return null;
    }
    public Map loadLobbies(ArrayList<Observer> observers) {
        FirebaseService fb = FirebaseService.getInstance();


    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
    public void updateData(FirebaseModel updateLobbyList) {
        this.lobbies = updateLobbyList.getLobbies();

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

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }

    public ArrayList<Map> getLobbies() {

    }
    public Map<String, Object> getDocumentAsMap() throws Exception {

    }


}
