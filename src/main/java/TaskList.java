import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> tasks;

    TaskList() {
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

    public void markDone(int index) {
        tasks.get(index - 1).markDone();
    }

    public void unmarkDone(int index) {
        tasks.get(index - 1).unmarkDone();
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
