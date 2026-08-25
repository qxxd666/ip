package doge.model;

/** Represents a task with a description and completion status. */
public class Task {
    protected final String description;
    protected boolean isDone;

    /** Creates an incomplete task with the given description. */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Returns the completion icon for this task. */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "]" + " " + description;
    }

    /** Marks this task as complete. */
    public void markDone() {
        this.isDone = true;
    }

    /** Marks this task as incomplete. */
    public void unmarkDone() {
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    /** Returns this task in the format used by persistent storage. */
    public String toStorageString() {
        String status = isDone ? "1" : "0";
        return "T | " + status + " | " + description;
    }

}
