package eu.upliftingflow.elastic.exception;

public class AuthorException extends Exception {
    public static String NOT_FOUND = "Author not found";

    public AuthorException() {
        super(NOT_FOUND);
    }

    public AuthorException(final String message) {
        super(message);
    }
}
