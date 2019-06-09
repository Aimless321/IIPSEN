package counterfeiters.models;

import counterfeiters.views.Observer;

import java.util.ArrayList;

public class BahamasBank implements Observable{
    private ArrayList<Observer> observers = new ArrayList<>();
    private int totalBankMoney;

    public int getTotalBankMoney() {
        return totalBankMoney;
    }

    public void setTotalBankMoney(int totalBankMoney) {
        this.totalBankMoney = totalBankMoney;
        notifyAllObservers();
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
