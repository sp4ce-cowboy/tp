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


public class TransactionLocationContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("home");
        List<String> secondPredicateKeywordList = Arrays.asList("home", "hostel");

        TransactionLocationContainsKeywordsPredicate firstPredicate =
                new TransactionLocationContainsKeywordsPredicate(firstPredicateKeywordList);
        TransactionLocationContainsKeywordsPredicate secondPredicate =
                new TransactionLocationContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionLocationContainsKeywordsPredicate firstPredicateCopy =
                new TransactionLocationContainsKeywordsPredicate(firstPredicateKeywordList);
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
    public void test_locationContainsKeywords_returnsTrue() {
        // One keyword
        TransactionLocationContainsKeywordsPredicate predicate =
                new TransactionLocationContainsKeywordsPredicate(Collections.singletonList("home"));
        assertTrue(predicate.test(new TransactionBuilder().withLocation("home").build()));

        // Multiple keywords
        predicate = new TransactionLocationContainsKeywordsPredicate(Arrays.asList("mall", "mcdonalds"));
        assertTrue(predicate.test(new TransactionBuilder().withLocation("mall mcdonalds").build()));

        // Only one matching keyword
        predicate = new TransactionLocationContainsKeywordsPredicate(Arrays.asList("mall", "bugis"));
        assertTrue(predicate.test(new TransactionBuilder().withLocation("mall").build()));

        // Mixed-case keywords
        predicate = new TransactionLocationContainsKeywordsPredicate(Arrays.asList("MaLl", "bUgIs"));
        assertTrue(predicate.test(new TransactionBuilder().withLocation("Bugis Mall").build()));
    }

    @Test
    public void test_locationDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionLocationContainsKeywordsPredicate predicate =
                new TransactionLocationContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withLocation("mall").build()));

        // Non-matching keyword
        predicate = new TransactionLocationContainsKeywordsPredicate(List.of("mall"));
        assertFalse(predicate.test(new TransactionBuilder().withLocation("Clementi").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionLocationContainsKeywordsPredicate predicate =
                new TransactionLocationContainsKeywordsPredicate(keywords);

        String expected = TransactionLocationContainsKeywordsPredicate
                .class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
