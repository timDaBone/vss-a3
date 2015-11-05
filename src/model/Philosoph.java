/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
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
    private boolean willBePunished;

    public Philosoph(Table table, long eatingTime, long thinkingTime, int philosophIndex) {
        this.table = table;
        this.eatingTime = eatingTime;
        this.thinkingTime = thinkingTime;
        this.philosophIndex = philosophIndex;
        this.eatingCounter = 0;
        this.willBePunished = false;
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                for (int eatCounter = 0; eatCounter < 3; eatCounter++) {
                    thinking();

                    if (willBePunished) {
                        System.out.println(this + " starts punishment");
                        Thread.sleep(VssA3.PENALTY_TIME);
                        willBePunished = false;
                    }
                    Place place = enqueueToPlace();
                    
                    eating(place.getIndex());
                }
                sleeping();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eating(int placeIndex) throws Exception {

        //VssA3.writeInGuiFile(this.philosophIndex, "place", placeIndex, "thinking");
        System.out.println(this + " took place " + placeIndex);

        // Konvention: im Uhrzeigersinn -> größere Zahl ist links, kleinere Zahl ist rechts
        // Konvention: Plätze an geraden Indizes nehmen zuerst die linke Gabel, ungerade die rechte Gabel
        if (placeIndex % 2 == 0) {
            table.takeFork((placeIndex + 1) % VssA3.MAX_PLACES);
            //VssA3.writeInGuiFile(this.philosophIndex, "fork", (placeIndex + 1) % VssA3.MAX_PLACES, "thinking");
            System.out.println(this + " has fork " + (placeIndex + 1) % VssA3.MAX_PLACES);
            table.takeFork(placeIndex);
            //VssA3.writeInGuiFile(this.philosophIndex, "fork", placeIndex, "thinking");
            System.out.println(this + " has fork " + placeIndex);
        } else {
            table.takeFork(placeIndex);
            //VssA3.writeInGuiFile(this.philosophIndex, "fork", placeIndex, "thinking");
            System.out.println(this + " has fork " + placeIndex);
            table.takeFork((placeIndex + 1) % VssA3.MAX_PLACES);
            //VssA3.writeInGuiFile(this.philosophIndex, "fork", (placeIndex + 1) % VssA3.MAX_PLACES, "thinking");
            System.out.println(this + " has fork " + (placeIndex + 1) % VssA3.MAX_PLACES);
        }
        
        //VssA3.writeInGuiFile(this.philosophIndex, null, -1, "eating");
        System.out.println(this + " goes eating");

        // schbaggeddi  schbaggeddi  schbaggeddi mmmmmmhhhhhh  schbaggeddi mmmmmmhhhhhh schbaggeddi  
        // schbaggeddi mmmmmmhhhhhh schbaggeddi  schbaggeddi  schbaggeddi  schbaggeddi mmmmmmhhhhhh
        Thread.sleep(this.eatingTime);

        this.eatingCounter++;

        System.out.println(this + " passes forks back");
        table.passBackFork(placeIndex);
        table.passBackFork((placeIndex + 1) % VssA3.MAX_PLACES);

        //VssA3.writeInGuiFile(this.philosophIndex, null, -1, "thinking");
        System.out.println(this + " goes thinking");
        table.leavePlace(placeIndex);
    }

    private void thinking() throws InterruptedException {
        Thread.sleep(this.thinkingTime);
    }

    private void sleeping() throws InterruptedException, IOException {
        //VssA3.writeInGuiFile(this.philosophIndex, null, -1, "sleeping");
        System.out.println(this + " goes sleeping");
        Thread.sleep(VssA3.SLEEPING_TIME);
    }

    public int getEatingCounter() {
        return eatingCounter;
    }

    public void penalty() {
        //VssA3.writeInGuiFile(this.philosophIndex, null, -1, "punishing");
        System.out.println(this + " was punished!!!!!!!!!!! ");
        this.willBePunished = true;
    }

    @Override
    public String toString() {
        return "Philosoph-" + this.philosophIndex + " (" + eatingCounter + ")";
    }

    private Place enqueueToPlace() throws Exception {
        
        Place minPlace = table.getPlace(0);
        
        for(Place place : table.getPlaces()) {
            if(place.getQueueSize() < minPlace.getQueueSize()) {
                minPlace = place;
            }
        }
        
        minPlace.enqueue(this);
        System.out.println(this + " is enqueued");
        return minPlace;
    }

}
