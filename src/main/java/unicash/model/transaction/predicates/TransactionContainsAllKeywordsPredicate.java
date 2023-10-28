package unicash.model.transaction.predicates;

import java.util.List;
import java.util.function.Predicate;

import unicash.commons.util.ToStringBuilder;
import unicash.model.transaction.Transaction;




/**
 * Tests that any of a {@code Transactions}'s properties matches all the keywords given.
 * Matching in this context either means a partial match of the keyword with the query, or
 * a full match of the keyword, repeated for each keyword present in the list of keywords.
 *
 * </p> The matching depends on the property of the Transaction being matched, and the
 * most appropriate matching is specified within the associated property predicate. This class
 * simulates a composed predicate that represents a short-circuiting logical AND of all property
 * predicates.
 */
public class TransactionContainsAllKeywordsPredicate
        implements Predicate<Transaction> {
    public static final String EMPTY_STRING = "";
    public static final List<String> EMPTY_STRING_LIST = List.of(EMPTY_STRING);

    private TransactionAmountContainsValuePredicate amountPredicate;
    private boolean amountPredicateExists;

    private TransactionCategoryContainsKeywordsPredicate categoryPredicate;
    private TransactionDateTimeContainsValuePredicate dateTimePredicate;
    private TransactionLocationContainsKeywordsPredicate locationPredicate;
    private TransactionNameContainsKeywordsPredicate namePredicate;
    private boolean namePredicateExists;

    private TransactionTypeContainsValuePredicate typePredicate;
    private Predicate<Transaction> composedTransactionPredicate;


    /**
     * Creates a composed predicate object.
     */
    public TransactionContainsAllKeywordsPredicate() {
        amountPredicate = new TransactionAmountContainsValuePredicate(EMPTY_STRING_LIST);
        amountPredicateExists = false;

        categoryPredicate = new TransactionCategoryContainsKeywordsPredicate(EMPTY_STRING_LIST);
        dateTimePredicate = new TransactionDateTimeContainsValuePredicate(EMPTY_STRING_LIST);
        locationPredicate = new TransactionLocationContainsKeywordsPredicate(EMPTY_STRING_LIST);

        namePredicate = new TransactionNameContainsKeywordsPredicate(EMPTY_STRING_LIST);
        namePredicateExists = false;

        typePredicate = new TransactionTypeContainsValuePredicate(EMPTY_STRING_LIST);
        composedTransactionPredicate = amountPredicate
                .and(categoryPredicate)
                .and(dateTimePredicate)
                .and(locationPredicate)
                .and(namePredicate)
                .and(typePredicate);
    }

    @Override
    public boolean test(Transaction transaction) {
        Predicate<Transaction> composedPredicate = composeAllPredicates();
        return composedPredicate.test(transaction);

    }

    /**
     * Composes all predicates.
     *
     * @return a composed predicate
     */
    public Predicate<Transaction> composeAllPredicates() {
        Predicate<Transaction> composedPredicate = unused -> true;

        if (amountPredicateExists) {
            composedPredicate = composedPredicate.and(amountPredicate);
        }

        if (namePredicateExists) {
            composedPredicate = composedPredicate.and(namePredicate);
        }

        return composedPredicate;
    }

    public void setAmount(String amount) {
        amountPredicate = new TransactionAmountContainsValuePredicate(List.of(amount));
        amountPredicateExists = true;
    }

    public void setName(String name) {
        namePredicate = new TransactionNameContainsKeywordsPredicate(List.of(name));
        namePredicateExists = true;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionContainsAllKeywordsPredicate)) {
            return false;
        }

        TransactionContainsAllKeywordsPredicate otherContainsKeywordsPredicate =
                (TransactionContainsAllKeywordsPredicate) other;

        return amountPredicate.equals(otherContainsKeywordsPredicate.amountPredicate)
                && namePredicate.equals(otherContainsKeywordsPredicate.namePredicate)
                && composedTransactionPredicate.equals(otherContainsKeywordsPredicate
                .composedTransactionPredicate);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("composedTransactionPredicate", composedTransactionPredicate)
                .add("amountPredicate", amountPredicate)
                .add("namePredicate", namePredicate)
                .toString();
    }
}
