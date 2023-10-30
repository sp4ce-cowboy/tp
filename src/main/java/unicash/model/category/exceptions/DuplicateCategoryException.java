package unicash.model.category.exceptions;

/**
 * Signals that the operation will result in duplicate Categories (Categories are considered duplicates
 * if they have the same identity).
 */
public class DuplicateCategoryException extends RuntimeException {
    /**
     * Creates a new DuplicateTagException
     */
    public DuplicateCategoryException() {
        super("Operation would result in duplicate categories");
    }
}
