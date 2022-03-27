package org.example.tdd.args.parser;

import java.util.List;

import org.example.tdd.args.annotation.Option;

/**
 * StringOptionParser
 *
 * @author yafevin
 */
@Deprecated
public class StringOptionParser implements OptionParser<String> {

    @Override
    public String parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return arguments.get(index + 1);
    }
}
