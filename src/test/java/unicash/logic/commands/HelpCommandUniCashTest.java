package unicash.logic.commands;

import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unicash.logic.commands.HelpCommandUniCash.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import unicash.model.Model;
import unicash.model.ModelManager;

public class HelpCommandUniCashTest {

    @Test
    public void execute_help_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(SHOWING_HELP_MESSAGE, true, false, false);

        assertCommandSuccess(new HelpCommandUniCash(), model,
                expectedCommandResult, expectedModel);
    }
}
