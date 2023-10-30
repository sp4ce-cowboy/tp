package unicash.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.logic.commands.CommandTestUtil.VALID_AMOUNT_INTERN;
import static unicash.logic.commands.CommandTestUtil.VALID_CATEGORY_EDUCATION;
import static unicash.logic.commands.CommandTestUtil.VALID_DATETIME_SHOPPING;
import static unicash.logic.commands.CommandTestUtil.VALID_TYPE_EXPENSE;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalTransactions.BUYING_GROCERIES;
import static unicash.testutil.TypicalTransactions.DINING_WITH_FRIENDS;
import static unicash.testutil.TypicalTransactions.INTERN;
import static unicash.testutil.TypicalTransactions.NUS;
import static unicash.testutil.TypicalTransactions.SHOPPING;
import static unicash.testutil.TypicalTransactions.getTypicalUniCash;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import unicash.model.budget.Budget;
import unicash.model.budget.Interval;
import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.Type;
import unicash.model.transaction.exceptions.TransactionNotFoundException;
import unicash.testutil.TransactionBuilder;
import unicash.testutil.UniCashBuilder;

public class UniCashTest {

    private final UniCash uniCash = new UniCash();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), uniCash.getTransactionList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniCash.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyUniCash_replacesData() {
        UniCash newData = getTypicalUniCash();
        uniCash.resetData(newData);
        assertEquals(newData, uniCash);
    }

    @Test
    public void resetData_withDuplicateTransactions_success() {
        // Two transactions with the same identity fields
        Transaction editedNus = new TransactionBuilder(NUS).withAmount(VALID_AMOUNT_INTERN).build();
        List<Transaction> newTransactions = Arrays.asList(NUS, editedNus);
        UniCashTest.UniCashStub newData = new UniCashTest.UniCashStub(newTransactions);

        assertDoesNotThrow(() -> uniCash.resetData(newData));
    }

    @Test
    public void hasTransaction_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniCash.hasTransaction(null));
    }

    @Test
    public void hasTransaction_transactionNotInUniCash_returnsFalse() {
        assertFalse(uniCash.hasTransaction(NUS));
    }

    @Test
    public void hasTransaction_transactionsInUniCash_returnsTrue() {
        uniCash.addTransaction(NUS);
        assertTrue(uniCash.hasTransaction(NUS));
    }

    @Test
    public void setTransaction_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniCash.setTransaction(NUS, null));
    }

    @Test
    public void setTransaction_transactionNotInUniCash_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> uniCash.setTransaction(NUS, BUYING_GROCERIES));
    }

    @Test
    public void setTransaction_transactionInUniCash_success() {
        uniCash.addTransaction(NUS);
        assertDoesNotThrow(() -> uniCash.setTransaction(NUS, BUYING_GROCERIES));
    }

    @Test
    public void removeTransaction_transactionInUniCash_returnsTrue() {
        UniCash transactionList = new UniCashBuilder().withTransaction(NUS).build();
        assertTrue(transactionList.hasTransaction(NUS));
        transactionList.removeTransaction(NUS);
        assertFalse(transactionList.hasTransaction(NUS));
    }

    @Test
    public void getUniCash_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniCash.getTransactionList().remove(0));
    }

    @Test
    public void getSumPerCategory_transactionsHaveExactlyOneCategory_success() {
        UniCash uniCash = new UniCashBuilder()
                .withTransaction(NUS)
                .withTransaction(BUYING_GROCERIES)
                .withTransaction(DINING_WITH_FRIENDS)
                .build();

        HashMap<String, Double> expectedOutput = new HashMap<>();
        expectedOutput.put("ta", 888.80);

        HashMap<String, Double> actualOutput = uniCash.getSumOfExpensePerCategory();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getSumPerCategory_transactionsHaveOneOrZeroCategory_success() {
        Transaction noCategoryExpense1 = new Transaction(
                new Name("Test"),
                new Type("expense"),
                new Amount(3.21),
                new DateTime(""),
                new Location(""),
                new UniqueCategoryList()
        );
        Transaction noCategoryExpense2 = new Transaction(
                new Name("Test"),
                new Type("expense"),
                new Amount(5.00),
                new DateTime(""),
                new Location(""),
                new UniqueCategoryList()
        );
        UniCash uniCash = new UniCashBuilder()
                .withTransaction(NUS)
                .withTransaction(BUYING_GROCERIES)
                .withTransaction(DINING_WITH_FRIENDS)
                .withTransaction(noCategoryExpense1)
                .withTransaction(noCategoryExpense2)
                .build();
        HashMap<String, Double> actualOutput = uniCash.getSumOfExpensePerCategory();

        HashMap<String, Double> expectedOutput = new HashMap<>();
        expectedOutput.put("ta", 888.80);
        expectedOutput.put("Uncategorized", 8.21);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getSumPerCategory_transactionsHaveMultipleCategories_success() {

        Transaction multipleCategoriesExpense = new TransactionBuilder().withName("test")
                .withType(VALID_TYPE_EXPENSE)
                .withAmount(500.1)
                .withDateTime(VALID_DATETIME_SHOPPING)
                .withCategories("TA", VALID_CATEGORY_EDUCATION)
                .build();

        UniCash uniCash = new UniCashBuilder()
                .withTransaction(NUS)
                .withTransaction(BUYING_GROCERIES)
                .withTransaction(DINING_WITH_FRIENDS)
                .withTransaction(SHOPPING)
                .withTransaction(multipleCategoriesExpense)
                .build();
        HashMap<String, Double> actualOutput = uniCash.getSumOfExpensePerCategory();

        HashMap<String, Double> expectedOutput = new HashMap<>();
        expectedOutput.put("education", 500.1);
        expectedOutput.put("ta", 888.8 + 500.1);

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void setBudget_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniCash.setBudget(null));
    }

    @Test
    public void setBudget_validBudget_success() {
        Budget budget = new Budget(new Amount(1000), new Interval("month"));
        uniCash.setBudget(budget);
        assertEquals(budget, uniCash.getBudget());
    }

    @Test
    public void toStringMethod() {
        String expected = UniCash.class.getCanonicalName() + "{transactions=" + uniCash.getTransactionList() + "}";
        assertEquals(expected, uniCash.toString());
    }

    @Test
    public void equals() {
        // same object -> returns true
        UniCash transactionList = new UniCashBuilder().build();
        assertEquals(transactionList, transactionList);

        // same lists
        transactionList.addTransaction(NUS);
        UniCash anotherList = new UniCashBuilder().withTransaction(NUS).build();
        assertEquals(transactionList, anotherList);

        // different lists
        anotherList = new UniCashBuilder().withTransaction(INTERN).build();
        assertNotEquals(transactionList, anotherList);

        // null -> returns false
        assertNotEquals(null, transactionList);

        assertFalse(transactionList.equals(1));
    }

    /**
     * A stub ReadOnlyUniCash whose transactions list can violate interface constraints.
     */
    private static class UniCashStub implements ReadOnlyUniCash {
        private final ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        private final Budget budget = new Budget(new Amount(0), new Interval("month"));

        UniCashStub(Collection<Transaction> transactions) {
            this.transactions.setAll(transactions);
        }

        @Override
        public ObservableList<Transaction> getTransactionList() {
            return transactions;
        }

        /**
         * Returns an unmodifiable view of the budget.
         */
        @Override
        public Budget getBudget() {
            return budget;
        }
    }

    @Test
    public void hashCode_test() {
        UniCash uniCash1 = new UniCash();
        UniCash uniCash2 = new UniCash();
        UniCash uniCash3 = new UniCash();
        uniCash3.addTransaction(NUS);
        assertEquals(uniCash1.hashCode(), uniCash2.hashCode());
        assertNotEquals(uniCash1.hashCode(), uniCash3.hashCode());
    }
}
