package doge.parser;

import doge.command.Command;
import doge.exception.DogeException;
import doge.model.Deadline;
import doge.model.Event;
import doge.model.Task;
import doge.model.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** Parses user input into task objects. */
public class Parser {

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");

    private Parser() {
    }

    /** Returns the task described by the given user command. */
    public static Task parseTask(String input) throws DogeException {
        String[] commandAndArguments = input.trim().split("\\s+", 2);

        Command command = Command.fromText(commandAndArguments[0]);
        String arguments = commandAndArguments.length == 2 ? commandAndArguments[1] : "";
        return switch (command) {
        case TODO -> parseTodo(arguments);
        case DEADLINE -> parseDeadline(arguments);
        case EVENT -> parseEvent(arguments);
        case MARK, UNMARK, LIST, FIND, BYE, DELETE ->
                throw new DogeException("I don't understand that command");
        };
    }

    private static Todo parseTodo(String description) throws DogeException {
        if (description.isEmpty()) {
            throw new DogeException("Todo cannot be empty! Use this format: todo DESCRIPTION");
        }
        return new Todo(description);
    }

    private static Deadline parseDeadline(String description) throws DogeException {
        String[] parts = description.split("\\s+/by\\s+", 2);

        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new DogeException(
                    "Use this format: deadline DESCRIPTION /by d/M/yyyy HHmm");
        }

        try {
            LocalDateTime deadline = LocalDateTime.parse(parts[1].trim(), INPUT_FORMATTER);
            return new Deadline(parts[0], deadline);
        } catch (DateTimeParseException e) {
            throw new DogeException("Please enter the deadline as d/M/yyyy HHmm.");
        }
    }

    private static Event parseEvent(String description) throws DogeException {
        String[] fromParts = description.split("\\s+/from\\s+", 2);
        if (fromParts.length != 2 || fromParts[0].isBlank()
                || fromParts[1].isBlank()) {
            throw new DogeException("Use this format: event DESCRIPTION /from START /to END");
        }

        String[] toParts = fromParts[1].split("\\s+/to\\s+", 2);

        if (toParts.length != 2 || toParts[0].isBlank() || toParts[1].isBlank()) {
            throw new DogeException("Use this format: event DESCRIPTION /from START /to END");
        }

        try {
            LocalDateTime start = LocalDateTime.parse(toParts[0].trim(), INPUT_FORMATTER);
            LocalDateTime end = LocalDateTime.parse(toParts[1].trim(), INPUT_FORMATTER);
            return new Event(fromParts[0], start, end);
        } catch (DateTimeParseException e) {
            throw new DogeException("Please enter event dates as d/M/yyyy HHmm.");
        }
    }
}
