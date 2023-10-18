package unicash.logic.parser;

import static unicash.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import unicash.logic.commands.ListCommand;

public class ListCommandParserTest {
    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_withArgs_throwsParseException() {
        //list with text
        assertParseFailure(parser, " abc", ListCommand.MESSAGE_FAILURE);

        //list with number
        assertParseFailure(parser, " 1", ListCommand.MESSAGE_FAILURE);
    }
}