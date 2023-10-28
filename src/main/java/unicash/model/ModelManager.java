package unicash.model;

import static java.util.Objects.requireNonNull;
import static unicash.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import unicash.commons.core.GuiSettings;
import unicash.commons.core.LogsCenter;
import unicash.model.budget.Budget;
import unicash.model.transaction.Transaction;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UniCash uniCash;
    private final UserPrefs userPrefs;
    private final FilteredList<Transaction> filteredTransactions;
    private final HashMap<String, Double> expenseSummary;

    /**
     * Initializes a ModelManager with the given userPrefs and UniCash.
     */
    public ModelManager(ReadOnlyUniCash uniCash, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(uniCash, userPrefs);

        logger.fine("Initializing with UniCash: " + uniCash + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.uniCash = new UniCash(uniCash);
        filteredTransactions = new FilteredList<>(this.uniCash.getTransactionList());
        expenseSummary = this.uniCash.getSumOfExpensePerCategory();
    }

    public ModelManager() {
        this(new UniCash(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getUniCashFilePath() {
        return userPrefs.getUniCashFilePath();
    }

    @Override
    public void setUniCashFilePath(Path uniCashFilePath) {
        requireNonNull(uniCashFilePath);
        userPrefs.setUniCashFilePath(uniCashFilePath);
    }

    //=========== UniCash ================================================================================
    @Override
    public void setUniCash(ReadOnlyUniCash uniCash) {
        this.uniCash.resetData(uniCash);
        updateExpenseSummary();
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);
        uniCash.setTransaction(target, editedTransaction);
        updateExpenseSummary();
    }

    @Override
    public ReadOnlyUniCash getUniCash() {
        return uniCash;
    }

    @Override
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return uniCash.hasTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Transaction target) {
        uniCash.removeTransaction(target);
        updateExpenseSummary();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        uniCash.addTransaction(transaction);
        updateExpenseSummary();
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
    }

    @Override
    public void setBudget(Budget budget) {
        uniCash.setBudget(budget);
    }

    public Budget getBudget() {
        return uniCash.getBudget();
    }

    //=========== Filtered Transaction List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Transaction} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return filteredTransactions;
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public void updateExpenseSummary() {
        HashMap<String, Double> newExpenseSummary = uniCash.getSumOfExpensePerCategory();
        clearExpenseSummary();
        expenseSummary.putAll(newExpenseSummary);
    }

    @Override
    public HashMap<String, Double> getExpenseSummary() {
        return expenseSummary;
    }

    /**
     * Removes all entries in expenseSummary
     * This public methods is only used during testing to see if different ModelManager
     * objects are equal when they have different data in {@code expenseSummary}
     */
    public void clearExpenseSummary() {
        expenseSummary.clear();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return uniCash.equals(otherModelManager.uniCash)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredTransactions.equals(otherModelManager.filteredTransactions)
                && expenseSummary.equals(otherModelManager.expenseSummary);
    }
}
