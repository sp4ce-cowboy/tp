package unicash.logic.parser;

import static unicash.logic.UniCashMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unicash.logic.commands.CommandTestUtil.AMOUNT_DESC_INTERN;
import static unicash.logic.commands.CommandTestUtil.AMOUNT_DESC_NUS;
import static unicash.logic.commands.CommandTestUtil.CATEGORY_DESC_ENTERTAINMENT;
import static unicash.logic.commands.CommandTestUtil.CATEGORY_DESC_NUS;
import static unicash.logic.commands.CommandTestUtil.DATETIME_DESC_INTERN;
import static unicash.logic.commands.CommandTestUtil.DATETIME_DESC_NUS;
import static unicash.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static unicash.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static unicash.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static unicash.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static unicash.logic.commands.CommandTestUtil.INVALID_TRANSACTION_NAME_DESC;
import static unicash.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static unicash.logic.commands.CommandTestUtil.LOCATION_DESC_NUS;
import static unicash.logic.commands.CommandTestUtil.LOCATION_DESC_ORCHARD;
import static unicash.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static unicash.logic.commands.CommandTestUtil.TRANSACTION_NAME_DESC_INTERN;
import static unicash.logic.commands.CommandTestUtil.TRANSACTION_NAME_DESC_NUS;
import static unicash.logic.commands.CommandTestUtil.TYPE_DESC_EXPENSE;
import static unicash.logic.commands.CommandTestUtil.TYPE_DESC_INCOME;
import static unicash.logic.commands.CommandTestUtil.VALID_AMOUNT_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_TRANSACTION_NAME_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_TYPE_EXPENSE;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;
import static unicash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static unicash.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static unicash.testutil.TypicalTransactions.NUS;

import org.junit.jupiter.api.Test;

import unicash.logic.UniCashMessages;
import unicash.logic.commands.AddTransactionCommand;
import unicash.model.category.Category;
import unicash.model.commons.Amount;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.Type;
import unicash.testutil.TransactionBuilder;


public class AddTransactionCommandParserTest {
    private final AddTransactionCommandParser parser = new AddTransactionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Transaction expectedTransaction = new TransactionBuilder(NUS).build();

        // whitespace only preamble
        assertParseSuccess(
                parser,
                PREAMBLE_WHITESPACE
                        + TRANSACTION_NAME_DESC_NUS
                        + TYPE_DESC_EXPENSE
                        + DATETIME_DESC_NUS
                        + AMOUNT_DESC_NUS
                        + CATEGORY_DESC_NUS
                        + LOCATION_DESC_NUS,
                new AddTransactionCommand(expectedTransaction));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedTransactionString = TRANSACTION_NAME_DESC_NUS + TYPE_DESC_EXPENSE
                + AMOUNT_DESC_NUS + DATETIME_DESC_NUS + LOCATION_DESC_ORCHARD + CATEGORY_DESC_ENTERTAINMENT;

        // multiple names
        assertParseFailure(parser, TRANSACTION_NAME_DESC_INTERN + validExpectedTransactionString,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple amount
        assertParseFailure(parser, AMOUNT_DESC_INTERN + validExpectedTransactionString,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_AMOUNT));

        // multiple datetime
        assertParseFailure(parser, DATETIME_DESC_INTERN + validExpectedTransactionString,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_DATETIME));

        // multiple type
        assertParseFailure(parser, TYPE_DESC_INCOME + validExpectedTransactionString,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_TYPE));

        // multiple location
        assertParseFailure(parser, LOCATION_DESC_ORCHARD + validExpectedTransactionString,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));


        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_TRANSACTION_NAME_DESC + validExpectedTransactionString,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid amount
        assertParseFailure(parser, INVALID_AMOUNT_DESC + validExpectedTransactionString,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_AMOUNT));

        // invalid datetime
        assertParseFailure(parser, INVALID_DATETIME_DESC + validExpectedTransactionString,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_DATETIME));

        // invalid type
        assertParseFailure(parser, INVALID_TYPE_DESC + validExpectedTransactionString,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_TYPE));

        // invalid category
        assertParseFailure(parser, INVALID_CATEGORY_DESC + validExpectedTransactionString,
                Category.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, INVALID_LOCATION_DESC + validExpectedTransactionString,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedTransactionString + INVALID_TRANSACTION_NAME_DESC,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid amount
        assertParseFailure(parser, validExpectedTransactionString + INVALID_AMOUNT_DESC,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_AMOUNT));

        // invalid datetime
        assertParseFailure(parser, validExpectedTransactionString + INVALID_DATETIME_DESC,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_DATETIME));

        // invalid type
        assertParseFailure(parser, validExpectedTransactionString + INVALID_TYPE_DESC,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_TYPE));

        // invalid category
        assertParseFailure(parser, validExpectedTransactionString + INVALID_CATEGORY_DESC,
                Category.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, validExpectedTransactionString + INVALID_LOCATION_DESC,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_TRANSACTION_NAME_NUS + AMOUNT_DESC_NUS + TYPE_DESC_EXPENSE,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, TRANSACTION_NAME_DESC_NUS + VALID_AMOUNT_NUS + TYPE_DESC_EXPENSE,
                expectedMessage);

        // missing type prefix
        assertParseFailure(parser, TRANSACTION_NAME_DESC_NUS + VALID_AMOUNT_NUS + VALID_TYPE_EXPENSE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TRANSACTION_NAME_DESC + AMOUNT_DESC_NUS + DATETIME_DESC_NUS
                + TYPE_DESC_EXPENSE, unicash.model.transaction.Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, TRANSACTION_NAME_DESC_NUS + INVALID_AMOUNT_DESC
                + DATETIME_DESC_NUS + TYPE_DESC_EXPENSE, Amount.MESSAGE_CONSTRAINTS);

        // invalid type
        assertParseFailure(parser, TRANSACTION_NAME_DESC_NUS + AMOUNT_DESC_NUS + DATETIME_DESC_NUS
                + INVALID_TYPE_DESC, Type.MESSAGE_CONSTRAINTS);
    }
}
