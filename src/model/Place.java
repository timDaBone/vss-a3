/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.concurrent.Semaphore;
import vss.a3.VssA3;

/**
 *
 * @author Tim
 */
public class Place {
    private boolean inUse = false;
    private final int index;
    
    public Place(int index) {
        this.index = index;
    }
    
    public boolean isEmpty() {
        return !inUse;
    }
    
    public void take() {
        inUse = true;
    }
    
    public void leave() {
        inUse = false;
    }

    int getIndex() {
        return index;
    }
}
