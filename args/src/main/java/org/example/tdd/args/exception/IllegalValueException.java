package org.example.tdd.args.exception;

/**
 * IllegalValueException
 *
 * @author yafevin
 */
public class IllegalValueException extends RuntimeException {
    private final Object option;
    private final Object value;

    public IllegalValueException(Object option, Object value) {
        this.option = option;
        this.value = value;
    }

    public Object getOption() {
        return option;
    }

    public Object getValue() {
        return value;
    }
}
