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

public class TransactionTypeContainsValuePredicateTest {
    public static final String EXPENSE = String.valueOf(TransactionType.EXPENSE);
    public static final String INCOME = String.valueOf(TransactionType.EXPENSE);

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList(EXPENSE);
        List<String> secondPredicateKeywordList = Arrays.asList(EXPENSE, INCOME);

        TransactionTypeContainsValuePredicate firstPredicate =
                new TransactionTypeContainsValuePredicate(firstPredicateKeywordList);
        TransactionTypeContainsValuePredicate secondPredicate =
                new TransactionTypeContainsValuePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionTypeContainsValuePredicate firstPredicateCopy =
                new TransactionTypeContainsValuePredicate(firstPredicateKeywordList);
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
        TransactionTypeContainsValuePredicate predicate =
                new TransactionTypeContainsValuePredicate(Collections.singletonList(EXPENSE));
        assertTrue(predicate.test(new TransactionBuilder().withType(EXPENSE.toLowerCase()).build()));

        // Multiple keywords
        predicate = new TransactionTypeContainsValuePredicate(Arrays.asList(EXPENSE, INCOME));
        assertTrue(predicate.test(new TransactionBuilder().withType(EXPENSE.toLowerCase()).build()));

        // Only one keyword match
        predicate = new TransactionTypeContainsValuePredicate(Arrays.asList("expense", "income"));
        assertTrue(predicate.test(new TransactionBuilder().withType(EXPENSE.toLowerCase()).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionTypeContainsValuePredicate predicate =
                new TransactionTypeContainsValuePredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withType(EXPENSE.toLowerCase()).build()));

    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionTypeContainsValuePredicate predicate =
                new TransactionTypeContainsValuePredicate(keywords);

        String expected = TransactionTypeContainsValuePredicate
                .class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
