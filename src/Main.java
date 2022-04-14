
import java.util.*;
import java.io.*;

/**
 * @author Carlos Verduzco, Steven Dao
 * @version 1.0
 *
 * Date: November 25, 2021
 * Purpose: Demonstrates different scheduling algorithms.
 *
 * Usage:
 *  java Main <algorithm> <schedule>
 *
 * where:
 *  - schedule is the schedule of tasks
 *  - algorithm = [FCFS, SJF, PRI, RR, PRI-RR]
 *
 * Credits to Greg Gagne for providing the skeleton code for this project.
 */
public class Main {

    /**
     * Executes the application.
     *
     * @param args the command-line arguments to the application
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Driver <algorithm> <schedule>");
            System.exit(0);
        }

        // store the schedule within a string
        String schedule;
        // create the queue of tasks
        List<Task> queue = new ArrayList<>();

        // read and store the program arguments
        try (BufferedReader inFile = new BufferedReader(new FileReader(args[1]))) {

            // separate the parameters and populate the task list queue
            while ((schedule = inFile.readLine()) != null) {
                String[] params = schedule.split(",\\s*");
                queue.add(new Task(params[0], Integer.parseInt(params[1]), Integer.parseInt(params[2])));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // create the scheduler
        Algorithm scheduler = null;

        String choice = args[0].toUpperCase();

        switch (choice) {
            case "FCFS" ->  {
                System.out.println("""
                ============================================
                     First-Come-First-Served Scheduling
                ============================================
                """);
                scheduler = new FCFS(queue);
            }
            case "SJF" -> {
                System.out.println("""
                =======================================
                     Shortest-Job-First Scheduling
                =======================================
                """);
                scheduler = new SJF(queue);
            }
            case "PRI" -> {
                System.out.println("""
                ==============================
                     Priority Scheduling
                ==============================
                """);
                scheduler = new Priority(queue);
            }
            case "RR" -> {
                System.out.println("""
                ================================
                     Round-Robin Scheduling
                ================================
                """);
                scheduler = new RR(queue);
            }
            case "PRI-RR" -> {
                System.out.println("""
                ==============================================
                     Priority with Round-Robin Scheduling
                ==============================================
                """);
                scheduler = new PriorityRR(queue);
            }
            default -> {
                System.err.println("Invalid algorithm; please restart with the correct argument input.");
                System.exit(0);
            }
        }

        // start the scheduler
        scheduler.schedule();
    }
}
