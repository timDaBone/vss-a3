package model;

import java.io.IOException;
import vss.a3.VssA3;

/**
 *
 * @author Andi Buchmann
 * @author Tim Böhnel
 */
public class Philosoph extends Thread {

    private final Table table;
    private final long eatingTime;
    private final long thinkingTime;
    private final int philosophIndex;
    private int eatingCounter;
    private boolean willBePunished;

    /**
     * Constructor for a philosoph.
     * 
     * @param table
     * @param eatingTime
     * @param thinkingTime
     * @param philosophIndex 
     */
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

    /**
     * Start and end eating on a specific place.
     * 
     * @param placeIndex
     * @throws Exception 
     */
    public void eating(int placeIndex) throws Exception {

        System.out.println(this + " took place " + placeIndex);

        // Konvention: im Uhrzeigersinn -> größere Zahl ist links, kleinere Zahl ist rechts
        // Konvention: Plätze an geraden Indizes nehmen zuerst die linke Gabel, ungerade die rechte Gabel
        if (placeIndex % 2 == 0) {
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

        this.eatingCounter++;

        System.out.println(this + " passes forks back");
        table.passBackFork(placeIndex);
        table.passBackFork((placeIndex + 1) % VssA3.MAX_PLACES);

        System.out.println(this + " goes thinking");
        table.leavePlace(placeIndex);
    }

    /**
     * Go thinking.
     * 
     * @throws InterruptedException 
     */
    private void thinking() throws InterruptedException {
        Thread.sleep(this.thinkingTime);
    }

    /**
     * Go sleeping.
     * 
     * @throws InterruptedException
     * @throws IOException 
     */
    private void sleeping() throws InterruptedException, IOException {
        System.out.println(this + " goes sleeping");
        Thread.sleep(VssA3.SLEEPING_TIME);
    }

    /**
     * Return count of eating actions.
     * 
     */
    public int getEatingCounter() {
        return eatingCounter;
    }

    /**
     * Punish this philosoph.
     */
    public void punish() {
        System.out.println(this + " was punished!!!!!!!!!!! ");
        this.willBePunished = true;
    }

    @Override
    public String toString() {
        return "Philosoph-" + this.philosophIndex + " (" + eatingCounter + ")";
    }

    /**
     * Enqueue this philosoph to the place with the smalles queue.
     * 
     * @return the place
     * @throws Exception 
     */
    private Place enqueueToPlace() throws Exception {
        
        Place minPlace = table.getPlace(0);
                    
        for(Place place : table.getPlaces()) {
            if(place.tryEnqueue()) {
                return place;
            }
            if(place.getQueueLength() < minPlace.getQueueLength()) {
                minPlace = place;   
            }
        }
        
        minPlace.enqueue(this);
        System.out.println(this + " is enqueued");
        return minPlace;
    }

}
