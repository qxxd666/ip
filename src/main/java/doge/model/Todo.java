package doge.model;

/** Represents a task without a deadline or event period. */
public class Todo extends Task {

    /** Creates a todo task with the given description. */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
