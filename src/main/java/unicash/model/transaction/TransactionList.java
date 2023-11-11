package unicash.model.transaction;

import static java.util.Objects.requireNonNull;
import static unicash.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import unicash.model.transaction.exceptions.MaxTransactionException;
import unicash.model.transaction.exceptions.TransactionNotFoundException;

/**
 * A list of Transactions that does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class TransactionList implements Iterable<Transaction> {
    public static final int MAX_TRANSACTIONS = 100000;
    public static final String MESSAGE_SIZE_CONSTRAINTS =
            "UniCa$h supports up to a maximum of 100,000 transactions.";
    private final ObservableList<Transaction> internalList = FXCollections.observableArrayList();
    private final ObservableList<Transaction> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Transaction as the given argument.
     */
    public boolean contains(Transaction toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equalsTransaction);
    }

    /**
     * Adds a Transaction to the list.
     */
    public void add(Transaction toAdd) {
        requireNonNull(toAdd);
        if (isFull()) {
            throw new MaxTransactionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the list.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TransactionNotFoundException();
        }

        internalList.set(index, editedTransaction);
    }

    /**
     * Removes the equivalent Transaction from the list.
     * The Transaction must exist in the list.
     */
    public void remove(Transaction toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TransactionNotFoundException();
        }
    }

    /**
     * Returns true if the storage is full, false otherwise.
     */
    public boolean isFull() {
        return internalList.size() == MAX_TRANSACTIONS;
    }

    /**
     * Replaces the contents of this list with {@code replacement}
     * @param replacement the TransactionList to replace with
     */
    public void setTransactions(TransactionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code transactions}.
     * {@code transactions} must not contain null.
     */
    public void setTransactions(List<Transaction> transactions) {
        requireAllNonNull(transactions);
        if (isMoreThanMax(transactions)) {
            throw new MaxTransactionException();
        }

        internalList.setAll(transactions);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Transaction> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Transaction> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionList)) {
            return false;
        }

        TransactionList otherTransactionList = (TransactionList) other;

        if (this.internalList.size() != otherTransactionList.internalList.size()) {
            return false;
        }

        // Use equalsTransaction for comparison instead of original equals
        for (int i = 0; i < this.internalList.size(); i++) {
            Transaction thisTransaction = this.internalList.get(i);
            Transaction otherTransaction = otherTransactionList.internalList.get(i);
            if (!thisTransaction.equalsTransaction(otherTransaction)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if a given list of transactions is less than the maximum allowed transactions.
     */
    public static boolean isMoreThanMax(List<Transaction> transactions) {
        return transactions.size() > MAX_TRANSACTIONS;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
