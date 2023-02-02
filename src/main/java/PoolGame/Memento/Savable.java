package PoolGame.Memento;

/**
 * Interface for each object which can have a save state.
 */
public interface Savable {

    /**
     * Method to save the current state of the object
     */
    void saveState();

    /**
     * Method to restore the state of the object from its own memento
     */
    void restoreState();
}
