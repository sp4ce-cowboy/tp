package unicash.logic.commands;

import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unicash.logic.commands.SummaryCommand.SHOWING_SUMMARY_MESSAGE;

import org.junit.jupiter.api.Test;

import unicash.model.Model;
import unicash.model.ModelManager;

public class SummaryCommandTest {
    @Test
    public void execute_summary_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(SHOWING_SUMMARY_MESSAGE, false, false, true);

        assertCommandSuccess(new SummaryCommand(), model,
                expectedCommandResult, expectedModel);
    }
}
