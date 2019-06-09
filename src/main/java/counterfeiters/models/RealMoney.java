package counterfeiters.models;

import counterfeiters.views.Observer;

import java.util.ArrayList;

/**
 * This model will save the realMoney.
 *
 * @author Ali Rezaa Ghariebiyan
 * @version 09-06-2019
 * */
public class RealMoney implements Observable{
    private ArrayList<Observer> observers = new ArrayList<>();
    private int totalMoney;

    public void setTotalMoney(int realMoney) {
        totalMoney = realMoney;
        notifyAllObservers();
    }

    public int getTotalMoney() {
        return totalMoney;
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
