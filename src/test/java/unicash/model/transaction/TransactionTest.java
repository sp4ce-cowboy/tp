package unicash.model.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static unicash.testutil.TypicalTransactions.BUYING_GROCERIES;
import static unicash.testutil.TypicalTransactions.DINING_WITH_FRIENDS;
import static unicash.testutil.TypicalTransactions.WORK_AT_LIHO;

import org.junit.jupiter.api.Test;

import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;
import unicash.testutil.TransactionBuilder;

public class TransactionTest {

    @Test
    public void getAmountAsDouble() {
        assertEquals(BUYING_GROCERIES.getAmountAsDouble(), 16.75);
        assertEquals(DINING_WITH_FRIENDS.getAmountAsDouble(), 234.50);
        assertEquals(WORK_AT_LIHO.getAmountAsDouble(), 888.0);
    }

    @Test
    public void initializeTransaction_nonEssentialFieldsMissing_success() {
        // Missing DateTime, Location, and Category
        assertDoesNotThrow(() -> new Transaction(
                new Name("Test"),
                new Type("expense"),
                new Amount(3.21),
                new DateTime(""),
                new Location(""),
                new UniqueCategoryList()
        ));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Transaction groceriesCopy = new TransactionBuilder(BUYING_GROCERIES).build();
        assertEquals(BUYING_GROCERIES, groceriesCopy);

        // same object -> returns true
        assertEquals(BUYING_GROCERIES, BUYING_GROCERIES);

        // null -> returns false
        assertNotEquals(null, groceriesCopy);

        // null -> returns false
        assertNotEquals(null, BUYING_GROCERIES);

        // different type -> returns false
        assertNotEquals(5, BUYING_GROCERIES);

        assertFalse(BUYING_GROCERIES.equals(5));

        // different person -> returns false
        assertNotEquals(BUYING_GROCERIES, DINING_WITH_FRIENDS);

        // different name -> returns false
        Transaction editedGroceries = new TransactionBuilder(BUYING_GROCERIES).withName("Another thing").build();
        assertNotEquals(BUYING_GROCERIES, editedGroceries);

        // different amount -> returns false
        editedGroceries = new TransactionBuilder(BUYING_GROCERIES).withAmount(10.15).build();
        assertNotEquals(BUYING_GROCERIES, editedGroceries);

        // different date -> returns false
        editedGroceries = new TransactionBuilder(BUYING_GROCERIES)
                .withDateTime("01-01-1999 18:18")
                .build();
        assertNotEquals(BUYING_GROCERIES, editedGroceries);

        // different location -> returns false
        editedGroceries = new TransactionBuilder(BUYING_GROCERIES).withLocation("UTown").build();
        assertNotEquals(BUYING_GROCERIES, editedGroceries);

        // different transaction type -> return false
        editedGroceries = new TransactionBuilder(BUYING_GROCERIES).withType("expense").build();
        assertNotEquals(BUYING_GROCERIES, editedGroceries);

        // different categories -> returns false
        editedGroceries = new TransactionBuilder(BUYING_GROCERIES).withCategories("TEST").build();
        assertFalse(BUYING_GROCERIES.equals(editedGroceries));

        // different type -> returns false
        editedGroceries = new TransactionBuilder(BUYING_GROCERIES).withType("expense").build();
        assertFalse(editedGroceries.getTypeString().equalsIgnoreCase("income"));
    }

    @Test
    public void toStringMethod() {
        String expected =
                Transaction.class.getCanonicalName()
                        + "{name=" + BUYING_GROCERIES.getName()
                        + ", type=" + BUYING_GROCERIES.getType()
                        + ", amount=" + BUYING_GROCERIES.getAmount()
                        + ", dateTime=" + BUYING_GROCERIES.getDateTime()
                        + ", location=" + BUYING_GROCERIES.getLocation()
                        + ", categories=" + BUYING_GROCERIES.getCategories()
                        + "}";
        assertEquals(expected, BUYING_GROCERIES.toString());
    }

    @Test
    public void hashCode_test() {
        assertEquals(BUYING_GROCERIES.hashCode(), BUYING_GROCERIES.hashCode());
        assertNotEquals(BUYING_GROCERIES.hashCode(), WORK_AT_LIHO.hashCode());
    }
}
