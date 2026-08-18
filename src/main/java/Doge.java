import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doge {
    public static void main(String[] args) {
        final String BANNER = "       ____   ___   ____ _____\n" +
                "      |  _ \\ / _ \\ / ___| ____|\n" +
                "      | | | | | | | |  _|  _|\n" +
                "      | |_| | |_| | |_| | |___\n" +
                "      |____/ \\___/ \\____|_____|";
        final String SEPARATOR = "    ____________________________________________________________";

        System.out.println(SEPARATOR);
        System.out.println(BANNER);
        System.out.println("    Rawr! I'm Doge.");
        System.out.println("    What can I do for you?");
        System.out.println(SEPARATOR);

        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

        mainLoop:
        while (true) {
            System.out.println();
            String text = scanner.nextLine().trim();
            String[] commands = text.split(" ");
            Command command;
            try {
                command = Command.fromText(commands[0]);
            } catch (DogeException e) {
                System.out.println(SEPARATOR);
                System.out.println("    " + e.getMessage());
                System.out.println(SEPARATOR);
                continue;
            }

            switch (command) {
            case MARK -> {
                if (commands.length != 2) {
                    printError("Use this format: mark TASK_NUMBER", SEPARATOR);
                    continue;
                }
                String number = commands[1];
                int taskNumber;
                try {
                    taskNumber = validateTaskNumber(number, tasks);
                } catch (DogeException e) {
                    System.out.println(SEPARATOR);
                    System.out.println("    " + e.getMessage());
                    System.out.println(SEPARATOR);
                    continue;
                }
                tasks.get(taskNumber - 1).markDone();
                System.out.println(SEPARATOR);
                System.out.println("    Nice! I've marked this task as done:");
                System.out.println("      " + tasks.get(taskNumber - 1));
                System.out.println(SEPARATOR);
            }
            case UNMARK -> {
                if (commands.length != 2) {
                    printError("Use this format: unmark TASK_NUMBER", SEPARATOR);
                    continue;
                }
                String number = commands[1];
                int taskNumber;
                try {
                    taskNumber = validateTaskNumber(number, tasks);
                } catch (DogeException e) {
                    System.out.println(SEPARATOR);
                    System.out.println("    " + e.getMessage());
                    System.out.println(SEPARATOR);
                    continue;
                }
                tasks.get(taskNumber - 1).unmarkDone();
                System.out.println(SEPARATOR);
                System.out.println("    OK, I've marked this task as not done yet:");
                System.out.println("      " + tasks.get(taskNumber - 1));
                System.out.println(SEPARATOR);
            }
            case BYE -> {
                if (commands.length != 1) {
                    printError("The bye command does not take any arguments.", SEPARATOR);
                    continue;
                }
                System.out.println(SEPARATOR);
                System.out.println("    Bye. Hope to see you again soon!");
                System.out.println(SEPARATOR);
                break mainLoop;
            }
            case LIST -> {
                if (commands.length != 1) {
                    printError("The list command does not take any arguments.", SEPARATOR);
                    continue;
                }
                System.out.println(SEPARATOR);
                System.out.println("    Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println("    " + (i + 1) + "." + tasks.get(i));
                }
                System.out.println(SEPARATOR);
            }
            case DELETE -> {
                if (commands.length != 2) {
                    printError("Use this format: delete TASK_NUMBER", SEPARATOR);
                    continue;
                }
                int taskNumber;
                try {
                    taskNumber = validateTaskNumber(commands[1], tasks);
                } catch (DogeException e) {
                    System.out.println(SEPARATOR);
                    System.out.println("    " + e.getMessage());
                    System.out.println(SEPARATOR);
                    continue;
                }
                String delTask = tasks.get(taskNumber - 1).toString();
                tasks.remove(taskNumber - 1);
                System.out.println(SEPARATOR);
                System.out.println("    Successfully deleted task: " + delTask);
                System.out.println(SEPARATOR);
            }
            case TODO, DEADLINE, EVENT -> addTask(text, tasks, SEPARATOR);
            }
        }
    }

    private static void addTask(String text, List<Task> tasks, String separator) {
        try {
            Task task = Parser.parseTask(text);
            tasks.add(task);

            System.out.println(separator);
            System.out.println("    Woof! I have added: " + task);
            System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
            System.out.println(separator);
        } catch (DogeException e) {
            System.out.println(separator);
            System.out.println("    " + e.getMessage());
            System.out.println(separator);
        }
    }

    private static void printError(String message, String separator) {
        System.out.println(separator);
        System.out.println("    " + message);
        System.out.println(separator);
    }

    private static int validateTaskNumber(String numberText, List<Task> tasks) throws DogeException {
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
