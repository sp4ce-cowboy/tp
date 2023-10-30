package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalBudgets.DAILY;
import static unicash.testutil.TypicalBudgets.MONTHLY;

import org.junit.jupiter.api.Test;

public class EditBudgetCommandTest {

    @Test
    public void constructor_nullTransaction_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBudgetCommand(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        var command = new EditBudgetCommand(DAILY);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void equals() {
        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(MONTHLY);
        EditBudgetCommand editBudgetCommandCopy = new EditBudgetCommand(MONTHLY);
        EditBudgetCommand editDifferentBudgetCommand = new EditBudgetCommand(DAILY);

        //same values -> returns true
        assertEquals(editBudgetCommand, editBudgetCommandCopy);

        //same budget command -> returns true
        assertEquals(editBudgetCommand, editBudgetCommand);

        //null -> returns false
        assertNotEquals(null, editBudgetCommand);

        //different type -> returns false
        assertNotEquals(editDifferentBudgetCommand, editBudgetCommand);

        assertFalse(editBudgetCommand.equals(5));
    }

    @Test
    public void toStringMethod() {
        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(MONTHLY);
        String expected =
                EditBudgetCommand.class.getCanonicalName() + "{budget=" + MONTHLY + "}";
        assertEquals(expected, editBudgetCommand.toString());
    }

}
