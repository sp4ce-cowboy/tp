package unicash.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import unicash.logic.commands.AddTransactionCommand;
import unicash.logic.commands.ClearTransactionsCommand;
import unicash.logic.commands.DeleteTransactionCommand;
import unicash.logic.commands.EditTransactionCommand;
import unicash.logic.commands.ExitCommand;
import unicash.logic.commands.FindCommand;
import unicash.logic.commands.GetTotalExpenditureCommand;
import unicash.logic.commands.HelpCommand;
import unicash.logic.commands.ListCommand;
import unicash.logic.commands.ResetCommand;
import unicash.logic.parser.Prefix;
import unicash.model.commons.Amount;
import unicash.model.transaction.Transaction;

/**
 * Container for user visible messages.
 */
public class UniCashMessages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n\n%1$s";
    public static final String MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX =
            "The transaction index provided is invalid";
    public static final String MESSAGE_TRANSACTIONS_LISTED_OVERVIEW = "%1$d transactions listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_MONTH = "Month must be between 1 and 12 (inclusive).";

    public static final String MESSAGE_UNICASH_WELCOME = "Welcome to UniCa$h!"
            + "\n\n"
            + "Available Commands:"
            + "\n"
            + AddTransactionCommand.COMMAND_WORD
            + "\n"
            + DeleteTransactionCommand.COMMAND_WORD
            + "\n"
            + EditTransactionCommand.COMMAND_WORD
            + "\n"
            + ListCommand.COMMAND_WORD
            + "\n"
            + FindCommand.COMMAND_WORD
            + "\n"
            + GetTotalExpenditureCommand.COMMAND_WORD
            + "\n\n"
            + ClearTransactionsCommand.COMMAND_WORD
            + "\n"
            + ResetCommand.COMMAND_WORD
            + "\n\n"
            + HelpCommand.COMMAND_WORD
            + "\n"
            + ExitCommand.COMMAND_WORD;


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code transaction} for display to the user with line breaks.
     */
    public static String formatTransaction(Transaction transaction) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(transaction.getName())
                .append("; \nType: ")
                .append(transaction.getType())
                .append("; \nAmount: $")
                .append(Amount.amountToDecimalString(transaction.getAmount()))
                .append("; \nDate: ")
                .append(transaction.getDateTime())
                .append("; \nLocation: ")
                .append(transaction.getLocation())
                .append("; \nCategories: ");

        transaction.getCategories().forEach(x -> {
            builder.append(x.categoryToStringWithPrefix());
            builder.append(" ");
        });

        return builder.toString();
    }

    /**
     * Formats the {@code transaction} for output as a continuous string.
     */
    public static String formatTransactionAsString(Transaction transaction) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(transaction.getName())
                .append("; Type: ")
                .append(transaction.getType())
                .append("; Amount: ")
                .append(transaction.getAmount())
                .append("; Date: ")
                .append(transaction.getDateTime())
                .append("; Location: ")
                .append(transaction.getLocation())
                .append("; Category: ");
        transaction.getCategories().forEach(builder::append);
        return builder.toString();
    }
}
