package unicash.testutil;

import unicash.model.budget.Budget;

/**
 * A utility class containing a list of {@code Budget} objects to be used in tests.
 */
public class TypicalBudgets {
    public static final Budget DAILY = new BudgetBuilder()
            .withAmount(16.75)
            .withInterval("day")
            .build();

    public static final Budget WEEKLY = new BudgetBuilder()
            .withAmount(234.50)
            .withInterval("week")
            .build();

    public static final Budget MONTHLY = new BudgetBuilder()
            .withAmount(888)
            .withInterval("month")
            .build();
}
