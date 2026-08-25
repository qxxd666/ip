package doge;

import doge.command.Command;
import doge.exception.DogeException;
import doge.model.Task;
import doge.model.TaskList;
import doge.parser.Parser;
import doge.storage.Storage;
import doge.ui.UI;

import java.util.List;

/** Runs the Doge task management application. */
public class Doge {

    private final UI ui;
    private final TaskList tasks;
    private final Storage storage;

    /** Starts the application. */
    public static void main(String[] args) {
        new Doge().run();
    }

    private Doge() {
        ui = new UI();
        storage = new Storage();
        tasks = loadTasks();
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

    private void processInput(String input) throws DogeException {
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
                ui.printMatchingTasks(matchingTasks);
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

        }
    }

    private int getTaskNumber(String[] commands) throws DogeException {
        if (commands.length < 2 || commands[1].isBlank()) {
            throw new DogeException("Please provide a task number.");
        }
        return validateTaskNumber(commands[1]);
    }

    private void addTask(String text) {
        try {
            Task task = Parser.parseTask(text);
            tasks.add(task);
            ui.showTaskAdded(task, tasks.size());
        } catch (DogeException e) {
            ui.printMessage("    " + e.getMessage());
        }
    }

    private int validateTaskNumber(String numberText) throws DogeException {
        try {
            int taskNumber = Integer.parseInt(numberText);

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
