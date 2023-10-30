package unicash.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static unicash.testutil.TypicalBudgets.DAILY;
import static unicash.testutil.TypicalBudgets.MONTHLY;
import static unicash.testutil.TypicalBudgets.WEEKLY;

import org.junit.jupiter.api.Test;

import unicash.testutil.BudgetBuilder;

public class BudgetTest {
    @Test
    public void equals() {
        // same values -> returns true
        Budget groceriesCopy = new BudgetBuilder(DAILY).build();
        assertEquals(DAILY, groceriesCopy);

        // same object -> returns true
        assertEquals(DAILY, DAILY);

        // null -> returns false
        assertNotEquals(null, groceriesCopy);

        // null -> returns false
        assertNotEquals(null, DAILY);

        // different type -> returns false
        assertNotEquals(5, DAILY);

        assertFalse(DAILY.equals(5));

        // different person -> returns false
        assertNotEquals(DAILY, WEEKLY);

        // different interval -> returns false
        Budget editedDaily = new BudgetBuilder(DAILY).withInterval("week").build();
        assertNotEquals(DAILY, editedDaily);

        // different amount -> returns false
        editedDaily = new BudgetBuilder(DAILY).withAmount(1330.15).build();
        assertNotEquals(DAILY, editedDaily);
    }

    @Test
    public void toStringMethod() {
        String expected =
                Budget.class.getCanonicalName()
                        + "{amount=" + DAILY.getAmount()
                        + ", interval=" + DAILY.getInterval()
                        + "}";
        assertEquals(expected, DAILY.toString());
    }

    @Test
    public void setBudget() {
        Budget budget = new BudgetBuilder(DAILY).build();
        budget.setBudget(MONTHLY);
        assertEquals(MONTHLY, budget);
    }

    @Test
    public void hashCode_test() {
        assertEquals(DAILY.hashCode(), DAILY.hashCode());
        assertNotEquals(DAILY.hashCode(), MONTHLY.hashCode());
    }
}
