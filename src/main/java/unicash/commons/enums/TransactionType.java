package unicash.commons.enums;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * Represents the transaction type for a given transaction.
 */
public enum TransactionType {
    EXPENSE("expense"),
    INCOME("income");

    private final String transactionType;

    /**
     * Constructs a TransactionType enum.
     *
     * @param transactionType the value representing the string value of the enum.
     */
    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Returns the TransactionType object from a given string.
     *
     * @param transactionType a string value of the enum represented by the value provided.
     * @return a TransactionType object of a transaction.
     */
    public static TransactionType parseType(String transactionType) {
        return Arrays.stream(values())
                .filter(type -> type.transactionType.equals(transactionType))
                .findFirst().orElseThrow();
    }

    /**
     * Returns a boolean value if a given string is a valid TransactionType.
     *
     * @param test a string value to be tested.
     * @return a boolean value if the given string is a valid enum.
     */
    public static boolean isValidTransactionType(String test) {
        return Arrays.stream(values())
                .anyMatch(type -> type.transactionType.equals(test));
    }

    /**
     * Returns a list of all transaction types separated by commas.
     */
    public static String listTransactionTypes() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (TransactionType type : values()) {
            stringJoiner.add(type.transactionType);
        }
        return stringJoiner.toString();
    }

    /**
     * Returns the string value of a {@code TransactionType}.
     *
     * @return the string representation of a TransactionType.
     */
    public String getOriginalString() {
        return transactionType;
    }
}
