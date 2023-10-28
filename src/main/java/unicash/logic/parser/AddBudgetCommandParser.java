package unicash.logic.parser;

import static unicash.logic.UniCashMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_INTERVAL;

import java.util.stream.Stream;

import unicash.logic.commands.AddBudgetCommand;
import unicash.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddBudgetCommand object
 */
public class AddBudgetCommandParser implements Parser<AddBudgetCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public AddBudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_INTERVAL);

        //check if mandatory fields (type, amt) are present
        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_INTERVAL)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddBudgetCommand.MESSAGE_USAGE));
        }

        //check for duplicates
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT, PREFIX_INTERVAL);

        //TODO: Parse amount and interval, and create new budget object

        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * @param argumentMultimap
     * @param prefixes
     * @return
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
