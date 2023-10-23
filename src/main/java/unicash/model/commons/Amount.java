package unicash.model.commons;

import static unicash.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's amount. Amounts are stored as a double with
 * a maximum precision of 2 decimal places as with most general financial
 * transactions in the real world. All amounts must be positive, and amounts
 * exceeding the above mandated precision will be rounded (not truncated) to
 * 2 decimal places. Amounts, if represented as a string, will be prefixed with
 * a currency indicator.
 */
public class Amount {

    public static final String MESSAGE_SIGN_WARNING =
            "Amounts must be positive.";

    public static final String MESSAGE_RANGE_LIMITS =
            "Amounts must be between -2^31 and 2^31.";

    public static final String MESSAGE_PRECISION_WARNING =
            "Amounts must not have more than 2 decimal places!";

    public static final String MESSAGE_STRING_CONSTRAINTS =
            "Amounts must be valid if entered as a String e.g. $[AMOUNT] ";

    public static final String MESSAGE_CONSTRAINTS_COMPILED = MESSAGE_SIGN_WARNING
            + MESSAGE_RANGE_LIMITS
            + MESSAGE_PRECISION_WARNING
            + MESSAGE_STRING_CONSTRAINTS;

    public static final String MESSAGE_CONSTRAINTS =
            "Amounts must be positive.";


    // Indicates the currency currently being used, set to dollar by default.
    public static final String CURRENCY_INDICATOR = "$";

    public final double amount;

    /**
     * Constructs an {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(double amount) {
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);

        /* A strict rounding of input amounts is enforced to avoid calculation discrepancies */
        this.amount = Math.round(amount * 100.0) / 100.0;
    }

    /**
     * Constructs an {@code Amount} given a String input.
     *
     * @param amount A valid amount String.
     */
    public Amount(String amount) {
        checkArgument(isValidAmountString(amount), MESSAGE_STRING_CONSTRAINTS);

        double parsedAmount = Double.parseDouble(amount.substring(1));

        /* A strict rounding of input amounts is enforced to avoid calculation discrepancies */
        this.amount = Math.round(parsedAmount * 100.0) / 100.0;
    }

    /**
     * Returns true if a given amount is a non-negative value.
     */
    public static boolean isValidAmount(double amount) {
        return amount >= 0.00;
    }

    /**
     * Returns true if a given amount is a non-negative value.
     */
    public static boolean isPositiveAmount(double amount) {
        return amount >= 0.00;
    }

    /**
     * Returns true if a given amount is within the appropriate value.
     */
    public static boolean isWithinRange(double amount) {
        return (amount >= 0.00 && amount < Integer.MAX_VALUE);
    }

    /**
     * Returns true if a given amount has less than or equal to two decimal places.
     */
    public static boolean hasNoMoreThanTwoDecimalPlaces(double amount) {
        String stringValue = Double.toString(amount);
        int decimalIndex = stringValue.indexOf(".");

        // If there's no decimal point, then it has no decimal places
        if (decimalIndex == -1) {
            return false;
        }
        int decimalCount = stringValue.length() - decimalIndex - 1;

        return decimalCount > 2;
    }

    /**
     * Returns true if a given amount, when parsed, is a non-negative value.
     * Strict validation in place, amount must start with the currency symbol.
     */
    public static boolean isValidAmountString(String amount) {
        amount = amount.trim();
        String[] amountCharArray = amount.split("");

        if (!amountCharArray[0].equals(CURRENCY_INDICATOR)) {
            return false;
        }

        try {
            Double.parseDouble(amount.substring(1));
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        return Double.hashCode(amount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Amount)) {
            return false;
        }

        return amount == ((Amount) other).amount;
    }

    /**
     * Returns a rounded two-decimal precision String version of an {@code Amount}.
     */
    public static String amountToDecimalString(Amount amt) {
        double roundedAmount = Math.round(amt.amount * 100.0) / 100.0;
        String formattedNumberString = String.format("%.2f", roundedAmount);

        return formattedNumberString;
    }


    //TODO: Consider making currency indicators available for the user to input
    /**
     * Returns the amount along as a string with no currency prefix.
     * Useful for tests that require simulation of raw user input.
     *
     * @return the amount as a String
     */
    public String amountString() {
        return Double.toString(amount);
    }

    @Override
    public String toString() {
        return CURRENCY_INDICATOR + String.format("%.2f", this.amount);
    }
}

