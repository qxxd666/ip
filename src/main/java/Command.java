public enum Command {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    MARK("mark"),
    UNMARK("unmark"),
    LIST("list"),
    BYE("bye"),
    DELETE("delete");

    private final String keyword;

    Command(String keyword) {
        this.keyword = keyword;
    }

    public static Command fromText(String text) throws DogeException {
        for (Command command : values()) {
            if (command.keyword.equals(text)) {
                return command;
            }
        }
        throw new DogeException("I don't understand that command");
    }
}
