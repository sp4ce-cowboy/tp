package unicash.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import unicash.commons.exceptions.IllegalValueException;
import unicash.model.category.Category;

/**
 * Jackson-friendly version of {@link Category}.
 */
class JsonAdaptedCategory {

    private final String category;

    /**
     * Constructs a {@code JsonAdaptedCategory} with the given {@code categoryName}.
     */
    @JsonCreator
    public JsonAdaptedCategory(String category) {
        this.category = category;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        category = source.category;
    }

    @JsonValue
    public String getCategoryName() {
        return category;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Category} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted category.
     */
    public Category toModelType() throws IllegalValueException {
        if (!Category.isValidCategory(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(category);
    }

}
