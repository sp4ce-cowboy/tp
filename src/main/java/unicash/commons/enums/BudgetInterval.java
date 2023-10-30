package unicash.commons.enums;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * Represents the budget interval for a given {@code Budget}.
 */
public enum BudgetInterval {
    DAY("day"),
    WEEK("week"),
    MONTH("month");

    private final String budgetInterval;

    /**
     * Constructs a BudgetInterval enum.
     *
     * @param budgetInterval the value representing the string value of the enum.
     */
    BudgetInterval(String budgetInterval) {
        this.budgetInterval = budgetInterval;
    }

    /**
     * Returns the BudgetInterval object from a given string.
     *
     * @param budgetInterval a string value of the enum represented by the value provided.
     * @return a BudgetInterval object of a transaction.
     */
    public static BudgetInterval parseInterval(String budgetInterval) {
        return Arrays.stream(values())
                .filter(type -> type.budgetInterval.equals(budgetInterval))
                .findFirst().orElseThrow();
    }

    /**
     * Returns a boolean value if a given string is a valid BudgetInterval.
     *
     * @param test a string value to be tested.
     * @return a boolean value if the given string is a valid enum.
     */
    public static boolean isValidBudgetInterval(String test) {
        return Arrays.stream(values())
                .anyMatch(type -> type.budgetInterval.equals(test));
    }

    /**
     * Returns a list of all possible budget intervals separated by commas.
     */
    public static String listBudgetIntervals() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (BudgetInterval type : values()) {
            stringJoiner.add(type.budgetInterval);
        }
        return stringJoiner.toString();
    }

    /**
     * Returns the string value of a {@code BudgetInterval}.
     *
     * @return the string representation of a BudgetInterval.
     */
    public String getOriginalString() {
        return budgetInterval;
    }
}
