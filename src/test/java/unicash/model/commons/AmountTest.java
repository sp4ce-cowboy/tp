package unicash.model.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmountTest {
    @Test
    public void constructor_negativeAmount_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Amount(-10));
    }

    @Test
    public void isValidAmount() {
        assertFalse(Amount.isValidAmount(-1));
        assertFalse(Amount.isValidAmount(-0.000001));
        assertTrue(Amount.isValidAmount(0.000));
        assertTrue(Amount.isValidAmount(10));
        assertTrue(Amount.isValidAmount(12.13));
    }

    @Test
    public void equals() {
        Amount amount = new Amount(12.13);
        assertEquals(12.13, amount.amount);
        assertEquals(amount, amount);
        assertEquals(amount, new Amount(12.13));
        assertNotEquals(amount, null);
        assertNotEquals(amount, new Amount(12.16));
    }

    @Test
    public void amountToDecimalString_standardConversion_returnsCorrectString() {
        Amount amt = new Amount(1234.5678);
        String result = Amount.amountToDecimalString(amt);
        assertEquals("1234.57", result);
    }

    @Test
    public void amountToDecimalString_roundingRequired_returnsRoundedString() {
        Amount amt = new Amount(45.678);
        String result = Amount.amountToDecimalString(amt);
        assertEquals("45.68", result);
    }
    @Test
    void testHasNoMoreThanTwoDecimalPlaces() {
        // Test cases with no more than two decimal places
        assertTrue(Amount.hasNoMoreThanTwoDecimalPlaces(10));
        assertTrue(Amount.hasNoMoreThanTwoDecimalPlaces(10.0));
        assertTrue(Amount.hasNoMoreThanTwoDecimalPlaces(10.1));
        assertTrue(Amount.hasNoMoreThanTwoDecimalPlaces(10.12));
        assertTrue(Amount.hasNoMoreThanTwoDecimalPlaces(-10.12));

        // Test cases with more than two decimal places
        assertFalse(Amount.hasNoMoreThanTwoDecimalPlaces(10.123));
        assertFalse(Amount.hasNoMoreThanTwoDecimalPlaces(-10.123));

        // Test cases with edge cases
        assertTrue(Amount.hasNoMoreThanTwoDecimalPlaces(0));
        assertTrue(Amount.hasNoMoreThanTwoDecimalPlaces(0.0));
        assertTrue(Amount.hasNoMoreThanTwoDecimalPlaces(-0.0));
        assertFalse(Amount.hasNoMoreThanTwoDecimalPlaces(0.001));
    }
}
