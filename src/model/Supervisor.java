package model;

import java.util.List;
import vss.a3.VssA3;

/**
 *
 * @author Andi Buchmann
 * @author Tim Böhnel
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
                    philosoph.punish();
                }
            }
            
            try {
                Thread.sleep(VssA3.SLEEPING_TIME_SUPERVISOR);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
