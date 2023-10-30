package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");
        CommandResult showSummaryCommandResult = new CommandResult("feedback", false, false, true);

        // same values -> returns true
        assertEquals(commandResult, new CommandResult("feedback"));
        assertEquals(commandResult, new CommandResult("feedback", false, false));
        assertEquals(commandResult, new CommandResult("feedback", false, false, false));
        assertEquals(showSummaryCommandResult, new CommandResult("feedback", false, false, true));

        // same object -> returns true
        assertEquals(commandResult, commandResult);

        // null -> returns false
        assertNotEquals(null, commandResult);

        // different types -> returns false
        assertNotEquals(0.5f, commandResult);
        assertFalse(commandResult.equals(5));

        // different feedbackToUser value -> returns false
        assertNotEquals(commandResult, new CommandResult("different"));

        // different showHelp value -> returns false
        assertNotEquals(commandResult, new CommandResult("feedback", true, false));

        // different exit value -> returns false
        assertNotEquals(commandResult, new CommandResult("feedback", false, true));

        // different showSummary value -> returns false
        assertNotEquals(commandResult, showSummaryCommandResult);
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());

        // different showSummary value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", showSummary=" + commandResult.isShowSummary() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
