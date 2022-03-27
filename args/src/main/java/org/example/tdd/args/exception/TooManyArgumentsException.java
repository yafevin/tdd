package org.example.tdd.args.exception;

/**
 * TooManyArgumentsException
 *
 * @author tianxinhui (tianxh@primeton.com)
 */
public class TooManyArgumentsException extends RuntimeException {
    private final String option;

    public TooManyArgumentsException(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
