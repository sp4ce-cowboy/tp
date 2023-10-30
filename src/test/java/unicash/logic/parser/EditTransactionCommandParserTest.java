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
import static unicash.logic.commands.CommandTestUtil.TRANSACTION_NAME_DESC_NUS;
import static unicash.logic.commands.CommandTestUtil.TYPE_DESC_EXPENSE;
import static unicash.logic.commands.CommandTestUtil.TYPE_DESC_INCOME;
import static unicash.logic.commands.CommandTestUtil.VALID_AMOUNT_INTERN;
import static unicash.logic.commands.CommandTestUtil.VALID_AMOUNT_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_CATEGORY_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_DATETIME_INTERN;
import static unicash.logic.commands.CommandTestUtil.VALID_DATETIME_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_LOCATION_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_TRANSACTION_NAME_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_TYPE_INCOME;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;
import static unicash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static unicash.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static unicash.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static unicash.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;
import static unicash.testutil.TypicalIndexes.INDEX_THIRD_TRANSACTION;

import org.junit.jupiter.api.Test;

import unicash.commons.core.index.Index;
import unicash.logic.UniCashMessages;
import unicash.logic.commands.EditTransactionCommand;
import unicash.model.category.Category;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Type;
import unicash.testutil.EditTransactionDescriptorBuilder;


