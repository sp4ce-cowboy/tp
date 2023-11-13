package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalTransactions.INTERN;
import static unicash.testutil.TypicalTransactions.NUS;
import static unicash.testutil.TypicalTransactions.getTypicalUniCash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import unicash.commons.enums.CommandType;
import unicash.model.Model;
import unicash.model.ModelManager;
import unicash.model.UniCash;
import unicash.model.UserPrefs;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.predicates.TransactionContainsAllKeywordsPredicate;
import unicash.model.transaction.predicates.TransactionNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private final Model model = new ModelManager(new UniCash(), new UserPrefs());
    private final Model modelWithTransactions = new ModelManager(getTypicalUniCash(), new UserPrefs());
    private final Model expectedModel = new ModelManager(new UniCash(), new UserPrefs());

    @Test
    public void equals() {
        TransactionContainsAllKeywordsPredicate firstPredicate =
                new TransactionContainsAllKeywordsPredicate();
        TransactionContainsAllKeywordsPredicate secondPredicate =
                new TransactionContainsAllKeywordsPredicate();

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

        // same predicate -> returns true
        assertEquals(findFirstCommand, findSecondCommand);

        assertFalse(findFirstCommand.equals(3));
    }

    @Test
    public void execute_zeroKeywords_noTransactionsFound() {
        String expectedMessage = String.format(CommandType.FIND.getMessageSuccess(), 0);
        TransactionContainsAllKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredTransactionList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_oneKeyword_multipleTransactionsFound() {
        String expectedMessage = String.format(CommandType.FIND.getMessageSuccess(), 3);
        TransactionContainsAllKeywordsPredicate predicate = preparePredicate("work");
        FindCommand command = new FindCommand(predicate);

        Model expectedModel = new ModelManager(getTypicalUniCash(), new UserPrefs());
        expectedModel.updateFilteredTransactionList(predicate);

        assertCommandSuccess(command, modelWithTransactions, expectedMessage, expectedModel);

        var filteredResult = modelWithTransactions.getFilteredTransactionList();
        assertEquals(filteredResult.get(1), NUS);
        assertEquals(filteredResult.get(2), INTERN);
    }

    @Test
    public void execute_predicateNull_assertionFailure() {
        assertThrows(NullPointerException.class, () -> new FindCommand(null));
    }

    @Test
    public void execute_predicateNotNull_assertion() {
        assertDoesNotThrow(() -> new FindCommand(preparePredicate()).execute(model));
    }

    @Test
    public void toStringMethod() {
        TransactionContainsAllKeywordsPredicate predicate =
                new TransactionContainsAllKeywordsPredicate();

        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Generates a {@code TransactionContainsAllKeywordsPredicate} object
     */
    private TransactionContainsAllKeywordsPredicate preparePredicate() {

        List<String> parsedKeywords = Arrays.asList("foo", "bar", "baz");
        List<Predicate<Transaction>> predicateList = new ArrayList<>();

        parsedKeywords.stream().forEach(keyword -> {
            TransactionNameContainsKeywordsPredicate namePredicate =
                    new TransactionNameContainsKeywordsPredicate(List.of(keyword));
            predicateList.add(namePredicate);
        });

        return new TransactionContainsAllKeywordsPredicate(predicateList);
    }

    /**
     * Generates a {@code TransactionContainsAllKeywordsPredicate} object with an input string
     */
    private TransactionContainsAllKeywordsPredicate preparePredicate(String string) {

        List<String> parsedKeywords = Arrays.asList(string);
        List<Predicate<Transaction>> predicateList = new ArrayList<>();

        parsedKeywords.stream().forEach(keyword -> {
            TransactionNameContainsKeywordsPredicate namePredicate =
                    new TransactionNameContainsKeywordsPredicate(List.of(keyword));
            predicateList.add(namePredicate);
        });

        return new TransactionContainsAllKeywordsPredicate(predicateList);
    }
}
