package unicash.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import unicash.commons.exceptions.DataLoadingException;
import unicash.model.ReadOnlyUniCash;
import unicash.model.ReadOnlyUserPrefs;
import unicash.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends UniCashStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getUniCashFilePath();

    @Override
    Optional<ReadOnlyUniCash> readUniCash() throws DataLoadingException;

    @Override
    void saveUniCash(ReadOnlyUniCash uniCash) throws IOException;

}
