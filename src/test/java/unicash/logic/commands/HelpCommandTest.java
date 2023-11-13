package unicash.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static unicash.logic.UniCashMessages.MESSAGE_UNKNOWN_COMMAND;
import static unicash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unicash.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import unicash.commons.enums.CommandType;
import unicash.commons.util.ToStringBuilder;
import unicash.model.Model;
import unicash.model.ModelManager;

public class HelpCommandTest {

    @Test
    public void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HelpCommand(null));
    }

    @Test
    public void execute_emptyArguments_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(SHOWING_HELP_MESSAGE, true, false);

        assertCommandSuccess(new HelpCommand(""), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_unknownCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(String.format("%s\n\n%s",
                        MESSAGE_UNKNOWN_COMMAND, HelpCommand.MESSAGE_USAGE));

        assertCommandSuccess(new HelpCommand("d"), model,
                expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_findCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(FindCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.FIND.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(ListCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.LIST.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_addTransactionCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(AddTransactionCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.ADD_TRANSACTION.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_editTransactionCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(EditTransactionCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.EDIT_TRANSACTION.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_deleteTransactionCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(DeleteTransactionCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.DELETE_TRANSACTION.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_getTotalExpenditureCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(GetTotalExpenditureCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.GET_TOTAL_EXPENDITURE.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_clearTransactionsCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(ClearTransactionsCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.CLEAR_TRANSACTIONS.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_setBudgetCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(SetBudgetCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.SET_BUDGET.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_getBudgetCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(GetBudgetCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.GET_BUDGET.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_clearBudgetCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(ClearBudgetCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.CLEAR_BUDGET.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_resetCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        CommandResult expectedCommandResult =
                new CommandResult(ResetCommand.MESSAGE_USAGE);
        assertCommandSuccess(new HelpCommand(CommandType.RESET.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_getCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(GetCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.GET.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(HelpCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.HELP.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_exitCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(ExitCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(CommandType.EXIT.getMainCommandWord()),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_summaryCommand_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult =
                new CommandResult(SummaryCommand.MESSAGE_USAGE);

        assertCommandSuccess(new HelpCommand(SummaryCommand.COMMAND_WORD),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        HelpCommand helpCommandEdit = new HelpCommand(EditTransactionCommand.COMMAND_WORD);
        HelpCommand helpCommandDelete = new HelpCommand(DeleteTransactionCommand.COMMAND_WORD);

        // same object -> returns true
        assertEquals(helpCommandEdit, helpCommandEdit);

        // same values -> returns true
        HelpCommand helpCommandEditCopy = new HelpCommand(EditTransactionCommand.COMMAND_WORD);
        assertEquals(helpCommandEdit, helpCommandEditCopy);

        // null -> returns false
        assertNotEquals(null, helpCommandEdit);

        // different HelpCommand -> returns false
        assertNotEquals(helpCommandEdit, helpCommandDelete);

        assertFalse(helpCommandEdit.equals(2));
    }


    @Test
    public void equals_differentCommandTypes_returnsFalse() {
        Command helpCommand = new HelpCommand(ResetCommand.COMMAND_WORD);;
        assertFalse(helpCommand.equals(new ResetCommand()));
    }


    @Test
    public void toStringMethod() {
        HelpCommand helpCommand = new HelpCommand(HelpCommand.COMMAND_WORD);
        String expected = new ToStringBuilder(helpCommand)
                .add("target", HelpCommand.COMMAND_WORD).toString();
        assertEquals(expected, helpCommand.toString());
    }
}
