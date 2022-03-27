package org.example.tdd.args.parser;

import java.util.List;

import org.example.tdd.args.annotation.Option;

/**
 * OptionParser
 *
 * @author yafevin
 */
public interface OptionParser<T> {
    T parse(List<String> arguments, Option option);
}
