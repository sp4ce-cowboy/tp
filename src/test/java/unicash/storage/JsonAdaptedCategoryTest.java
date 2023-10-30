package unicash.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static unicash.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import unicash.commons.exceptions.IllegalValueException;
import unicash.model.category.Category;

public class JsonAdaptedCategoryTest {
    private static final String INVALID_CATEGORY = "@@@";
    private static final String VALID_CATEGORY = "Food";

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        var adaptedCategory = new JsonAdaptedCategory(INVALID_CATEGORY);
        assertThrows(IllegalValueException.class, adaptedCategory::toModelType);
    }

    @Test
    public void toModelType_validCategory_returnsCategory() throws IllegalValueException {
        var adaptedCategory = new JsonAdaptedCategory(VALID_CATEGORY);
        var expected = new Category(VALID_CATEGORY);
        assertEquals(expected, adaptedCategory.toModelType());
    }
}
