package unicash.model.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalCategories.EDUCATION;
import static unicash.testutil.TypicalCategories.ENTERTAINMENT;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidCategoryName_throwsIllegalArgumentException() {
        String invalidCategoryName = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategoryName));
    }

    @Test
    public void isValidCategoryName() {
        // null Category name
        assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        // contains non-alphanumeric
        assertFalse(Category.isValidCategory("ca&**"));

        // empty
        assertFalse(Category.isValidCategory(""));

        // longer than 15 characters
        assertFalse(Category.isValidCategory("categorycategorycategory"));

        assertTrue(Category.isValidCategory("entertainment"));
    }

    @Test
    public void equals() {
        Category category = new Category("Valid");

        // same values -> returns true
        assertTrue(category.equals(new Category("Valid")));

        // same object -> returns true
        assertTrue(category.equals(category));

        // null -> returns false
        assertFalse(category.equals(null));

        // different types -> returns false
        assertFalse(category.equals(5.0f));

        // different values -> returns false
        assertFalse(category.equals(new Category("Another")));
    }

    @Test
    public void hashCodeMethod() {
        var entertainmentClone = new Category(ENTERTAINMENT.category);
        assertEquals(ENTERTAINMENT.hashCode(), ENTERTAINMENT.hashCode());
        assertEquals(ENTERTAINMENT.hashCode(), entertainmentClone.hashCode());
        assertNotEquals(ENTERTAINMENT.hashCode(), EDUCATION.hashCode());
    }

    @Test
    public void toStringMethod() {
        assertEquals("entertainment", ENTERTAINMENT.toString());
    }

    @Test
    public void categoryToStringWithPrefix() {
        assertEquals("#entertainment", ENTERTAINMENT.categoryToStringWithPrefix());
    }
}
