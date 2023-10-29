package unicash.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_MONTH;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;
import static unicash.logic.parser.CliSyntax.PREFIX_YEAR;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import unicash.logic.parser.Prefix;

public class ExampleGeneratorTest {
    @Test
    public void generate_nullCommandWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ExampleGenerator.generate(null));
    }

    @Test
    public void generate_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ExampleGenerator.generate(
                "test", (String) null
        ));
    }

    @Test
    public void generate_nullPrefix_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ExampleGenerator
                .generate(
                        "test",
                        (Prefix) null,
                        PREFIX_NAME,
                        PREFIX_TYPE
                )
        );
        assertThrows(NullPointerException.class, () -> ExampleGenerator
                .generate(
                        "test",
                        PREFIX_NAME,
                        null,
                        PREFIX_TYPE
                )
        );
        assertThrows(NullPointerException.class, () -> ExampleGenerator
                .generate(
                        "test",
                        PREFIX_NAME,
                        PREFIX_TYPE,
                        null
                )
        );
    }

    @Test
    public void generate_onlyCommandWord() {
        var expected = "test";
        var result = ExampleGenerator.generate("test");
        assertEquals(expected, result);
    }

    @Test
    public void generate_commandWordAndAllKnownPrefixes() {
        var expected = "test n/Buying groceries amt/300 dt/18-08-2023 19:30 "
                + "type/expense c/Food l/NTUC month/10 year/2006";
        var result = ExampleGenerator.generate(
                "test",
                PREFIX_NAME,
                PREFIX_AMOUNT,
                PREFIX_DATETIME,
                PREFIX_TYPE,
                PREFIX_CATEGORY,
                PREFIX_LOCATION,
                PREFIX_MONTH,
                PREFIX_YEAR
        );
        assertEquals(expected, result);
    }

    @Test
    public void generate_commandWordAndSomeKnownPrefixes() {
        var expected = "test n/Buying groceries amt/300 "
                + "type/expense c/Food l/NTUC";
        var result = ExampleGenerator.generate(
                "test",
                PREFIX_NAME,
                PREFIX_AMOUNT,
                PREFIX_TYPE,
                PREFIX_CATEGORY,
                PREFIX_LOCATION
        );
        assertEquals(expected, result);
    }

    @Test
    public void generate_unknownPrefix_defaultsToUnknownValue() {
        var tempPrefix = new Prefix("b/");
        var expected = "test b/unknown";
        var result = ExampleGenerator.generate("test", tempPrefix);
        assertEquals(expected, result);
    }
}
