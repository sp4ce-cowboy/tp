package unicash.logic.parser;

import static unicash.logic.UniCashMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_MONTH;
import static unicash.logic.parser.CliSyntax.PREFIX_YEAR;

import java.time.LocalDate;

import unicash.logic.commands.GetTotalExpenditureCommand;
import unicash.logic.parser.exceptions.ParseException;
import unicash.model.category.Category;


/**
 * Parses get_total_expenditure command to extract parameters.
 */
public class GetTotalExpenditureCommandParser implements Parser<GetTotalExpenditureCommand> {

    @Override
    public GetTotalExpenditureCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MONTH, PREFIX_YEAR, PREFIX_CATEGORY);

        if (argMultimap.getValue(PREFIX_MONTH).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetTotalExpenditureCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MONTH, PREFIX_YEAR, PREFIX_CATEGORY);

        String categoryString = argMultimap.getValue(PREFIX_CATEGORY).orElse(null);
        Category category = null;
        if (categoryString != null) {
            category = ParserUtil.parseCategory(categoryString);
        }

        int month;
        try {
            String monthString = argMultimap.getValue(PREFIX_MONTH).get();
            month = Integer.parseInt(monthString);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid month value, must be number!");
        }

        int year;
        try {
            String yearString = argMultimap.getValue(PREFIX_YEAR).orElse(String.valueOf(LocalDate.now().getYear()));
            year = Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid year value, must be number!");
        }


        return new GetTotalExpenditureCommand(month, year, category);
    }
}
