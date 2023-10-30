package unicash.logic.commands;

import static java.util.Objects.requireNonNull;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;

import unicash.commons.util.CommandUsage;
import unicash.commons.util.ExampleGenerator;
import unicash.commons.util.ToStringBuilder;
import unicash.logic.UniCashMessages;
import unicash.logic.commands.exceptions.CommandException;
import unicash.model.Model;
import unicash.model.transaction.Transaction;


/**
 * Adds a transaction to UniCash.
 */
public class AddTransactionCommand extends Command {
    public static final String COMMAND_WORD = "add_transaction";

    public static final String MESSAGE_USAGE = new CommandUsage.Builder()
            .setCommandWord(COMMAND_WORD)
            .setDescription("Adds a transaction to UniCa$h.")
            .setExample(
                    ExampleGenerator.generate(
                            "add_transaction",
                            PREFIX_NAME,
                            PREFIX_TYPE,
                            PREFIX_AMOUNT,
                            PREFIX_DATETIME,
                            PREFIX_LOCATION,
                            PREFIX_CATEGORY
                    )
            )
            .addParameter(PREFIX_NAME, "Name")
            .addParameter(PREFIX_TYPE, "Type")
            .addParameter(PREFIX_AMOUNT, "Amount")
            .addParameter(PREFIX_DATETIME, "DateTime", true, false)
            .addParameter(PREFIX_LOCATION, "Location", true, false)
            .addParameter(PREFIX_CATEGORY, "Category", true, true)
            .build()
            .toString();

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
