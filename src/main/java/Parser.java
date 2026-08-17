public class Parser {
    private Parser() {

    }

    public static Task parseTask(String input) {
        String[] commandAndArguments = input.trim().split(" ", 2);

        String command = commandAndArguments[0];
        String arguments = commandAndArguments.length == 2 ? commandAndArguments[1] : "";
        switch (command) {
            case "todo" -> {
                return parseTodo(arguments);
            }
            case "deadline" -> {
                return parseDeadline(arguments);
            }
            case "event" -> {
                return parseEvent(arguments);
            }
        }
        return null;
    }

    private static Todo parseTodo(String description) {
        return new Todo(description);
    }

    private static Deadline parseDeadline(String description) {
        String[] parts = description.split("/by", 2);
        return new Deadline(parts[0], parts[1]);
    }

    private static Event parseEvent(String description) {
        String[] fromParts = description.split("/from", 2);
        String[] toParts = fromParts[1].split("/to", 2);
        return new Event(fromParts[0], toParts[0], toParts[1]);
    }
}
