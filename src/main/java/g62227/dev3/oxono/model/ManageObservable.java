package g62227.dev3.oxono.model;

import g62227.dev3.oxono.util.Observable;
import g62227.dev3.oxono.util.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The ManageObservable class provides a concrete implementation of the Observable interface.
 * It manages a list of observers and allows notifying them of changes.
 */
public class ManageObservable implements Observable {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);

        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers about a change.
     * This will trigger the update method of each observer.
     */
    void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
