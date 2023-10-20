package unicash.logic.commands;

import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unicash.logic.commands.ShowUserGuideCommand.SHOWING_USER_GUIDE_MESSAGE;

import org.junit.jupiter.api.Test;

import unicash.model.Model;
import unicash.model.ModelManager;

public class ShowUserGuideCommandTest {

    @Test
    public void execute_showUserGuide_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(SHOWING_USER_GUIDE_MESSAGE, false, false, true);

        assertCommandSuccess(new ShowUserGuideCommand(), model,
                expectedCommandResult, expectedModel);
    }
}
