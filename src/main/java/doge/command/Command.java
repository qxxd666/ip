package doge.command;

import doge.exception.DogeException;

/** Represents a command accepted by Doge. */
public enum Command {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    MARK("mark"),
    UNMARK("unmark"),
    LIST("list"),
    FIND("find"),
    BYE("bye"),
    DELETE("delete");

    private final String keyword;

    Command(String keyword) {
        this.keyword = keyword;
    }

    /** Returns the command represented by the given text. */
    public static Command fromText(String text) throws DogeException {
        for (Command command : values()) {
            if (command.keyword.equals(text)) {
                return command;
            }
        }
        throw new DogeException("I don't understand that command");
    }
}
