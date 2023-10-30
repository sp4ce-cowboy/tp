package unicash.logic.commands;

import static unicash.logic.UniCashMessages.MESSAGE_UNICASH_WELCOME;

import unicash.commons.util.CommandUsage;
import unicash.commons.util.ExampleGenerator;
import unicash.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = new CommandUsage.Builder()
            .setCommandWord(COMMAND_WORD)
            .setDescription("Shows UniCa$h usage instructions.")
            .setExample(ExampleGenerator.generate(COMMAND_WORD))
            .build()
            .toString();

    public static final String SHOWING_HELP_MESSAGE = MESSAGE_UNICASH_WELCOME;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
