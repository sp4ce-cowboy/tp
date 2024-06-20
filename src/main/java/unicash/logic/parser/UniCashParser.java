package unicash.logic.parser;

import static unicash.logic.UniCashMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unicash.logic.UniCashMessages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import unicash.commons.core.LogsCenter;
import unicash.commons.enums.CommandType;
import unicash.logic.commands.ClearBudgetCommand;
import unicash.logic.commands.Command;
import unicash.logic.commands.ExitCommand;
import unicash.logic.commands.GetBudgetCommand;
import unicash.logic.commands.HelpCommand;
import unicash.logic.commands.SummaryCommand;
import unicash.logic.commands.TestCommand;
import unicash.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class UniCashParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(UniCashParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        CommandType commandType = CommandType.parseCommandType(commandWord);

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandType) {
        case FIND:
            return new FindCommandParser().parse(arguments);

        case LIST:
            return new ListCommandParser().parse(arguments);

        case ADD_TRANSACTION:
            return new AddTransactionCommandParser().parse(arguments);

        case EDIT_TRANSACTION:
            return new EditTransactionCommandParser().parse(arguments);

        case DELETE_TRANSACTION:
            return new DeleteTransactionCommandParser().parse(arguments);

        case GET_TOTAL_EXPENDITURE:
            return new GetTotalExpenditureCommandParser().parse(arguments);

        case CLEAR_TRANSACTIONS:
            return new ClearTransactionsCommandParser().parse(arguments);

        case SET_BUDGET:
            return new SetBudgetCommandParser().parse(arguments);

        case CLEAR_BUDGET:
            return new ClearBudgetCommand();

        case GET_BUDGET:
            return new GetBudgetCommand();

        case RESET:
            return new ResetCommandParser().parse(arguments);

        case GET:
            return new GetCommandParser().parse(arguments);

        case HELP:
            return new HelpCommandParser().parse(arguments);

        case EXIT:
            return new ExitCommand();

        case SUMMARY:
            return new SummaryCommand();

        case TestCommand.COMMAND_WORD:
            return new TestCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
