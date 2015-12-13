package vss.a3;

import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.Philosoph;
import model.Supervisor;
import model.Table;

/**
 *
 * @author Andi Buchmann
 * @author Tim Böhnel
 */
public class VssA3 {

    public final static int MAX_PLACES = 5;
    public final static int PHILOSOPH_COUNT = 50;
    public static long SLEEPING_TIME = 100;
    public static long SLEEPING_TIME_SUPERVISOR = 500;
    public static long EATING_TIME = 10;
    public static long EATING_TIME_HUNGRY = 50;
    public static long THINKING_TIME = 50;
    public static long THINKING_TIME_HUNGRY = 10;
    public static long PENALTY_TIME = 500;
    public static int MAXIMUM_EATING_DIFFERENCE_AVERAGE = 3;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        VssA3 vssA3 = new VssA3();
        Table table = new Table();
        List<Philosoph> philosophs = createPhilosophs(table);
        Supervisor supervisor = new Supervisor(philosophs);
        supervisor.start();
        Thread.sleep(60000);
        for(Philosoph philosoph: philosophs) {
            philosoph.stop();
        }
        for(Philosoph philosoph: philosophs) {
            System.out.println("EndCounter: " + philosoph);
        }
        supervisor.stop();
    }

    private static List<Philosoph> createPhilosophs(Table table) {

        System.out.println("start");

        List<Philosoph> philosophs = new ArrayList<>();

        for (int index = 0; index < PHILOSOPH_COUNT; index++) {

            Philosoph philosoph;

            if (index % 2 == 0) {
                philosoph = new Philosoph(table, EATING_TIME_HUNGRY, THINKING_TIME_HUNGRY, index);
            } else {
                philosoph = new Philosoph(table, EATING_TIME, THINKING_TIME, index);
            }

            philosophs.add(philosoph);
            philosoph.start();
        }

        return philosophs;
    }
}
