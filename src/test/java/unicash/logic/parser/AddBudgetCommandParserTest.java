package unicash.logic.parser;

import static unicash.logic.UniCashMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static unicash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static unicash.testutil.TypicalBudgets.MONTHLY;

import org.junit.jupiter.api.Test;

import unicash.logic.UniCashMessages;
import unicash.logic.commands.AddBudgetCommand;
public class AddBudgetCommandParserTest {
    private final AddBudgetCommandParser parser = new AddBudgetCommandParser();

    @Test
    public void parse_fieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE);
        assertParseFailure(parser, MONTHLY.getAmount().toString(), expectedMessage);
        assertParseFailure(parser, MONTHLY.getInterval().toString(), expectedMessage);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_duplicates_failure() {
        String validCommand = " " + PREFIX_AMOUNT + MONTHLY.getAmount().toString() + " "
                + PREFIX_INTERVAL + MONTHLY.getInterval().toString();
        String amount = " " + PREFIX_AMOUNT + MONTHLY.getAmount().toString();
        String interval = " " + PREFIX_INTERVAL + MONTHLY.getInterval().toString();

        // multiple names
        assertParseFailure(parser, amount + validCommand,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_AMOUNT));
        assertParseFailure(parser, validCommand + interval,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_INTERVAL));

    }

    @Test
    public void parse_missingPreamble_failure() {
        String invalidCommand = PREFIX_AMOUNT + MONTHLY.getAmount().toString() + " "
                + PREFIX_INTERVAL + MONTHLY.getInterval().toString();
        assertParseFailure(parser, invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddBudgetCommand.MESSAGE_USAGE));
    }
}
