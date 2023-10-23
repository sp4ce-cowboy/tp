package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;
import static unicash.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unicash.commons.core.index.Index;
import unicash.logic.commands.exceptions.CommandException;
import unicash.model.Model;
import unicash.model.UniCash;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.predicates.TransactionContainsKeywordsPredicate;
import unicash.testutil.EditTransactionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TRANSACTION_NAME_NUS = "Work at NUS";
    public static final String VALID_TRANSACTION_NAME_INTERN = "Internship work";
    public static final String VALID_TRANSACTION_NAME_SHOPPING = "Shopping";
    public static final String VALID_TYPE_EXPENSE = "expense";
    public static final String VALID_TYPE_INCOME = "income";
    public static final Double VALID_AMOUNT_NUS = 888.8;
    public static final Double VALID_AMOUNT_INTERN = 8.8;
    public static final Double VALID_AMOUNT_SHOPPING = 899.8;
    public static final String VALID_DATETIME_NUS = "12-12-2021 12:12";
    public static final String VALID_DATETIME_INTERN = "08-08-2008 08:08";
    public static final String VALID_DATETIME_SHOPPING = "01-02-2008 11:08";
    public static final String VALID_CATEGORY_ENTERTAINMENT = "entertainment";
    public static final String VALID_CATEGORY_EDUCATION = "education";
    public static final String VALID_CATEGORY_NUS = "TA";
    public static final String VALID_LOCATION_ORCHARD = "orchard";
    public static final String VALID_LOCATION_NUS = "NUS";

    public static final String TRANSACTION_NAME_DESC_NUS = " " + PREFIX_NAME + VALID_TRANSACTION_NAME_NUS;
    public static final String TRANSACTION_NAME_DESC_INTERN = " " + PREFIX_NAME + VALID_TRANSACTION_NAME_INTERN;
    public static final String AMOUNT_DESC_NUS = " " + PREFIX_AMOUNT + VALID_AMOUNT_NUS;
    public static final String AMOUNT_DESC_INTERN = " " + PREFIX_AMOUNT + VALID_AMOUNT_INTERN;
    public static final String DATETIME_DESC_NUS = " " + PREFIX_DATETIME + VALID_DATETIME_NUS;
    public static final String DATETIME_DESC_INTERN = " " + PREFIX_DATETIME + VALID_DATETIME_INTERN;
    public static final String TYPE_DESC_EXPENSE = " " + PREFIX_TYPE + VALID_TYPE_EXPENSE;
    public static final String TYPE_DESC_INCOME = " " + PREFIX_TYPE + VALID_TYPE_INCOME;
    public static final String CATEGORY_DESC_ENTERTAINMENT = " " + PREFIX_CATEGORY + VALID_CATEGORY_ENTERTAINMENT;
    public static final String CATEGORY_DESC_NUS = " " + PREFIX_CATEGORY + VALID_CATEGORY_NUS;
    public static final String LOCATION_DESC_ORCHARD = " " + PREFIX_LOCATION + VALID_LOCATION_ORCHARD;
    public static final String LOCATION_DESC_NUS = " " + PREFIX_LOCATION + VALID_LOCATION_NUS;

    public static final String INVALID_TRANSACTION_NAME_DESC = " " + PREFIX_NAME + "NUS&"; // '&' not allowed in names
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "-3.0"; // negative amounts not allowed
    public static final String INVALID_DATETIME_DESC = " " + PREFIX_DATETIME + "19/13/2001 19:30"; // invalid date
    public static final String INVALID_TYPE_DESC = " " + PREFIX_TYPE + "afaf"; // invalid type
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + "afraf*&"; // invalid type
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + "a214faf*&"; // invalid type

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditTransactionCommand.EditTransactionDescriptor DESC_NUS;
    public static final EditTransactionCommand.EditTransactionDescriptor DESC_SHOPPING;

    static {
        DESC_NUS = new EditTransactionDescriptorBuilder()
                .withName(VALID_TRANSACTION_NAME_NUS)
                .withAmount(VALID_AMOUNT_NUS)
                .withType(VALID_TYPE_INCOME)
                .withCategories(VALID_CATEGORY_NUS)
                .withDateTime(VALID_DATETIME_NUS)
                .withLocation(VALID_LOCATION_NUS)
                .build();
        DESC_SHOPPING = new EditTransactionDescriptorBuilder()
                .withName(VALID_TRANSACTION_NAME_SHOPPING)
                .withCategories(VALID_CATEGORY_ENTERTAINMENT)
                .withAmount(VALID_AMOUNT_SHOPPING)
                .withType(VALID_TYPE_EXPENSE)
                .withDateTime(VALID_DATETIME_SHOPPING)
                .withLocation(VALID_LOCATION_ORCHARD)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(
            Command command,
            Model actualModel,
            CommandResult expectedCommandResult,
            Model expectedModel
    ) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        UniCash expectedUniCash = new UniCash(actualModel.getUniCash());
        List<Transaction> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTransactionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedUniCash, actualModel.getUniCash());
        assertEquals(expectedFilteredList, actualModel.getFilteredTransactionList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the transaction at the
     * given {@code targetIndex} in the {@code model}'s database.
     */
    public static void showTransactionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTransactionList().size());

        Transaction transaction = model.getFilteredTransactionList().get(targetIndex.getZeroBased());
        final String[] splitName = transaction.getName().fullName.split("\\s+");
        model.updateFilteredTransactionList(
                new TransactionContainsKeywordsPredicate(Collections.singletonList(splitName[0])));

        assertEquals(1, model.getFilteredTransactionList().size());
    }

}
