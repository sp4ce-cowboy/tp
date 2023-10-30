package unicash.model.transaction.predicates;

import java.util.List;
import java.util.function.Predicate;

import unicash.commons.util.StringUtil;
import unicash.commons.util.ToStringBuilder;
import unicash.model.transaction.Transaction;

/**
 * Tests that a {@code Transactions}'s {@code Type} matches any of the keywords given.
 * Similar to the transaction amount predicate, this predicate only makes sense if the
 * full transaction type is specified, thus, partial matching will not be flagged as a
 * match by this predicate.
 */
public class TransactionTypeContainsValuePredicate
        implements Predicate<Transaction> {
    private final List<String> keywords;

    public TransactionTypeContainsValuePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        transaction.getType().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionTypeContainsValuePredicate)) {
            return false;
        }

        TransactionTypeContainsValuePredicate otherNameContainsKeywordsPredicate =
                (TransactionTypeContainsValuePredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
