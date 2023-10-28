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

public class TransactionContainsAnyKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TransactionContainsAnyKeywordsPredicate firstPredicate =
                new TransactionContainsAnyKeywordsPredicate(firstPredicateKeywordList);
        TransactionContainsAnyKeywordsPredicate secondPredicate =
                new TransactionContainsAnyKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionContainsAnyKeywordsPredicate firstPredicateCopy =
                new TransactionContainsAnyKeywordsPredicate(firstPredicateKeywordList);
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
        TransactionContainsAnyKeywordsPredicate predicate =
                new TransactionContainsAnyKeywordsPredicate(Collections.singletonList("Food"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Food at mcdonalds").build()));

        // Multiple keywords
        predicate = new TransactionContainsAnyKeywordsPredicate(Arrays.asList("Food", "mcdonalds"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Food mcdonalds").build()));

        // Only one matching keyword
        predicate = new TransactionContainsAnyKeywordsPredicate(Arrays.asList("Chicken", "Rice"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Chicken Rice").build()));

        // Mixed-case keywords
        predicate = new TransactionContainsAnyKeywordsPredicate(Arrays.asList("fOod", "McDonalds"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Food mcdonalds").build()));
    }

    @Test
    public void test_transactionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionContainsAnyKeywordsPredicate predicate =
                new TransactionContainsAnyKeywordsPredicate(Collections.singletonList("chicken"));
        assertFalse(predicate.test(new TransactionBuilder().withName("test").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionContainsAnyKeywordsPredicate predicate =
                new TransactionContainsAnyKeywordsPredicate(keywords);

        String expected = TransactionContainsAnyKeywordsPredicate
                .class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
