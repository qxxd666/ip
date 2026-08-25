package doge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Stores tasks and provides one-based access for user commands. */
public class TaskList {
    private final List<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /** Adds a task to the end of this list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /** Returns the task at the given one-based index. */
    public Task get(int index) {
        return tasks.get(index - 1);
    }

    /** Deletes and returns the task at the given one-based index. */
    public Task delete(int index) {
        return tasks.remove(index - 1);
    }

    /** Returns the number of tasks in this list. */
    public int size() {
        return tasks.size();
    }

    /** Returns the tasks in insertion order. */
    public List<Task> getTasks() {
        return tasks;
    }

    /** Marks the task at the given one-based index as complete. */
    public void markDone(int index) {
        tasks.get(index - 1).markDone();
    }

    /** Marks the task at the given one-based index as incomplete. */
    public void unmarkDone(int index) {
        tasks.get(index - 1).unmarkDone();
    }

    /**
     * Returns tasks whose descriptions contain the given keyword.
     * Matching is case-insensitive and follows the order in which tasks were added.
     *
     * @param keyword text to search for
     * @return matching tasks, or an empty list if there are no matches
     */
    public List<Task> find(String keyword) {
        String searchTerm = keyword.toLowerCase(Locale.ROOT);
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase(Locale.ROOT).contains(searchTerm)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            result.append("\n").append("    ").append(i + 1).append(".").append(tasks.get(i));
        }
        return result.toString();
    }
}
