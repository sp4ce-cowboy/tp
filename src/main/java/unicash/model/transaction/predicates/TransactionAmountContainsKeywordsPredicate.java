package unicash.model.transaction.predicates;

import java.util.List;
import java.util.function.Predicate;

import unicash.commons.util.StringUtil;
import unicash.commons.util.ToStringBuilder;
import unicash.model.transaction.Amount;
import unicash.model.transaction.Transaction;

/**
 * Tests that a {@code Transactions}'s {@code Amount} matches any of the keywords given.
 * Keywords in this context refers to amount strings, and given that the String value of
 * any valid amount will always be a single word string, the entire amount has to match.
 * This is unlike most of the other property predicates that only require a partial word
 * match - the reason being, it does not make sense to match partial amounts in a find context.
 *
 * </p> Additionally, given the nature of the Amount String i.e. it containing the currency
 * symbol, this predicate forms a String of the raw amount value directly, so keyword comparisons
 * are valid regardless of currency used.
 */
public class TransactionAmountContainsKeywordsPredicate
        implements Predicate<Transaction> {
    private final List<String> keywords;

    public TransactionAmountContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        Amount.amountToDecimalString(transaction.getAmount()),
                        keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionAmountContainsKeywordsPredicate)) {
            return false;
        }

        TransactionAmountContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (TransactionAmountContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
