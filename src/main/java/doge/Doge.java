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
                int taskNumber = getTaskNumber(commands);
                Task task = tasks.get(taskNumber);
                task.markDone();
                ui.showTaskMarked(task);
            }

            case UNMARK -> {
                int taskNumber = getTaskNumber(commands);
                Task task = tasks.get(taskNumber);
                task.unmarkDone();
                ui.showTaskUnmarked(task);
            }

            case LIST -> {
                ui.printTaskList(tasks);
            }

            case FIND -> {
                if (commands.length < 2 || commands[1].isBlank()) {
                    throw new DogeException("Please provide a keyword to find.");
                }
                List<Task> matchingTasks = tasks.find(commands[1]);
                ui.printMessage(ui.printMatchingTasks(matchingTasks));
            }

            case BYE -> {
                // The main loop handles saving and displaying the goodbye message.
            }

            case DELETE -> {
                int taskNumber = getTaskNumber(commands);
                Task delTask = tasks.delete(taskNumber);
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
                    Task task = tasks.get(getTaskNumber(commands));
                    task.markDone();
                    yield "Nice! I've marked this task as done:\n" + task;
                }
                case UNMARK -> {
                    Task task = tasks.get(getTaskNumber(commands));
                    task.unmarkDone();
                    yield "Okay, I've marked this task as not done:\n" + task;
                }
                case LIST -> tasks.toString();
                case FIND -> {
                    if (commands.length < 2 || commands[1].isBlank()) {
                        throw new DogeException("Please provide a keyword to find.");
                    }

                    List<Task> matchingTasks = tasks.find(commands[1]);
                    yield ui.printMatchingTasks(matchingTasks);
                }
                case BYE -> {
                    storage.save(tasks);
                    yield "Bye. Hope to see you again soon!";
                }
                case DELETE -> {
                    Task deletedTask = tasks.delete(getTaskNumber(commands));
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
