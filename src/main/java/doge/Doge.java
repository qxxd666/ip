package doge;

import java.util.List;

import doge.command.Command;
import doge.exception.DogeException;
import doge.model.Task;
import doge.model.TaskList;
import doge.parser.Parser;
import doge.storage.Storage;
import doge.ui.UI;

/** Runs the Doge task-management application and coordinates its components. */

public class Doge {

    private final UI ui;
    private final TaskList tasks;
    private final Storage storage;


    Doge() {
        ui = new UI();
        storage = new Storage();
        tasks = loadTasks();
    }

    /** Starts the command-line application and processes commands until the user exits. */
    public static void main(String[] args) {
        new Doge().run();
    }

    private void run() {
        ui.showWelcome();

        while (true) {
            String text = ui.readCommand();
            try {
                processInput(text);
            } catch (DogeException e) {
                ui.printMessage(e.getMessage());
            }

            if (text.equals("bye")) {
                try {
                    storage.save(tasks);
                } catch (DogeException e) {
                    ui.printMessage(e.getMessage());
                }
                ui.showGoodbye();
                break;
            }
        }
    }

    /** Executes a single user command after parsing its command keyword and arguments. */
    public void processInput(String input) throws DogeException {
        String[] commands = input.split("\\s+");
        Command command = Command.fromText(commands[0]);

        switch (command) {

            case MARK -> {
                Task task = markTask(commands);
                ui.showTaskMarked(task);
            }

            case UNMARK -> {
                Task task = unmarkTask(commands);
                ui.showTaskUnmarked(task);
            }

            case PRIORITY -> {
                Task task = setPriority(commands);
                ui.printMessage("    Successfully set task priority: " + task);
            }

            case UNPRIORITY -> {
                Task task = clearPriority(commands);
                ui.printMessage("    Successfully cleared task priority: " + task);
            }

            case LIST -> {
                ui.printTaskList(tasks);
            }

            case FIND -> {
                List<Task> matchingTasks = findMatchingTasks(commands);
                ui.printMessage(ui.printMatchingTasks(matchingTasks));
            }

            case BYE -> {
                // The main loop handles saving and displaying the goodbye message.
            }

            case DELETE -> {
                Task delTask = deleteTask(commands);
                ui.printMessage("    Successfully deleted task: " + delTask);
            }

            case TODO, DEADLINE, EVENT -> addTask(input);
            default -> {
                // All command values are handled above.
            }
        }
    }

    /**
     * Executes a command from the graphical user interface and returns a response.
     *
     * @param input command entered by the user
     * @return response message for the graphical user interface
     */
    public String getResponse(String input) {
        try {
            String[] commands = input.trim().split("\\s+");
            Command command = Command.fromText(commands[0]);

            return switch (command) {
                case MARK -> {
                    Task task = markTask(commands);
                    yield "Nice! I've marked this task as done:\n" + task;
                }
                case UNMARK -> {
                    Task task = unmarkTask(commands);
                    yield "Okay, I've marked this task as not done:\n" + task;
                }
                case PRIORITY -> {
                    Task task = setPriority(commands);
                    yield "Successfully set task priority:\n" + task;
                }
                case UNPRIORITY -> {
                    Task task = clearPriority(commands);
                    yield "Successfully cleared task priority:\n" + task;
                }
                case LIST -> tasks.toString();
                case FIND -> {
                    List<Task> matchingTasks = findMatchingTasks(commands);
                    yield ui.printMatchingTasks(matchingTasks);
                }
                case BYE -> {
                    storage.save(tasks);
                    yield "Bye. Hope to see you again soon!";
                }
                case DELETE -> {
                    Task deletedTask = deleteTask(commands);
                    yield "Successfully deleted task: " + deletedTask;
                }
                case TODO, DEADLINE, EVENT -> {
                    Task task = Parser.parseTask(input);
                    tasks.add(task);
                    yield "Woof! I have added: " + task
                            + "\nNow you have " + tasks.size() + " tasks in the list.";
                }
            };
        } catch (DogeException e) {
            return e.getMessage();
        }
    }

    /** Extracts and validates the one-based task number from a command. */
    private int getTaskNumber(String[] commands) throws DogeException {
        if (commands.length < 2 || commands[1].isBlank()) {
            throw new DogeException("Please provide a task number.");
        }
        return validateTaskNumber(commands[1]);
    }

    /** Marks the task selected by a command as completed. */
    private Task markTask(String[] commands) throws DogeException {
        Task task = tasks.get(getTaskNumber(commands));
        task.markDone();
        return task;
    }

    /** Marks the task selected by a command as incomplete. */
    private Task unmarkTask(String[] commands) throws DogeException {
        Task task = tasks.get(getTaskNumber(commands));
        task.unmarkDone();
        return task;
    }

    /** Sets the selected task's priority, defaulting to level 1 when omitted. */
    private Task setPriority(String[] commands) throws DogeException {
        if (commands.length < 2 || commands.length > 3) {
            throw new DogeException("Use this format: priority TASK_NUMBER [LEVEL]");
        }
        int priority = commands.length == 2 ? 1 : parsePriority(commands[2]);
        Task task = tasks.get(getTaskNumber(commands));
        task.setPriority(priority);
        return task;
    }

    /** Clears the selected task's priority. */
    private Task clearPriority(String[] commands) throws DogeException {
        if (commands.length != 2) {
            throw new DogeException("Use this format: unpriority TASK_NUMBER");
        }
        Task task = tasks.get(getTaskNumber(commands));
        task.setPriority(0);
        return task;
    }

    /** Parses a positive priority level. */
    private int parsePriority(String priorityText) throws DogeException {
        try {
            int priority = Integer.parseInt(priorityText);
            if (priority < 1) {
                throw new DogeException("Priority level must be at least 1.");
            }
            return priority;
        } catch (NumberFormatException e) {
            throw new DogeException("Priority level must be a positive number.");
        }
    }

    /** Removes and returns the task selected by a command. */
    private Task deleteTask(String[] commands) throws DogeException {
        return tasks.delete(getTaskNumber(commands));
    }

    /** Returns the tasks matching the keyword supplied in a command. */
    private List<Task> findMatchingTasks(String[] commands) throws DogeException {
        if (commands.length < 2 || commands[1].isBlank()) {
            throw new DogeException("Please provide a keyword to find.");
        }
        return tasks.find(commands[1]);
    }

    /** Parses a task command, adds the resulting task, and reports parsing errors. */
    private void addTask(String text) {
        try {
            Task task = Parser.parseTask(text);
            tasks.add(task);
            ui.showTaskAdded(task, tasks.size());
        } catch (DogeException e) {
            ui.printMessage("    " + e.getMessage());
        }
    }


    /** Converts a task number to an integer and verifies that it exists in the task list. */
    private int validateTaskNumber(String numberText) throws DogeException {
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(numberText);

            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new DogeException("That task number does not exist.");
            }

            return taskNumber;
        } catch (NumberFormatException e) {
            throw new DogeException("Please enter a valid task number.");
        }
    }

    private TaskList loadTasks() {
        try {
            return storage.load();
        } catch (DogeException e) {
            ui.printMessage(e.getMessage());
            return new TaskList();
        }
    }
}
