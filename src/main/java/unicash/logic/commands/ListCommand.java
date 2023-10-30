package unicash.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import unicash.commons.util.CommandUsage;
import unicash.commons.util.ExampleGenerator;
import unicash.model.Model;

/**
 * Lists all transactions in UniCa$h to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all transactions";

    private static final Logger logger = Logger.getLogger("ListCommandLogger");

    public static final String MESSAGE_USAGE = new CommandUsage.Builder()
            .setCommandWord(COMMAND_WORD)
            .setDescription("Lists all transactions in UniCa$h.")
            .setExample(ExampleGenerator.generate(COMMAND_WORD))
            .build()
            .toString();

    public static final String MESSAGE_FAILURE = "Command not recognised. Try using the command " + COMMAND_WORD
            + " without any parameters instead.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.log(Level.INFO, "Executing list command");
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        logger.log(Level.INFO, "List command executed successfully");
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        // Nothing to compare so all ListCommand instances are equal to each other
        return other instanceof ListCommand;
    }
}
