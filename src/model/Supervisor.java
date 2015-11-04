/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import vss.a3.VssA3;

/**
 *
 * @author Tim
 */
public class Supervisor extends Thread { 

    private final List<Philosoph> philosophs;

    public Supervisor(List<Philosoph> philosophs) {
        this.philosophs = philosophs;
    }

    @Override
    public void run() {
        super.run();
        while(true) {
            

            int averageEatingCounter = 0;
            for(Philosoph philosoph: philosophs) {
                averageEatingCounter += philosoph.getEatingCounter();
            }

            averageEatingCounter = averageEatingCounter/philosophs.size();

            for(Philosoph philosoph: philosophs) {
                if(philosoph.getEatingCounter()-VssA3.MAXIMUM_EATING_DIFFERENCE_AVERAGE >= averageEatingCounter) {
                    philosoph.penalty();
                }
            }
            /*Philosoph max = philosophs.get(0);
            Philosoph min = philosophs.get(0);
            for(Philosoph philosoph: philosophs) {
                if(philosoph.getEatingCounter() > max.getEatingCounter()) {
                    max = philosoph;
                }
                if(philosoph.getEatingCounter() < min.getEatingCounter()) {
                    min = philosoph;
                }
            }
            System.out.println(philosophs);
            if(max.getEatingCounter() > min.getEatingCounter() + 3) {
                max.penalty();
            }
*/
            try {
                Thread.sleep(VssA3.SLEEPING_TIME_SUPERVISOR);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
