package unicash.logic.parser;

import static unicash.logic.UniCashMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unicash.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static unicash.logic.commands.CommandTestUtil.TRANSACTION_NAME_DESC_NUS;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static unicash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static unicash.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static unicash.testutil.TypicalBudgets.MONTHLY;

import org.junit.jupiter.api.Test;

import unicash.logic.UniCashMessages;
import unicash.logic.commands.SetBudgetCommand;
import unicash.model.budget.Interval;
import unicash.model.commons.Amount;

public class SetBudgetCommandParserTest {
    private final SetBudgetCommandParser parser = new SetBudgetCommandParser();

    @Test
    public void parse_noFields_throwsParseException() {
        var expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetBudgetCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expected);
        assertParseFailure(parser, "   ", expected);
    }

    @Test
    public void parse_missingAmountField_throwsParseException() {
        var expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetBudgetCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "interval/day", expected);
    }

    @Test
    public void parse_missingIntervalField_throwsParseException() {
        var expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetBudgetCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "amt/1000", expected);
    }

    @Test
    public void parse_duplicates_failure() {
        String validCommandStr = " " + PREFIX_AMOUNT + MONTHLY.getAmount().toString() + " "
                + PREFIX_INTERVAL + MONTHLY.getInterval().toString();
        String amount = " " + PREFIX_AMOUNT + MONTHLY.getAmount().toString();
        String interval = " " + PREFIX_INTERVAL + MONTHLY.getInterval().toString();

        // multiple names
        assertParseFailure(parser, amount + validCommandStr,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_AMOUNT));
        assertParseFailure(parser, validCommandStr + interval,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_INTERVAL));

    }

    @Test
    public void parse_missingPreamble_failure() {
        String invalidCommand = PREFIX_AMOUNT + MONTHLY.getAmount().toString() + " "
                + PREFIX_INTERVAL + MONTHLY.getInterval().toString();
        assertParseFailure(parser, invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetBudgetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validInput_returnsSetBudgetCommand() {
        var validCommandStr =
                " " + PREFIX_AMOUNT + MONTHLY.getAmount() + " " + PREFIX_INTERVAL + MONTHLY.getInterval().toString();
        var expectedCommand = new SetBudgetCommand(MONTHLY);
        assertParseSuccess(parser, validCommandStr, expectedCommand);
    }

    @Test
    public void parse_invalidInput_returnsSetBudgetCommand() {
        // invalid amount
        var invalidCommandStr =
                " " + INVALID_AMOUNT_DESC + " " + PREFIX_INTERVAL + MONTHLY.getInterval().toString();
        assertParseFailure(parser, invalidCommandStr, Amount.MESSAGE_CONSTRAINTS);

        // invalid interval
        invalidCommandStr =
                " " + PREFIX_AMOUNT + MONTHLY.getAmount() + " " + PREFIX_INTERVAL + "yearly";
        assertParseFailure(parser, invalidCommandStr, Interval.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_extraPrefixesAtTheStart_throwsParseException() {
        var validCommandStr =
                " " + PREFIX_AMOUNT + MONTHLY.getAmount() + " " + PREFIX_INTERVAL + MONTHLY.getInterval().toString();
        var expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetBudgetCommand.MESSAGE_USAGE);
        assertParseFailure(parser, TRANSACTION_NAME_DESC_NUS + " " + validCommandStr, expected);
    }

    @Test
    public void parse_extraPrefixesAtTheEnd_throwsParseException() {
        // prefix order is amount, interval
        var validCommandStr =
                " " + PREFIX_AMOUNT + MONTHLY.getAmount() + " " + PREFIX_INTERVAL + MONTHLY.getInterval().toString();
        assertParseFailure(parser, validCommandStr + " " + TRANSACTION_NAME_DESC_NUS, Interval.MESSAGE_CONSTRAINTS);

        // prefix order is interval, amount
        validCommandStr =
                " " + PREFIX_INTERVAL + MONTHLY.getInterval().toString() + " " + PREFIX_AMOUNT + MONTHLY.getAmount();
        assertParseFailure(parser, validCommandStr + " " + TRANSACTION_NAME_DESC_NUS, Amount.MESSAGE_CONSTRAINTS);
    }
}
