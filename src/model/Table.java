/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import vss.a3.VssA3;

/**
 *
 * @author Tim
 */
public class Table {
    
    private final List<Fork> forks = new ArrayList<>();
    private final List<Place> places = new ArrayList<>();
    private final Semaphore availablePlaces = new Semaphore(VssA3.MAX_PLACES, true);
    private int wholeEatingCounter;
    
    public Table() {
        this.wholeEatingCounter = 0;
        for(int index = 0; index < VssA3.MAX_PLACES; index ++) {
            Fork fork = new Fork(index);
            Place place = new Place(index);
            this.forks.add(fork);
            this.places.add(place);
        }
    }
    
    public void takeFork(int index) throws InterruptedException {
        Fork fork = this.forks.get(index);
        fork.take();
    }
    
    public void passBackFork(int index) {
        Fork fork = this.forks.get(index);
        fork.passBack();
    }
    
    public Place takePlace() throws Exception {
        availablePlaces.acquire();
        for(Place place : places) {
            if(place.isEmpty()) {
                place.take();
                return place;
            }
        }
        throw new Exception("No place found.");
    }
    
    public void leavePlace(int index) {
        Place place = places.get(index);
        place.leave();
        synchronized(this) {
            wholeEatingCounter++;
        }
        availablePlaces.release();
    }
    
}
