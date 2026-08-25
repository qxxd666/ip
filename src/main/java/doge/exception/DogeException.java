package doge.exception;

/** Represents a user-facing error raised by the Doge application. */
public class DogeException extends Exception {

    /** Creates an exception with the given user-facing message. */
    public DogeException(String message) {
        super(message);
    }
}
