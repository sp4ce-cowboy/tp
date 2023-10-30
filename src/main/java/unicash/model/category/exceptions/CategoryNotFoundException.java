package unicash.model.category.exceptions;

/**
 * Signals that the operation is unable to find the specified category.
 */
public class CategoryNotFoundException extends RuntimeException {
    /**
     * Constructs a CategoryNotFoundException.
     */
    public CategoryNotFoundException() {
        super("Category not found");
    }
}
