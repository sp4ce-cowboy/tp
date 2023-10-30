package unicash.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static unicash.storage.JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalTransactions.SHOPPING;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import unicash.commons.exceptions.IllegalValueException;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.Type;
import unicash.testutil.TransactionBuilder;

public class JsonAdaptedTransactionTest {
    private static final String INVALID_NAME = "R@chel[]";
    private static final double INVALID_AMOUNT = -10;
    private static final String INVALID_CATEGORY = "@@@";
    private static final String INVALID_DATETIME = "hi";
    private static final String INVALID_LOCATION = "[]@@@@";
    private static final String INVALID_TYPE = "others";

    private static final String VALID_NAME = SHOPPING.getName().toString();
    private static final double VALID_AMOUNT = SHOPPING.getAmount().amount;
    private static final String VALID_DATETIME = SHOPPING.getDateTime().inputString();
    private static final String VALID_LOCATION = SHOPPING.getLocation().location;
    private static final String VALID_TYPE = SHOPPING.getType().toString();
    private static final List<JsonAdaptedCategory> VALID_CATEGORIES = SHOPPING.getCategories()
            .asUnmodifiableObservableList()
            .stream()
            .map(JsonAdaptedCategory::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsTransaction() throws Exception {
        var transaction = new JsonAdaptedTransaction(SHOPPING);
        assertEquals(SHOPPING, transaction.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        var transaction = new JsonAdaptedTransaction(
                INVALID_NAME,
                VALID_AMOUNT,
                VALID_DATETIME,
                VALID_LOCATION,
                VALID_TYPE,
                VALID_CATEGORIES
        );
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        var transaction = new JsonAdaptedTransaction(
                null,
                VALID_AMOUNT,
                VALID_DATETIME,
                VALID_LOCATION,
                VALID_TYPE,
                VALID_CATEGORIES

        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        var transaction = new JsonAdaptedTransaction(
                VALID_NAME,
                INVALID_AMOUNT,
                VALID_DATETIME,
                VALID_LOCATION,
                VALID_TYPE,
                VALID_CATEGORIES
        );
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidCategories_throwsIllegalValueException() {
        List<JsonAdaptedCategory> invalidCategories = new ArrayList<>(VALID_CATEGORIES);
        invalidCategories.add(new JsonAdaptedCategory(INVALID_CATEGORY));
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(
                        VALID_NAME,
                        INVALID_AMOUNT,
                        VALID_DATETIME,
                        VALID_LOCATION,
                        VALID_TYPE,
                        invalidCategories);
        assertThrows(IllegalValueException.class, transaction::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        var transaction = new JsonAdaptedTransaction(
                VALID_NAME,
                VALID_AMOUNT,
                VALID_DATETIME,
                VALID_LOCATION,
                VALID_TYPE,
                null
        );
        Transaction transaction2 = new TransactionBuilder()
                .withName(VALID_NAME)
                .withAmount(VALID_AMOUNT)
                .withDateTime(VALID_DATETIME)
                .withLocation(VALID_LOCATION)
                .withType(VALID_TYPE)
                .withCategories()
                .build();
        try {
            assertEquals(transaction.toModelType(), transaction2);
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        var transaction = new JsonAdaptedTransaction(
                VALID_NAME,
                VALID_AMOUNT,
                INVALID_DATETIME,
                VALID_LOCATION,
                VALID_TYPE,
                VALID_CATEGORIES
        );
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        var transaction = new JsonAdaptedTransaction(
                VALID_NAME,
                VALID_AMOUNT,
                null,
                VALID_LOCATION,
                VALID_TYPE,
                VALID_CATEGORIES
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        var transaction = new JsonAdaptedTransaction(
                VALID_NAME,
                VALID_AMOUNT,
                VALID_DATETIME,
                INVALID_LOCATION,
                VALID_TYPE,
                VALID_CATEGORIES
        );
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        var transaction = new JsonAdaptedTransaction(
                VALID_NAME,
                VALID_AMOUNT,
                VALID_DATETIME,
                VALID_LOCATION,
                INVALID_TYPE,
                VALID_CATEGORIES
        );
        String expectedMessage = Type.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        var transaction = new JsonAdaptedTransaction(
                VALID_NAME,
                VALID_AMOUNT,
                VALID_DATETIME,
                VALID_LOCATION,
                null,
                VALID_CATEGORIES
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }
}
