package unicash.logic.commands;

import static java.util.Objects.requireNonNull;
import static unicash.logic.UniCashMessages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import unicash.commons.core.index.Index;
import unicash.commons.util.CommandUsage;
import unicash.commons.util.ExampleGenerator;
import unicash.commons.util.ToStringBuilder;
import unicash.logic.UniCashMessages;
import unicash.logic.commands.exceptions.CommandException;
import unicash.model.Model;
import unicash.model.transaction.Transaction;

/**
 * Retrieves expanded details of a specific transaction given its
 * identifier in the transactions list, and displays them to the user.
 */
public class GetCommand extends Command {

    public static final String COMMAND_WORD = "get";

    public static final String MESSAGE_USAGE = new CommandUsage.Builder()
            .setCommandWord(COMMAND_WORD)
            .setDescription("Displays expanded details of a specific transaction.")
            .setArgument("Index (must be a positive integer)")
            .setExample(ExampleGenerator.generate(COMMAND_WORD, "2"))
            .build()
            .toString();

    public static final String MESSAGE_GET_TRANSACTION_SUCCESS = "Transaction %1$d retrieved:"
            + "\n\n%2$s";

    private static final Logger logger = Logger.getLogger("GetCommandLogger");

    private final Index index;


    /**
     * Creates a GetTransactionCommand Object
     *
     * @param index
     */
    public GetCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.log(Level.INFO, "Executing get command");

        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.INFO, "Get command execution failed");
            throw new CommandException(MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToRetrieve = lastShownList.get(index.getZeroBased());
        logger.log(Level.INFO, "Get command executed successfully");
        return new CommandResult(String.format(MESSAGE_GET_TRANSACTION_SUCCESS,
                index.getOneBased(), UniCashMessages.formatTransaction(transactionToRetrieve)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GetCommand)) {
            return false;
        }

        GetCommand otherGetCommand = (GetCommand) other;
        return index.equals(otherGetCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
