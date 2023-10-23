package unicash.logic.commands;

import static unicash.logic.UniCashMessages.MESSAGE_UNICASH_WELCOME;
import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import unicash.model.Model;
import unicash.model.ModelManager;

public class HelpCommandTest {

    @Test
    public void execute_help_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(MESSAGE_UNICASH_WELCOME, true, false);

        assertCommandSuccess(new HelpCommand(), model,
                expectedCommandResult, expectedModel);
    }
}
