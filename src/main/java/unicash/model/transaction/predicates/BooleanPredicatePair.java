package unicash.model.transaction.predicates;

import unicash.commons.util.Pair;
import unicash.model.transaction.Transaction;

import java.util.function.Predicate;

/**
 * A wrapper class for containing Boolean and TransactionPredicate pairs.
 */
public class BooleanPredicatePair
        extends Pair<Boolean, Predicate<Transaction>> {

    /**
     * Creates the transaction predicate pair object.
     *
     * @param isActive             The boolean indicating the status of the predicate
     * @param transactionPredicate The transaction property predicate
     */
    public BooleanPredicatePair(Boolean isActive, Predicate<Transaction> transactionPredicate) {
        super(isActive, transactionPredicate);
    }

    /**
     * Creates the transaction predicate pair object with default false value
     *
     * @param transactionPredicate The transaction property predicate
     */
    public BooleanPredicatePair(Predicate<Transaction> transactionPredicate) {
        super(false, transactionPredicate);
    }

}
