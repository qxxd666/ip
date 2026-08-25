package doge;

import doge.command.Command;
import doge.exception.DogeException;
import doge.model.Task;
import doge.model.TaskList;
import doge.parser.Parser;
import doge.storage.Storage;
import doge.ui.UI;

/** Runs the Doge task-management application and coordinates its components. */
public class Doge {

    private static UI ui;
    private static TaskList tasks;
    private static Storage storage;

    /** Starts the command-line application and processes commands until the user exits. */
    public static void main(String[] args) {
        ui = new UI();
        storage = new Storage();

        try {
            tasks = storage.load();
        } catch (DogeException e) {
            ui.printMessage(e.getMessage());
        }
        
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
    private static void processInput(String input) throws DogeException {
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

            case DELETE -> {
                int taskNumber = getTaskNumber(commands);
                Task delTask = tasks.delete(taskNumber);
                ui.printMessage("    Successfully deleted task: " + delTask);
            }
            case TODO, DEADLINE, EVENT -> addTask(input);

        }
    }

    /** Extracts and validates the one-based task number from a command. */
    private static int getTaskNumber(String[] commands) throws DogeException {
        if (commands.length < 2 || commands[1].isBlank()) {
            throw new DogeException("Please provide a task number.");
        }
        return validateTaskNumber(commands[1]);
    }

    /** Parses a task command, adds the resulting task, and reports parsing errors. */
    private static void addTask(String text) {
        try {
            Task task = Parser.parseTask(text);
            tasks.add(task);
            ui.showTaskAdded(task, tasks.size());
        } catch (DogeException e) {
            ui.printMessage("    " + e.getMessage());
        }
    }

    /** Converts a task number to an integer and verifies that it exists in the task list. */
    private static int validateTaskNumber(String numberText) throws DogeException {
        int taskNumber;

        try {
            taskNumber = Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            throw new DogeException("Please enter a valid task number.");
        }

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new DogeException("That task number does not exist.");
        }

        return taskNumber;
    }
}
