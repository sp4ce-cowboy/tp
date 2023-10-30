package unicash.logic.parser;

import static unicash.logic.UniCashMessages.MESSAGE_INVALID_COMMAND_FORMAT;

import unicash.commons.core.index.Index;
import unicash.logic.commands.GetCommand;
import unicash.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteTransactionCommand object
 */
public class GetCommandParser implements Parser<GetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * GetTransactionCommand and returns a GetTransactionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public GetCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GetCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            GetCommand.MESSAGE_USAGE), pe);
        }
    }

}

