package org.example.tdd.args.parser;

import java.util.List;
import java.util.function.Function;

import org.example.tdd.args.exception.InsufficientArgumentsException;
import org.example.tdd.args.exception.TooManyArgumentsException;
import org.example.tdd.args.util.OptionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * SingleValuedOptionParserTest
 *
 * @author yafevin
 */
public class SingleValuedOptionParserTest {
    // sad path:
    // TODO: - int -p/ -p 8080 8081
    // TODO: - string -d/ -d /usr/logs /usr/vars
    // default value:
    // TODO: -int :0
    // TODO: - string ""

    @Test
    void should_not_accept_extra_argument_for_int_single_valued_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new SingleValuedOptionParser<>(0, Integer::parseInt).parse(List.of("-p", "8080", "8081"), OptionUtils.option("p")));
        assertEquals("p", e.getOption());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-p -l", "-p"})
    void should_not_accept_insufficient_argument_for_int_single_valued_option(String arguments) {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                () -> new SingleValuedOptionParser<>(0, Integer::parseInt).parse(List.of(arguments.split(" ")), OptionUtils.option("p")));
        assertEquals("p", e.getOption());
    }

    @Test
    void should_set_default_value_for_single_valued_option() {
        assertEquals(0, new SingleValuedOptionParser<>(0, Integer::parseInt).parse(List.of(), OptionUtils.option("p")));
    }

    @Test
    void should_parse_value_if_flag_present() {
        assertEquals(8080, new SingleValuedOptionParser<>(0, Integer::parseInt).parse(List.of("-p", "8080"), OptionUtils.option("p")));
    }

    // assertSame
    @Test
    void should_set_default_value_for_all() {
        Function<String, Object> whatever = item -> null;
        Object defaultValue = new Object();
        assertSame(defaultValue, new SingleValuedOptionParser<>(defaultValue, whatever).parse(List.of(), OptionUtils.option("p")));
    }

    // assertSame
    @Test
    void should_parse_value_for_all() {
        Object parsed = new Object();
        Function<String, Object> parse = item -> parsed;
        Object whatever = new Object();
        assertSame(parsed, new SingleValuedOptionParser<>(whatever, parse).parse(List.of("-p", "8080"), OptionUtils.option("p")));
    }
}
