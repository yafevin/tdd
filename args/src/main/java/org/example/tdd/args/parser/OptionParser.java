package org.example.tdd.args;

import java.util.List;

import org.example.tdd.args.annotation.Option;

/**
 * OptionParser
 *
 * @author yafevin
 */
interface OptionParser {
    Object parse(List<String> argumrnts, Option option);
}
