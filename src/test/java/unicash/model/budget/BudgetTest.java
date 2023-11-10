package unicash.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static unicash.testutil.BudgetBuilder.DEFAULT_AMOUNT;
import static unicash.testutil.TypicalBudgets.DAILY;
import static unicash.testutil.TypicalBudgets.MONTHLY;
import static unicash.testutil.TypicalBudgets.WEEKLY;

import org.junit.jupiter.api.Test;

import unicash.model.commons.Amount;
import unicash.testutil.BudgetBuilder;

public class BudgetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Both parameters null
        assertThrows(NullPointerException.class, () -> new Budget(null, null));

        // Amount is null
        assertThrows(NullPointerException.class, () -> new Budget(null, new Interval("day")));

        // Interval is null
        assertThrows(NullPointerException.class, () -> new Budget(new Amount(DEFAULT_AMOUNT), null));
    }

    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_validBudget_throwsNullPointerException() {
        Budget budget = new BudgetBuilder().withAmount(300).withInterval("day").build();
        Budget anotherBudget = new Budget(budget);
        assertEquals(budget, anotherBudget);
    }

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
    public void setBudget_null_throwsNullPointerException() {
        Budget budget = new BudgetBuilder(DAILY).build();
        assertThrows(NullPointerException.class, () -> budget.setBudget(null));
    }

    @Test
    public void setBudget_validBudget_success() {
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
