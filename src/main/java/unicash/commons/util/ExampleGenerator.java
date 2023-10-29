package unicash.commons.util;

import static java.util.Objects.requireNonNull;
import static unicash.commons.util.CollectionUtil.requireAllNonNull;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_MONTH;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;
import static unicash.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import unicash.logic.parser.Prefix;

/**
 * Generates example usage of commands given predefined valid values of each prefix for consistency.
 */
public class ExampleGenerator {
    private static final Map<Prefix, String> PREFIX_VALUES = Map.ofEntries(
            new SimpleEntry<>(PREFIX_NAME, "Buying groceries"),
            new SimpleEntry<>(PREFIX_AMOUNT, "300"),
            new SimpleEntry<>(PREFIX_DATETIME, "18-08-2023 19:30"),
            new SimpleEntry<>(PREFIX_TYPE, "expense"),
            new SimpleEntry<>(PREFIX_CATEGORY, "Food"),
            new SimpleEntry<>(PREFIX_LOCATION, "NTUC"),
            new SimpleEntry<>(PREFIX_MONTH, "10"),
            new SimpleEntry<>(PREFIX_YEAR, "2006")
    );

    private ExampleGenerator() {

    }

    /**
     * Generates example, prefixed by {@code commandWord} and with optional list of {@code Prefix} that provides
     * default set of values for each type of {@code Prefix}.
     *
     * <p>Note, this method does not impose any duplication restrictions on the list of {@code Prefix} as future
     * usages may include multiple prefixes, like demonstrating variadic parameters.</p>
     *
     * @throws NullPointerException if {@code commandWord} or any of the {@code Prefix} is null.
     */
    public static String generate(String commandWord, Prefix... prefixes) {
        requireNonNull(commandWord);
        var stringBuilder = new StringBuilder();
        stringBuilder.append(commandWord);
        if (prefixes.length == 0) {
            return stringBuilder.toString();
        }

        stringBuilder.append(" ");
        for (var prefix : prefixes) {
            requireNonNull(prefix);
        }

        var arguments = Arrays.stream(prefixes)
                .map(p ->
                        String.format("%s%s", p.getPrefix(), PREFIX_VALUES.getOrDefault(p, "unknown"))
                )
                .collect(Collectors.joining(" "));
        stringBuilder.append(arguments);
        return stringBuilder.toString();
    }


    /**
     * Generate example by combining {@code commandWord} with {@code argument} together and then
     * relies on {@link #generate(String, Prefix...) generate} method to create the example.
     *
     * @throws NullPointerException if {@code commandWord} or {@code argument} is null or any {@code Prefix}
     *                              is null.
     */
    public static String generate(String commandWord, String argument, Prefix... prefixes) {
        requireAllNonNull(commandWord, argument);
        var newStart = String.format("%s %s", commandWord, argument);
        return generate(newStart, prefixes);
    }

}
