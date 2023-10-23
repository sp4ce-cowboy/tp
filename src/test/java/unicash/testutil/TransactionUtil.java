package unicash.testutil;

import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;

import unicash.logic.commands.AddTransactionCommand;
import unicash.logic.commands.EditTransactionCommand;
import unicash.model.category.Category;
import unicash.model.transaction.Transaction;


/**
 * A utility class for Transaction.
 */
public class TransactionUtil {

    /**
     * Returns an add command string for adding the {@code transaction}.
     */
    public static String getAddTransactionCommand(Transaction transaction) {
        return AddTransactionCommand.COMMAND_WORD + " " + getTransactionDetails(transaction);
    }

    /**
     * Returns the part of command string for the given {@code transaction}'s details.
     */
    public static String getTransactionDetails(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(transaction.getName().fullName).append(" ");
        sb.append(PREFIX_TYPE).append(transaction.getType().type.getOriginalString()).append(" ");
        sb.append(PREFIX_AMOUNT).append(transaction.getAmount().amountString()).append(" ");
        sb.append(PREFIX_DATETIME).append(transaction.getDateTime().originalString()).append(" ");
        sb.append(PREFIX_LOCATION).append(transaction.getLocation().location).append(" ");
        transaction.getCategories().forEach(
                category -> sb.append(PREFIX_CATEGORY).append(category.category).append(" ")
        );
        return sb.toString();
    }

    public static String getEditTransactionDescriptorDetails(EditTransactionCommand
                                                                     .EditTransactionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName)
                .append(" "));
        descriptor.getType().ifPresent(type -> sb.append(PREFIX_TYPE).append(type.type.getOriginalString())
                .append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount.amount)
                .append(" "));
        descriptor.getLocation().ifPresent(location -> sb.append(PREFIX_LOCATION).append(location)
                .append(" "));
        descriptor.getDateTime().ifPresent(dateTime -> sb.append(PREFIX_DATETIME).append(dateTime.originalString())
                .append(" "));
        if (descriptor.getCategories().isPresent()) {
            List<Category> categories = descriptor.getCategories().get().asUnmodifiableObservableList();
            if (categories.isEmpty()) {
                sb.append(PREFIX_CATEGORY);
            } else {
                categories.forEach(s -> sb.append(PREFIX_CATEGORY).append(s.category).append(" "));
            }
        }
        return sb.toString();
    }
}
