package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static unicash.logic.UniCashMessages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW;
import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unicash.testutil.TypicalTransactions.INTERN;
import static unicash.testutil.TypicalTransactions.NUS;
import static unicash.testutil.TypicalTransactions.getTypicalUniCash;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import unicash.model.Model;
import unicash.model.ModelManager;
import unicash.model.UniCash;
import unicash.model.UserPrefs;
import unicash.model.transaction.predicates.TransactionContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private final Model model = new ModelManager(new UniCash(), new UserPrefs());
    private final Model modelWithTransactions = new ModelManager(getTypicalUniCash(), new UserPrefs());
    private final Model expectedModel = new ModelManager(new UniCash(), new UserPrefs());

    @Test
    public void equals() {
        TransactionContainsKeywordsPredicate firstPredicate =
                new TransactionContainsKeywordsPredicate(Collections.singletonList("first"));
        TransactionContainsKeywordsPredicate secondPredicate =
                new TransactionContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different transaction -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);

        assertFalse(findFirstCommand.equals(3));
    }

    @Test
    public void execute_zeroKeywords_noTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 0);
        TransactionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_oneKeyword_multipleTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 3);
        TransactionContainsKeywordsPredicate predicate = preparePredicate("work");
        FindCommand command = new FindCommand(predicate);

        Model expectedModel = new ModelManager(getTypicalUniCash(), new UserPrefs());
        expectedModel.updateFilteredTransactionList(predicate);

        assertCommandSuccess(command, modelWithTransactions, expectedMessage, expectedModel);

        var filteredResult = modelWithTransactions.getFilteredTransactionList();
        assertEquals(filteredResult.get(1), NUS);
        assertEquals(filteredResult.get(2), INTERN);
    }

    @Test
    public void toStringMethod() {
        TransactionContainsKeywordsPredicate predicate = new TransactionContainsKeywordsPredicate(
                List.of("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TransactionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TransactionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
