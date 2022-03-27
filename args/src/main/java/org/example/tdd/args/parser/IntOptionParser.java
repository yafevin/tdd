package org.example.tdd.args.parser;

import java.util.List;

import org.example.tdd.args.annotation.Option;

/**
 * IntOptionParser
 *
 * @author yafevin
 */
@Deprecated
public class IntOptionParser implements OptionParser<Integer> {

    @Override
    public Integer parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return Integer.valueOf(arguments.get(index + 1));
    }
}
