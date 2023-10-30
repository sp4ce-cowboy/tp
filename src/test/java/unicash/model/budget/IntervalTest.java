package unicash.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IntervalTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Interval(null));
    }

    @Test
    public void constructor_invalidInterval_throwsIllegalArgumentException() {
        String[] invalidIntervals = new String[] {"", "1", "Wrong"};
        for (String invalidInterval: invalidIntervals) {
            assertThrows(IllegalArgumentException.class, () -> new Interval(invalidInterval));
        }
    }

    @Test
    public void isValidInterval() {
        // null name
        assertFalse(Interval.isValidInterval(null));

        // invalid name
        assertFalse(Interval.isValidInterval("")); // empty string
        assertFalse(Interval.isValidInterval(" ")); // spaces only
        assertFalse(Interval.isValidInterval("^")); // wrong interval
        assertFalse(Interval.isValidInterval("peter*")); // wrong interval
        assertFalse(Interval.isValidInterval("transfers")); // wrong interval
        assertFalse(Interval.isValidInterval("day&")); // wrong interval
        assertFalse(Interval.isValidInterval(" day ")); // valid day with whitespace
        assertFalse(Interval.isValidInterval(" week ")); // valid week with whitespace
        assertFalse(Interval.isValidInterval(" month ")); // valid month with whitespace

        // valid name
        assertTrue(Interval.isValidInterval("day")); // day
        assertTrue(Interval.isValidInterval("week")); // week
        assertTrue(Interval.isValidInterval("month")); // month
    }

    @Test
    public void equals() {
        Interval interval = new Interval("week");

        // same values -> returns true
        assertEquals(interval, new Interval("week"));

        // same object -> returns true
        assertEquals(interval, interval);

        // null -> returns false
        assertNotEquals(null, interval);

        // different intervals -> returns false
        assertNotEquals(5.0f, interval);

        assertFalse(interval.equals(5));

        // different values -> returns false
        assertNotEquals(interval, new Interval("month"));
    }

    @Test
    public void hashCode_test() {
        Interval week = new Interval("week");
        Interval week2 = new Interval("week");
        Interval month = new Interval("month");
        assertEquals(week.hashCode(), week2.hashCode());
        assertNotEquals(week.hashCode(), month.hashCode());
    }
}
