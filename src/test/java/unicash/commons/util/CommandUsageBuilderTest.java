package unicash.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;

import org.junit.jupiter.api.Test;

public class CommandUsageBuilderTest {
    @Test
    public void setCommandWord_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandUsage.Builder().setCommandWord(null));
    }

    @Test
    public void setCommandWord_validInput_returnsSameBuilder() {
        var builder = new CommandUsage.Builder();
        var result = builder.setCommandWord("test");
        assertEquals(builder, result);
    }

    @Test
    public void setDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandUsage.Builder().setDescription(null));
    }

    @Test
    public void setDescription_validInput_returnsSameBuilder() {
        var builder = new CommandUsage.Builder();
        var result = builder.setDescription("test");
        assertEquals(builder, result);
    }

    @Test
    public void addParameterTwoParameter_nullPrefix_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandUsage.Builder().addParameter(null, "hi"));
    }

    @Test
    public void addParameterTwoParameter_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandUsage.Builder().addParameter(PREFIX_NAME, null));
    }

    @Test
    public void addParameter_nullPrefix_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandUsage.Builder()
                .addParameter(
                        null,
                        "test",
                        true,
                        true
                )
        );
    }

    @Test
    public void addParameter_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandUsage.Builder()
                .addParameter(
                        PREFIX_NAME,
                        null,
                        true,
                        true
                )
        );
    }

    @Test
    public void setExample_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandUsage.Builder().setExample(null));
    }

    @Test
    public void setExample_validInput_returnsSameBuilder() {
        var builder = new CommandUsage.Builder();
        var result = builder.setExample("test");
        assertEquals(builder, result);
    }

    @Test
    public void build_nullCommandWord_throwsNullPointerException() {
        var builder = new CommandUsage.Builder().setDescription("test").setExample("test");
        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    public void build_nullDescription_throwsNullPointerException() {
        var builder = new CommandUsage.Builder().setCommandWord("test").setExample("test");
        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    public void build_nullExample_throwsNullPointerException() {
        var builder = new CommandUsage.Builder().setCommandWord("test").setDescription("test");
        assertThrows(NullPointerException.class, builder::build);
    }
}
