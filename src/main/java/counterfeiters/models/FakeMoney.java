package counterfeiters.models;

import counterfeiters.views.Observer;

import java.util.ArrayList;

public class FakeMoney implements Observable{
    private ArrayList<Observer> observers = new ArrayList<>();
    private int qualityOne, qualityTwo, qualityThree;

    public void setQualityOne(int qualityOne) {
        this.qualityOne = qualityOne;
        notifyAllObservers();
    }

    public void setQualityTwo(int qualityTwo) {
        this.qualityTwo = qualityTwo;
        notifyAllObservers();
    }

    public void setQualityThree(int qualityThree) {
        this.qualityThree = qualityThree;
        notifyAllObservers();
    }

    public int getQualityOne() {
        return qualityOne;
    }

    public int getQualityTwo() {
        return qualityTwo;
    }

    public int getQualityThree() {
        return qualityThree;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyAllObservers() {
        for(Observer observer : observers) {
            observer.update(this);
        }
    }
}
