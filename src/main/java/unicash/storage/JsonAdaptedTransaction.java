package unicash.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import unicash.commons.exceptions.IllegalValueException;
import unicash.model.category.Category;
import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.Type;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
public class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String name;
    private final double amount;
    private final String dateTime;
    private final String location;
    private final String type;
    private final List<JsonAdaptedCategory> categories = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(
            @JsonProperty("name") String name,
            @JsonProperty("amount") double amount,
            @JsonProperty("dateTime") String dateTime,
            @JsonProperty("location") String location,
            @JsonProperty("type") String type,
            @JsonProperty("categories") List<JsonAdaptedCategory> categories
    ) {
        this.name = name;
        this.amount = amount;
        this.dateTime = dateTime;
        this.location = location;
        this.type = type;
        if (categories != null) {
            this.categories.addAll(categories);
        }
    }

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    public JsonAdaptedTransaction(Transaction source) {
        name = source.getName().fullName;
        amount = source.getAmount().amount;
        dateTime = source.getDateTime().inputString();
        location = source.getLocation().location;
        type = source.getType().type.getOriginalString();
        categories.addAll(source.getCategories().asUnmodifiableObservableList()
                .stream()
                .map(JsonAdaptedCategory::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted transaction object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted format.
     */
    public Transaction toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(formatMissingFieldMessage(Name.class));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (dateTime == null) {
            throw new IllegalValueException(formatMissingFieldMessage(DateTime.class));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);

        if (location != null && !Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        if (type == null) {
            throw new IllegalValueException(formatMissingFieldMessage(Type.class));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);

        final List<Category> transactionCategories = new ArrayList<>();
        for (JsonAdaptedCategory category : categories) {
            transactionCategories.add(category.toModelType());
        }
        final UniqueCategoryList modelCategories = new UniqueCategoryList(transactionCategories);
        return new Transaction(modelName, modelType, modelAmount, modelDateTime, modelLocation, modelCategories);
    }

    /**
     * Pretty formats missing field message with the class name.
     */
    private <T> String formatMissingFieldMessage(Class<T> fieldName) {
        return String.format(MISSING_FIELD_MESSAGE_FORMAT, fieldName.getSimpleName());
    }
}
