package unicash.logic.commands;

import static java.util.Objects.requireNonNull;

import unicash.commons.util.ToStringBuilder;
import unicash.logic.UniCashMessages;
import unicash.logic.commands.exceptions.CommandException;
import unicash.logic.parser.CliSyntax;
import unicash.model.Model;
import unicash.model.transaction.Transaction;


/**
 * Adds a transaction to UniCash.
 */
public class AddTransactionCommand extends Command {
    public static final String COMMAND_WORD = "add_transaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to UniCa$h. \n"
            + "\n"
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "NAME "
            + CliSyntax.PREFIX_TYPE + "TYPE "
            + CliSyntax.PREFIX_AMOUNT + "AMOUNT "
            + CliSyntax.PREFIX_DATETIME + "DATETIME "
            + CliSyntax.PREFIX_LOCATION + "LOCATION "
            + "[" + CliSyntax.PREFIX_CATEGORY + "CATEGORY]...\n"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Buying groceries "
            + CliSyntax.PREFIX_TYPE + "expense "
            + CliSyntax.PREFIX_AMOUNT + "300 "
            + CliSyntax.PREFIX_DATETIME + "18-08-2023 19:30 "
            + CliSyntax.PREFIX_LOCATION + "ntuc "
            + CliSyntax.PREFIX_CATEGORY + "household";

    public static final String MESSAGE_SUCCESS = "New transaction added: \n\n%1$s";

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
        return toAdd.equals(otherCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
