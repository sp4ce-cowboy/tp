package unicash.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;

import org.junit.jupiter.api.Test;

public class CommandUsageTest {
    @Test
    public void toString_noArgumentNoParameter() {
        var expected = "test: test command\n\nExample: test\n\n";
        var result = new CommandUsage.Builder()
                .setCommandWord("test")
                .setDescription("test command")
                .setExample("test")
                .build()
                .toString();
        assertEquals(expected, result);
    }

    @Test
    public void toString_withArgumentNoParameters() {
        var expected = "test: test command\n\nArgument: 1\n\nExample: test 1\n\n";
        var result = new CommandUsage.Builder()
                .setCommandWord("test")
                .setDescription("test command")
                .setExample(ExampleGenerator.generate("test", "1"))
                .setArgument("1")
                .build()
                .toString();
        assertEquals(expected, result);
    }

    @Test
    public void toString_noArgumentWithPlainParameters() {
        var expected = "test: test command\n\n"
                + "Parameters: n/Name type/Type\n\n"
                + "Example: test n/Buying groceries type/expense\n\n";
        var result = new CommandUsage.Builder()
                .setCommandWord("test")
                .setDescription("test command")
                .setExample(ExampleGenerator.generate("test", PREFIX_NAME, PREFIX_TYPE))
                .addParameter(PREFIX_NAME, "Name")
                .addParameter(PREFIX_TYPE, "Type")
                .build()
                .toString();
        assertEquals(expected, result);
    }

    @Test
    public void toString_noArgumentWithOptionalParameters() {
        var expected = "test: test command\n\n"
                + "Parameters: n/Name [amt/Amount]\n\n"
                + "Example: test n/Buying groceries amt/300\n\n";
        var result = new CommandUsage.Builder()
                .setCommandWord("test")
                .setDescription("test command")
                .setExample(ExampleGenerator.generate("test", PREFIX_NAME, PREFIX_AMOUNT))
                .addParameter(PREFIX_NAME, "Name")
                .addParameter(PREFIX_AMOUNT, "Amount", true, false)
                .build()
                .toString();
        assertEquals(expected, result);
    }

    @Test
    public void toString_noArgumentWithVariadicParameters() {
        var expected = "test: test command\n\n"
                + "Parameters: n/Name amt/Amount...\n\n"
                + "Example: test n/Buying groceries amt/300\n\n";
        var result = new CommandUsage.Builder()
                .setCommandWord("test")
                .setDescription("test command")
                .setExample(ExampleGenerator.generate("test", PREFIX_NAME, PREFIX_AMOUNT))
                .addParameter(PREFIX_NAME, "Name")
                .addParameter(PREFIX_AMOUNT, "Amount", false, true)
                .build()
                .toString();
        assertEquals(expected, result);
    }

    @Test
    public void toString_noArgumentWithOptionalVariadicParameters() {
        var expected = "test: test command\n\n"
                + "Parameters: n/Name [amt/Amount]...\n\n"
                + "Example: test n/Buying groceries amt/300\n\n";
        var result = new CommandUsage.Builder()
                .setCommandWord("test")
                .setDescription("test command")
                .setExample(ExampleGenerator.generate("test", PREFIX_NAME, PREFIX_AMOUNT))
                .addParameter(PREFIX_NAME, "Name")
                .addParameter(PREFIX_AMOUNT, "Amount", true, true)
                .build()
                .toString();
        assertEquals(expected, result);
    }

    @Test
    public void toString_withArgumentWithOptionalVariadicParameters() {
        var expected = "test: test command\n\n"
                + "Argument: Index\n\n"
                + "Parameters: n/Name [amt/Amount]...\n\n"
                + "Example: test n/Buying groceries amt/300\n\n";
        var result = new CommandUsage.Builder()
                .setCommandWord("test")
                .setDescription("test command")
                .setExample(ExampleGenerator.generate("test", PREFIX_NAME, PREFIX_AMOUNT))
                .addParameter(PREFIX_NAME, "Name")
                .addParameter(PREFIX_AMOUNT, "Amount", true, true)
                .setArgument("Index")
                .build()
                .toString();
        assertEquals(expected, result);
    }
}
