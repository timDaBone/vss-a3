/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vss.a3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Philosoph;
import model.Supervisor;
import model.Table;

/**
 *
 * @author Tim
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

    public static File file;
    public static BufferedWriter bufferedWriter;

    public VssA3() {
        try {
            this.file = new File("philosophs_gui.json");
            this.bufferedWriter = new BufferedWriter(new FileWriter(file));
            String init = "[{\"places\":" + VssA3.MAX_PLACES + ", \"philosops\":" + VssA3.PHILOSOPH_COUNT + "},";
            bufferedWriter.write(init);
            bufferedWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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

    public static void writeInGuiFile(int philosopId, String take, int takeId, String state) throws IOException {
        String newStuff;
        if (take != null) {
            newStuff = "{\"id\":" + philosopId + ",\"takes\":{\"" + take + "\":" + takeId + "},\"status\":\"" + state + "\"},";
        } else {
            newStuff = "{\"id\":" + philosopId + ",\"status\":\"" + state + "\"},";
        }
        bufferedWriter.write(newStuff);
        bufferedWriter.flush();
    }
}
