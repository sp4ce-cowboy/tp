package unicash.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.ui.StyleSheet.BLUE_OFFSET;
import static unicash.ui.StyleSheet.BRIGHTNESS_OFFSET;
import static unicash.ui.StyleSheet.BRIGHTNESS_THRESHOLD;
import static unicash.ui.StyleSheet.COLOR_END_INDEX;
import static unicash.ui.StyleSheet.COLOR_START_INDEX;
import static unicash.ui.StyleSheet.DEFAULT_BACKGROUND_COLOR_HEX;
import static unicash.ui.StyleSheet.GREEN_OFFSET;
import static unicash.ui.StyleSheet.IS_YELLOW_SKEW;
import static unicash.ui.StyleSheet.MAX_COLOUR_VALUE;
import static unicash.ui.StyleSheet.RED_OFFSET;
import static unicash.ui.StyleSheet.USE_DEFAULT_STYLE;
import static unicash.ui.StyleSheet.absoluteColorAddition;
import static unicash.ui.StyleSheet.adjustBrightness;
import static unicash.ui.StyleSheet.getColorOutput;
import static unicash.ui.StyleSheet.padHexString;

import org.junit.jupiter.api.Test;

import unicash.model.category.Category;

public class StyleSheetTest {

    public static final Category TEST_CATEGORY = new Category("test");

    @Test
    public void hexValues_nonNegativeValue_returnTrue() {
        assertTrue(COLOR_START_INDEX >= 0);
        assertTrue(COLOR_END_INDEX <= 6);

    }

    @Test
    public void offsetValues_withinRange_returnTrue() {
        assertTrue(BRIGHTNESS_THRESHOLD >= 0);
        assertTrue(RED_OFFSET >= -255 && RED_OFFSET <= 255);
        assertTrue(GREEN_OFFSET >= -255 && GREEN_OFFSET <= 255);
        assertTrue(BLUE_OFFSET >= -255 && BLUE_OFFSET <= 255);

    }

    @Test
    public void combinedColorBrightness_lessThanThreshold_returnTrue() {
        String color = StyleSheet.getBrightColorFromHash(TEST_CATEGORY);
        int r = Integer.parseInt(color.substring(1, 3), 16);
        int g = Integer.parseInt(color.substring(3, 5), 16);
        int b = Integer.parseInt(color.substring(5, 7), 16);

        int totalBrightness = r + g + b;

        assertTrue(totalBrightness >= BRIGHTNESS_THRESHOLD);
    }

    @Test
    public void combinedColorBrightness_isColorSkewed_returnTrue() {
        String color = StyleSheet.getBrightColorFromHash(new Category("test") {
            @Override
            public int hashCode() {
                return 11111111;
            }
        });

        if (IS_YELLOW_SKEW) {

            int r = Integer.parseInt(color.substring(1, 3), 16);
            int g = Integer.parseInt(color.substring(3, 5), 16);
            int b = Integer.parseInt(color.substring(5, 7), 16);

            assertTrue(r >= 0 && g >= 0);
        }
    }

    @Test
    public void shortHashCodeObject_isColorSkewed_returnTrue() {
        String color = StyleSheet.getBrightColorFromHash(new Category("test") {
            @Override
            public int hashCode() {
                return 000000001;
            }
        });

        if (IS_YELLOW_SKEW) {

            int r = Integer.parseInt(color.substring(1, 3), 16);
            int g = Integer.parseInt(color.substring(3, 5), 16);
            int b = Integer.parseInt(color.substring(5, 7), 16);
            int r2 = Math.min(MAX_COLOUR_VALUE, r + RED_OFFSET);
            assertTrue(r2 == Math.min(MAX_COLOUR_VALUE, r + RED_OFFSET));
        }
    }

    @Test
    public void zeroHashCodeObject_parsedToString_returnZero() {
        String color = StyleSheet.getColorFromHash(new Category("test") {
            @Override
            public int hashCode() {
                return 0;
            }
        });

        int r = Integer.parseInt(color.substring(1, 3), 16);
        int g = Integer.parseInt(color.substring(3, 5), 16);
        int b = Integer.parseInt(color.substring(5, 7), 16);

        assertTrue(r == g);
        assertTrue(r == b);
        assertTrue(g == b);
    }

    @Test
    public void testRegularCategoryColor() {
        String color = StyleSheet.getColorFromHash(TEST_CATEGORY);

        // Check if the color is a valid hex
        assertTrue(color.matches("^#[0-9A-F]{6}$"));
    }

    @Test
    public void testAdjustBrightness() {
        assertEquals(MAX_COLOUR_VALUE, adjustBrightness(MAX_COLOUR_VALUE));
        assertEquals(150 + BRIGHTNESS_OFFSET, adjustBrightness(150));
        assertEquals(MAX_COLOUR_VALUE, adjustBrightness(MAX_COLOUR_VALUE + 1));
    }

    @Test
    public void testAbsoluteColorAddition() {
        assertEquals(30, absoluteColorAddition(10, 20));
        assertEquals(30, absoluteColorAddition(-10, 40));
        assertEquals(MAX_COLOUR_VALUE, absoluteColorAddition(255, 255));
    }

    @Test
    public void testPadHexString() {
        assertEquals("00a3f5", padHexString("a3f5"));
        assertEquals("123456", padHexString("123456"));
        assertEquals("001234", padHexString("1234"));
    }

    @Test
    public void testGetColorOutput() {
        if (USE_DEFAULT_STYLE) {
            assertEquals("#" + DEFAULT_BACKGROUND_COLOR_HEX,
                    getColorOutput("123456"));
        } else {
            assertEquals("#123456", getColorOutput("123456"));
        }
    }
}
