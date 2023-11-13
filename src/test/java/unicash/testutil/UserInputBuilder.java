package unicash.testutil;

import static java.util.Objects.requireNonNull;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;

import unicash.commons.enums.CommandType;
import unicash.model.category.Category;
import unicash.model.transaction.Transaction;


/**
 * A utility class to help with reverse engineering possible user inputs from Transactions
 * for the purposes of emulating user input.
 */
public class UserInputBuilder {

    private static final String WHITESPACE = " ";

    private final Transaction transaction;
    private String userInput;


    /**
     * Creates a {@code UserInputBuilder} object with the given transaction
     */
    public UserInputBuilder(Transaction transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
        userInput = "";

    }

    /**
     * Creates a {@code UserInputBuilder} object with a TransactionBuilder.
     */
    public UserInputBuilder(TransactionBuilder transactionBuilder) {
        this(transactionBuilder.build());

    }

    /**
     * Prefixes the user input with the main command word of a given {@code CommandType}
     */
    public UserInputBuilder addCommand(CommandType command) {
        userInput = command.getMainCommandWord() + WHITESPACE + userInput;

        return this;
    }

    /**
     * Adds the name of the Transaction as a string with the corresponding CLI
     * syntax prefix required.
     */
    public UserInputBuilder addName() {
        userInput = userInput + WHITESPACE + PREFIX_NAME + transaction.getName();

        return this;
    }

    /**
     * Adds the amount of the Transaction as a string with the corresponding CLI
     * syntax prefix required.
     */
    public UserInputBuilder addAmount() {
        userInput = userInput + WHITESPACE + PREFIX_AMOUNT + transaction.getAmountAsDouble();

        return this;
    }

    /**
     * Adds the DateTime of the Transaction as a string with the corresponding CLI
     * syntax prefix required.
     */
    public UserInputBuilder addDateTime() {
        userInput = userInput + WHITESPACE + PREFIX_DATETIME + transaction.getDateTime();

        return this;
    }

    /**
     * Adds the Location of the Transaction as a string with the corresponding CLI
     * syntax prefix required.
     */
    public UserInputBuilder addLocation() {
        userInput = userInput + WHITESPACE + PREFIX_LOCATION + transaction.getLocation();

        return this;
    }

    /**
     * Adds the Type of the Transaction as a string with the corresponding CLI
     * syntax prefix required.
     */
    public UserInputBuilder addType() {
        userInput = userInput + WHITESPACE + PREFIX_TYPE + transaction.getType();

        return this;
    }

    /**
     * Adds all Categories of the Transaction as strings each with the corresponding CLI
     * syntax prefix required.
     */
    public UserInputBuilder addCategories() {
        for (Category category : transaction.getCategories()) {
            userInput = userInput + WHITESPACE + PREFIX_CATEGORY + category.toString();
        }

        return this;
    }

    /**
     * Adds the Type of the Transaction as a string with the corresponding CLI
     * syntax prefix required.
     */
    public UserInputBuilder withAllProperties() {
        return new UserInputBuilder(transaction)
                .addName()
                .addAmount()
                .addType()
                .addLocation()
                .addDateTime()
                .addCategories();
    }

    /**
     * Returns a transaction formatted as the equivalent String user input
     * required to input that transaction.
     *
     * @return User input string representation of the transaction
     */
    @Override
    public String toString() {
        return this.userInput;
    }
}
