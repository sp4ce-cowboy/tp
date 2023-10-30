package unicash.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static unicash.testutil.Assert.assertThrows;
import static unicash.testutil.TypicalTransactions.BUYING_GROCERIES;
import static unicash.testutil.TypicalTransactions.INTERN;
import static unicash.testutil.TypicalTransactions.getTypicalUniCash;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import unicash.commons.exceptions.DataLoadingException;
import unicash.model.ReadOnlyUniCash;
import unicash.model.UniCash;

public class JsonUniCashStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonUniCashStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readUniCash_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readUniCash(null));
    }

    private java.util.Optional<ReadOnlyUniCash> readUniCash(String filePath) throws Exception {
        return new JsonUniCashStorage(Paths.get(filePath)).readUniCash(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder) : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readUniCash("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readUniCash("notJsonFormatUniCash.json"));
    }

    @Test
    public void readUniCash_invalidTransactionUniCash_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readUniCash("invalidTransactionUniCash.json"));
    }

    @Test
    public void readUniCash_invalidAndValidTransactionUniCash_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readUniCash("invalidAndValidTransactionUniCash.json"));
    }

    @Test
    public void readAndSaveUniCash_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempUniCash.json");
        UniCash original = getTypicalUniCash();
        JsonUniCashStorage jsonUniCashStorage = new JsonUniCashStorage(filePath);
        original.removeTransaction(INTERN);

        // Save in new file and read back
        jsonUniCashStorage.saveUniCash(original, filePath);
        ReadOnlyUniCash readBack = jsonUniCashStorage.readUniCash(filePath).get();
        assertEquals(original, new UniCash(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTransaction(INTERN);
        original.removeTransaction(BUYING_GROCERIES);
        jsonUniCashStorage.saveUniCash(original, filePath);
        readBack = jsonUniCashStorage.readUniCash(filePath).get();
        assertEquals(original, new UniCash(readBack));

        // Save and read without specifying file path
        original.addTransaction(BUYING_GROCERIES);
        jsonUniCashStorage.saveUniCash(original); // file path not specified
        readBack = jsonUniCashStorage.readUniCash().get(); // file path not specified
        assertEquals(original, new UniCash(readBack));

    }

    @Test
    public void saveUniCash_nullUniCash_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUniCash(null, "SomeFile.json"));
    }

    /**
     * Saves {@code UniCash} at the specified {@code filePath}.
     */
    private void saveUniCash(ReadOnlyUniCash uniCash, String filePath) {
        try {
            new JsonUniCashStorage(Paths.get(filePath)).saveUniCash(uniCash, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveUniCash_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUniCash(new UniCash(), null));
    }
}
