package doge.model;

import java.util.ArrayList;
import java.util.List;

/** Maintains the ordered collection of tasks used by the application. */
public class TaskList {
    private List<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Adds a task to the end of the list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Returns the task at a one-based index. */
    public Task get(int index) {
        return tasks.get(index - 1);
    }

    /** Removes and returns the task at a one-based index. */
    public Task delete(int index) {
        return tasks.remove(index - 1);
    }

    /** Returns the number of tasks in the list. */
    public int size() {
        return tasks.size();
    }

    /** Returns the underlying ordered task collection. */
    public List<Task> getTasks() {
        return tasks;
    }

    /** Marks the task at a one-based index as completed. */
    public void markDone(int index) {
        tasks.get(index - 1).markDone();
    }

    /** Marks the task at a one-based index as incomplete. */
    public void unmarkDone(int index) {
        tasks.get(index - 1).unmarkDone();
    }

    /** Returns a numbered, display-ready representation of all tasks. */
    @Override
    public String toString() {
        String res = "    Here are the tasks in your list:";
        for (int i = 0; i < tasks.size(); i++) {
            res += "\n" + "    " + (i + 1) + "." + tasks.get(i);
        }
        return res;
    }
}
