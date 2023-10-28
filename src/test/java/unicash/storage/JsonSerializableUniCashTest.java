package unicash.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static unicash.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import unicash.commons.exceptions.IllegalValueException;
import unicash.commons.util.JsonUtil;
import unicash.model.ReadOnlyUniCash;
import unicash.model.UniCash;
import unicash.model.util.SampleDataUtil;

public class JsonSerializableUniCashTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableUniCashTest");
    private static final Path TYPICAL_TRANSACTIONS_FILE = TEST_DATA_FOLDER.resolve("typicalTransactionUniCash.json");
    private static final Path INVALID_TRANSACTION_FILE = TEST_DATA_FOLDER.resolve("invalidTransactionUniCash.json");
    private static final Path DUPLICATE_TRANSACTION_FILE = TEST_DATA_FOLDER.resolve("duplicateTransactionUniCash.json");

    @Test
    public void toModelType_typicalTransactionsFile_success() throws Exception {
        JsonSerializableUniCash dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRANSACTIONS_FILE,
                JsonSerializableUniCash.class).get();
        UniCash uniCashFromFile = dataFromFile.toModelType();
        ReadOnlyUniCash typicalTransactionsUniCash = SampleDataUtil.getSampleUniCash();
        assertEquals(uniCashFromFile, typicalTransactionsUniCash);
    }

    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableUniCash dataFromFile = JsonUtil.readJsonFile(INVALID_TRANSACTION_FILE,
                JsonSerializableUniCash.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
