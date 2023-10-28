package unicash.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static unicash.logic.commands.CommandTestUtil.VALID_AMOUNT_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_LOCATION_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_TRANSACTION_NAME_NUS;
import static unicash.logic.commands.CommandTestUtil.VALID_TYPE_INCOME;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import unicash.model.category.UniqueCategoryList;
import unicash.model.commons.Amount;
import unicash.model.transaction.DateTime;
import unicash.model.transaction.Location;
import unicash.model.transaction.Name;
import unicash.model.transaction.Transaction;
import unicash.model.transaction.Type;
import unicash.testutil.TransactionBuilder;

public class JsonAdaptedTransactionIntegrationTest {
    private static final Clock clock = Clock.fixed(Instant.parse("2014-12-21T10:15:30.00Z"), ZoneId.of("UTC"));
    @Test
    public void toModelType_emptyDate_returnsTransaction() throws Exception {
        Name name = new Name(VALID_TRANSACTION_NAME_NUS);
        Amount amount = new Amount(VALID_AMOUNT_NUS);
        Type type = new Type(VALID_TYPE_INCOME);
        DateTime emptyDateTime = new DateTime("", clock);
        Location location = new Location(VALID_LOCATION_NUS);
        UniqueCategoryList categoryList = new UniqueCategoryList();
        Transaction transactionWithNoDate = new Transaction(name, type,
                amount, emptyDateTime, location, categoryList);

        var jsonTransaction = new JsonAdaptedTransaction(transactionWithNoDate);
        Transaction transactionFromJson = jsonTransaction.toModelType();
        assertEquals(transactionFromJson, transactionWithNoDate);
    }

    @Test
    public void toModelType_emptyLocation_returnsTransaction() throws Exception {
        Transaction transaction = new TransactionBuilder().withLocation("").build();

        var jsonTransaction = new JsonAdaptedTransaction(transaction);
        Transaction transactionFromJson = jsonTransaction.toModelType();
        assertEquals(transactionFromJson, transaction);
    }

    @Test
    public void toModelType_emptyCategory_returnsTransaction() throws Exception {
        Transaction transaction = new TransactionBuilder().withCategories().build();

        var jsonTransaction = new JsonAdaptedTransaction(transaction);
        Transaction transactionFromJson = jsonTransaction.toModelType();
        assertEquals(transactionFromJson, transaction);
    }
}
