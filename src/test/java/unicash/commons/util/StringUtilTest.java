package unicash.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;


public class StringUtilTest {

    //---------------- Tests for isNonZeroUnsignedInteger --------------------------------------

    @Test
    public void isNonZeroUnsignedInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }


    //---------------- Tests for containsSubstringIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for substring: null, empty.
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsSubstringIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil
                .containsSubstringIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsSubstringIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Substring parameter cannot be empty", ()
                -> StringUtil.containsSubstringIgnoreCase("typical sentence", "  "));
    }


    @Test
    public void containsSubstringIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil
                .containsSubstringIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for substring:
     *   - any substring
     *   - substring containing symbols/numbers
     *   - substring with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsSubstringIgnoreCase_invalidInputs_returnFalse() {
        // Empty sentence
        assertFalse(StringUtil.containsSubstringIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsSubstringIgnoreCase("    ", "123"));

        // Query word bigger than sentence word
        assertFalse(StringUtil.containsSubstringIgnoreCase("aaa bbb ccc", "bbbb"));
    }

    @Test
    public void containsSubstringIgnoreCase_validInputs_returnTrue() {
        // Matches a partial word only
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bbb ccc", "bb"));

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bBb ccc", "Bbb"));

        // Last word (boundary case)
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bBb ccc@1", "CCc@1"));

        // Sentence has extra spaces
        assertTrue(StringUtil.containsSubstringIgnoreCase("  AAA   bBb   ccc  ", "aaa"));

        // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsSubstringIgnoreCase("Aaa", "aaa"));

        // Leading/trailing spaces
        assertTrue(StringUtil.containsSubstringIgnoreCase("aaa bbb ccc", "  ccc  "));

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsSubstringIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter cannot be empty", ()
                -> StringUtil.containsWordIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter should be a single word", ()
                -> StringUtil.containsWordIgnoreCase("typical sentence", "aaa BBB"));
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
                .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }

    @Test
    public void capitalizeString_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.capitalizeString(null));
    }

    @Test
    public void capitalizeString_emptyString_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> StringUtil.capitalizeString(""));
    }

    @Test
    public void capitalizeString_whitespaceOnly_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> StringUtil.capitalizeString("    "));
    }

    @Test
    public void capitalizeString_startingAlphabetString_returnsCapitalizedString() {
        assertEquals("Foo", StringUtil.capitalizeString("foo"));
        assertEquals("Foo", StringUtil.capitalizeString("FOO"));
        assertEquals("Foo", StringUtil.capitalizeString("fOO"));
    }

    @Test
    public void capitalizeString_startingNonAlphabetString_returnsLowercaseString() {
        assertEquals("$xyz", StringUtil.capitalizeString("$xyz"));
        assertEquals("$xyz", StringUtil.capitalizeString("$XYZ"));
        assertEquals("$xyz", StringUtil.capitalizeString("$xYZ"));
    }

    @Test
    public void capitalizeString_singleAlphabetString_returnsUppercaseCharacterString() {
        assertEquals("F", StringUtil.capitalizeString("F"));
        assertEquals("F", StringUtil.capitalizeString("f"));
    }

    @Test
    public void capitalizeString_singleNonAlphabetString_returnsOriginalString() {
        assertEquals("$", StringUtil.capitalizeString("$"));
        assertEquals("$", StringUtil.capitalizeString("$"));
    }

    @Test
    public void capitalizeString_stringWithSurroundingWhitespace_returnsCapitalizedAndTrimmedString() {
        assertEquals("Foo", StringUtil.capitalizeString("FOO  "));
        assertEquals("Foo", StringUtil.capitalizeString("   FOo"));
        assertEquals("Foo", StringUtil.capitalizeString("   FOo   "));
    }

    @Test
    public void capitalizeString_longString_returnsNormallyCapitalizedString() {
        assertEquals("Helloworldthisisonestring", StringUtil.capitalizeString("heLLoWorldThisIsOneStRIng"));
    }

    @Test
    public void capitalizeString_longSentence_returnsSentenceWithFirstWordCapitalized() {
        assertEquals(
                "Hello world, this is a long string",
                StringUtil.capitalizeString("hello WORLD, THIS IS A long string")
        );
    }

}
