package unicash.logic.commands;

import unicash.model.Model;

/**
 * Shows UniCa$h help via a Help Window.
 */
public class HelpCommandUniCash extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows UniCa$h usage instructions.\n"
            + "\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened UniCa$h help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false, false);
    }
}
