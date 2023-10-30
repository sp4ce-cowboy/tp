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

public class TransactionAmountContainsValuePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("1.00");
        List<String> secondPredicateKeywordList = Arrays.asList("1.00", "1.00");

        TransactionAmountContainsValuePredicate firstPredicate =
                new TransactionAmountContainsValuePredicate(firstPredicateKeywordList);
        TransactionAmountContainsValuePredicate secondPredicate =
                new TransactionAmountContainsValuePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionAmountContainsValuePredicate firstPredicateCopy =
                new TransactionAmountContainsValuePredicate(firstPredicateKeywordList);
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
    public void test_amountContainsKeywords_returnsTrue() {
        // One keyword
        TransactionAmountContainsValuePredicate predicate =
                new TransactionAmountContainsValuePredicate(Collections.singletonList("1.10"));
        assertTrue(predicate.test(new TransactionBuilder().withAmount(1.10).build()));

        // Multiple keywords
        predicate = new TransactionAmountContainsValuePredicate(Arrays.asList("1.00", "2.00"));
        assertTrue(predicate.test(new TransactionBuilder().withAmount(1.00).build()));

        // Only one matching keyword
        predicate = new TransactionAmountContainsValuePredicate(Arrays.asList("1.00", "2.00"));
        assertTrue(predicate.test(new TransactionBuilder().withAmount(2.00).build()));


    }

    @Test
    public void test_amountDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionAmountContainsValuePredicate predicate =
                new TransactionAmountContainsValuePredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withAmount(2.00).build()));

        // Partial match only
        predicate = new TransactionAmountContainsValuePredicate(Arrays.asList("1.00"));
        assertFalse(predicate.test(new TransactionBuilder().withAmount(21.00).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionAmountContainsValuePredicate predicate =
                new TransactionAmountContainsValuePredicate(keywords);

        String expected = TransactionAmountContainsValuePredicate
                .class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
