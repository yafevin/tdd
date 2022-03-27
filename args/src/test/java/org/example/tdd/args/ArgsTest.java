package org.example.tdd.args;

import org.example.tdd.args.annotation.Option;
import org.example.tdd.args.exception.IllegalOptionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ArgsTest
 *
 * @author yafevin
 */
public class ArgsTest {
    @Test
    void should_parse_nulti_options() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    @Test
    void should_throw_illegal_option_exception_if_annotation_not_present() {
        IllegalOptionException e = assertThrows(IllegalOptionException.class,
                () -> Args.parse(OptionWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/logs"));
        assertEquals("port", e.getParameter());
    }

    record OptionWithoutAnnotation(@Option("l") boolean logging, int port, @Option("d") String directory) {
    }

    record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
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
