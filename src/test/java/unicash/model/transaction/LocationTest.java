package unicash.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_noLocation_setDefault() {
        assertEquals("-", new Location("").toString());
        assertEquals("-", new Location(" ").toString());
    }

    @Test
    public void isValidLocation() {
        // null name
        assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // invalid name
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation("^")); // only non-allowed characters
        assertFalse(Location.isValidLocation("peter*")); // contains non-allowed characters

        // valid name
        assertTrue(Location.isValidLocation("fairprice")); // alphabets only
        assertTrue(Location.isValidLocation("12345")); // numbers only
        assertTrue(Location.isValidLocation("block 283")); // alphanumeric characters
        assertTrue(Location.isValidLocation("Fairprice")); // with capital letters
        assertTrue(Location.isValidLocation("Fairprice at NUS University Town")); // long names
        assertTrue(Location.isValidLocation("Fairprice (NUS)")); // ()
        assertTrue(Location.isValidLocation("Fairprice #02-160")); // # and -
        assertTrue(Location.isValidLocation("Fairprice (NUS_UTown)")); // _
        assertTrue(Location.isValidLocation("Ben & Jerry")); // &
        assertTrue(Location.isValidLocation("First Avenue, Block 283")); // ,
        assertTrue(Location.isValidLocation("First Avenue, Block 28.3")); // ,
        assertTrue(Location.isValidLocation("-")); // Blank location
    }

    @Test
    public void equals() {
        Location name = new Location("Valid Location");

        // same values -> returns true
        assertEquals(name, new Location("Valid Location"));

        // same object -> returns true
        assertEquals(name, name);

        // null -> returns false
        assertNotEquals(null, name);

        // different types -> returns false
        assertNotEquals(5.0f, name);

        assertFalse(name.equals(5));

        // different values -> returns false
        assertNotEquals(name, new Location("Other Valid Location"));
    }
}
