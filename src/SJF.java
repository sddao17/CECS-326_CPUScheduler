
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Carlos Verduzco, Steven Dao
 * @version 1.0
 *
 * Date: November 25, 2021
 * Purpose: This class represents the Shortest-Job-First scheduling algorithm.
 */
public class SJF implements Algorithm {

    // our unscheduled list of tasks
    private final List<Task> taskList;

    /**
     * Constructs a new instance of a SJF scheduling algorithm.
     *
     * @param queue the queue of tasks to schedule
     */
    public SJF(List<Task> queue) {

        taskList = queue;
        // match the ready queue's size to the size of the provided list of tasks
        CPU.readyQueue = new ArrayBlockingQueue<>(queue.size());
    }

    /**
     * Schedules the task into the ready queue according to the scheduling algorithm.
     */
    @Override
    public void schedule() {

        // store the size to prevent excessive method calls within loops
        int queueSize = taskList.size();

        // iterate through all elements within the queue
        for (int i = 0; i < queueSize; ++i) {
            // pick the next task to be run
            Task currentTask = pickNextTask();

            // add the task to be run in the ready queue
            CPU.addTaskToReadyQueue(currentTask);
            // let the user know the current task is running through the CPU
            CPU.run(currentTask, Integer.MAX_VALUE);
            // remove the task from the list
            taskList.remove(currentTask);
        }
    }

    /**
     * Picks the next task according to the scheduling algorithm.
     *
     * @return the next task to schedule
     */
    @Override
    public Task pickNextTask() {

        // store the current lowest burst from the task list and its index
        int lowestBurst = Integer.MAX_VALUE;
        int lowestBurstIndex = 0;
        // store the size to prevent excessive method calls within loops
        int queueSize = taskList.size();

        // iterate through the list of tasks
        for (int i = 0; i < queueSize; ++i) {
            // store the task burst for readability
            int currentTaskBurst = taskList.get(i).getBurst();

            // if the current task's burst is lower than our current lowest, keep track of it and its index
            if (currentTaskBurst < lowestBurst) {
                lowestBurst = currentTaskBurst;
                lowestBurstIndex = i;
            }
        }

        // SJF logic: return the task with the lowest burst
        return taskList.get(lowestBurstIndex);
    }
}
