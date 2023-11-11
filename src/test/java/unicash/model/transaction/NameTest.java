package unicash.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class NameTest {

    private static final String[] INVALID_NAMES = {
        "", // empty string
        " ", // whitespace
        "^", // only non-supported characters
        "peter*", // non-supported characters
        "a".repeat(501) // too long
    };

    private static final String[] VALID_NAMES = {
        "peter jack", // alphabets only
        "12345", // numbers only
        "peter the 2nd", // alphanumeric
        "Capital Tan", // capital letters
        "David Roger Jackson Ray Jr 2nd", // longer names
        "hello (world) @ NUS School_of-Computing #1234 & Longer, longer.", // Symbols
        "a".repeat(500) // exactly max length characters
    };

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        for (var name : INVALID_NAMES) {
            assertFalse(Name.isValidName(name));
        }

        // valid name
        for (var name : VALID_NAMES) {
            assertTrue(Name.isValidName(name));
        }
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertEquals(name, new Name("Valid Name"));

        // same object -> returns true
        assertEquals(name, name);

        // null -> returns false
        assertNotEquals(null, name);

        // different types -> returns false
        assertFalse(name.equals(5));

        // different values -> returns false
        assertNotEquals(name, new Name("Other Valid Name"));
    }

    @Test
    public void hashCodeMethod() {
        var name = new Name("Valid name");
        var nameCopy = new Name(name.fullName);
        assertEquals(name.hashCode(), nameCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        var name = new Name("Valid name");
        assertEquals("Valid name", name.toString());
    }
}
