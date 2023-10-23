package unicash.model.transaction.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import unicash.commons.enums.TransactionType;
import unicash.testutil.TransactionBuilder;

public class TransactionTypeContainsKeywordsPredicateTest {
    public static final String EXPENSE = String.valueOf(TransactionType.EXPENSE);
    public static final String INCOME = String.valueOf(TransactionType.EXPENSE);

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList(EXPENSE);
        List<String> secondPredicateKeywordList = Arrays.asList(EXPENSE, INCOME);

        TransactionTypeContainsKeywordsPredicate firstPredicate =
                new TransactionTypeContainsKeywordsPredicate(firstPredicateKeywordList);
        TransactionTypeContainsKeywordsPredicate secondPredicate =
                new TransactionTypeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionTypeContainsKeywordsPredicate firstPredicateCopy =
                new TransactionTypeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        assertFalse(firstPredicate.equals(1));

        // different person -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_typeContainsKeywords_returnsTrue() {
        // One keyword
        TransactionTypeContainsKeywordsPredicate predicate =
                new TransactionTypeContainsKeywordsPredicate(Collections.singletonList(EXPENSE));
        assertTrue(predicate.test(new TransactionBuilder().withType(EXPENSE.toLowerCase()).build()));

        // Multiple keywords
        predicate = new TransactionTypeContainsKeywordsPredicate(Arrays.asList(EXPENSE, INCOME));
        assertTrue(predicate.test(new TransactionBuilder().withType(EXPENSE.toLowerCase()).build()));

        // Only one keyword match
        predicate = new TransactionTypeContainsKeywordsPredicate(Arrays.asList("expense", "income"));
        assertTrue(predicate.test(new TransactionBuilder().withType(EXPENSE.toLowerCase()).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionTypeContainsKeywordsPredicate predicate =
                new TransactionTypeContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withType(EXPENSE.toLowerCase()).build()));

    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionTypeContainsKeywordsPredicate predicate =
                new TransactionTypeContainsKeywordsPredicate(keywords);

        String expected = TransactionTypeContainsKeywordsPredicate
                .class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
