package doge.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Represents a task that must be completed by a specific date and time. */
public class Deadline extends Task {
    private LocalDateTime by;

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /** Creates an incomplete deadline task. */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /** Returns the date and time by which this task should be completed. */
    public LocalDateTime getBy() {
        return by;
    }

    /** Returns this deadline in the pipe-delimited persistence format. */
    @Override
    public String toStorageString() {
        String status = isDone ? "1" : "0";
        return "D | " + status + " | " + description + " | " + by;
    }

    /** Returns this deadline in the format displayed by the user interface. */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + by.format(DISPLAY_FORMATTER) + ")";
    }
}
