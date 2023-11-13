package unicash.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import unicash.commons.enums.CommandType;
import unicash.commons.util.ToStringBuilder;
import unicash.model.Model;
import unicash.model.transaction.predicates.TransactionContainsAllKeywordsPredicate;



/**
 * Finds and lists all transactions in UniCa$h whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = CommandType.FIND.getCommandWords();
    public static final String MESSAGE_USAGE = CommandType.FIND.getMessageUsage();
    public static final String MESSAGE_SUCCESS = CommandType.FIND.getMessageSuccess();

    private static final Logger logger = Logger.getLogger("FindCommandLogger");

    private final TransactionContainsAllKeywordsPredicate predicate;


    /**
     * Creates a {@code FindCommand} object with a non-null
     * {@code TransactionContainsAllKeywordsPredicate} object.
     *
     * @param predicate the {@code TransactionContainsAllKeywordsPredicate} to be used
     */
    public FindCommand(TransactionContainsAllKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert predicate != null : "predicate cannot be null";

        model.updateFilteredTransactionList(predicate);

        logger.log(Level.INFO, String.format(
                "Transaction List successfully updated with the predicate %s", predicate));

        logger.log(Level.INFO, "Find command executed successfully");

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
