package doge.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the public behaviour of {@link TaskList}.
 */
class TaskListTest {

    @Test
    void constructor_newTaskList_hasNoTasks() {
        TaskList tasks = new TaskList();

        assertEquals(0, tasks.size());
    }

    @Test
    void add_taskProvided_increasesListSize() {
        TaskList tasks = new TaskList();

        tasks.add(new Todo("Read about JUnit"));

        assertEquals(1, tasks.size());
    }

    @Test
    void get_taskIndexProvided_returnsTaskAtOneBasedIndex() {
        TaskList tasks = new TaskList();
        Task firstTask = new Todo("First task");
        Task secondTask = new Todo("Second task");
        tasks.add(firstTask);
        tasks.add(secondTask);

        assertSame(firstTask, tasks.get(1));
        assertSame(secondTask, tasks.get(2));
    }

    @Test
    void delete_taskIndexProvided_removesAndReturnsTask() {
        TaskList tasks = new TaskList();
        Task firstTask = new Todo("First task");
        Task secondTask = new Todo("Second task");
        tasks.add(firstTask);
        tasks.add(secondTask);

        Task deletedTask = tasks.delete(1);

        assertSame(firstTask, deletedTask);
        assertEquals(1, tasks.size());
        assertSame(secondTask, tasks.get(1));
    }

    @Test
    void getTasks_taskListHasTasks_returnsTasksInInsertionOrder() {
        TaskList tasks = new TaskList();
        Task firstTask = new Todo("First task");
        Task secondTask = new Todo("Second task");
        tasks.add(firstTask);
        tasks.add(secondTask);

        assertEquals(2, tasks.getTasks().size());
        assertSame(firstTask, tasks.getTasks().get(0));
        assertSame(secondTask, tasks.getTasks().get(1));
    }

    @Test
    void markDone_taskIndexProvided_marksTaskAsDone() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Read about JUnit"));

        tasks.markDone(1);

        assertTrue(tasks.get(1).isDone());
    }

    @Test
    void unmarkDone_taskIndexProvided_marksTaskAsNotDone() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Read about JUnit"));
        tasks.markDone(1);

        tasks.unmarkDone(1);

        assertFalse(tasks.get(1).isDone());
    }

    @Test
    void toString_emptyTaskList_returnsHeaderOnly() {
        TaskList tasks = new TaskList();

        assertEquals("    Here are the tasks in your list:", tasks.toString());
    }

    @Test
    void toString_taskListHasTasks_returnsNumberedTaskList() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("First task"));
        tasks.add(new Todo("Second task"));

        assertEquals("    Here are the tasks in your list:\n"
                + "    1.[T][ ] First task\n"
                + "    2.[T][ ] Second task", tasks.toString());
    }
}
