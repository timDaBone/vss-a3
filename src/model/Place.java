package model;

import java.util.concurrent.Semaphore;

/**
 * A place where a philosoph can eat.
 *
 * @author Andi Buchmann
 * @author Tim Böhnel
 */
public class Place {

    private boolean inUse = false;
    private final int index;
    private final Semaphore takePlace = new Semaphore(1, true);

    /**
     * Constructor for a place.
     *
     * @param index
     */
    public Place(int index) {
        this.index = index;
    }

    /**
     * Return true if empty.
     *
     * @return empty
     */
    public boolean isEmpty() {
        return !inUse;
    }

    /**
     * Enqueues a philosoph into the queue for a place. The philosoph waits
     * until the place is free.
     *
     * @param philosoph
     * @throws Exception
     */
    public void enqueue(Philosoph philosoph) throws Exception {
        System.out.println(takePlace.getQueueLength());

        takePlace.acquire();
        if (isEmpty()) {
            inUse = true;
        } else {
            throw new Exception("place is not empty");
        }
    }

    /**
     * Leave the place. (Should only be called by the Philosoph which sits on
     * the place.
     *
     * @throws Exception
     */
    public void leave() throws Exception {
        inUse = false;
        takePlace.release();
    }

    /**
     * Get index of the place.
     *
     * @return index
     */
    int getIndex() {
        return index;
    }

    /**
     * Get the length of the queue.
     *
     * @return length
     */
    int getQueueLength() {
        return this.takePlace.getQueueLength();
    }

}
