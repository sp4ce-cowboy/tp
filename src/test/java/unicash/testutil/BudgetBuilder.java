package unicash.testutil;

import unicash.model.budget.Budget;
import unicash.model.budget.Interval;
import unicash.model.commons.Amount;

/**
 * A utility class to help with building Budget objects.
 */
public class BudgetBuilder {

    public static final double DEFAULT_AMOUNT = 123.45;
    public static final String DEFAULT_INTERVAL = "week";

    private Amount amount;
    private Interval interval;

    /**
     * Creates a {@code BudgetBuilder} with the default details.
     */
    public BudgetBuilder() {
        amount = new Amount(DEFAULT_AMOUNT);
        interval = new Interval(DEFAULT_INTERVAL);
    }

    /**
     * Initializes the BudgetBuilder with the data of {@code budgetToCopy}.
     */
    public BudgetBuilder(Budget budgetToCopy) {
        amount = budgetToCopy.getAmount();
        interval = budgetToCopy.getInterval();
    }

    /**
     * Sets the {@code Amount} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withAmount(double amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withInterval(String interval) {
        this.interval = new Interval(interval);
        return this;
    }

    public Budget build() {
        return new Budget(amount, interval);
    }
}
