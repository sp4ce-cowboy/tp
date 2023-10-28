package unicash.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;

import javafx.collections.ObservableList;
import unicash.commons.util.ToStringBuilder;
import unicash.model.budget.Budget;
import unicash.model.category.Category;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.TransactionList;

/**
 * Wraps all data in UniCash
 */
public class UniCash implements ReadOnlyUniCash {

    private final TransactionList transactions;
    private Budget budget;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        transactions = new TransactionList();
    }

    public UniCash() {}

    /**
     * Creates an UniCash using the Transactions in the {@code toBeCopied}
     */
    public UniCash(ReadOnlyUniCash toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Transaction list with {@code transactions}.
     * {@code transactions} must not contain any null.
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setTransactions(transactions);
    }

    /**
     * Resets the existing data of this {@code UniCash} with {@code newData}.
     */
    public void resetData(ReadOnlyUniCash newData) {
        requireNonNull(newData);

        setTransactions(newData.getTransactionList());
    }

    //// Transaction-level operations

    /**
     * Returns true if a Transaction with the same identity as {@code Transaction} exists in UniCash.
     */
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return transactions.contains(transaction);
    }

    /**
     * Adds a transaction to UniCash
     */
    public void addTransaction(Transaction p) {
        transactions.add(p);
    }

    /**
     * Replaces the given Transaction {@code target} in the list with {@code editedTransaction}.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireNonNull(editedTransaction);
        transactions.setTransaction(target, editedTransaction);
    }

    /**
     * Removes {@code key} from this {@code UniCash}.
     * {@code key} must exist in UniCash.
     */
    public void removeTransaction(Transaction key) {
        transactions.remove(key);
    }

    public void setBudget(Budget budget) {
        requireNonNull(budget);
        this.budget = budget;
    }

    /**
     * Returns the amount for each category of expenses.
     * Note: This function ignores all 'income' transactions
     */
    HashMap<String, Double> getSumOfExpensePerCategory() {
        HashMap<String, Double> sumPerCategory = new HashMap<>();
        ObservableList<Transaction> allTransactions = getTransactionList();
        String uncategorizedCategoryName = "Uncategorized";
        for (Transaction t : allTransactions) {
            if (t.getType().toString().equals("income")) {
                continue;
            }
            Double transactionAmount = t.getAmountAsDouble();
            // If t has no categories
            if (t.getCategories().asUnmodifiableObservableList().isEmpty()) {
                if (!sumPerCategory.containsKey(uncategorizedCategoryName)) {
                    sumPerCategory.put(uncategorizedCategoryName, (double) 0);
                }
                Double currAmount = sumPerCategory.get(uncategorizedCategoryName);
                Double newAmount = currAmount + transactionAmount;
                sumPerCategory.put(uncategorizedCategoryName, newAmount);
                continue;
            }
            // If t has at least one category
            for (Category transactionCategory : t.getCategories()) {
                if (!sumPerCategory.containsKey(transactionCategory.category)) {
                    sumPerCategory.put(transactionCategory.category, (double) 0);
                }
                Double currAmount = sumPerCategory.get(transactionCategory.category);
                Double newAmount = currAmount + transactionAmount;
                sumPerCategory.put(transactionCategory.category, newAmount);
            }
        }
        return sumPerCategory;
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("transactions", transactions)
                .toString();
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions.asUnmodifiableObservableList();
    }

    /**
     * Returns an unmodifiable view of the budget.
     */
    @Override
    public Budget getBudget() {
        return budget;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniCash)) {
            return false;
        }

        UniCash otherUniCash = (UniCash) other;
        return transactions.equals(otherUniCash.transactions);
    }

    @Override
    public int hashCode() {
        return transactions.hashCode();
    }
}
