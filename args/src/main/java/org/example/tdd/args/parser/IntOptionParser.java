package org.example.tdd.args;

import java.util.List;

import org.example.tdd.args.annotation.Option;

/**
 * IntOptionParser
 *
 * @author yafevin
 */
class IntOptionParser implements OptionParser {

    @Override
    public Object parse(List<String> argumrnts, Option option) {
        int index = argumrnts.indexOf("-" + option.value());
        return Integer.valueOf(argumrnts.get(index + 1));
    }
}
