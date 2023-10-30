package unicash.logic.commands;

import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import unicash.model.Model;
import unicash.model.ModelManager;

public class ExitCommandTest {


    @Test
    public void execute_exit_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);

        assertCommandSuccess(new ExitCommand(), model,
                expectedCommandResult, expectedModel);
    }
}

