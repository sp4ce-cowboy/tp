package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.logic.UniCashMessages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
import static unicash.logic.commands.CommandTestUtil.assertCommandFailure;
import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unicash.logic.commands.CommandTestUtil.showTransactionAtIndex;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static unicash.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;
import static unicash.testutil.TypicalTransactions.getTypicalUniCash;

import org.junit.jupiter.api.Test;

import unicash.commons.core.index.Index;
import unicash.logic.UniCashMessages;
import unicash.model.Model;
import unicash.model.ModelManager;
import unicash.model.UserPrefs;
import unicash.model.transaction.Transaction;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code GetCommand}.
 */
public class GetCommandTest {

    private final Model model = new ModelManager(getTypicalUniCash(), new UserPrefs());

    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTransactionCommand(null));
    }

    @Test
    public void equals() {
        GetCommand getFirstCommand = new GetCommand(INDEX_FIRST_TRANSACTION);
        GetCommand getSecondCommand = new GetCommand(INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertEquals(getFirstCommand, getFirstCommand);

        // different types -> returns false
        assertFalse(getFirstCommand.equals(1));

        // null -> returns false
        assertNotEquals(null, getFirstCommand);

        // different transaction -> returns false
        assertNotEquals(getFirstCommand, getSecondCommand);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        GetCommand getCommand = new GetCommand(targetIndex);
        String expected = GetCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, getCommand.toString());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Transaction transactionToGet = model.getFilteredTransactionList().get(
                INDEX_FIRST_TRANSACTION.getZeroBased());
        GetCommand getCommand = new GetCommand(INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(GetCommand.MESSAGE_GET_TRANSACTION_SUCCESS,
                INDEX_FIRST_TRANSACTION.getOneBased(),
                UniCashMessages.formatTransaction(transactionToGet));

        assertCommandSuccess(getCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsParseException() {
        showTransactionAtIndex(model, INDEX_FIRST_TRANSACTION);

        Index outOfBoundIndex = INDEX_SECOND_TRANSACTION;
        // ensures that outOfBoundIndex is still in bounds of transactions list
        assertTrue(outOfBoundIndex.getZeroBased() < getTypicalUniCash().getTransactionList().size());

        GetCommand getCommand = new GetCommand(outOfBoundIndex);

        assertCommandFailure(getCommand, model, MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

}

