package unicash.logic.commands;

import static java.util.Objects.requireNonNull;
import static unicash.logic.UniCashMessages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
import static unicash.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static unicash.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static unicash.logic.parser.CliSyntax.PREFIX_DATETIME;
import static unicash.logic.parser.CliSyntax.PREFIX_LOCATION;
import static unicash.logic.parser.CliSyntax.PREFIX_NAME;
import static unicash.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import unicash.commons.core.index.Index;
import unicash.commons.util.CollectionUtil;
import unicash.commons.util.CommandUsage;
import unicash.commons.util.ExampleGenerator;
import unicash.commons.util.ToStringBuilder;
import unicash.logic.UniCashMessages;
import unicash.logic.commands.exceptions.CommandException;
import unicash.model.Model;
import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.Type;

/**
 * Edits the details of an existing transaction in the transactions list.
 */
public class EditTransactionCommand extends Command {

    public static final String COMMAND_WORD = "edit_transaction";
    public static final String MESSAGE_USAGE = new CommandUsage.Builder()
            .setCommandWord(COMMAND_WORD)
            .setDescription(
                    "Edits the details of the transaction identified by the "
                            + "index number used in the displayed transaction list."
            )
            .setArgument("Index (must be a positive integer)")
            .addParameter(PREFIX_NAME, "Name", true, false)
            .addParameter(PREFIX_TYPE, "Type", true, false)
            .addParameter(PREFIX_AMOUNT, "Amount", true, false)
            .addParameter(PREFIX_DATETIME, "DateTime", true, false)
            .addParameter(PREFIX_LOCATION, "Location", true, false)
            .addParameter(PREFIX_CATEGORY, "Category", true, true)
            .setExample(
                    ExampleGenerator.generate(
                            COMMAND_WORD,
                            "1",
                            PREFIX_NAME,
                            PREFIX_TYPE,
                            PREFIX_AMOUNT,
                            PREFIX_DATETIME,
                            PREFIX_LOCATION,
                            PREFIX_CATEGORY
                    )
            )
            .build()
            .toString();


    public static final String MESSAGE_EDIT_TRANSACTION_SUCCESS = "Edited Transaction: \n\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditTransactionDescriptor editTransactionDescriptor;

    /**
     * @param index                     of the transaction in the filtered transaction list to edit
     * @param editTransactionDescriptor details to edit the transaction with
     */
    public EditTransactionCommand(
            Index index,
            EditTransactionDescriptor editTransactionDescriptor
    ) {
        requireNonNull(index);
        requireNonNull(editTransactionDescriptor);

        this.index = index;
        this.editTransactionDescriptor = new EditTransactionDescriptor(editTransactionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToEdit = lastShownList.get(index.getZeroBased());
        Transaction editedTransaction = createEditedTransaction(transactionToEdit, editTransactionDescriptor);

        model.setTransaction(transactionToEdit, editedTransaction);
        return new CommandResult(String.format(MESSAGE_EDIT_TRANSACTION_SUCCESS,
                UniCashMessages.formatTransaction(editedTransaction)));
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code editTransactionDescriptor}.
     */
    private static Transaction createEditedTransaction(
            Transaction transactionToEdit,
            EditTransactionDescriptor editTransactionDescriptor
    ) {
        assert transactionToEdit != null;

        Name updatedName = editTransactionDescriptor.getName().orElse(transactionToEdit.getName());
        Amount updatedAmount = editTransactionDescriptor.getAmount().orElse(transactionToEdit.getAmount());
        DateTime updatedDateTime = editTransactionDescriptor.getDateTime().orElse(transactionToEdit.getDateTime());
        Location updatedLocation = editTransactionDescriptor.getLocation().orElse(transactionToEdit.getLocation());
        Type updatedType = editTransactionDescriptor.getType().orElse(transactionToEdit.getType());
        UniqueCategoryList updatedCategories = editTransactionDescriptor.getCategories()
                .orElse(transactionToEdit.getCategories());

        return new Transaction(updatedName, updatedType, updatedAmount, updatedDateTime,
                updatedLocation, updatedCategories);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTransactionCommand)) {
            return false;
        }

        EditTransactionCommand otherEditTransactionCommand = (EditTransactionCommand) other;
        return index.equals(otherEditTransactionCommand.index)
                && editTransactionDescriptor.equals(otherEditTransactionCommand.editTransactionDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editTransactionDescriptor", editTransactionDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class EditTransactionDescriptor {
        private Name name;
        private Amount amount;
        private DateTime datetime;
        private Location location;
        private Type type;
        private UniqueCategoryList categories;

        public EditTransactionDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditTransactionDescriptor(EditTransactionDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setDateTime(toCopy.datetime);
            setLocation(toCopy.location);
            setType(toCopy.type);
            setCategories(toCopy.categories);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount, datetime, location, type, categories);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDateTime(DateTime datetime) {
            this.datetime = datetime;
        }

        public Optional<DateTime> getDateTime() {
            return Optional.ofNullable(datetime);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Optional<Type> getType() {
            return Optional.ofNullable(type);
        }

        /**
         * Sets {@code categories} to this object's {@code categories}.
         * A defensive copy of {@code categories} is used internally.
         */
        public void setCategories(UniqueCategoryList categories) {
            this.categories = categories;
        }

        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code categories} is null.
         */
        public Optional<UniqueCategoryList> getCategories() {
            return Optional.ofNullable(categories);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTransactionDescriptor)) {
                return false;
            }

            var otherEditTransactionDescriptor = (EditTransactionDescriptor) other;
            return Objects.equals(name, otherEditTransactionDescriptor.name)
                    && Objects.equals(amount, otherEditTransactionDescriptor.amount)
                    && Objects.equals(datetime, otherEditTransactionDescriptor.datetime)
                    && Objects.equals(location, otherEditTransactionDescriptor.location)
                    && Objects.equals(type, otherEditTransactionDescriptor.type)
                    && Objects.equals(categories, otherEditTransactionDescriptor.categories);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("amount", amount)
                    .add("datetime", datetime)
                    .add("location", location)
                    .add("type", type)
                    .add("categories", categories)
                    .toString();
        }
    }

}
