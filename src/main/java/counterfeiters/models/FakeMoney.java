package counterfeiters.models;

import counterfeiters.views.Observer;

import java.util.ArrayList;

/**
 * This model will save the fakeMoney based on the quality
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 09-06-2019
 * */
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

    public int getQuality(String quality){
        switch (quality){
            case "qualityOne":
                return getQualityOne();
            case "qualityTwo":
                return getQualityTwo();
            case "qualityThree":
                return getQualityThree();
            default:
                break;

        }
        return 0;
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
