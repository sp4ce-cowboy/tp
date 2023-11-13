package unicash.model.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.logic.commands.CommandTestUtil.VALID_AMOUNT_INTERN;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalTransactions.BUYING_GROCERIES;
import static unicash.testutil.TypicalTransactions.INTERN;
import static unicash.testutil.TypicalTransactions.NUS;
import static unicash.testutil.TypicalTransactions.SHOPPING;
import static unicash.testutil.TypicalTransactions.getMaxTransactionList;
import static unicash.testutil.TypicalTransactions.getTypicalTransactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unicash.model.transaction.exceptions.MaxTransactionException;
import unicash.model.transaction.exceptions.TransactionNotFoundException;
import unicash.testutil.TransactionBuilder;

public class TransactionListTest {

    private TransactionList transactionList;

    @BeforeEach
    public void init() {
        transactionList = new TransactionList();
    }

    @Test
    public void contains_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.contains(null));
    }

    @Test
    public void contains_transactionNotInList_returnsFalse() {
        assertFalse(transactionList.contains(NUS));
    }

    @Test
    public void contains_transactionInList_returnsTrue() {
        transactionList.add(NUS);
        transactionList.add(SHOPPING);
        assertTrue(transactionList.contains(NUS));
    }

    @Test
    public void contains_differentTransaction_returnsFalse() {
        transactionList.add(NUS);
        Transaction editedNus = new TransactionBuilder(NUS)
                .withAmount(VALID_AMOUNT_INTERN)
                .build();
        assertFalse(transactionList.contains(editedNus));
    }

    @Test
    public void add_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.add(null));
    }

    @Test
    public void add_duplicateTransaction_doesNotThrow() {
        transactionList.add(NUS);
        assertDoesNotThrow(() -> transactionList.add(NUS));
    }

    @Test
    public void add_maxTransactions_throwsMaxTransactionException() {
        transactionList.setTransactions(getMaxTransactionList());
        assertThrows(MaxTransactionException.class, () -> transactionList.add(NUS));
    }

    @Test
    public void setTransaction_nullTargetTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(null, NUS));
    }

    @Test
    public void setTransaction_nullEditedTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransaction(NUS, null));
    }

    @Test
    public void setTransaction_targetTransactionNotInList_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.setTransaction(NUS, NUS));
    }

    // TODO: Add explicit unit tests for the case of duplicate transactions and editing one of them
    @Test
    public void setTransaction_editedTransactionIsSameTransaction_success() {
        transactionList.add(NUS);
        transactionList.setTransaction(NUS, NUS);
        TransactionList expectedUniqueTransactionList = new TransactionList();
        expectedUniqueTransactionList.add(NUS);
        assertEquals(expectedUniqueTransactionList, transactionList);
    }

    @Test
    public void setTransaction_editedTransactionHasDifferentIdentity_success() {
        transactionList.add(NUS);
        transactionList.setTransaction(NUS, BUYING_GROCERIES);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(BUYING_GROCERIES);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void remove_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.remove(null));
    }

    @Test
    public void remove_transactionDoesNotExist_throwsTransactionNotFoundException() {
        assertThrows(TransactionNotFoundException.class, () -> transactionList.remove(NUS));
    }

    @Test
    public void remove_existingTransaction_removesTransaction() {
        transactionList.add(NUS);
        transactionList.remove(NUS);
        TransactionList expectedTransactionList = new TransactionList();
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void isFull() {
        transactionList.setTransactions(getMaxTransactionList());

        // equal to max transactions -> true
        assertTrue(transactionList.isFull());

        // less than max transactions -> false
        transactionList.setTransactions(getTypicalTransactions());
        assertFalse(transactionList.isFull());
    }

    @Test
    public void setTransactions_nullTransactionList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((TransactionList) null));
    }

    @Test
    public void setTransactions_transactionList_replacesOwnListWithProvidedTransactionList() {
        transactionList.add(NUS);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(BUYING_GROCERIES);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transactionList.setTransactions((List<Transaction>) null));
    }

    @Test
    public void setTransactions_uniqueTransactionList_replacesOwnListWithProvidedUniqueTransactionList() {
        transactionList.add(NUS);
        TransactionList expectedTransactionList = new TransactionList();
        expectedTransactionList.add(BUYING_GROCERIES);
        transactionList.setTransactions(expectedTransactionList);
        assertEquals(expectedTransactionList, transactionList);
    }

    @Test
    public void setTransactions_listWithDuplicateTransactions_success() {
        List<Transaction> listWithDuplicateTransactions = Arrays.asList(NUS, NUS);
        assertDoesNotThrow(() -> transactionList.setTransactions(listWithDuplicateTransactions));
    }

    @Test
    public void setTransactions_notMoreThanMaxTransactions_success() {
        List<Transaction> maxTransactions = getMaxTransactionList();
        assertDoesNotThrow(() -> transactionList.setTransactions(maxTransactions));
    }

    @Test
    public void setTransactions_moreThanMaxTransactions_success() {
        List<Transaction> maxTransactions = getMaxTransactionList();
        maxTransactions.add(NUS);
        assertThrows(MaxTransactionException.class, () -> transactionList.setTransactions(maxTransactions));
    }


    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> transactionList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(transactionList.asUnmodifiableObservableList().toString(), transactionList.toString());
    }

    @Test
    public void iterator() {
        transactionList.add(NUS);
        transactionList.add(INTERN);
        Iterator<Transaction> iterator = transactionList.iterator();
        List<Transaction> actual = new ArrayList<>();
        while (iterator.hasNext()) {
            actual.add(iterator.next());
        }
        List<Transaction> expectedIterator = new ArrayList<>();
        expectedIterator.add(NUS);
        expectedIterator.add(INTERN);
        assertEquals(expectedIterator, actual);
    }

    @Test
    public void equals() {
        // same object -> returns true
        TransactionList transactionList = new TransactionList();
        assertEquals(transactionList, transactionList);

        // same lists
        transactionList.add(NUS);
        TransactionList anotherList = new TransactionList();
        anotherList.add(NUS);
        assertEquals(transactionList, anotherList);

        // different lists
        anotherList = new TransactionList();
        anotherList.add(BUYING_GROCERIES);
        assertNotEquals(transactionList, anotherList);

        // null -> returns false
        assertNotEquals(null, transactionList);

        assertFalse(transactionList.equals(5));
    }

    @Test
    public void isMoreThanMax() {

        // EP 1 > 100000
        List<Transaction> transactionsList = getMaxTransactionList();
        transactionsList.add(NUS);
        assertTrue(TransactionList.isMoreThanMax(transactionsList));

        // EP 2 <= 100000
        transactionsList = getMaxTransactionList();
        assertFalse(TransactionList.isMoreThanMax(transactionsList));
        assertFalse(TransactionList.isMoreThanMax(getTypicalTransactions()));
    }

    @Test
    public void hashCode_test() {
        TransactionList transactionList1 = new TransactionList();
        TransactionList transactionList2 = new TransactionList();
        TransactionList transactionList3 = new TransactionList();
        transactionList3.add(NUS);
        assertEquals(transactionList1.hashCode(), transactionList2.hashCode());
        assertNotEquals(transactionList1.hashCode(), transactionList3.hashCode());
    }
}
