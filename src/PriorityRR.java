
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Carlos Verduzco, Steven Dao
 * @version 1.0
 *
 * Date: November 25, 2021
 * Purpose: This class represents priority scheduling algorithm using RR.
 */
public class PriorityRR implements Algorithm {

    // our unscheduled list of tasks
    private final List<Task> taskList;
    // our constant time quantum variable for round-robin tasks
    private static final int TIME_QUANTUM = 10;

    /**
     * Constructs a new instance of a Priority Round Robin scheduling algorithm.
     *
     * @param queue the queue of tasks to schedule
     */
    public PriorityRR(List<Task> queue) {

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

            // check if the given task's burst exceeds the given time quantum;
            // also check if the task list contains multiple tasks with the priority level
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

        // stop when the task list is empty
        if (taskList.size() == 0)
            return null;

        // else: search for the task with the highest priority
        // store the current lowest burst from the task list and its index
        int highestPriority = 0;
        int highestPriorityIndex = 0;
        // store the size to prevent excessive method calls within loops
        int queueSize = taskList.size();

        for (int i = 0; i < queueSize; ++i) {
            // store the task burst for readability
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
