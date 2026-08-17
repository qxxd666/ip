import java.util.Scanner;

public class Doge {
    public static void main(String[] args) {
        final String banner = "       ____   ___   ____ _____\n" +
                "      |  _ \\ / _ \\ / ___| ____|\n" +
                "      | | | | | | | |  _|  _|\n" +
                "      | |_| | |_| | |_| | |___\n" +
                "      |____/ \\___/ \\____|_____|";
        final String separator = "    ____________________________________________________________";

        System.out.println(separator);
        System.out.println(banner);
        System.out.println("    Hello! I'm Doge.");
        System.out.println("    What can I do for you?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            String text = scanner.nextLine();
            if (text.equals("bye")) {
                System.out.println(separator);
                System.out.println("    Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            } else {
                System.out.println(separator);
                System.out.println("    " + text);
                System.out.println(separator);
            }
        }

    }
}
