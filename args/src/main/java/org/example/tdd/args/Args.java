package org.example.tdd.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.example.tdd.args.annotation.Option;
import org.example.tdd.args.exception.IllegalOptionException;
import org.example.tdd.args.parser.BooleanOptionParser;
import org.example.tdd.args.parser.OptionParser;
import org.example.tdd.args.parser.SingleValuedOptionParser;

/**
 * Args
 *
 * @author yafevin
 */
public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        try {
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
            List<String> arguments = List.of(args);
            Object[] values = Arrays.stream(constructor.getParameters())
                    .map(parameter -> parseOption(arguments, parameter))
                    .toArray();
            return (T) constructor.newInstance(values);
        } catch (IllegalOptionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        if (!parameter.isAnnotationPresent(Option.class)) {
            throw new IllegalOptionException(parameter.getName());
        }
        return PARSERS.get(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }

    private static final Map<Class<?>, OptionParser> PARSERS = Map.of(boolean.class, new BooleanOptionParser(),
            int.class, new SingleValuedOptionParser<>(0, Integer::parseInt),
            String.class, new SingleValuedOptionParser<>("", String::valueOf));

}
