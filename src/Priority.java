
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Carlos Verduzco, Steven Dao
 * @version 1.0
 *
 * Date: November 25, 2021
 * Purpose: This class represents the non-preemptive priority scheduling algorithm.
 */
public class Priority implements Algorithm {

    // our unscheduled list of tasks
    private final List<Task> taskList;

    /**
     * Constructs a new instance of a priority scheduling algorithm.
     *
     * @param queue the queue of tasks to schedule
     */
    public Priority(List<Task> queue) {

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
        int highestPriority = 0;
        int highestPriorityIndex = 0;
        // store the size to prevent excessive method calls within loops
        int queueSize = taskList.size();

        // iterate through the list of tasks
        for (int i = 0; i < queueSize; ++i) {
            // store the task priority for readability
            int currentTaskPriority = taskList.get(i).getPriority();

            // if the current task's priority is higher than our current highest, keep track of it and its index
            if (currentTaskPriority > highestPriority) {
                highestPriority = currentTaskPriority;
                highestPriorityIndex = i;
            }
        }

        // Priority logic: return the task with the highest priority
        return taskList.get(highestPriorityIndex);
    }
}
