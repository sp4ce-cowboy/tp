package unicash.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import unicash.logic.parser.Prefix;


public class UniCashMessagesTest {

    @Test
    public void getErrorMessageForDuplicatePrefixes_noPrefixes_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> UniCashMessages.getErrorMessageForDuplicatePrefixes());
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_singlePrefix_returnsFormattedMessage() {
        String errorMessage = UniCashMessages.getErrorMessageForDuplicatePrefixes(new Prefix("n/"));
        assertEquals("Multiple values specified for the following single-valued field(s): n/", errorMessage);
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_multiplePrefixes_returnsFormattedMessage() {
        String errorMessage = UniCashMessages.getErrorMessageForDuplicatePrefixes(new Prefix("n/"), new Prefix("p/"));
        String expectedMessage = "Multiple values specified for the following single-valued field(s): n/ p/";
        assertEquals(expectedMessage, errorMessage);
    }

}


