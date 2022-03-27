package org.example.tdd.args;

import org.example.tdd.args.annotation.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ArgsTest
 *
 * @author yafevin
 */
public class ArgsTest {
    @Test
    void should_be_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assertTrue(option.logging());
    }

    @Test
    void should_be_false_if_flag_not_present() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.logging());
    }

    @Test
    void should_parse_int_as_option_value() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, option.port());
    }

    @Test
    void should_parse_string_as_option_value() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", option.directory());
    }

    record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    record ListOptions(@Option("g") String[] group, @Option("g") int[] decimals) {
    }

    record BooleanOption(@Option("l") boolean logging) {
    }

    record IntOption(@Option("p") int port) {
    }

    record StringOption(@Option("d") String directory) {
    }
}
