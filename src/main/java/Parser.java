public class Parser {
    private Parser() {

    }

    public static Task parseTask(String input) throws DogeException {
        String[] commandAndArguments = input.trim().split("\\s+", 2);

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
            default -> {
                throw new DogeException("I don't understand that command");
            }
        }
    }

    private static Todo parseTodo(String description) {
        return new Todo(description);
    }

    private static Deadline parseDeadline(String description) throws DogeException {
        String[] parts = description.split("\\s+/by\\s+", 2);
        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new DogeException("Use this format: deadline DESCRIPTION /by DATE");
        }
        return new Deadline(parts[0], parts[1]);
    }

    private static Event parseEvent(String description) throws DogeException {
        String[] fromParts = description.split("\\s+/from\\s+", 2);
        if (fromParts.length != 2 || fromParts[0].isBlank() || fromParts[1].isBlank()) {
            throw new DogeException("Use this format: event DESCRIPTION /from START /to END");
        }

        String[] toParts = fromParts[1].split("\\s+/to\\s+", 2);

        if (toParts.length != 2 || toParts[0].isBlank() || toParts[1].isBlank()) {
            throw new DogeException("Use this format: event DESCRIPTION /from START /to END");
        }

        return new Event(fromParts[0], toParts[0], toParts[1]);
    }
}
