
/**
 * @author Carlos Verduzco, Steven Dao
 * @version 1.0
 *
 * Date: November 25, 2021
 * Purpose: This class represents the interface of the typical methods used in scheduling algorithms.
 */
public interface Algorithm
{
    /**
     * Invokes the scheduler.
     */
    void schedule();

    /**
     * Selects the next task using the appropriate scheduling algorithm.
     */
    Task pickNextTask();
}
