package unicash.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static unicash.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import unicash.logic.parser.exceptions.ParseException;
import unicash.model.category.Category;
import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Type;

public class ParserUtilTest {
    private static final String INVALID_NAME = "Ex@pen$e";
    private static final String INVALID_AMOUNT = "str";
    private static final String INVALID_DATETIME = "18-8-2001";
    private static final String INVALID_CATEGORY = "$$af$";
    private static final String INVALID_LOCATION = "^$2af";

    private static final String VALID_NAME = "Expense";
    private static final String VALID_AMOUNT = "3.0";
    private static final String VALID_DATETIME = "18-08-2001 18:30";
    private static final String VALID_CATEGORY = "hobbies";
    private static final String VALID_CATEGORY_2 = "entertainment";
    private static final String VALID_CATEGORY_3 = "test";
    private static final String VALID_CATEGORY_4 = "test2";
    private static final String VALID_CATEGORY_5 = "test3";
    private static final String VALID_CATEGORY_6 = "test4";
    private static final String VALID_LOCATION = "orchard road";
    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_TRANSACTION, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TRANSACTION, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseTransactionName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTransactionName(null));
    }

    @Test
    public void parseTransactionName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTransactionName(INVALID_NAME));
    }

    @Test
    public void parseTransactionName_validValueWithoutWhitespace_returnsTransactionName() throws Exception {
        Name expectedTransactionName = new Name(VALID_NAME);
        assertEquals(expectedTransactionName, ParserUtil.parseTransactionName(VALID_NAME));
    }

    @Test
    public void parseTransactionName_validValueWithWhitespace_returnsTrimmedTransactionName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedTransactionName = new Name(VALID_NAME);
        assertEquals(expectedTransactionName, ParserUtil.parseTransactionName(nameWithWhitespace));
    }

    @Test
    public void parseAmount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAmount(null));
    }

    @Test
    public void parseAmount_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_AMOUNT));
    }

    @Test
    public void parseAmount_validValueWithoutWhitespace_returnsTransactionName() throws Exception {
        Amount expectedAmount = new Amount(3.0);
        assertEquals(expectedAmount, ParserUtil.parseAmount(VALID_AMOUNT));
    }

    @Test
    public void parseAmount_validValueWithWhitespace_returnsTrimmedTransactionName() throws Exception {
        String amountWithWhitespace = WHITESPACE + VALID_AMOUNT + WHITESPACE;
        Amount expectedAmount = new Amount(3.0);
        assertEquals(expectedAmount, ParserUtil.parseAmount(amountWithWhitespace));
    }

    @Test
    public void parseType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseType(null));
    }

    @Test
    public void parseType_invalidType_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseType("hi"));
    }

    @Test
    public void parseType_validValueWithoutWhitespace_returnsType() throws ParseException {
        String value = "expense";
        Type expected = new Type("expense");
        assertEquals(expected, ParserUtil.parseType(value));
    }

    @Test
    public void parseType_validValueWithWhitespace_returnsTrimmedType() throws ParseException {
        String value = "  income   ";
        Type expected = new Type("income");
        assertEquals(expected, ParserUtil.parseType(value));
    }

    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime(null));
    }

    @Test
    public void parseDateTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime(INVALID_DATETIME));
    }

    @Test
    public void parseDateTime_validValueWithoutWhitespace_returnsTransactionName() throws Exception {
        DateTime expectedDateTime = new DateTime(VALID_DATETIME);
        assertEquals(expectedDateTime, ParserUtil.parseDateTime(VALID_DATETIME));
    }

    @Test
    public void parseDateTime_validValueWithWhitespace_returnsTrimmedTransactionName() throws Exception {
        String dateTimeWithWhiteSpace = WHITESPACE + VALID_DATETIME + WHITESPACE;
        DateTime expectedDateTime = new DateTime(VALID_DATETIME);
        assertEquals(expectedDateTime, ParserUtil.parseDateTime(dateTimeWithWhiteSpace));
    }

    @Test
    public void parseCategory_empty_doesNotThrow() throws Exception {
        assertDoesNotThrow(() -> ParserUtil.parseDateTime(""));
        assertDoesNotThrow(() -> ParserUtil.parseDateTime(WHITESPACE + WHITESPACE));
    }

    @Test
    public void parseLocation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocation(null));
    }

    @Test
    public void parseLocation_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocation(INVALID_LOCATION));
    }

    @Test
    public void parseLocation_validValueWithoutWhitespace_returnsTransactionName() throws Exception {
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(VALID_LOCATION));
    }

    @Test
    public void parseLocation_validValueWithWhitespace_returnsTrimmedLocation() throws Exception {
        String locationWithWhiteSpace = WHITESPACE + VALID_LOCATION + WHITESPACE;
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(locationWithWhiteSpace));
    }

    @Test
    public void parseLocation_empty_returnsDefaultLocation() throws Exception {
        Location expectedLocation = new Location(WHITESPACE);
        assertEquals(expectedLocation, ParserUtil.parseLocation(WHITESPACE));
    }

    @Test
    public void parseCategory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategory(null));
    }

    @Test
    public void parseCategory_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(INVALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithoutWhitespace_returnsCategory() throws Exception {
        Category expectedCategory = new Category(VALID_CATEGORY);
        assertEquals(expectedCategory, ParserUtil.parseCategory(VALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithWhitespace_returnsTrimmedCategory() throws Exception {
        String categoryWithWhitespace = WHITESPACE + VALID_CATEGORY + WHITESPACE;
        Category expectedCategory = new Category(VALID_CATEGORY);
        assertEquals(expectedCategory, ParserUtil.parseCategory(categoryWithWhitespace));
    }

    @Test
    public void parseCategories_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategories(null));
    }

    @Test
    public void parseCategories_collectionWithInvalidCategories_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategories(
                Arrays.asList(VALID_CATEGORY, INVALID_CATEGORY)));
    }

    @Test
    public void parseCategories_emptyCollection_returnsEmptySet() {
        assertDoesNotThrow(() -> ParserUtil.parseCategories(Collections.emptyList()));
    }

    @Test
    public void parseCategories_collectionWithMoreThanAllowedNumberOfCategories_returnsCategorySet() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategories(
                Arrays.asList(VALID_CATEGORY, VALID_CATEGORY_2, VALID_CATEGORY_3, VALID_CATEGORY_4,
                        VALID_CATEGORY_5, VALID_CATEGORY_6)));
    }

    @Test
    public void parseCategories_collectionWithValidCategories_returnsCategorySet() throws Exception {
        UniqueCategoryList actualCategoryList = ParserUtil.parseCategories(
                Arrays.asList(VALID_CATEGORY, VALID_CATEGORY_2));
        List<Category> categoryList = new ArrayList<>(
                Arrays.asList(new Category(VALID_CATEGORY), new Category(VALID_CATEGORY_2)));
        UniqueCategoryList expectedCategoryList = new UniqueCategoryList(categoryList);
        assertEquals(expectedCategoryList, actualCategoryList);
    }

    @Test
    public void parseCategories_collectionWithDuplicateCategories_returnsCategorySet() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategories(
                Arrays.asList(VALID_CATEGORY, VALID_CATEGORY)));
    }
}
