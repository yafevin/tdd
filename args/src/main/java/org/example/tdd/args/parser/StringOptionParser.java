package org.example.tdd.args;

import java.util.List;

import org.example.tdd.args.annotation.Option;

/**
 * StringOptionParser
 *
 * @author yafevin
 */
class StringOptionParser implements OptionParser {

    @Override
    public Object parse(List<String> argumrnts, Option option) {
        int index = argumrnts.indexOf("-" + option.value());
        return argumrnts.get(index + 1);
    }
}
