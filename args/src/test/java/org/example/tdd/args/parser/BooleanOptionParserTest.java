package org.example.tdd.args.parser;

import java.util.Collections;
import java.util.List;

import org.example.tdd.args.exception.TooManyArgumentsException;
import org.example.tdd.args.util.OptionUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * BooleanOptionParserTest
 *
 * @author yafevin
 */
public class BooleanOptionParserTest {
    // sad path:
    // TODO: -bool -l t / -l t f
    // default:
    // TODO: - bool : false

    @Test
    void should_not_accept_extra_argument_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new BooleanOptionParser().parse(List.of("-l", "t"), OptionUtils.option("l")));
        assertEquals("l", e.getOption());
    }

    @Test
    void should_not_accept_extra_arguments_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new BooleanOptionParser().parse(List.of("-l", "t", "f"), OptionUtils.option("l")));
        assertEquals("l", e.getOption());
    }

    @Test
    void should_set_default_value_to_false_if_option_not_present() {
        assertFalse(new BooleanOptionParser().parse(Collections.emptyList(), OptionUtils.option("l")));
    }

    @Test
    void should_set_value_to_true_if_option_present() {
        assertTrue(new BooleanOptionParser().parse(List.of("-l"), OptionUtils.option("l")));
    }
}
