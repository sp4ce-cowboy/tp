package unicash.model.budget;

import static java.util.Objects.requireNonNull;
import static unicash.commons.util.AppUtil.checkArgument;

import unicash.commons.enums.BudgetInterval;

/**
 * Represents a Budget's interval.
 */
public class Interval {

    public static final String MESSAGE_CONSTRAINTS =
            String.format("Transaction must be of the following intervals: %s", BudgetInterval.listBudgetIntervals());

    public final BudgetInterval interval;

    /**
     * Constructs a {@code Interval}.
     *
     * @param interval A valid interval.
     */
    public Interval(String interval) {
        requireNonNull(interval);
        checkArgument(isValidInterval(interval), MESSAGE_CONSTRAINTS);
        this.interval = BudgetInterval.parseInterval(interval);
    }

    /**
     * Returns true if a given string is a valid interval for a transaction.
     */
    public static boolean isValidInterval(String test) {
        return BudgetInterval.isValidBudgetInterval(test);
    }


    @Override
    public int hashCode() {
        return interval.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Interval)) {
            return false;
        }

        return interval.equals(((Interval) other).interval);
    }

    @Override
    public String toString() {
        return interval.getOriginalString();
    }
}
