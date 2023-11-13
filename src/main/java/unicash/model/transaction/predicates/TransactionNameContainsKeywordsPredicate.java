package unicash.model.transaction.predicates;

import java.util.List;
import java.util.function.Predicate;

import unicash.commons.util.StringUtil;
import unicash.commons.util.ToStringBuilder;
import unicash.model.transaction.Transaction;

/**
 * Tests that a {@code Transactions}'s {@code Name} matches any of the keywords given.
 */
public class TransactionNameContainsKeywordsPredicate
        implements Predicate<Transaction> {

    private final List<String> keywords;

    /**
     * Creates a new {@code TransactionNameContainsKeywordsPredicate} object
     * with the given list of string keywords.
     *
     * @param keywords the input list of keywords to be matched
     */
    public TransactionNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns true if the {@code Name} of the Transaction contains any of
     * the keywords in the keywords list as a substring.
     *
     * @param transaction the input {@code Transaction} object to be tested
     */
    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsSubstringIgnoreCase(
                        transaction.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionNameContainsKeywordsPredicate)) {
            return false;
        }

        TransactionNameContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (TransactionNameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}


