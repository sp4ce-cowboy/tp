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
import unicash.model.category.Category;
import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Type;
import unicash.model.transaction.predicates.TransactionContainsAllKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    private TransactionContainsAllKeywordsPredicate filterPredicate =
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

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_CATEGORY, PREFIX_TYPE,
                PREFIX_AMOUNT, PREFIX_DATETIME, PREFIX_LOCATION);


        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Name transactionName = ParserUtil.parseTransactionName(argMultimap.getValue(PREFIX_NAME).get());
            filterPredicate.setNameKeyword(transactionName.toString());

        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            Amount transactionAmount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            filterPredicate.setAmountKeyword(Amount.amountToDecimalString(transactionAmount));
        }

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            Category transactionCategory = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
            filterPredicate.setCategoryKeyword(transactionCategory.toString());
        }

        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            Location transactionLocation = ParserUtil.parseLocation(
                    argMultimap.getValue(PREFIX_LOCATION).get());
            filterPredicate.setLocationKeyword(transactionLocation.toString());
        }

        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            DateTime transactionDateTime = ParserUtil.parseDateTime(
                    argMultimap.getValue(PREFIX_DATETIME).get());
            filterPredicate.setDateTimeKeyword(transactionDateTime.toString());
        }

        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            Type transactionType = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
            filterPredicate.setTypeKeyword(transactionType.toString());
        }

        return new FilterCommand(filterPredicate);
    }

}
