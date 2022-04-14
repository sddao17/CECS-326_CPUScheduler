
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Carlos Verduzco, Steven Dao
 * @version 1.0
 *
 * Date: November 25, 2021
 * Purpose: This class represents the non-preemptive priority scheduling algorithm using Round-Robin.
 */
public class RR implements Algorithm {

    // our unscheduled list of tasks
    private final List<Task> taskList;
    // our constant time quantum variable for round-robin tasks
    private static final int TIME_QUANTUM = 10;

    /**
     * Constructs a new instance of a RR scheduling algorithm.
     *
     * @param queue the queue of tasks to schedule
     */
    public RR(List<Task> queue) {

        taskList = queue;
        // match the ready queue's size to the size of the provided list of tasks
        CPU.readyQueue = new ArrayBlockingQueue<>(queue.size());
    }

    /**
     * Schedules the task into the ready queue according to the scheduling algorithm.
     */
    @Override
    public void schedule() {

        // pick the next task to be run
        Task currentTask = pickNextTask();

        // loop for as long as there are still tasks in the list
        while (currentTask != null) {
            // store the potential new burst and flag for the time quantum check
            int newBurst = 0;
            boolean exceedsTimeQuantum = false;

            // check if the given task's burst exceeds the given time quantum
            if (currentTask.getBurst() > TIME_QUANTUM) {
                // store the adjusted new burst and mark the task for rotation to the end of the queue
                newBurst = currentTask.getBurst() - TIME_QUANTUM;
                exceedsTimeQuantum = true;
            }

            // add the task to be run in the ready queue
            CPU.addTaskToReadyQueue(currentTask);
            // run the task through the CPU and let the user know
            CPU.run(currentTask, TIME_QUANTUM);
            // remove the task from the list
            taskList.remove(currentTask);

            // check if the task exceeded the time quantum
            if (exceedsTimeQuantum) {
                // update the new burst and add it to the end of the task list
                currentTask.setBurst(newBurst);
                taskList.add(currentTask);
                // else, the current task was completed
            } else {
                currentTask.setBurst(0);
            }

            // re-pick the next task to be run
            currentTask = pickNextTask();
        }
    }

    /**
     * Picks the next task according to the scheduling algorithm.
     *
     * @return the next task to schedule
     */
    @Override
    public Task pickNextTask() {

        // use FIFO logic, except stop when the task list is empty
        if (taskList.size() == 0)
            return null;
        else
            return taskList.get(0);
    }
}
