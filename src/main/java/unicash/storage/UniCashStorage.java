package unicash.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import unicash.commons.exceptions.DataLoadingException;
import unicash.model.ReadOnlyUniCash;
import unicash.model.UniCash;

/**
 * Represents a storage for {@link UniCash}.
 */
public interface UniCashStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getUniCashFilePath();

    /**
     * Returns UniCash data as a {@link ReadOnlyUniCash}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyUniCash> readUniCash() throws DataLoadingException;

    /**
     * @see #getUniCashFilePath()
     */
    Optional<ReadOnlyUniCash> readUniCash(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyUniCash} to the storage.
     *
     * @param uniCash cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUniCash(ReadOnlyUniCash uniCash) throws IOException;

    /**
     * @see #saveUniCash(ReadOnlyUniCash)
     */
    void saveUniCash(ReadOnlyUniCash uniCash, Path filePath) throws IOException;

}
