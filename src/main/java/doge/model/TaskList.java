package doge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index - 1);
    }

    public Task delete(int index) {
        return tasks.remove(index - 1);
    }

    public int size() {
        return tasks.size();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void markDone(int index) {
        tasks.get(index - 1).markDone();
    }

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
        String res = "    Here are the tasks in your list:";
        for (int i = 0; i < tasks.size(); i++) {
            res += "\n" + "    " + (i + 1) + "." + tasks.get(i);
        }
        return res;
    }
}
