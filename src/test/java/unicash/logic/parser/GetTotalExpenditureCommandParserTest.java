package unicash.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static unicash.logic.UniCashMessages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import unicash.logic.commands.GetTotalExpenditureCommand;
import unicash.logic.parser.exceptions.ParseException;
import unicash.model.category.Category;

public class GetTotalExpenditureCommandParserTest {

    private final GetTotalExpenditureCommandParser parser = new GetTotalExpenditureCommandParser();

    @Test
    public void parse_noCategory_returnsGetTotalExpenditureCommandWithoutCategoryFilter() throws ParseException {
        var result = parser.parse(" month/1 year/2022");
        var currentYear = LocalDate.now().getYear();
        var expected = new GetTotalExpenditureCommand(1, 2022, null);
        assertEquals(expected, result);
    }

    @Test
    public void parse_noYear_returnsGetTotalExpenditureCommandWithDefaultYear() throws ParseException {
        var result = parser.parse(" month/1");
        var currentYear = LocalDate.now().getYear();
        var expected = new GetTotalExpenditureCommand(1, currentYear, null);
        assertEquals(expected, result);
    }

    @Test
    public void parse_withCategory_returnsGetTotalExpenditureCommandWithCategoryFilter() throws ParseException {
        var result = parser.parse(" month/1 c/Food");
        var currentYear = LocalDate.now().getYear();
        var expected = new GetTotalExpenditureCommand(1, currentYear, new Category("Food"));
        assertEquals(expected, result);
    }

    @Test
    public void parse_withCategoryAndYear_returnsCommandWithYearAndCategoryFilter() throws ParseException {
        var result = parser.parse(" month/1 c/Food year/2022");
        var expected = new GetTotalExpenditureCommand(1, 2022, new Category("Food"));
        assertEquals(expected, result);
    }

    @Test
    public void parse_withUnknownArgument_throwsParseException() throws ParseException {
        // Parser will treat the month as 1 amt/500 and as such throw an exception
        assertThrows(ParseException.class, () -> parser.parse(" month/1 amt/500"));
    }

    @Test
    public void parse_missingMonth_throwsParseException() {
        var thrown = assertThrows(ParseException.class, () -> parser.parse(" c/Food"));
        assertEquals(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetTotalExpenditureCommand.MESSAGE_USAGE),
                thrown.getMessage()
        );
    }

    @Test
    public void parse_nonIntegerMonth_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" month/hi"));
    }

    @Test
    public void parse_nonIntegerYear_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" month/hi year/hi"));
    }
}
