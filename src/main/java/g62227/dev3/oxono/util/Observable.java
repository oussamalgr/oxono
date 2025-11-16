package g62227.dev3.oxono.util;


/**
 * Interface representing an observable entity in the observer pattern.
 * Classes implementing this interface can manage a list of observers and notify them of changes.
 */
public interface Observable {
    /**
     * Adds an observer to be notified of changes in this observable.
     *
     * @param observer the observer to be added
     */
    void addObserver(Observer observer);
    /**
     * Removes an observer so it no longer receives updates from this observable.
     *
     * @param observer the observer to be removed
     */
    void removeObserver(Observer observer);
}
