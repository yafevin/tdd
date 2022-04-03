package org.example.tdd.args.parser;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.example.tdd.args.exception.IllegalValueException;
import org.example.tdd.args.exception.InsufficientArgumentsException;
import org.example.tdd.args.exception.TooManyArgumentsException;
import org.example.tdd.args.util.OptionUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * OptionParsersTest
 *
 * @author yafevin
 */
public class OptionParsersTest {
    @Nested
    class BooleanOptionParserTest {
        // sad path:
        // -bool -l t / -l t f
        // default:
        // - bool : false

        @Test
        void should_not_accept_extra_argument_for_boolean_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                    () -> OptionParsers.bool().parse(List.of("-l", "t"), OptionUtils.option("l")));
            assertEquals("l", e.getOption());
        }

        @Test
        void should_not_accept_extra_arguments_for_boolean_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                    () -> OptionParsers.bool().parse(List.of("-l", "t", "f"), OptionUtils.option("l")));
            assertEquals("l", e.getOption());
        }

        @Test
        void should_set_default_value_to_false_if_option_not_present() {
            assertFalse(OptionParsers.bool().parse(Collections.emptyList(), OptionUtils.option("l")));
        }

        @Test
        void should_set_value_to_true_if_option_present() {
            assertTrue(OptionParsers.bool().parse(List.of("-l"), OptionUtils.option("l")));
        }
    }

    @Nested
    class UnaryOptionParserTest {
        // sad path:
        // - int -p/ -p 8080 8081
        // - string -d/ -d /usr/logs /usr/vars
        // default value:
        // - int :0
        // - string ""

        @Test
        void should_not_accept_extra_argument_for_int_single_valued_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                    () -> OptionParsers.unary(0, Integer::parseInt).parse(List.of("-p", "8080", "8081"), OptionUtils.option("p")));
            assertEquals("p", e.getOption());
        }

        @ParameterizedTest
        @ValueSource(strings = {"-p -l", "-p"})
        void should_not_accept_insufficient_argument_for_int_single_valued_option(String arguments) {
            InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                    () -> OptionParsers.unary(0, Integer::parseInt).parse(List.of(arguments.split(" ")), OptionUtils.option("p")));
            assertEquals("p", e.getOption());
        }

        @Test
        void should_set_default_value_for_single_valued_option() {
            assertEquals(0, OptionParsers.unary(0, Integer::parseInt).parse(List.of(), OptionUtils.option("p")));
        }

        @Test
        void should_parse_value_if_flag_present() {
            assertEquals(8080, OptionParsers.unary(0, Integer::parseInt).parse(List.of("-p", "8080"), OptionUtils.option("p")));
        }

        // assertSame
        @Test
        void should_set_default_value_for_all() {
            Function<String, Object> whatever = item -> null;
            Object defaultValue = new Object();
            assertSame(defaultValue, OptionParsers.unary(defaultValue, whatever).parse(List.of(), OptionUtils.option("p")));
        }

        // assertSame
        @Test
        void should_parse_value_for_all() {
            Object parsed = new Object();
            Function<String, Object> parse = item -> parsed;
            Object whatever = new Object();
            assertSame(parsed, OptionParsers.unary(whatever, parse).parse(List.of("-p", "8080"), OptionUtils.option("p")));
        }
    }

    @Nested
    class ListOptionParserTest {
        // -g "this" "is" {"this", is"}
        @Test
        void should_parse_list_value() {
            String[] value = OptionParsers.list(String[]::new, String::valueOf).parse(List.of("-g", "this", "is"), OptionUtils.option("g"));
            assertArrayEquals(new String[]{"this", "is"}, value);
        }

        @Test
        void should_not_treat_negative_int_as_flag() {
            Integer[] value = OptionParsers.list(Integer[]::new, Integer::parseInt).parse(List.of("-g", "-1", "-2"), OptionUtils.option("g"));
            assertArrayEquals(new Integer[]{-1, -2}, value);
        }

        // default value[]
        @Test
        void shoule_use_enpty_as_default_value() {
            String[] value = OptionParsers.list(String[]::new, String::valueOf).parse(List.of(), OptionUtils.option("g"));
            assertEquals(0, value.length);
        }

        //TODO: -d a throw exception

        @Test
        void should_throw_exception_if_cant_parse_value() {
            Function<String, String> parser = it -> {
                throw new RuntimeException();
            };
            IllegalValueException e = assertThrows(IllegalValueException.class,
                    () -> OptionParsers.list(String[]::new, parser).parse(List.of("-g", "this", "is"), OptionUtils.option("g")));
            assertEquals("g", e.getOption());
            assertEquals("this", e.getValue());
        }
    }
}
