package unicash.logic.commands;

import static java.util.Objects.requireNonNull;
import static unicash.model.Model.PREDICATE_SHOW_ALL_TRANSACTIONS;

import java.util.logging.Level;
import java.util.logging.Logger;

import unicash.commons.enums.CommandType;
import unicash.commons.util.ToStringBuilder;
import unicash.model.Model;
import unicash.model.UniCash;

/**
 * Clears all transactions in UniCash.
 */
public class ClearTransactionsCommand extends Command {

    public static final String COMMAND_WORD = CommandType.CLEAR_TRANSACTIONS.getCommandWords();
    public static final String MESSAGE_SUCCESS = CommandType.CLEAR_TRANSACTIONS.getMessageSuccess();
    public static final String MESSAGE_USAGE = CommandType.CLEAR_TRANSACTIONS.getMessageUsage();
    public static final String MESSAGE_FAILURE = CommandType.CLEAR_TRANSACTIONS.getMessageFailure();

    private static final Logger logger = Logger.getLogger("ClearCommandLogger");


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setUniCash(new UniCash());
        model.updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        logger.log(Level.INFO, "Successfully cleared all transactions in UniCash!");
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        return other instanceof ClearTransactionsCommand;

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
