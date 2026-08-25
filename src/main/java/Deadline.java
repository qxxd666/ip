public class Deadline extends Task {
    private String by;

    Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String getBy() {
        return by;
    }

    @Override
    public String toStorageString() {
        String status = isDone ? "1" : "0";
        return "D | " + status + " | " + description + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
