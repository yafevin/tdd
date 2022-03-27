package org.example.tdd.args;

import java.util.List;

import org.example.tdd.args.annotation.Option;

/**
 * BooleanOptionParser
 *
 * @author yafevin
 */
class BooleanOptionParser implements OptionParser {

    @Override
    public Object parse(List<String> argumrnts, Option option) {
        return argumrnts.contains("-" + option.value());
    }
}
