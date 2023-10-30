package unicash.logic.commands;

import static java.util.Objects.requireNonNull;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;

import unicash.commons.util.ToStringBuilder;
import unicash.logic.UniCashMessages;
import unicash.logic.commands.exceptions.CommandException;
import unicash.model.Model;
import unicash.model.transaction.predicates.TransactionContainsAllKeywordsPredicate;

/**
 * Filters the displayed Transactions according to certain parameters
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the transactions in UniCa$h "
            + "according to the specified properties"
            + "All properties must match in order for the transaction to be displayed."
            + "Any combination of properties may be provided but at least one property must be provided."
            + "\n\n"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TYPE + "TYPE] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_LOCATION + "LOCATION]"
            + "[" + PREFIX_CATEGORY + "CATEGORY]..."
            + "\n\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Buying groceries "
            + PREFIX_TYPE + "expense "
            + PREFIX_AMOUNT + "300 "
            + PREFIX_DATETIME + "18-08-2001 19:30 "
            + PREFIX_LOCATION + "ntuc"
            + PREFIX_CATEGORY + "household";

    public static final String MESSAGE_SUCCESS = "Filtered %1$s Transactions";

    private final TransactionContainsAllKeywordsPredicate predicate;

    /**
     * Creates an FilterCommand to filter the transactions list
     */
    public FilterCommand(TransactionContainsAllKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTransactionList(predicate);
        int listSize = model.getFilteredTransactionList().size();
        return new CommandResult(String.format(UniCashMessages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                listSize));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherCommand = (FilterCommand) other;
        return predicate.equals(otherCommand.predicate);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
