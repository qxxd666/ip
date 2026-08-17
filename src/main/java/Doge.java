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
        System.out.println("    Hello! I'm Doge.");
        System.out.println("    What can I do for you?");
        System.out.println(SEPARATOR);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            String text = scanner.nextLine();
            if (text.equals("bye")) {
                System.out.println(SEPARATOR);
                System.out.println("    Bye. Hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            } else {
                System.out.println(SEPARATOR);
                System.out.println("    added: " + text);
                System.out.println(SEPARATOR);
            }
        }
    }
}
