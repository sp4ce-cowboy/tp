package unicash.logic.commands;

import static java.util.Objects.requireNonNull;
import static unicash.logic.UniCashMessages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;

import java.util.List;

import unicash.commons.core.index.Index;
import unicash.logic.UniCashMessages;
import unicash.logic.commands.exceptions.CommandException;
import unicash.model.Model;
import unicash.model.transaction.Transaction;

/**
 * Retrieve expanded details of a specific transaction given its
 * identifier in the transactions list.
 */
public class GetTransactionCommand extends Command {

    public static final String COMMAND_WORD = "get";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays expanded details "
            + "of a specific transaction."
            + "\n\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "\n\n"
            + "Example: get 2";

    public static final String MESSAGE_GET_TRANSACTION_SUCCESS = "Transaction retrieved:"
            + "\n\n%1$s";

    private final Index index;

    /**
     * Creates a GetTransactionCommand Object
     * @param index
     */
    public GetTransactionCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Transaction> transactionList = model.getFilteredTransactionList();

        if (index.getZeroBased() > transactionList.size()) {
            throw new CommandException(MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToRetrieve = transactionList.get(index.getZeroBased());

        return new CommandResult(String.format(MESSAGE_GET_TRANSACTION_SUCCESS,
                UniCashMessages.formatTransaction(transactionToRetrieve)));
    }
}
