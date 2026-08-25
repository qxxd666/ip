package doge.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Represents a task occurring during a specified period. */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /** Creates an event task with the given description and time period. */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toStorageString() {
        String status = isDone ? "1" : "0";
        return "E | " + status + " | " + description + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(DISPLAY_FORMATTER)
                + " to: " + to.format(DISPLAY_FORMATTER) + ")";
    }
}
