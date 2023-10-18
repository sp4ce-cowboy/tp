package unicash.logic.commands;

import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unicash.logic.commands.HelpCommandUniCash.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import unicash.model.Model;
import unicash.model.ModelManager;

public class HelpCommandUniCashTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult =
                new CommandResult(SHOWING_HELP_MESSAGE, true, false);

        assertCommandSuccess(new HelpCommandUniCash(), model,
                expectedCommandResult, expectedModel);
    }
}