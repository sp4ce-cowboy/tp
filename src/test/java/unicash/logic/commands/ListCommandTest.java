package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import unicash.model.Model;
import unicash.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals_nullOther_returnsFalse() {
        assertNotEquals(new ListCommand(), null);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(new ListCommand().equals(5));
    }

    @Test
    public void equals_otherListCommand_returnsTrue() {
        var first = new ListCommand();
        var second = new ListCommand();
        assertEquals(first, second);
    }
}
