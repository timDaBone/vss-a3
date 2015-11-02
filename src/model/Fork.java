/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Tim
 */
public class Fork {
    
    private final Semaphore available = new Semaphore(1, true);
    private final int index;
    
    public Fork(int index) {
        this.index = index;
    }
    
    public void take() throws InterruptedException {
        available.acquire();
    }
    
    public void passBack() {
        available.release();
    }
    
}
