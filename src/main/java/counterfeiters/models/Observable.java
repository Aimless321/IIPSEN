package counterfeiters.models;

import counterfeiters.views.Observer;

public interface Observable {
    public void registerObserver(Observer observer);
    public void notifyAllObservers();
}
