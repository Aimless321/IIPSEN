package counterfeiters.models;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.DocumentReference;

import counterfeiters.controllers.ApplicationController;
import counterfeiters.firebase.FirebaseService;
import counterfeiters.views.LobbyListView;
import counterfeiters.views.Observer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FirebaseModel implements Observable {

    FirebaseService fb = FirebaseService.getInstance();
    private ArrayList<QueryDocumentSnapshot> lobbies = new ArrayList<QueryDocumentSnapshot>();
    private ArrayList<Observer> observers = new ArrayList<>();

    public FirebaseModel() {

    }


    public DocumentSnapshot LoadGames(){
        return null;
    }


    public ArrayList<QueryDocumentSnapshot> loadLobbies(ArrayList<Observer> observers) {
        FirebaseService fb = FirebaseService.getInstance();

        return null;
    }

    public ArrayList<QueryDocumentSnapshot> updateLobbies(ArrayList<Observer> observers) {
    //return List<QueryDocumentSnapshot>
        FirebaseService fb = FirebaseService.getInstance();
        lobbies.clear();

        // retrieve all documents in lobbies
        lobbies.addAll(fb.getAllDocumentsFromCollection("lobbies"));
        return lobbies;
    }
/*
    public List<QueryDocumentSnapshot> getAllDocuments() throws Exception {
        // [START fs_get_all_docs]
        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = fb.collection("cities").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println(document.getId() + " => " + document.toObject(City.class));
        }
        // [END fs_get_all_docs]
        return documents;
    }
    */

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }



}
