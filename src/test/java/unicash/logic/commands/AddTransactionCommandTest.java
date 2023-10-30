package unicash.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalTransactions.INTERN;
import static unicash.testutil.TypicalTransactions.NUS;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import unicash.commons.core.GuiSettings;
import unicash.logic.UniCashMessages;
import unicash.model.Model;
import unicash.model.ReadOnlyUniCash;
import unicash.model.ReadOnlyUserPrefs;
import unicash.model.UniCash;
import unicash.model.budget.Budget;
import unicash.model.transaction.Transaction;
import unicash.testutil.TransactionBuilder;

public class AddTransactionCommandTest {

    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTransactionCommand(null));
    }

    @Test
    public void execute_transactionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTransactionAdded modelStub = new ModelStubAcceptingTransactionAdded();
        Transaction validTransaction = new TransactionBuilder().build();

        CommandResult commandResult = new AddTransactionCommand(validTransaction).execute(modelStub);

        assertEquals(String.format(AddTransactionCommand.MESSAGE_SUCCESS,
                        UniCashMessages.formatTransaction(validTransaction)),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validTransaction), modelStub.transactionsAdded);
    }

    @Test
    public void execute_duplicateTransaction_success() {
        Transaction validTransaction = new TransactionBuilder().build();
        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(validTransaction);
        ModelStub modelStub = new ModelStubWithTransaction(validTransaction);

        assertDoesNotThrow(() -> addTransactionCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddTransactionCommand addNusCommand = new AddTransactionCommand(NUS);
        AddTransactionCommand addInternCommand = new AddTransactionCommand(INTERN);

        // same object -> returns true
        assertEquals(addNusCommand, addNusCommand);

        // same values -> returns true
        AddTransactionCommand addNusCommandCopy = new AddTransactionCommand(NUS);
        assertEquals(addNusCommand, addNusCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addNusCommand);

        // null -> returns false
        assertNotEquals(null, addNusCommand);

        // different Transaction -> returns false
        assertNotEquals(addNusCommand, addInternCommand);

        assertFalse(addNusCommand.equals(2));
    }

    @Test
    public void toStringMethod() {
        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(NUS);
        String expected = AddTransactionCommand.class.getCanonicalName() + "{toAdd=" + NUS + "}";
        assertEquals(expected, addTransactionCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getUniCashFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUniCashFilePath(Path uniCashFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransaction(Transaction target, Transaction editedTransaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUniCash(ReadOnlyUniCash uniCash) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUniCash getUniCash() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(Transaction target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public Budget getBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateExpenseSummary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HashMap<String, Double> getExpenseSummary() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single transaction.
     */
    private class ModelStubWithTransaction extends AddTransactionCommandTest.ModelStub {
        private final Transaction transaction;

        ModelStubWithTransaction(Transaction transaction) {
            requireNonNull(transaction);
            this.transaction = transaction;
        }

        @Override
        public boolean hasTransaction(Transaction transaction) {
            requireNonNull(transaction);
            return this.transaction.equals(transaction);
        }

        @Override
        public void addTransaction(Transaction transaction) {
        }
    }

    /**
     * A Model stub that always accept the transaction being added.
     */
    private class ModelStubAcceptingTransactionAdded extends AddTransactionCommandTest.ModelStub {
        final ArrayList<Transaction> transactionsAdded = new ArrayList<>();

        @Override
        public boolean hasTransaction(Transaction transaction) {
            requireNonNull(transaction);
            return transactionsAdded.stream().anyMatch(transaction::equals);
        }

        @Override
        public void addTransaction(Transaction transaction) {
            requireNonNull(transaction);
            transactionsAdded.add(transaction);
        }

        @Override
        public ReadOnlyUniCash getUniCash() {
            return new UniCash();
        }
    }

}
