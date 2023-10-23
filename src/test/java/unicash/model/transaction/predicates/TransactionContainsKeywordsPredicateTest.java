package unicash.model.transaction.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import unicash.testutil.TransactionBuilder;

public class TransactionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TransactionContainsKeywordsPredicate firstPredicate =
                new TransactionContainsKeywordsPredicate(firstPredicateKeywordList);
        TransactionContainsKeywordsPredicate secondPredicate =
                new TransactionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionContainsKeywordsPredicate firstPredicateCopy =
                new TransactionContainsKeywordsPredicate(firstPredicateKeywordList);
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
    public void test_transactionContainsKeywords_returnsTrue() {
        // One keyword
        TransactionContainsKeywordsPredicate predicate =
                new TransactionContainsKeywordsPredicate(Collections.singletonList("Food"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Food at mcdonalds").build()));

        // Multiple keywords
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("Food", "mcdonalds"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Food mcdonalds").build()));

        // Only one matching keyword
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("Chicken", "Rice"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Chicken Rice").build()));

        // Mixed-case keywords
        predicate = new TransactionContainsKeywordsPredicate(Arrays.asList("fOod", "McDonalds"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Food mcdonalds").build()));
    }

    @Test
    public void test_transactionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionContainsKeywordsPredicate predicate =
                new TransactionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withName("Food").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionContainsKeywordsPredicate predicate =
                new TransactionContainsKeywordsPredicate(keywords);

        String expected = TransactionContainsKeywordsPredicate
                .class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
