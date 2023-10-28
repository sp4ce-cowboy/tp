package unicash.model.budget;

import static unicash.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import unicash.commons.util.ToStringBuilder;
import unicash.model.commons.Amount;

/**
 * Represents a Budget in UniCash.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {
    private Amount amount;
    private Interval interval;


    /**
     * Constructs a Budget with all fields populated.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Budget(Amount amount, Interval interval) {
        requireAllNonNull(amount, interval);
        this.amount = amount;
        this.interval = interval;
    }

    public Amount getAmount() {
        return amount;
    }

    public Interval getInterval() {
        return interval;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, interval);
    }

    /**
     * Returns true if both budgets have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return amount.equals(otherBudget.amount)
                && interval.equals(otherBudget.interval);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("amount", amount)
                .add("interval", interval)
                .toString();
    }

    public void setBudget(Budget budget) {
        amount = budget.getAmount();
        interval = budget.getInterval();
    }
}
