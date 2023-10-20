package unicash.logic.commands;

import unicash.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class ShowUserGuideCommand extends Command {

    public static final String COMMAND_WORD = "show_user_guide";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens local UniCa$h user guide.\n"
            + "\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_USER_GUIDE_MESSAGE = "Opened UniCa$h User Guide.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_USER_GUIDE_MESSAGE,
                false, false, true);
    }
}
