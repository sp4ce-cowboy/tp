package unicash.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Type(null));
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        String[] invalidTypes = new String[] {"", "1", "Wrong"};
        for (String invalidType: invalidTypes) {
            assertThrows(IllegalArgumentException.class, () -> new Type(invalidType));
        }
    }

    @Test
    public void isValidType() {
        // null name
        assertFalse(Type.isValidType(null));

        // invalid name
        assertFalse(Type.isValidType("")); // empty string
        assertFalse(Type.isValidType(" ")); // spaces only
        assertFalse(Type.isValidType("^")); // wrong type
        assertFalse(Type.isValidType("peter*")); // wrong type
        assertFalse(Type.isValidType("transfers")); // wrong type

        // valid name
        assertTrue(Type.isValidType("income")); // income
        assertTrue(Type.isValidType("expense")); // expense
    }

    @Test
    public void equals() {
        Type type = new Type("income");

        // same values -> returns true
        assertEquals(type, new Type("income"));

        // same object -> returns true
        assertEquals(type, type);

        // null -> returns false
        assertNotEquals(null, type);

        // different types -> returns false
        assertNotEquals(5.0f, type);

        assertFalse(type.equals(5));

        // different values -> returns false
        assertNotEquals(type, new Type("expense"));
    }

    @Test
    public void hashCode_test() {
        Type expense = new Type("expense");
        Type income = new Type("income");
        Type income2 = new Type("income");
        assertEquals(income.hashCode(), income2.hashCode());
        assertNotEquals(expense.hashCode(), income.hashCode());
    }
}
