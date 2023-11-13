package unicash.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LocationTest {

    private static final String[] INVALID_LOCATIONS = {
        "", // empty string
        " ", // whitespace
        "^", // only non-supported characters
        "peter*", // non-supported characters
        "a".repeat(501) // too long
    };

    private static final String[] VALID_LOCATIONS = {
        "fairprice", // alphabets only
        "12345", // numbers only
        "block 283", // alphanumeric characters
        "Fairprice", // with capital letters
        "Fairprice at NUS University Town", // long names
        "Fairprice (NUS)", // ()
        "Fairprice #02-160", // # and -
        "Fairprice (NUS_UTown)", // _
        "Ben & Jerry", // &
        "First Avenue, Block 283", // ,
        "First Avenue, Block 28.3", // ,
        "-", // Blank location
        "a".repeat(500)// exactly max length characters
    };

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
        // null location
        assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // invalid location
        for (var location : INVALID_LOCATIONS) {
            assertFalse(Location.isValidLocation(location));
        }

        // valid location
        for (var location : VALID_LOCATIONS) {
            assertTrue(Location.isValidLocation(location));
        }
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
        assertFalse(name.equals(5));

        // different values -> returns false
        assertNotEquals(name, new Location("Other Valid Location"));
    }

    @Test
    public void hashCodeMethod() {
        var location = new Location("Valid location");
        var locationCopy = new Location(location.location);
        assertEquals(location.hashCode(), locationCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        assertEquals("Valid location", new Location("Valid location").toString());
        assertEquals("-", new Location("").toString());
    }
}
