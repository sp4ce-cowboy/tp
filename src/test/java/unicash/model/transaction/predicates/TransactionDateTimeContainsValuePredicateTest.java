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


public class TransactionDateTimeContainsValuePredicateTest {
    public static final String FIRST_DATE = "18-08-2001 10:10";
    public static final String SECOND_DATE = "18-08-2023 10:10";

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList(FIRST_DATE);
        List<String> secondPredicateKeywordList = Arrays.asList(FIRST_DATE, SECOND_DATE);

        TransactionDateTimeContainsValuePredicate firstPredicate =
                new TransactionDateTimeContainsValuePredicate(firstPredicateKeywordList);
        TransactionDateTimeContainsValuePredicate secondPredicate =
                new TransactionDateTimeContainsValuePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionDateTimeContainsValuePredicate firstPredicateCopy =
                new TransactionDateTimeContainsValuePredicate(firstPredicateKeywordList);
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
    public void test_dateTimeContainsKeywords_returnsTrue() {
        // One keyword
        TransactionDateTimeContainsValuePredicate predicate =
                new TransactionDateTimeContainsValuePredicate(Collections
                        .singletonList("18 Aug 2001"));
        assertTrue(predicate.test(new TransactionBuilder().withDateTime(FIRST_DATE).build()));

        // Multiple keywords
        predicate = new TransactionDateTimeContainsValuePredicate(
                Arrays.asList("18 Aug 2001", "10:10"));
        assertTrue(predicate.test(new TransactionBuilder().withDateTime(FIRST_DATE).build()));

        // Partial match
        predicate = new TransactionDateTimeContainsValuePredicate(Arrays.asList("18 Aug"));
        assertTrue(predicate.test(new TransactionBuilder().withDateTime(FIRST_DATE).build()));

        // Partial match
        predicate = new TransactionDateTimeContainsValuePredicate(Arrays.asList("Aug 2001"));
        assertTrue(predicate.test(new TransactionBuilder().withDateTime(FIRST_DATE).build()));
    }

    @Test
    public void test_dateTimeDoesNotContainKeywords_returnsFalse() {
        TransactionDateTimeContainsValuePredicate predicate =
                new TransactionDateTimeContainsValuePredicate(Collections.emptyList());

        // One matching keyword but combined entry of keywords
        predicate = new TransactionDateTimeContainsValuePredicate(
                Arrays.asList("18 Aug 2001", "18 Sep 2001"));
        assertFalse(predicate.test(new TransactionBuilder().withDateTime(FIRST_DATE).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionDateTimeContainsValuePredicate predicate =
                new TransactionDateTimeContainsValuePredicate(keywords);

        String expected = TransactionDateTimeContainsValuePredicate
                .class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
