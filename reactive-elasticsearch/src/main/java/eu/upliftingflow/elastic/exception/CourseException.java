package eu.upliftingflow.elastic.exception;

public class CourseException extends Exception {
    public static String NOT_FOUND = "Course not found";

    public CourseException() {
        super(NOT_FOUND);
    }

    public CourseException(final String message) {
        super(message);
    }
}
