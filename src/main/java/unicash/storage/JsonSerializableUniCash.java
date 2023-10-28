package unicash.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import unicash.commons.exceptions.IllegalValueException;
import unicash.model.ReadOnlyUniCash;
import unicash.model.UniCash;
import unicash.model.transaction.Transaction;

/**
 * An Immutable UniCash that is serializable to JSON format.
 */
@JsonRootName(value = "unicash")
class JsonSerializableUniCash {

    public static final String MESSAGE_DUPLICATE_TRANSACTION = "Transaction list contains duplicate transaction(s).";

    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableUniCash} with the given transactions.
     */
    @JsonCreator
    public JsonSerializableUniCash(
            @JsonProperty("transactions") List<JsonAdaptedTransaction> transactions
    ) {
        this.transactions.addAll(transactions);
    }

    /**
     * Converts a given {@code ReadOnlyUniCash} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUniCash}.
     */
    public JsonSerializableUniCash(ReadOnlyUniCash source) {
        transactions.addAll(
                source.getTransactionList()
                        .stream()
                        .map(JsonAdaptedTransaction::new)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts this transaction list into the model's {@code UniCash} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UniCash toModelType() throws IllegalValueException {
        UniCash uniCash = new UniCash();
        for (var jsonAdaptedTransaction : transactions) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            uniCash.addTransaction(transaction);
        }
        return uniCash;
    }

}
