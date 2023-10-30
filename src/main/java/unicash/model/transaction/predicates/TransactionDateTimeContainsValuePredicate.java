package unicash.model.transaction.predicates;

import java.util.List;
import java.util.function.Predicate;

import unicash.commons.util.StringUtil;
import unicash.commons.util.ToStringBuilder;
import unicash.model.transaction.Transaction;

/**
 * Tests that a {@code Transactions}'s {@code DateTime} matches any of the keywords given.
 * Given that DateTimes are stored alphanumerically, match is required as per the exact format
 * specified in {@code DateTime} for storing dates. The match format for DateTime is identical
 * to the one displayed in the {@code TransactionsListPanel}.
 *
 * </p> This property predicate would work for finding both date and time separately as they
 * are separated and thus the input keyword would still match part of the date. Substring matching
 * is used to ensure that the user has better precision over the transactions to find, and word
 * matching is not feasible given the whitespace restriction imposed by the
 * {@code StringUtil::containsWordIgnoreCase} method, which contradicts the natural display format
 * of dates as separated by spaces.
 */
public class TransactionDateTimeContainsValuePredicate
        implements Predicate<Transaction> {
    private final List<String> keywords;

    public TransactionDateTimeContainsValuePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        // Keywords are rejoined as split by FindCommand
        String joinedKeyword = String.join(" ", keywords);
        String dateTimeString = transaction.getDateTime().toString();

        return StringUtil.containsSubstringIgnoreCase(dateTimeString,
                joinedKeyword);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionDateTimeContainsValuePredicate)) {
            return false;
        }

        TransactionDateTimeContainsValuePredicate otherNameContainsKeywordsPredicate =
                (TransactionDateTimeContainsValuePredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
