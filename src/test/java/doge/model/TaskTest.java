package doge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the public behavior of {@link Task}.
 *
 * <p>Each test follows the same simple pattern: arrange the objects,
 * perform an action, and assert the result.</p>
 */
class TaskTest {

    @Test
    /** Verifies that a new task starts incomplete. */
    void constructor_newTask_startsIncomplete() {
        Task task = new Task("Read about JUnit");

        assertFalse(task.isDone());
    }

    @Test
    /** Verifies the status icon for an incomplete task. */
    void getStatusIcon_taskNotDone_returnsBlankIcon() {
        Task task = new Task("Read about JUnit");

        assertEquals(" ", task.getStatusIcon());
    }

    @Test
    /** Verifies the status icon for a completed task. */
    void getStatusIcon_taskDone_returnsXIcon() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        assertEquals("X", task.getStatusIcon());
    }

    @Test
    /** Verifies the display of an incomplete task. */
    void toString_taskNotDone_includesBlankStatusAndDescription() {
        Task task = new Task("Read about JUnit");

        assertEquals("[ ] Read about JUnit", task.toString());
    }

    @Test
    /** Verifies the display of a completed task. */
    void toString_taskDone_includesXStatusAndDescription() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        assertEquals("[X] Read about JUnit", task.toString());
    }

    @Test
    /** Verifies that marking an incomplete task completes it. */
    void markDone_incompleteTask_marksTaskComplete() {
        Task task = new Task("Read about JUnit");

        task.markDone();

        assertTrue(task.isDone());
    }

    @Test
    /** Verifies that marking an already completed task has no adverse effect. */
    void markDone_alreadyCompleteTask_remainsComplete() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        task.markDone();

        assertTrue(task.isDone());
    }

    @Test
    /** Verifies that an explicitly completed task can be made incomplete. */
    void unmarkDone_completeTask_marksTaskIncomplete() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        task.unmarkDone();

        assertFalse(task.isDone());
    }

    @Test
    /** Verifies that unmarking an incomplete task leaves it incomplete. */
    void unmarkDone_incompleteTask_remainsIncomplete() {
        Task task = new Task("Read about JUnit");

        task.unmarkDone();

        assertFalse(task.isDone());
    }

    @Test
    /** Verifies that a task returns its original description. */
    void getDescription_taskCreatedWithDescription_returnsDescription() {
        Task task = new Task("Read about JUnit");

        assertEquals("Read about JUnit", task.getDescription());
    }

    @Test
    /** Verifies the completion status of a new task. */
    void isDone_newTask_returnsFalse() {
        Task task = new Task("Read about JUnit");

        assertFalse(task.isDone());
    }

    @Test
    /** Verifies the completion status after marking a task done. */
    void isDone_taskMarkedDone_returnsTrue() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        assertTrue(task.isDone());
    }

    @Test
    /** Verifies the persisted representation of an incomplete task. */
    void toStorageString_incompleteTask_returnsTaskStorageFormat() {
        Task task = new Task("Read about JUnit");

        assertEquals("T | 0 | Read about JUnit", task.toStorageString());
    }

    @Test
    /** Verifies the persisted representation of a completed task. */
    void toStorageString_completeTask_returnsTaskStorageFormat() {
        Task task = new Task("Read about JUnit");
        task.markDone();

        assertEquals("T | 1 | Read about JUnit", task.toStorageString());
    }
}
