package org.example.tdd.args.util;

import java.lang.annotation.Annotation;

import org.example.tdd.args.annotation.Option;

/**
 * OptionUtil
 *
 * @author yafevin
 */
public class OptionUtils {
    public static Option option(String value) {
        return new Option() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }
}
