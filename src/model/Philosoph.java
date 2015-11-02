/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import vss.a3.VssA3;

/**
 *
 * @author Tim
 */
public class Philosoph extends Thread {
    private final Table table;
    private final long eatingTime;
    private final long thinkingTime;
    private final int philosophIndex;
    private int eatingCounter;

    public Philosoph(Table table, long eatingTime, long thinkingTime, int philosophIndex) {
        this.table = table;
        this.eatingTime = eatingTime;
        this.thinkingTime = thinkingTime;
        this.philosophIndex = philosophIndex;
        this.eatingCounter = 0;
    }
    
    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                for(int eatCounter = 0; eatCounter < 3; eatCounter++) {
                    
                    thinking();
                    
                    eating();
                }
                
                sleeping();

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }

    private void eating() throws Exception {
        
        Place place = table.takePlace();
        
        int placeIndex = place.getIndex();
        
        System.out.println(this + "took place " + placeIndex);
        
        // Konvention: im Uhrzeigersinn -> größere Zahl ist links, kleinere Zahl ist rechts
        // Konvention: Plätze an geraden Indizes nehmen zuerst die linke Gabel, ungerade die rechte Gabel
        if(placeIndex % 2 == 0) {
            table.takeFork((placeIndex + 1) % VssA3.MAX_PLACES);
            System.out.println(this + " has fork " + (placeIndex + 1) % VssA3.MAX_PLACES);
            table.takeFork(placeIndex);
            System.out.println(this + " has fork " + placeIndex);
        } else {
            table.takeFork(placeIndex);
            System.out.println(this + " has fork " + placeIndex);
            table.takeFork((placeIndex + 1) % VssA3.MAX_PLACES);
            System.out.println(this + " has fork " + (placeIndex + 1) % VssA3.MAX_PLACES);
        }
        System.out.println(this + " goes eating");
        // schbaggeddi  schbaggeddi  schbaggeddi mmmmmmhhhhhh  schbaggeddi mmmmmmhhhhhh schbaggeddi  
        // schbaggeddi mmmmmmhhhhhh schbaggeddi  schbaggeddi  schbaggeddi  schbaggeddi mmmmmmhhhhhh
        
        Thread.sleep(this.eatingTime);
        
        this.eatingCounter ++;
        
        table.passBackFork(placeIndex);
        table.passBackFork((placeIndex + 1) % VssA3.MAX_PLACES);
        
        table.leavePlace(placeIndex);
    }

    private void thinking() throws InterruptedException {
        System.out.println(this + " goes thinking");
        Thread.sleep(this.thinkingTime);
    }

    private void sleeping() throws InterruptedException {
        System.out.println(this + " goes sleeping");
        Thread.sleep(VssA3.SLEEPING_TIME);
    }
    
    public int getEatingCounter() {
        return eatingCounter;
    }
    
    public void penalty() throws InterruptedException {
        Thread.sleep(VssA3.PENALTY_TIME);
    }

    @Override
    public String toString() {
        return "Philosoph" + this.philosophIndex;
    }
    
    
    
}
