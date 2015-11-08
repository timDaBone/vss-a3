package model;

import java.util.ArrayList;
import java.util.List;
import vss.a3.VssA3;

/**
 *
 * @author Andi Buchmann
 * @author Tim Böhnel
 */
public class Table {
    
    private final List<Fork> forks = new ArrayList<>();
    private final List<Place> places = new ArrayList<>();
    private int wholeEatingCounter;
    
    /**
     * Constructor for a Table.
     * 
     */
    public Table() {
        this.wholeEatingCounter = 0;
        for(int index = 0; index < VssA3.MAX_PLACES; index ++) {
            Fork fork = new Fork(index);
            Place place = new Place(index);
            this.forks.add(fork);
            this.places.add(place);
        }
    }
   
    /**
     * Takes a the fork on the given index.
     * 
     * @param index
     * @throws InterruptedException 
     */
    public void takeFork(int index) throws InterruptedException {
        Fork fork = this.forks.get(index);
        fork.take();
    }
    
    /**
     * Passes the fork on the given index back.
     * 
     * @param index 
     */
    public void passBackFork(int index) {
        Fork fork = this.forks.get(index);
        fork.passBack();
    }
    
    /**
     * Leaves the place on the given index.
     * 
     * @param index
     * @throws Exception 
     */
    public void leavePlace(int index) throws Exception {
        Place place = places.get(index);
        place.leave();
        synchronized(this) {
            wholeEatingCounter++;
        }
    }
    
    /**
     * Returns all places.
     * 
     * @return 
     */
    public List<Place> getPlaces() {
        return places;
    }
    
    

    Place getPlace(int i) {
       return this.places.get(i);
    }
    
}
