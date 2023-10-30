package unicash.logic.commands;

import static java.util.Objects.requireNonNull;

import unicash.commons.util.ToStringBuilder;
import unicash.logic.commands.exceptions.CommandException;
import unicash.logic.parser.CliSyntax;
import unicash.model.Model;
import unicash.model.budget.Budget;

/**
 * Edits the budget of the specified interval.
 */
public class EditBudgetCommand extends Command {
    public static final String COMMAND_WORD = "edit_budget";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the budget of the specified interval. "
            + "Existing values will be overwritten by the input values."
            + "\n"
            + "Parameters: "
            + CliSyntax.PREFIX_AMOUNT + "AMOUNT "
            + CliSyntax.PREFIX_INTERVAL + "TYPE "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_AMOUNT + "300 "
            + CliSyntax.PREFIX_INTERVAL + "month ";

    public static final String MESSAGE_EDIT_BUDGET_SUCCESS = "Edited Budget: \n\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public final Budget budget;

    /**
     * Creates an EditBudgetCommand to edit the specified {@code Budget}
     */
    public EditBudgetCommand(Budget budget) {
        requireNonNull(budget);
        this.budget = budget;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditBudgetCommand)) {
            return false;
        }

        EditBudgetCommand e = (EditBudgetCommand) other;
        return budget.equals(e.budget);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        //TODO: Edit budget command

        return null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("budget", budget)
                .toString();
    }
}
