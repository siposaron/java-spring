package eu.uflow.generator.docgen.application.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApplicationException extends Exception {
    final String errorCode;
    final String description;
    final String message;
}
