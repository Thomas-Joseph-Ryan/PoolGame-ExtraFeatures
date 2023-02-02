package PoolGame.Observer;

/**
 * Interface for an object that will be observed
 */
public interface Subject {

    /**
     * Detaches an observer from the subject
     * @param observer Observer to be detached
     */
    void detach(Observer observer);

    /**
     * Attaches an observer to a subject
     * @param observer The observer to attach
     */
    void attach(Observer observer);

    /**
     * Alerts the observers attached to the subject
     */
    void alert();

}
