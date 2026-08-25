package doge.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the public behaviour of {@link Task}.
 *
 * <p>Each test follows the same simple pattern: arrange the objects,
 * perform an action, and assert the result.</p>
 */
class TaskTest {

    @Test
    void constructor_newTask_startsIncomplete() {
        Task task = new Task("Read about JUnit");

        assertFalse(task.isDone());
    }

    @Test
    void getStatusIcon_taskNotDone_returnsBlankIcon() {
        Task task = new Task("Read about JUnit");

        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    void getStatusIcon_taskDone_returnsXIcon() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        assertEquals("X", task.getStatusIcon());
    }

    @Test
    void toString_taskNotDone_includesBlankStatusAndDescription() {
        Task task = new Task("Read about JUnit");

        assertEquals("[ ] Read about JUnit", task.toString());
    }

    @Test
    void toString_taskDone_includesXStatusAndDescription() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        assertEquals("[X] Read about JUnit", task.toString());
    }

    @Test
    void markDone_incompleteTask_marksTaskComplete() {
        Task task = new Task("Read about JUnit");

        task.markDone();

        assertTrue(task.isDone());
    }

    @Test
    void markDone_alreadyCompleteTask_remainsComplete() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        task.markDone();

        assertTrue(task.isDone());
    }

    @Test
    void unmarkDone_completeTask_marksTaskIncomplete() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        task.unmarkDone();

        assertFalse(task.isDone());
    }

    @Test
    void unmarkDone_incompleteTask_remainsIncomplete() {
        Task task = new Task("Read about JUnit");

        task.unmarkDone();

        assertFalse(task.isDone());
    }

    @Test
    void getDescription_taskCreatedWithDescription_returnsDescription() {
        Task task = new Task("Read about JUnit");

        assertEquals("Read about JUnit", task.getDescription());
    }

    @Test
    void isDone_newTask_returnsFalse() {
        Task task = new Task("Read about JUnit");

        assertFalse(task.isDone());
    }

    @Test
    void isDone_taskMarkedDone_returnsTrue() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        assertTrue(task.isDone());
    }

    @Test
    void toStorageString_incompleteTask_returnsTaskStorageFormat() {
        Task task = new Task("Read about JUnit");

        assertEquals("T | 0 | Read about JUnit", task.toStorageString());
    }

    @Test
    void toStorageString_completeTask_returnsTaskStorageFormat() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        assertEquals("T | 1 | Read about JUnit", task.toStorageString());
    }
}
