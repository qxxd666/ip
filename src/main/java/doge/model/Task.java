package doge.model;

/** Stores the description and completion status shared by all task types. */
public class Task {
    protected String description;
    protected boolean isDone;

    /** Creates an incomplete task with the given description. */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Returns {@code X} for a completed task and a blank space otherwise. */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /** Returns the task in the format displayed by the user interface. */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "]" + " " + description;
    }

    /** Marks this task as completed. */
    public void markDone() {
        this.isDone = true;
    }

    /** Marks this task as incomplete. */
    public void unmarkDone() {
        this.isDone = false;
    }

    /** Returns this task's description. */
    public String getDescription() {
        return description;
    }

    /** Returns whether this task is completed. */
    public boolean isDone() {
        return isDone;
    }

    /** Returns this task in the pipe-delimited format used for persistence. */
    public String toStorageString() {
        String status = isDone ? "1" : "0";
        return "T | " + status + " | " + description;
    }

}
