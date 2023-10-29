package unicash.logic.commands;

import static java.util.Objects.requireNonNull;

import unicash.commons.util.CommandUsage;
import unicash.commons.util.ExampleGenerator;
import unicash.commons.util.ToStringBuilder;
import unicash.logic.UniCashMessages;
import unicash.model.Model;
import unicash.model.transaction.predicates.TransactionContainsKeywordsPredicate;

/**
 * Finds and lists all transactions in UniCa$h whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = new CommandUsage.Builder()
            .setCommandWord(COMMAND_WORD)
            .setDescription(
                    "Finds all transactions whose names contain any of the specified keywords "
                            + "(case-insensitive) and displays them as a list with index numbers."
            )
            .setArgument("Keyword [More keywords]...")
            .setExample(ExampleGenerator.generate(COMMAND_WORD, "chicken rice"))
            .build()
            .toString();

    private final TransactionContainsKeywordsPredicate predicate;

    public FindCommand(TransactionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert predicate != null : "predicate cannot be null";
        model.updateFilteredTransactionList(predicate);
        return new CommandResult(
                String.format(UniCashMessages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size()));
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
