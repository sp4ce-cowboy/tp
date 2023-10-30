package unicash.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import unicash.commons.core.LogsCenter;
import unicash.commons.exceptions.DataLoadingException;
import unicash.model.ReadOnlyUniCash;
import unicash.model.ReadOnlyUserPrefs;
import unicash.model.UserPrefs;

/**
 * Manages storage of UniCash data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final UniCashStorage uniCashStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code UniCashStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(UniCashStorage uniCashStorage, UserPrefsStorage userPrefsStorage) {
        this.userPrefsStorage = userPrefsStorage;
        this.uniCashStorage = uniCashStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ UniCash methods ==============================

    @Override
    public Path getUniCashFilePath() {
        return uniCashStorage.getUniCashFilePath();
    }

    @Override
    public Optional<ReadOnlyUniCash> readUniCash() throws DataLoadingException {
        return readUniCash(uniCashStorage.getUniCashFilePath());
    }

    @Override
    public Optional<ReadOnlyUniCash> readUniCash(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return uniCashStorage.readUniCash(filePath);
    }

    @Override
    public void saveUniCash(ReadOnlyUniCash uniCash) throws IOException {
        saveUniCash(uniCash, uniCashStorage.getUniCashFilePath());
    }

    @Override
    public void saveUniCash(ReadOnlyUniCash uniCash, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        uniCashStorage.saveUniCash(uniCash, filePath);
    }

}
