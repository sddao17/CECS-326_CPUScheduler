
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Carlos Verduzco, Steven Dao
 * @version 1.0
 *
 * Date: November 25, 2021
 * Purpose: This class represents a task to be scheduled by the scheduling algorithm,
 *          where each task is represented by:
 *
 *  - String name: a task name, not necessarily unique
 *  - int tid: unique task identifier
 *  - int priority: the relative priority of a task where a higher number indicates
 *                  higher relative priority.
 *  - int burst: the CPU burst of this task
 */
public class Task implements Runnable {

    // the representation of each task
    private final String name;
    private final int tid;
    private int priority;
    private int burst;

    // use an atomic integer to assign each task a unique task id
    private static final AtomicInteger tidAllocator = new AtomicInteger();

    public Task(String name, int priority, int burst) {
        this.name = name;
        this.priority = priority;
        this.burst = burst;

        this.tid = tidAllocator.getAndIncrement();
    }

    /**
     * Returns the name of the task.
     *
     * @return the task's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the unique task ID.
     *
     * @return the task's unique task ID
     */
    public int getTid() {
        return tid;
    }

    /**
     * Returns the priority level of the task.
     *
     * @return the task's priority level
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Returns the CPU burst of the task.
     *
     * @return the task's CPU burst
     */
    public int getBurst() {
        return burst;
    }

    /**
     * Sets the priority level of the task.
     *
     * @param newPriority the new priority level of the task
     */
    public void setPriority(int newPriority) {
        priority = newPriority;
    }

    /**
     * Sets the CPU burst of the task.
     *
     * @param newBurst the new CPU burst of the task
     */
    public void setBurst(int newBurst) {
        burst = newBurst;
    }

    /**
     * Override equals() so we can use a Task object in Java collection classes.
     *
     * @param other the task to be compared against
     */
    public boolean equals(Object other) {
        if (other == this)
            return true;

        if (!(other instanceof Task rhs))
            return false;

        // we're dealing with another Task; two tasks are equal if they have the same tid
        return this.tid == rhs.tid;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return the task as a string
     */
    public String toString() {
        return
                """
                - Name: %s
                - Tid: %s
                - Priority: %s
                - Burst: %s
                """.formatted(name, tid, priority, burst);
    }

    /**
     * Simulates a task running.
     */
    @Override
    public void run() {

        // catch InterruptedExceptions
        try {
            // simulate the task running by sleeping for the duration of its burst
            Thread.sleep(burst);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
