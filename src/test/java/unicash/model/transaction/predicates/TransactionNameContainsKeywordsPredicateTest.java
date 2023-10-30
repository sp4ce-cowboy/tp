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

public class TransactionNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TransactionNameContainsKeywordsPredicate firstPredicate =
                new TransactionNameContainsKeywordsPredicate(firstPredicateKeywordList);
        TransactionNameContainsKeywordsPredicate secondPredicate =
                new TransactionNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionNameContainsKeywordsPredicate firstPredicateCopy =
                new TransactionNameContainsKeywordsPredicate(firstPredicateKeywordList);
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
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        TransactionNameContainsKeywordsPredicate predicate =
                new TransactionNameContainsKeywordsPredicate(Collections.singletonList("Food"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Food at mcdonalds").build()));

        // Multiple keywords
        predicate = new TransactionNameContainsKeywordsPredicate(Arrays.asList("Food", "mcdonalds"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Food mcdonalds").build()));

        // Only one matching keyword
        predicate = new TransactionNameContainsKeywordsPredicate(Arrays.asList("Chicken", "Rice"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Chicken Rice").build()));

        // Mixed-case keywords
        predicate = new TransactionNameContainsKeywordsPredicate(Arrays.asList("fOod", "McDonalds"));
        assertTrue(predicate.test(new TransactionBuilder().withName("Food mcdonalds").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionNameContainsKeywordsPredicate predicate =
                new TransactionNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withName("Food").build()));

        // Non-matching keyword
        predicate = new TransactionNameContainsKeywordsPredicate(List.of("Food"));
        assertFalse(predicate.test(new TransactionBuilder().withName("Chicken Rice").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionNameContainsKeywordsPredicate predicate =
                new TransactionNameContainsKeywordsPredicate(keywords);

        String expected = TransactionNameContainsKeywordsPredicate
                .class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
