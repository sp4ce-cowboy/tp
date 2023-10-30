package unicash.model.transaction;

import static java.util.Objects.requireNonNull;
import static unicash.commons.util.AppUtil.checkArgument;

import unicash.commons.enums.TransactionType;

/**
 * Represents a Transaction's type.
 */
public class Type {

    public static final String MESSAGE_CONSTRAINTS =
            String.format("Transaction must be of the following types: %s", TransactionType.listTransactionTypes());

    public final TransactionType type;

    /**
     * Constructs a {@code Type}.
     *
     * @param type A valid type.
     */
    public Type(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_CONSTRAINTS);
        this.type = TransactionType.parseType(type);
    }

    /**
     * Returns true if a given string is a valid type for a transaction.
     */
    public static boolean isValidType(String test) {
        return TransactionType.isValidTransactionType(test);
    }


    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Type)) {
            return false;
        }

        return type.equals(((Type) other).type);
    }

    @Override
    public String toString() {
        return type.getOriginalString();
    }

}
