package doge.ui;

import doge.model.Task;
import doge.model.TaskList;

import java.util.Scanner;
import java.util.List;

public class UI {
    private final String BANNER = "       ____   ___   ____ _____\n" +
            "      |  _ \\ / _ \\ / ___| ____|\n" +
            "      | | | | | | | |  _|  _|\n" +
            "      | |_| | |_| | |_| | |___\n" +
            "      |____/ \\___/ \\____|_____|";
    private final String SEPARATOR = "    ____________________________________________________________";

    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(SEPARATOR);
        System.out.println(BANNER);
        System.out.println("    Rawr! I'm Doge.");
        System.out.println("    What can I do for you?");
        System.out.println(SEPARATOR);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void printMessage(String message) {
        System.out.println(SEPARATOR);
        System.out.println(message);
        System.out.println(SEPARATOR);
    }

    public void showTaskMarked(Task task) {
        System.out.println(SEPARATOR);
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("      " + task);
        System.out.println(SEPARATOR);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(SEPARATOR);
        System.out.println("    Ok, I've marked this task as not done:");
        System.out.println("      " + task);
        System.out.println(SEPARATOR);
    }

    public void showGoodbye() {
        System.out.println(SEPARATOR);
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(SEPARATOR);
        System.out.println("    Woof! I have added: " + task);
        System.out.println("    Now you have " + taskCount + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    public void printTaskList(TaskList tasks) {
        System.out.println(SEPARATOR);
        System.out.println(tasks.toString());
        System.out.println(SEPARATOR);
    }

    /**
     * Displays the tasks matching a search keyword.
     *
     * @param matchingTasks tasks selected by the search
     */
    public void printMatchingTasks(List<Task> matchingTasks) {
        StringBuilder output = new StringBuilder("    Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            output.append("\n    ").append(i + 1).append(".").append(matchingTasks.get(i));
        }
        printMessage(output.toString());
    }
}
