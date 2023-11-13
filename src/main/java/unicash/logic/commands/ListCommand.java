package unicash.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import unicash.commons.enums.CommandType;
import unicash.model.Model;

/**
 * Lists all transactions in UniCa$h to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = CommandType.LIST.getCommandWords();

    public static final String MESSAGE_USAGE = CommandType.LIST.getMessageUsage();

    public static final String MESSAGE_SUCCESS = CommandType.LIST.getMessageSuccess();

    public static final String MESSAGE_FAILURE = CommandType.LIST.getMessageFailure();

    private static final Logger logger = Logger.getLogger("ListCommandLogger");

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