public class EditTransactionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTransactionCommand.MESSAGE_USAGE);
    private static final String CATEGORY_EMPTY = " " + PREFIX_CATEGORY;

    private final EditTransactionCommandParser parser = new EditTransactionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TRANSACTION_NAME_NUS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTransactionCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(
                parser,
                "-5" + TRANSACTION_NAME_DESC_NUS,
                MESSAGE_INVALID_FORMAT
        );

        // zero index
        assertParseFailure(
                parser,
                "0" + TRANSACTION_NAME_DESC_NUS,
                MESSAGE_INVALID_FORMAT
        );

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(
                parser,
                "1" + INVALID_TRANSACTION_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS
        ); // invalid name
        assertParseFailure(
                parser,
                "1" + INVALID_AMOUNT_DESC,
                Amount.MESSAGE_CONSTRAINTS
        ); // invalid amount
        assertParseFailure(
                parser,
                "1" + INVALID_CATEGORY_DESC,
                Category.MESSAGE_CONSTRAINTS
        ); // invalid category
        assertParseFailure(
                parser,
                "1" + INVALID_DATETIME_DESC,
                DateTime.MESSAGE_CONSTRAINTS
        ); // invalid datetime
        assertParseFailure(
                parser,
                "1" + INVALID_LOCATION_DESC,
                Location.MESSAGE_CONSTRAINTS
        ); // invalid location
        assertParseFailure(
                parser,
                "1" + INVALID_TYPE_DESC,
                Type.MESSAGE_CONSTRAINTS
        ); // invalid type

        // invalid amount followed by valid category
        assertParseFailure(
                parser,
                "1" + INVALID_AMOUNT_DESC + CATEGORY_DESC_ENTERTAINMENT,
                Amount.MESSAGE_CONSTRAINTS
        );

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(
                parser,
                "1" + INVALID_TRANSACTION_NAME_DESC
                        + INVALID_CATEGORY_DESC
                        + VALID_AMOUNT_INTERN
                        + VALID_DATETIME_INTERN,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TRANSACTION;
        String userInput = targetIndex.getOneBased()
                + AMOUNT_DESC_NUS
                + LOCATION_DESC_NUS
                + TYPE_DESC_INCOME
                + CATEGORY_DESC_NUS
                + DATETIME_DESC_NUS
                + TRANSACTION_NAME_DESC_NUS;

        var descriptor = new EditTransactionDescriptorBuilder()
                .withName(VALID_TRANSACTION_NAME_NUS)
                .withAmount(VALID_AMOUNT_NUS)
                .withCategories(VALID_CATEGORY_NUS)
                .withLocation(VALID_LOCATION_NUS)
                .withDateTime(VALID_DATETIME_NUS)
                .withType(VALID_TYPE_INCOME)
                .build();
        var expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedTransactionCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC_INTERN + CATEGORY_DESC_NUS;

        var descriptor = new EditTransactionDescriptorBuilder()
                .withAmount(VALID_AMOUNT_INTERN)
                .withCategories(VALID_CATEGORY_NUS)
                .build();
        var expectedTransactionCommand = new EditTransactionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedTransactionCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TRANSACTION;
        String userInput = targetIndex.getOneBased() + TRANSACTION_NAME_DESC_NUS;
        var descriptor = new EditTransactionDescriptorBuilder()
                .withName(VALID_TRANSACTION_NAME_NUS)
                .build();
        var expectedCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = targetIndex.getOneBased() + AMOUNT_DESC_NUS;
        descriptor = new EditTransactionDescriptorBuilder().withAmount(VALID_AMOUNT_NUS).build();
        expectedCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // category
        userInput = targetIndex.getOneBased() + CATEGORY_DESC_NUS;
        descriptor = new EditTransactionDescriptorBuilder().withCategories(VALID_CATEGORY_NUS).build();
        expectedCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // datetime
        userInput = targetIndex.getOneBased() + DATETIME_DESC_NUS;
        descriptor = new EditTransactionDescriptorBuilder().withDateTime(VALID_DATETIME_NUS).build();
        expectedCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + LOCATION_DESC_NUS;
        descriptor = new EditTransactionDescriptorBuilder().withLocation(VALID_LOCATION_NUS).build();
        expectedCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // type
        userInput = targetIndex.getOneBased() + TYPE_DESC_INCOME;
        descriptor = new EditTransactionDescriptorBuilder().withType(VALID_TYPE_INCOME).build();
        expectedCommand = new EditTransactionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddTransactionCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_TRANSACTION;
        String userInput = targetIndex.getOneBased() + LOCATION_DESC_NUS + INVALID_LOCATION_DESC;

        assertParseFailure(parser, userInput, UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + INVALID_LOCATION_DESC + LOCATION_DESC_NUS;

        assertParseFailure(parser, userInput, UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased()
                + LOCATION_DESC_NUS
                + TYPE_DESC_INCOME
                + AMOUNT_DESC_NUS
                + CATEGORY_DESC_NUS
                + DATETIME_DESC_NUS
                + LOCATION_DESC_NUS
                + TYPE_DESC_INCOME
                + AMOUNT_DESC_NUS
                + CATEGORY_DESC_NUS
                + DATETIME_DESC_NUS
                + LOCATION_DESC_ORCHARD
                + TYPE_DESC_EXPENSE
                + AMOUNT_DESC_INTERN
                + CATEGORY_DESC_ENTERTAINMENT
                + DATETIME_DESC_INTERN;

        assertParseFailure(
                parser,
                userInput,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_LOCATION,
                        PREFIX_TYPE,
                        PREFIX_AMOUNT,
                        PREFIX_DATETIME
                )
        );

        // multiple invalid values
        userInput = targetIndex.getOneBased()
                + INVALID_LOCATION_DESC
                + INVALID_CATEGORY_DESC
                + INVALID_DATETIME_DESC
                + INVALID_LOCATION_DESC
                + INVALID_CATEGORY_DESC
                + INVALID_DATETIME_DESC;

        assertParseFailure(
                parser,
                userInput,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_LOCATION,
                        PREFIX_DATETIME
                )
        );
    }

    @Test
    public void parse_resetCategories_success() {
        Index targetIndex = INDEX_THIRD_TRANSACTION;
        String userInput = targetIndex.getOneBased() + CATEGORY_EMPTY;

        var descriptor = new EditTransactionDescriptorBuilder().withCategories().build();
        var expectedCommand = new EditTransactionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
