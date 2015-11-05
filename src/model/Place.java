/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import vss.a3.VssA3;

/**
 *
 * @author Tim
 */
public class Place {
    private boolean inUse = false;
    private final int index;
    private int waitingTime;
    private final Semaphore takePlace = new Semaphore(1, true);
    private final Queue<Philosoph> placeQueue = new LinkedList();
    
    public Place(int index) {
        this.index = index;
    }
    
    public boolean isEmpty(){
        return !inUse;
    }
    
    public void enqueue(Philosoph philosoph) throws Exception {
        System.out.println(takePlace.getQueueLength());
        takePlace.acquire();
        if(isEmpty()) {
            inUse = true;
            
        } else {
            throw new Exception("place is not empty");
        }
    }
    
    public void leave() throws Exception {
        
        inUse = false;
        takePlace.release();
    }

    int getIndex() {
        return index;
    }
    
    int getQueueSize() {
        return this.takePlace.getQueueLength();
    }

    public int getWaitingTime() {
        return waitingTime;
    }

}
