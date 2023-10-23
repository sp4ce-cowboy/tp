package unicash.ui;

import unicash.model.category.Category;

/**
 * A class to consolidate and standardize all text styling, text formatting
 * and coloring involved in FXML and associated Controller classes.
 */
public class StyleSheet {

    // This flag will set all colors to a default value
    public static final boolean USE_DEFAULT_STYLE = false;
    public static final String DEFAULT_BACKGROUND_COLOR_HEX = "FFD43E";

    /* Text colour filling */
    public static final String TEXT_FILL_RED = "-fx-text-fill: red";
    public static final String TEXT_FILL_GREEN = "-fx-text-fill: green";
    public static final String TEXT_FILL_BLACK = "-fx-text-fill: black";
    public static final String TEXT_FILL_WHITE = "-fx-text-fill: white";

    /* Font styling */
    public static final String FONT_STYLE_BOLD = "-fx-font-weight: bold";
    public static final String FONT_STYLE_REGULAR = "-fx-font-weight: normal";
    public static final String FONT_STYLE_ITALIC = "-fx-font-style: italic";

    /* Custom text formatting */
    public static final String TRANSACTION_ID_SEPARATOR = ". ";
    public static final String HEX_STRING_PADDING_SPECIFIER = "%6s";
    public static final String HEX_COLOR_STRING_SPECIFIER = "%02X%02X%02X";

    /* Background styling and formatting */
    public static final String TEXT_BACKGROUND_COLOR_SPECIFIER = "-fx-background-color: %s";

    /* Color utilities */
    public static final int MAX_COLOUR_VALUE = 255;
    public static final int MEDIAN_COLOR_VALUE = 128;
    public static final int HEXADECIMAL_RADIX = 16;
    public static final String HEXADECIMAL_RADIX_SYMBOL = "#";
    public static final int COLOR_START_INDEX = 0;
    public static final int COLOR_END_INDEX = 6;

    /* Color variables and offsets to be modified according to preference */
    public static final int BRIGHTNESS_THRESHOLD = 130; // Higher threshold = brighter value, 130 - 140 ideal
    public static final int BRIGHTNESS_OFFSET = 60; // Default is 64
    public static final int RED_OFFSET = 90;
    public static final int GREEN_OFFSET = 90;
    public static final int BLUE_OFFSET = 0;
    public static final boolean IS_YELLOW_SKEW = true;

    /**
     * Returns a 6-digit hexadecimal number based on the Category's unique
     * hash code, to be used as a color indicator, without any color offsetting.
     */
    public static String getColorFromHash(Category category) {

        // Absolute value of hash code taken to guard against negative hash values
        String hexString = Integer.toHexString(Math.abs(category.hashCode()));

        // Padding for hash codes that are less than 6 digits long
        hexString = padHexString(hexString);

        // Hex index refers to the specific substring of the hash code to be used.
        // This can be altered according to specific hashing patterns.
        String categoryColorString = hexString.substring(COLOR_START_INDEX,
                COLOR_END_INDEX).toUpperCase();

        return getColorOutput(categoryColorString);
    }

    /**
     * Returns a 6-digit hexadecimal number based on an Object's unique
     * hash code, to be used as a color indicator. If the hex color value
     * generated from the hashcode accords poor {@code Label} visibility,
     * the value is offset such that is always of a certain brightness value.
     */
    public static String getBrightColorFromHash(Category category) {

        // Absolute value of hash code taken to guard against negative values
        String hexString = Integer.toHexString(Math.abs(category.hashCode()));

        // Padding for hash codes that are less than 6 digits long
        hexString = padHexString(hexString);

        // Hex index refers to the specific substring of the hash code to be used.
        // This can be altered according to specific hashing patterns.
        String objectColorString = hexString.substring(COLOR_START_INDEX,
                COLOR_END_INDEX).toUpperCase();

        int r = Integer.parseInt(objectColorString.substring(0, 2), HEXADECIMAL_RADIX);
        int g = Integer.parseInt(objectColorString.substring(2, 4), HEXADECIMAL_RADIX);
        int b = Integer.parseInt(objectColorString.substring(4, 6), HEXADECIMAL_RADIX);

        assert(r >= 0 && g >= 0 && b >= 0);
        int avg = (r + g + b) / 3;

        if (avg < BRIGHTNESS_THRESHOLD) {
            r = adjustBrightness(r);
            g = adjustBrightness(g);
            b = adjustBrightness(b);
        }

        // Skew hex code to favour more warm colors.
        if (IS_YELLOW_SKEW) {
            r = Math.min(MAX_COLOUR_VALUE, absoluteColorAddition(r, RED_OFFSET));
            g = Math.min(MAX_COLOUR_VALUE, absoluteColorAddition(g, GREEN_OFFSET));
            b = Math.min(absoluteColorAddition(b, BLUE_OFFSET), r + g / 4);
        }

        objectColorString = String.format(HEX_COLOR_STRING_SPECIFIER, r, g, b);

        return getColorOutput(objectColorString);
    }

    /**
     * Returns the input colour value adjusted by {@code BRIGHTNESS_OFFSET}
     * or the {@code MAX_COlOUR_VALUE} if the adjusted value exceeds the
     * {@code MAX_COlOUR_VALUE}.
     *
     * @param value the input color value to be adjusted
     * @return
     */
    public static int adjustBrightness(int value) {
        return Math.min(absoluteColorAddition(value, BRIGHTNESS_OFFSET),
                MAX_COLOUR_VALUE);
    }


    /**
     * Helper guard function that returns the absolute value of the sum of 2
     * integers, but returns the max color value if the max color threshold is
     * exceeded.
     *
     * @param a the first integer to be added
     * @param b the second integer to be added
     * @return int the absolute value of the sum of both integers
     */
    public static int absoluteColorAddition(int a, int b) {
        return Math.abs(a + b) > MAX_COLOUR_VALUE
                ? MAX_COLOUR_VALUE
                : Math.abs(a + b);
    }

    /**
     * Helper function to pad hash converted strings that are smaller than
     * {@code COLOR_END_INDEX} with leading zeros such that they align
     * syntactically with JavaFX hexadecimal color specifications. Guards
     * against hash codes that are smaller than 6 digits.
     *
     * @param hexString the input String
     * @return the padded String
     */
    public static String padHexString(String hexString) {
        return hexString.length() < COLOR_END_INDEX
                ? String.format(HEX_STRING_PADDING_SPECIFIER, hexString).replace(' ', '0')
                : hexString;

    }

    /**
     * Helper function that returns the hex color string if the default
     * flag is false, but returns {@code DEFAULT_BACKGROUND_COLOR_HEX}
     * otherwise.
     *
     * @param colorString the input 6-digit hexadecimal color string
     * @return the hexadecimal color string prefixed with the "#" symbol
     */
    public static String getColorOutput(String colorString) {
        return USE_DEFAULT_STYLE
                ? HEXADECIMAL_RADIX_SYMBOL + DEFAULT_BACKGROUND_COLOR_HEX
                : HEXADECIMAL_RADIX_SYMBOL + colorString;

    }

}
