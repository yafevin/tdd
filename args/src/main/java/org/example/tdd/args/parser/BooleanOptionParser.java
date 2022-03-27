package org.example.tdd.args.parser;

import java.util.List;

import org.example.tdd.args.annotation.Option;
import org.example.tdd.args.exception.TooManyArgumentsException;

/**
 * BooleanOptionParser
 *
 * @author yafevin
 */
public class BooleanOptionParser implements OptionParser<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (index + 1 < arguments.size() && !arguments.get(index + 1).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }
        return index != -1;
    }
}
