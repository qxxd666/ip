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

        while (true) {
            System.out.println();
            String text = scanner.nextLine().trim();
            String[] commands = text.split(" ");
            if (commands.length == 2 && commands[0].equals("mark")) {
                String number = commands[1];
                int taskNumber;
                try {
                    taskNumber = Integer.parseInt(number);
                } catch (NumberFormatException e) {
                    System.out.println("    Please enter a valid task number.");
                    continue;
                }
                if (taskNumber < 1 || taskNumber > tasks.size()) {
                    System.out.println("    That task number does not exist.");
                    continue;
                }
                tasks.get(taskNumber - 1).markDone();
                System.out.println(SEPARATOR);
                System.out.println("    Nice! I've marked this task as done:");
                System.out.println("      " + tasks.get(taskNumber - 1));
                System.out.println(SEPARATOR);
            } else if (commands.length == 2 && commands[0].equals("unmark")) {
                String number = commands[1];
                int taskNumber;
                try {
                    taskNumber = Integer.parseInt(number);
                } catch (NumberFormatException e) {
                    System.out.println("    Please enter a valid task number.");
                    continue;
                }
                if (taskNumber < 1 || taskNumber > tasks.size()) {
                    System.out.println("    That task number does not exist.");
                    continue;
                }
                tasks.get(taskNumber - 1).unmarkDone();
                System.out.println(SEPARATOR);
                System.out.println("    OK, I've marked this task as not done yet:");
                System.out.println("      " + tasks.get(taskNumber - 1));
                System.out.println(SEPARATOR);
            } else if (text.equals("bye")) {
                System.out.println(SEPARATOR);
                System.out.println("    Bye. Hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            } else if (text.equals("list")) {
                System.out.println(SEPARATOR);
                System.out.println("    Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println("    " + (i + 1) + "." + tasks.get(i));
                }
                System.out.println(SEPARATOR);
            } else {
                try {
                    Task task = Parser.parseTask(text);
                    tasks.add(task);

                    System.out.println(SEPARATOR);
                    System.out.println("    Woof! I have added: " + task);
                    System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(SEPARATOR);
                } catch (DogeException e) {
                    System.out.println(SEPARATOR);
                    System.out.println("    " + e.getMessage());
                    System.out.println(SEPARATOR);
                }
            }
        }
    }
}
