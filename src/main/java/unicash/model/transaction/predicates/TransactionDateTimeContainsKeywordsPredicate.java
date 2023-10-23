package unicash.model.transaction.predicates;

import java.util.List;
import java.util.function.Predicate;

import unicash.commons.util.StringUtil;
import unicash.commons.util.ToStringBuilder;
import unicash.model.transaction.Transaction;

/**
 * Tests that a {@code Transactions}'s {@code DateTime} matches any of the keywords given.
 * Given that DateTimes are numeric, full word match is required, as in the exact format
 * specified in {@code DateTime}.
 *
 * </p> However, given that the actual specification splits date and time, this property
 * predicate would work for finding both date and time separately as they are separated
 * by a space and thus would parse to two words.
 */
public class TransactionDateTimeContainsKeywordsPredicate
        implements Predicate<Transaction> {
    private final List<String> keywords;

    public TransactionDateTimeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        //return keywords.stream()
            //    .anyMatch(keyword -> StringUtil.containsSubstringIgnoreCase(
                      //  transaction.getDateTime().toString(), keyword));
        return transaction.getDateTime().toString().contains(String.join(" ", keywords));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionDateTimeContainsKeywordsPredicate)) {
            return false;
        }

        TransactionDateTimeContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (TransactionDateTimeContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
