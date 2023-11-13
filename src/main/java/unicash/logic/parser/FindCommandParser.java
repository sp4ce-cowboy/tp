package unicash.logic.parser;

import static java.util.Objects.requireNonNull;
import static unicash.logic.UniCashMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import unicash.commons.util.ToStringBuilder;
import unicash.logic.commands.FindCommand;
import unicash.logic.parser.exceptions.ParseException;
import unicash.model.category.Category;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.predicates.TransactionContainsAllKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private TransactionContainsAllKeywordsPredicate findPredicate =
            new TransactionContainsAllKeywordsPredicate();

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        /* All prefixes are parsed first */
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                PREFIX_DATETIME, PREFIX_AMOUNT, PREFIX_TYPE, PREFIX_CATEGORY, PREFIX_LOCATION);

        /* If any of the invalid prefixes are present, an invalid command format message
         * is displayed to the user, as opposed to the invalid prefixes themselves being
         * parsed together with the valid prefixes as a standard field input. */
        if (areAnyPrefixesPresent(
                argMultimap, PREFIX_DATETIME, PREFIX_AMOUNT, PREFIX_TYPE)) {

            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        /* Enforces singular prefix input by the user */
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_CATEGORY, PREFIX_LOCATION);


        /* If present, add the argument following the Name prefix as a name predicate keyword */
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Name transactionName = ParserUtil.parseTransactionName(
                    argMultimap.getValue(PREFIX_NAME).get());
            findPredicate.addNameKeyword(transactionName.toString());
        }

        /* If present, add the argument following the Category prefix as a category predicate keyword */
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            Category transactionCategory = ParserUtil.parseCategory(
                    argMultimap.getValue(PREFIX_CATEGORY).get());
            findPredicate.addCategoryKeyword(transactionCategory.toString());
        }

        /* If present, add the argument following the Location prefix as a location predicate keyword */
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            Location transactionLocation = ParserUtil.parseLocation(
                    argMultimap.getValue(PREFIX_LOCATION).get());
            findPredicate.addLocationKeyword(transactionLocation.toString());
        }

        return new FindCommand(findPredicate);

    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommandParser)) {
            return false;
        }

        FindCommandParser otherCommand = (FindCommandParser) other;
        return findPredicate.equals(otherCommand.findPredicate);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("findPredicate", findPredicate)
                .toString();
    }

    /**
     * Returns true if any of the prefixes contains any {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap the input {@code ArgumentMultimap}
     * @param prefixes the input prefixes to check with the {@code ArgumentMultimap}
     * @return true if any input prefix is present in {@code ArgumentMultimap}, false otherwise
     */
    public static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .anyMatch(prefix -> argumentMultimap
                        .getValue(prefix)
                        .isPresent());
    }

}

