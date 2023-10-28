package unicash.logic.parser;

import static java.util.Objects.requireNonNull;
import static unicash.logic.UniCashMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;

import unicash.logic.commands.FilterCommand;
import unicash.logic.parser.exceptions.ParseException;
import unicash.model.commons.Amount;
import unicash.model.transaction.Name;
import unicash.model.transaction.predicates.TransactionContainsAllKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String EMPTY_STRING = "";
    public static final List<String> EMPTY_STRING_LIST = List.of(EMPTY_STRING);

    private static TransactionContainsAllKeywordsPredicate filterPredicate =
            new TransactionContainsAllKeywordsPredicate();

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TYPE, PREFIX_AMOUNT, PREFIX_DATETIME,
                        PREFIX_CATEGORY, PREFIX_LOCATION);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TYPE, PREFIX_AMOUNT, PREFIX_DATETIME,
                PREFIX_LOCATION);


        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Name transactionName = ParserUtil.parseTransactionName(argMultimap.getValue(PREFIX_NAME).get());
            filterPredicate.setName(transactionName.toString());

        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            Amount transactionAmount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            filterPredicate.setAmount(Amount.amountToDecimalString(transactionAmount));
        }

        return new FilterCommand(filterPredicate);
    }

}
