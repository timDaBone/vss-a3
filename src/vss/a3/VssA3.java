/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vss.a3;

import java.util.ArrayList;
import java.util.List;
import model.Philosoph;
import model.Table;

/**
 *
 * @author Tim
 */
public class VssA3 {

    public final static int MAX_PLACES = 2;
    public final static int PHILOSOPH_COUNT = 3;
    public static long SLEEPING_TIME = 100;
    public static long EATING_TIME = 10;
    public static long EATING_TIME_HUNGRY = 20;
    public static long THINKING_TIME = 50;
    public static long THINKING_TIME_HUNGRY = 25;
    public static long PENALTY_TIME = 1000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Table table = new Table();
        List<Philosoph> philosophs = createPhilosophs(table);

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
