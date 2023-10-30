package unicash.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import unicash.commons.core.GuiSettings;
import unicash.model.UniCash;
import unicash.model.UserPrefs;
import unicash.testutil.TypicalTransactions;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        var uniCashStorage = new JsonUniCashStorage(getTempFilePath("ab"));
        var userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(uniCashStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void uniCashReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUniCashStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUniCashStorageTest} class.
         */
        var original = TypicalTransactions.getTypicalUniCash();
        storageManager.saveUniCash(original);
        var retrieved = storageManager.readUniCash().get();
        Assertions.assertEquals(original, new UniCash(retrieved));
    }

    @Test
    public void getUniCashFilePath() {
        assertNotNull(storageManager.getUniCashFilePath());
    }

}
