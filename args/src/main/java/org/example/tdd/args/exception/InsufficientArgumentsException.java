package org.example.tdd.args.exception;

/**
 * InsufficientArgumentsException
 *
 * @author yafevin
 */
public class InsufficientArgumentsException extends RuntimeException {
    private final String option;

    public InsufficientArgumentsException(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
