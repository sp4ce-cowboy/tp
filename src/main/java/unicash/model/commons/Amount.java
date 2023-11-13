package unicash.model.commons;

import static unicash.commons.util.AppUtil.checkArgument;

/**
 * Represents a transaction or budget's amount.
 *
 * <p>Amounts are stored as a double and are rounded to the nearest 2 decimal places.</p>
 *
 * <p>Amounts must be positive and cannot exceed the value of {@code Integer.MAX_VALUE}.</p>
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amounts must be within range of [0, 2147483647] and either start with $ or nothing at all";

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
        this.amount = round(amount);
    }

    /**
     * Constructs an {@code Amount} given a String input with a prefixed
     * currency symbol.
     *
     * @param amount A valid amount String.
     */
    public Amount(String amount) {
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);

        var trimmedAmount = amount.trim();
        var starting = trimmedAmount.startsWith(CURRENCY_INDICATOR) ? 1 : 0;
        double parsedAmount = Double.parseDouble(trimmedAmount.substring(starting));

        /* A strict rounding of input amounts is enforced to avoid calculation discrepancies */
        this.amount = round(parsedAmount);
    }

    /**
     * Returns true if a given amount is a non-negative value.
     */
    public static boolean isValidAmount(double amount) {
        return amount >= 0.00 && amount <= (double) Integer.MAX_VALUE;
    }

    /**
     * Returns true if a given amount, when parsed, is a non-negative value.
     *
     * <p>A valid string amount is either of format $xx.xx or xx.xx.</p>
     *
     * <p>Trailing/leading whitespace is trimmed first.</p>
     */
    public static boolean isValidAmount(String amount) {
        var trimmedAmount = amount.trim();

        var starting = trimmedAmount.startsWith(CURRENCY_INDICATOR) ? 1 : 0;

        try {
            var parsedAmount = Double.parseDouble(trimmedAmount.substring(starting));
            return isValidAmount(parsedAmount);
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns a rounded two-decimal precision String version of an {@code Amount}.
     */
    public static String amountToDecimalString(Amount amt) {
        return String.format("%.2f", amt.amount);
    }


    /**
     * Returns the amount along as a string with no currency prefix.
     * Useful for tests that require simulation of raw user input.
     *
     * @return the amount as a String
     */
    public String amountString() {
        return Double.toString(amount);
    }

    private static double round(double amount) {
        return Math.round(amount * 100.0) / 100.0;
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

    @Override
    public String toString() {
        return String.format("%s%.2f", CURRENCY_INDICATOR, amount);
    }
}

