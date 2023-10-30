package unicash.model.transaction;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;


public class DateTimeTest {
    private static final Clock clock = Clock.fixed(Instant.parse("2014-12-21T10:15:30.00Z"), ZoneId.of("UTC"));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime(null));
        assertThrows(NullPointerException.class, () -> new DateTime("18-08-2001 18:18", null));
        assertThrows(NullPointerException.class, () -> new DateTime(null, null));
    }

    @Test
    public void constructor_noDateTime_setDefault() {
        String empty = "";
        assertEquals("21 Dec 2014 10:15", new DateTime(empty, clock).toString());
    }

    @Test
    public void isValidDate() {
        assertFalse(DateTime.isValidDateTime("1-1-2001 18:18")); // wrong day and month format
        assertFalse(DateTime.isValidDateTime("01-1-2001 17:1")); // invalid time
        assertFalse(DateTime.isValidDateTime("01-01-2001")); // missing time
        assertFalse(DateTime.isValidDateTime("18:18")); // missing date
        assertFalse(DateTime.isValidDateTime("20-31-2001 18:18")); // invalid date
        assertFalse(DateTime.isValidDateTime("20-01-2001 25:18")); // invalid time
        assertFalse(DateTime.isValidDateTime("31-02-2001 18:18")); // invalid date
    }

    @Test
    public void isValidDate_acceptedFormats() {
        assertTrue(DateTime.isValidDateTime("01-01-2001 18:18")); //dd-MM-uuuu HH:mm
        assertTrue(DateTime.isValidDateTime("2001-01-01 18:18")); //uuuu-MM-dd HH:mm
        assertTrue(DateTime.isValidDateTime("01 Jan 2001 18:18")); //dd MMM uuuu HH:mm
    }

    @Test
    public void inputString() {
        DateTime emptyDateTime = new DateTime("", clock);
        String stringifyEmptyDateTime = emptyDateTime.inputString();
        assertEquals("21 Dec 2014 10:15", stringifyEmptyDateTime);

        DateTime dateTimeFormatOne = new DateTime("18-12-2023 01:01");
        String stringifyOne = dateTimeFormatOne.inputString();
        assertEquals("18-12-2023 01:01", stringifyOne);

        DateTime dateTimeFormatTwo = new DateTime("2023-12-18 01:01");
        String stringifyTwo = dateTimeFormatTwo.inputString();
        assertEquals("2023-12-18 01:01", stringifyTwo);

        DateTime dateTimeFormatThree = new DateTime("18 Dec 2023 01:01");
        String stringifyThree = dateTimeFormatThree.inputString();
        assertEquals("18 Dec 2023 01:01", stringifyThree);
    }

    @Test
    public void equals() {
        DateTime datetime = new DateTime("01-01-2001 01:01");

        // same values -> returns true
        assertEquals(datetime, new DateTime("01-01-2001 01:01"));

        // same object -> returns true
        assertEquals(datetime, datetime);

        // null -> returns false
        assertNotEquals(null, datetime);

        // different types -> returns false
        assertNotEquals("hi", datetime);

        // different year -> returns false
        assertNotEquals(datetime, new DateTime("01-01-2000 01:01"));

        // different month -> returns false
        assertNotEquals(datetime, new DateTime("01-02-2001 01:01"));

        // different day -> returns false
        assertNotEquals(datetime, new DateTime("02-01-2001 01:01"));

        // different time -> returns false
        assertNotEquals(datetime, new DateTime("01-01-2001 02:02"));

        assertFalse(datetime.equals(2));
    }

    @Test
    public void differentFormatEquals() {
        //check if different formats are equal using the DateTime.java equals() method
        DateTime datetime1 = new DateTime("01-01-2001 01:01");
        DateTime datetime2 = new DateTime("2001-01-01 01:01");
        DateTime datetime3 = new DateTime("01 Jan 2001 01:01");

        assertTrue(datetime1.equals(datetime2));
        assertTrue(datetime2.equals(datetime3));
        assertTrue(datetime1.equals(datetime3));
    }

    @Test
    public void toStringMethod() {
        DateTime dateTime = new DateTime("18-08-2023 01:01");
        assertEquals(dateTime.toString(), "18 Aug 2023 01:01");
    }

    @Test
    public void hashCode_test() {
        DateTime dateTime1 = new DateTime("01-01-2001 12:12");
        DateTime dateTime2 = new DateTime("01-01-2001 12:12");
        DateTime dateTime3 = new DateTime("01-01-2002 12:12");
        assertEquals(dateTime1.hashCode(), dateTime2.hashCode());
        assertNotEquals(dateTime1.hashCode(), dateTime3.hashCode());
    }
}
