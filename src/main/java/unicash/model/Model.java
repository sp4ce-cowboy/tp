package unicash.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import unicash.commons.core.GuiSettings;
import unicash.model.budget.Budget;
import unicash.model.transaction.Transaction;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluates to true
     */
    Predicate<Transaction> PREDICATE_SHOW_ALL_TRANSACTIONS = unused -> true;

    /**
     * Returns true if a budget with the same identity as {@code budget} exists in UniCash.
     */
    Predicate<Budget> PREDICATE_SHOW_ALL_BUDGETS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Replaces the given transaction {@code target} with {@code editedTransaction}.
     * {@code target} must exist in the transaction list.
     * The transaction identity of {@code editedTransaction} must not be the same as another existing transaction.
     */
    void setTransaction(Transaction target, Transaction editedTransaction);

    /**
     * Returns the user prefs' UniCash file path.
     */
    Path getUniCashFilePath();

    /**
     * Sets the user prefs' UniCash file path.
     */
    void setUniCashFilePath(Path uniCashFilePath);

    /**
     * Replaces UniCash data with the data in {@code uniCash}.
     */
    void setUniCash(ReadOnlyUniCash uniCash);

    /**
     * Returns UniCash
     */
    ReadOnlyUniCash getUniCash();

    /**
     * Deletes the given transaction.
     * The transaction must exist in UniCash.
     */
    void deleteTransaction(Transaction target);

    /**
     * Adds the given transaction.
     */
    void addTransaction(Transaction transaction);

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in UniCash.
     */
    boolean hasTransaction(Transaction transaction);

    /**
     * Returns an unmodifiable view of the filtered transaction list
     */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Updates the filter of the filtered UniCash to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate);

    /**
     * Adds the given budget
     */
    void setBudget(Budget budget);

    /**
     * Returns the budget
     */
    Budget getBudget();

    /**
     * Updates the summary of expenses saved in UniCash.
     */
    void updateExpenseSummary();

    HashMap<String, Double> getExpenseSummary();
}
