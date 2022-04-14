
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * @author Carlos Verduzco, Steven Dao
 * @version 1.0
 *
 * Date: November 25, 2021
 * Purpose: This class simulates a virtual CPU which also maintains system time.
 */
public class CPU {

    // use a blocking queue in order to allow synchronization upon addition / removal of tasks to the queue
    protected static BlockingQueue<Task> readyQueue;
    // schedulers will give task threads to the dispatcher, which will give control of the CPU to the process
    private static final Semaphore dispatcher = new Semaphore(1, true);

    /**
     * Attempts to run the current task.
     * After the task is finished, it is removed from the ready queue.
     *
     * @param currentTask the current task to be run
     * @param slice the time slice of the process
     */
    public static void run(Task currentTask, int slice) {

        // ensure that the variable is pointing to a non-null object
        assert currentTask != null;
        System.out.println("Currently running:\n" + currentTask);

        // the task should only be running once at any given time
        while (true) {
            // keep track of whether the task's current burst is less than the given slice
            boolean taskIsDone = false;
            // only run the task if the CPU dispatcher permit is available
            if (readyQueue.peek() == currentTask && dispatcher.tryAcquire()) {
                // limit the thread runtime to a minimum of the slice
                if (currentTask.getBurst() > slice)
                    currentTask.setBurst(slice);
                else
                    taskIsDone = true;

                // simulate the task running
                Thread currentThread = new Thread(currentTask, currentTask.getName());
                currentThread.start();

                // if the task completes its last cpu burst, let the user know the current task is done
                if (taskIsDone) {
                    System.out.printf("""
                         =====================
                         Task %s has finished.
                         =====================
                                                            
                         """, currentTask.getName());
                }

                // done simulating the task
                // remove the head of the queue to traverse through all tasks
                readyQueue.remove();
                // release the permit and break out of the loop
                dispatcher.release();
                break;
            }
        }
    }

    /**
     * Adds the task to the end of the ready queue.
     *
     * @param newTask the task to be added to the queue
     */
    public static void addTaskToReadyQueue(Task newTask) {

        // catch InterruptedExceptions
        try {
            readyQueue.put(newTask);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
