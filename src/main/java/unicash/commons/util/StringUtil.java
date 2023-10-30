package unicash.commons.util;

import static java.util.Objects.requireNonNull;
import static unicash.commons.util.AppUtil.checkArgument;
import static unicash.commons.util.CollectionUtil.requireAllNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    private StringUtil() {

    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code substring}.
     * Ignores case, and a full word match is not required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == true //substring match
     *       </pre>
     *
     * @param sentence  cannot be null
     * @param substring cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsSubstringIgnoreCase(String sentence, String substring) {
        requireAllNonNull(sentence, substring);

        String preppedString = substring.trim();
        checkArgument(!preppedString.isEmpty(), "Substring parameter cannot be empty");

        return sentence.toLowerCase().contains(preppedString.toLowerCase());
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     *
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Capitalizes a given input string such that the first character is in uppercase and the rest is in lowercase.
     *
     * <p>If first character is not an alphabet, it just returns the original string but in lowercase now</p>
     *
     * @throws NullPointerException if {@code input} is null.
     */
    public static String capitalizeString(String input) {
        requireNonNull(input);
        String trimmedInput = input.trim();
        assert trimmedInput.length() > 0;

        var builder = new StringBuilder();
        var firstCharacter = trimmedInput.charAt(0);
        if (Character.isAlphabetic(firstCharacter)) {
            builder.append(Character.toUpperCase(firstCharacter));
        } else {
            builder.append(firstCharacter);
        }

        if (trimmedInput.length() == 1) {
            // Proactively catches cases substring on 1 length strings throw IndexOutOfBoundsException
            return builder.toString();
        }

        var rest = trimmedInput.substring(1);
        builder.append(rest.toLowerCase());

        return builder.toString();
    }
}
