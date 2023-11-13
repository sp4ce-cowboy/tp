package unicash.logic.parser;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static unicash.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static unicash.logic.commands.CommandTestUtil.INVALID_TRANSACTION_NAME_DESC;
import static unicash.logic.commands.CommandTestUtil.TRANSACTION_NAME_DESC_NUS;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CommandParserTestUtil.assertParseFailure;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import unicash.commons.enums.CommandType;
import unicash.commons.util.ToStringBuilder;
import unicash.logic.UniCashMessages;
import unicash.logic.parser.exceptions.ParseException;
import unicash.model.category.Category;
import unicash.model.transaction.Location;
import unicash.model.transaction.predicates.TransactionContainsAllKeywordsPredicate;


/**
 * A class to test the FindCommandParser.
 */
public class FindCommandParserTest {

    private static final String WHITESPACE = " ";
    private final FindCommandParser parser = new FindCommandParser();
    private final UniCashParser uniCashParser = new UniCashParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        String emptyArgument = "";
        assertThrows(ParseException.class, () -> parser.parse(emptyArgument));

    }

    @Test
    public void parse_repeatedType_failure() {
        assertParseFailure(parser, TRANSACTION_NAME_DESC_NUS + TRANSACTION_NAME_DESC_NUS,
                UniCashMessages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }


    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_TRANSACTION_NAME_DESC,
                unicash.model.transaction.Name.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, INVALID_LOCATION_DESC, Location.MESSAGE_CONSTRAINTS);

        // invalid datetime
        assertParseFailure(parser, INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void equals_sameFindCommandParserObject_returnsTrue() {
        FindCommandParser parser = new FindCommandParser();
        assertTrue(parser.equals(parser));
        assertTrue(parser.equals(new FindCommandParser()));

    }

    @Test
    public void equals_differentCommandTypes_returnsFalse() {
        FindCommandParser findCommandParser = new FindCommandParser();
        ListCommandParser listCommandParser = new ListCommandParser();
        assertNotEquals(listCommandParser, findCommandParser);
        assertFalse(findCommandParser.equals(listCommandParser));
    }

    @Test
    public void equals_nullInput_returnsFalse() {
        assertNotEquals(null, new FindCommandParser());
        assertFalse(new FindCommandParser().equals(null));
    }

    @Test
    public void toStringMethod() {
        FindCommandParser findCommandParser = new FindCommandParser();
        TransactionContainsAllKeywordsPredicate findPredicate =
                new TransactionContainsAllKeywordsPredicate();

        String expected = new ToStringBuilder(new FindCommandParser())
                .add("findPredicate", findPredicate)
                .toString();

        assertEquals(expected, findCommandParser.toString());
    }

    @Test
    public void parseMethod_inputContainsDateTimePrefix_throwsParseException() {
        String dateTimePrefixedArgument = "dt/10-10-2023 10:10";
        String findCommandArgumentWithDateTime =
                CommandType.FIND.getMainCommandWord()
                        + WHITESPACE + dateTimePrefixedArgument;

        assertThrows(ParseException.class, () -> {
            uniCashParser.parseCommand(findCommandArgumentWithDateTime);
        });

        assertThrows(ParseException.class, () -> {
            parser.parse(WHITESPACE + findCommandArgumentWithDateTime);
        });
    }

    @Test
    public void parseMethod_inputContainsTypePrefix_throwsParseException() {
        String typePrefixedArgument = "type/expense";
        String findCommandArgumentWithType =
                CommandType.FIND.getMainCommandWord()
                        + WHITESPACE + typePrefixedArgument;

        assertThrows(ParseException.class, () -> {
            uniCashParser.parseCommand(findCommandArgumentWithType);
        });

        assertThrows(ParseException.class, () -> {
            parser.parse(WHITESPACE + typePrefixedArgument);
        });
    }

    @Test
    public void parseMethod_inputContainsAmountPrefix_throwsParseException() {
        String amountPrefixedArgument = "amt/30.00";
        String findCommandArgumentWithAmount =
                CommandType.FIND.getMainCommandWord()
                        + WHITESPACE + amountPrefixedArgument;

        assertThrows(ParseException.class, () -> {
            uniCashParser.parseCommand(findCommandArgumentWithAmount);
        });

        assertThrows(ParseException.class, () -> {
            parser.parse(WHITESPACE + amountPrefixedArgument);
        });

    }

    @Test
    public void parseMethod_inputContainsNamePrefix_doesNotThrowsParseException() {
        String namePrefixedArgument = "n/buying eggs";
        String findCommandArgumentWithName =
                CommandType.FIND.getMainCommandWord()
                        + WHITESPACE + namePrefixedArgument;

        assertDoesNotThrow(() -> {
            uniCashParser.parseCommand(findCommandArgumentWithName);
        });

        assertDoesNotThrow(() -> {
            parser.parse(WHITESPACE + namePrefixedArgument);
        });

    }

    @Test
    public void parseMethod_inputContainsCategoryPrefix_doesNotThrowsParseException() {
        String categoryPrefixedArgument = "c/food";
        String findCommandArgumentWithCategory =
                CommandType.FIND.getMainCommandWord()
                        + WHITESPACE + categoryPrefixedArgument;

        assertDoesNotThrow(() -> {
            uniCashParser.parseCommand(findCommandArgumentWithCategory);
        });

        assertDoesNotThrow(() -> {
            parser.parse(WHITESPACE + categoryPrefixedArgument);
        });

    }

    @Test
    public void parseMethod_inputContainsLocationPrefix_doesNotThrowsParseException() {
        String locationPrefixedArgument = "l/mall";
        String findCommandArgumentWithLocation =
                CommandType.FIND.getMainCommandWord()
                        + WHITESPACE + locationPrefixedArgument;

        assertDoesNotThrow(() -> {
            uniCashParser.parseCommand(findCommandArgumentWithLocation);
        });

        assertDoesNotThrow(() -> {
            parser.parse(WHITESPACE + locationPrefixedArgument);
        });

    }


}
