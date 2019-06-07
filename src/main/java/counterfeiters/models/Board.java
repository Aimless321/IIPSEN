package counterfeiters.models;

import counterfeiters.views.Observer;

import java.util.ArrayList;

public class Board implements Observable{
    private ArrayList<Observer> observers = new ArrayList<>();
    public BlackMarket blackmarket = new BlackMarket();
    public PolicePawn policePawn = new PolicePawn(4);


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for(Observer obs : observers) {
            obs.update(this);
        }
    }

    public void prepareBlackMarket() {
        blackmarket.prepareView();
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
}