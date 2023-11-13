package unicash.model;

import javafx.collections.ObservableList;
import unicash.model.budget.Budget;
import unicash.model.transaction.Transaction;

/**
 * Unmodifiable view of UniCash
 */
public interface ReadOnlyUniCash {

    /**
     * Returns an unmodifiable view of the transaction list.
     */
    ObservableList<Transaction> getTransactionList();

    /**
     * Returns an unmodifiable view of the budget.
     */
    Budget getBudget();

}
