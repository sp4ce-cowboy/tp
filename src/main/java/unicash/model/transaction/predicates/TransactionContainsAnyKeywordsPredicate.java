package unicash.model.transaction.predicates;

import java.util.List;
import java.util.function.Predicate;

import unicash.commons.util.ToStringBuilder;
import unicash.model.transaction.Transaction;

/**
 * Tests that any of a {@code Transactions}'s properties matches any of the keywords given.
 * Matching in this context either means a partial match of the keyword with the query, or
 * a full match of the keyword, repeated for each keyword present in the list of keywords.
 *
 * </p> The matching depends on the property of the Transaction being matched, and the
 * most appropriate matching is specified within the associated property predicate. This class
 * simulates a composed predicate that represents a short-circuiting logical OR of all property
 * predicates.
 */
public class TransactionContainsAnyKeywordsPredicate
        implements Predicate<Transaction> {
    private final List<String> keywords;

    public TransactionContainsAnyKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        List<Predicate<Transaction>> allPredicates = List.of(
                new TransactionAmountContainsValuePredicate(keywords),
                new TransactionCategoryContainsKeywordsPredicate(keywords),
                new TransactionDateTimeContainsValuePredicate(keywords),
                new TransactionLocationContainsKeywordsPredicate(keywords),
                new TransactionNameContainsKeywordsPredicate(keywords),
                new TransactionTypeContainsValuePredicate(keywords)
        );

        return allPredicates.stream()
                .anyMatch(predicate -> predicate.test(transaction));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionContainsAnyKeywordsPredicate)) {
            return false;
        }

        TransactionContainsAnyKeywordsPredicate otherNameContainsKeywordsPredicate =
                (TransactionContainsAnyKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
