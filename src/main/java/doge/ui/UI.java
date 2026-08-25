package doge.ui;

import doge.model.Task;
import doge.model.TaskList;

import java.util.Scanner;
import java.util.List;

/** Handles console input and output for the Doge application. */
public class UI {
    private static final String BANNER = "       ____   ___   ____ _____\n" +
            "      |  _ \\ / _ \\ / ___| ____|\n" +
            "      | | | | | | | |  _|  _|\n" +
            "      | |_| | |_| | |_| | |___\n" +
            "      |____/ \\___/ \\____|_____|";
    private static final String SEPARATOR = "    ____________________________________________________________";

    private final Scanner scanner = new Scanner(System.in);

    /** Displays the application welcome message. */
    public void showWelcome() {
        System.out.println(SEPARATOR);
        System.out.println(BANNER);
        System.out.println("    Rawr! I'm Doge.");
        System.out.println("    What can I do for you?");
        System.out.println(SEPARATOR);
    }

    /** Returns the next trimmed command entered by the user. */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /** Displays a message between separators. */
    public void printMessage(String message) {
        System.out.println(SEPARATOR);
        System.out.println(message);
        System.out.println(SEPARATOR);
    }

    /** Displays confirmation that a task was marked complete. */
    public void showTaskMarked(Task task) {
        System.out.println(SEPARATOR);
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("      " + task);
        System.out.println(SEPARATOR);
    }

    /** Displays confirmation that a task was marked incomplete. */
    public void showTaskUnmarked(Task task) {
        System.out.println(SEPARATOR);
        System.out.println("    Ok, I've marked this task as not done:");
        System.out.println("      " + task);
        System.out.println(SEPARATOR);
    }

    /** Displays the application goodbye message. */
    public void showGoodbye() {
        System.out.println(SEPARATOR);
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
    }

    /** Displays confirmation that a task was added. */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(SEPARATOR);
        System.out.println("    Woof! I have added: " + task);
        System.out.println("    Now you have " + taskCount + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    /** Displays all tasks in the given list. */
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
