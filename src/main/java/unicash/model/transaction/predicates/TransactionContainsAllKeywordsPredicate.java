package unicash.model.transaction.predicates;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import unicash.commons.enums.TransactionProperty;
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

    public HashMap<TransactionProperty, BooleanPredicatePair>
            predicatePairMap = new HashMap<>();

    /**
     * Creates a composed predicate object.
     */
    public TransactionContainsAllKeywordsPredicate() {

        TransactionAmountContainsValuePredicate amountPredicate =
                new TransactionAmountContainsValuePredicate(EMPTY_STRING_LIST);
        TransactionCategoryContainsKeywordsPredicate categoryPredicate =
                new TransactionCategoryContainsKeywordsPredicate(EMPTY_STRING_LIST);
        TransactionDateTimeContainsValuePredicate dateTimePredicate =
                new TransactionDateTimeContainsValuePredicate(EMPTY_STRING_LIST);
        TransactionLocationContainsKeywordsPredicate locationPredicate =
                new TransactionLocationContainsKeywordsPredicate(EMPTY_STRING_LIST);
        TransactionNameContainsKeywordsPredicate namePredicate =
                new TransactionNameContainsKeywordsPredicate(EMPTY_STRING_LIST);
        TransactionTypeContainsValuePredicate typePredicate =
                new TransactionTypeContainsValuePredicate(EMPTY_STRING_LIST);

        predicatePairMap.put(TransactionProperty.AMOUNT, new BooleanPredicatePair(amountPredicate));
        predicatePairMap.put(TransactionProperty.CATEGORY, new BooleanPredicatePair(categoryPredicate));
        predicatePairMap.put(TransactionProperty.DATETIME, new BooleanPredicatePair(dateTimePredicate));
        predicatePairMap.put(TransactionProperty.LOCATION, new BooleanPredicatePair(locationPredicate));
        predicatePairMap.put(TransactionProperty.NAME, new BooleanPredicatePair(namePredicate));
        predicatePairMap.put(TransactionProperty.TYPE, new BooleanPredicatePair(typePredicate));

    }

    @Override
    public boolean test(Transaction transaction) {
        Predicate<Transaction> composedPredicate = composeAllActivePredicates();
        return composedPredicate.test(transaction);

    }

    /**
     * Composes all predicates.
     *
     * @return a composed predicate
     */
    public Predicate<Transaction> composeAllActivePredicates() {
        Predicate<Transaction> composedPredicate = unused -> true;

        for (TransactionProperty property : predicatePairMap.keySet()) {
            Boolean predicateState = predicatePairMap.get(property).getFirst();
            Predicate<Transaction> predicate = predicatePairMap.get(property).getSecond();

            if (predicateState) {
                composedPredicate = composedPredicate.and(predicate);
            }
        }

        return composedPredicate;
    }

    public void setAmount(String amount) {
        BooleanPredicatePair predicatePair = new BooleanPredicatePair(true,
                        new TransactionAmountContainsValuePredicate(
                                Collections.singletonList(amount)));

        predicatePairMap.put(TransactionProperty.AMOUNT, predicatePair);
    }

    public void setName(String name) {
        BooleanPredicatePair predicatePair = new BooleanPredicatePair(true,
                new TransactionNameContainsKeywordsPredicate(
                        Collections.singletonList(name)));

        predicatePairMap.put(TransactionProperty.NAME, predicatePair);
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

        return predicatePairMap.equals(
                otherContainsKeywordsPredicate.predicatePairMap);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicatePairMap", predicatePairMap)
                .toString();
    }
}
