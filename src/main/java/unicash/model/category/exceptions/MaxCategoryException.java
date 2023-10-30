package unicash.model.category.exceptions;

/**
 * Represents an error when the maximum number of categories has been added for
 * a transaction.
 */
public class MaxCategoryException extends RuntimeException {
    /**
     * Constructs a MaxCategoryException.
     */
    public MaxCategoryException() {
        super("Maximum number of categories added.");
    }
}
