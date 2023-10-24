package unicash.model.transaction.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import unicash.testutil.TransactionBuilder;


public class TransactionDateTimeContainsKeywordsPredicateTest {
    public static final String FIRST_DATE = "18-08-2001 10:10";
    public static final String SECOND_DATE = "18-08-2023 10:10";

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList(FIRST_DATE);
        List<String> secondPredicateKeywordList = Arrays.asList(FIRST_DATE, SECOND_DATE);

        TransactionDateTimeContainsKeywordsPredicate firstPredicate =
                new TransactionDateTimeContainsKeywordsPredicate(firstPredicateKeywordList);
        TransactionDateTimeContainsKeywordsPredicate secondPredicate =
                new TransactionDateTimeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionDateTimeContainsKeywordsPredicate firstPredicateCopy =
                new TransactionDateTimeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        assertFalse(firstPredicate.equals(1));

        // different person -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    /* TODO: FIX THIS WITH REGARD TO NEW DATETIME FORMAT
    @Test
    public void test_dateTimeContainsKeywords_returnsTrue() {
        // One keyword
        TransactionDateTimeContainsKeywordsPredicate predicate =
                new TransactionDateTimeContainsKeywordsPredicate(Collections
                        .singletonList("18-08-2001"));
        assertTrue(predicate.test(new TransactionBuilder().withDateTime(FIRST_DATE).build()));

        // Only one matching keyword
        predicate = new TransactionDateTimeContainsKeywordsPredicate(
                Arrays.asList("18-08-2001", "18-09-2001"));
        assertTrue(predicate.test(new TransactionBuilder().withDateTime(FIRST_DATE).build()));

        // Multiple keywords
        predicate = new TransactionDateTimeContainsKeywordsPredicate(
                Arrays.asList("18-08-2001", "10:10"));
        assertTrue(predicate.test(new TransactionBuilder().withDateTime(FIRST_DATE).build()));


    }*/

    @Test
    public void test_dateTimeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionDateTimeContainsKeywordsPredicate predicate =
                new TransactionDateTimeContainsKeywordsPredicate(Collections.emptyList());

        // Partial match only
        predicate = new TransactionDateTimeContainsKeywordsPredicate(Arrays.asList("18-08"));
        assertFalse(predicate.test(new TransactionBuilder().withDateTime(FIRST_DATE).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionDateTimeContainsKeywordsPredicate predicate =
                new TransactionDateTimeContainsKeywordsPredicate(keywords);

        String expected = TransactionDateTimeContainsKeywordsPredicate
                .class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}