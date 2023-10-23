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

public class TransactionCategoryContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("food");
        List<String> secondPredicateKeywordList = Arrays.asList("household", "food");

        TransactionCategoryContainsKeywordsPredicate firstPredicate =
                new TransactionCategoryContainsKeywordsPredicate(firstPredicateKeywordList);
        TransactionCategoryContainsKeywordsPredicate secondPredicate =
                new TransactionCategoryContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        TransactionCategoryContainsKeywordsPredicate firstPredicateCopy =
                new TransactionCategoryContainsKeywordsPredicate(firstPredicateKeywordList);
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
    public void test_categoriesContainKeywords_returnsTrue() {
        // One keyword
        TransactionCategoryContainsKeywordsPredicate predicate =
                new TransactionCategoryContainsKeywordsPredicate(Collections.singletonList("food"));
        assertTrue(predicate.test(new TransactionBuilder().withCategories("food").build()));

        // Multiple keywords
        predicate = new TransactionCategoryContainsKeywordsPredicate(Arrays.asList("food", "household"));
        assertTrue(predicate.test(new TransactionBuilder().withCategories("food", "household").build()));

        // Only one matching keyword
        predicate = new TransactionCategoryContainsKeywordsPredicate(Arrays.asList("food", "household"));
        assertTrue(predicate.test(new TransactionBuilder().withCategories("food").build()));

        // Partial match, returns true
        predicate = new TransactionCategoryContainsKeywordsPredicate(Arrays.asList("food"));
        assertTrue(predicate.test(new TransactionBuilder().withCategories("dogfood").build()));


    }

    @Test
    public void test_categoriesDoNotContainKeywords_returnsFalse() {
        // Zero keywords
        TransactionCategoryContainsKeywordsPredicate predicate =
                new TransactionCategoryContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new TransactionBuilder().withCategories("food").build()));

        // Keyword exceeds query length
        predicate = new TransactionCategoryContainsKeywordsPredicate(Arrays.asList("dogfood"));
        assertFalse(predicate.test(new TransactionBuilder().withCategories("food").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        TransactionCategoryContainsKeywordsPredicate predicate =
                new TransactionCategoryContainsKeywordsPredicate(keywords);

        String expected = TransactionCategoryContainsKeywordsPredicate
                .class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
