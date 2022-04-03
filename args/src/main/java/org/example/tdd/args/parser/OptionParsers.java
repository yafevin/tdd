package org.example.tdd.args.parser;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import org.example.tdd.args.annotation.Option;
import org.example.tdd.args.exception.IllegalValueException;
import org.example.tdd.args.exception.InsufficientArgumentsException;
import org.example.tdd.args.exception.TooManyArgumentsException;

/**
 * SingleValuedOptionParser
 *
 * @author yafevin
 */
public class OptionParsers {
    public static OptionParser<Boolean> bool() {
        return ((arguments, option) -> values(arguments, option, 0).isPresent());
    }

    public static <T> OptionParser<T> unary(T defaultValue, Function<String, T> valueParser) {
        return (arguments, option) -> {
            Optional<List<String>> argumentList = values(arguments, option, 1);
            return argumentList.map(item -> parseValue(option, item.get(0), valueParser)).orElse(defaultValue);
        };
    }

    public static <T> OptionParser<T[]> list(IntFunction<T[]> generator, Function<String, T> valueParser) {
        return (arguments, option) -> values(arguments, option)
                .map(item -> item.stream()
                        .map(value -> parseValue(option, value, valueParser))
                        .toArray(generator)).
                orElse(generator.apply(0));
    }

    private static Optional<List<String>> values(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return Optional.ofNullable(index == -1 ? null : values(arguments, index));
    }

    private static Optional<List<String>> values(List<String> arguments, Option option, int expectedSize) {
        return values(arguments, option).map(item -> checkSize(option, expectedSize, item));
    }

    private static List<String> checkSize(Option option, int expectedSize, List<String> values) {
        if (values.size() < expectedSize) {
            throw new InsufficientArgumentsException(option.value());
        }
        if (values.size() > expectedSize) {
            throw new TooManyArgumentsException(option.value());
        }
        return values;
    }

    private static <T> T parseValue(Option option, String value, Function<String, T> valueParser) {
        try {
            return valueParser.apply(value);
        } catch (Exception e) {
            throw new IllegalValueException(option.value(), value);
        }
    }

    private static List<String> values(List<String> arguments, int index) {
        int flag = IntStream.range(index + 1, arguments.size())
                .filter(item -> arguments.get(item).matches("^-[a-zA-Z-]+$"))
                .findFirst()
                .orElse(arguments.size());// .orElse(index + 1)
//        return flag + 1 == arguments.size() ? Collections.singletonList(arguments.get(flag)) : arguments.subList(index + 1, flag);
        return arguments.subList(index + 1, flag);
    }
}
