package PoolGame.Prototype;

/**
 * Interface to enforce objects can be cloned. Used in this application to allow pocket behaviours to be cloned and stored in a memento
 */
public interface Cloneable {

    /**
     * Method cloning a Cloneable Object
     * @return The Cloned object
     */
    Cloneable clone();
}
