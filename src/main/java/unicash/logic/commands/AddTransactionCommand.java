package unicash.logic.commands;

import static java.util.Objects.requireNonNull;

import unicash.commons.enums.CommandType;
import unicash.commons.util.ToStringBuilder;
import unicash.logic.UniCashMessages;
import unicash.logic.commands.exceptions.CommandException;
import unicash.model.Model;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.TransactionList;


/**
 * Adds a transaction to UniCash.
 */
public class AddTransactionCommand extends Command {
    public static final String COMMAND_WORD = CommandType.ADD_TRANSACTION.getCommandWords();

    public static final String MESSAGE_USAGE = CommandType.ADD_TRANSACTION.getMessageUsage();

    public static final String MESSAGE_SUCCESS = CommandType.ADD_TRANSACTION.getMessageSuccess();

    private final Transaction toAdd;

    /**
     * Creates an AddTransactionCommand to add the specified {@code Transaction}
     */
    public AddTransactionCommand(Transaction transaction) {
        requireNonNull(transaction);
        toAdd = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.isFull()) {
            throw new CommandException(TransactionList.MESSAGE_SIZE_CONSTRAINTS);
        }
        model.addTransaction(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, UniCashMessages.formatTransaction(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTransactionCommand)) {
            return false;
        }

        AddTransactionCommand otherCommand = (AddTransactionCommand) other;
        return toAdd.equalsTransaction(otherCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
